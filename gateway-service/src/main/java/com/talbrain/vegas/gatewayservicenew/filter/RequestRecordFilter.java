package com.talbrain.vegas.gatewayservicenew.filter;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBufAllocator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
//@Component
public class RequestRecordFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI requestUri = request.getURI();
        //只记录 http 请求(包含 https)
        String schema = requestUri.getScheme();
        if ((!"http".equals(schema) && !"https".equals(schema))){
            return chain.filter(exchange);
        }
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setPath(requestUri.getPath());
        accessRecord.setQueryString(request.getQueryParams());
        exchange.getAttributes().put("startTime", System.currentTimeMillis());

        String method = request.getMethodValue();
        String contentType = request.getHeaders().getFirst("Content-Type");
        //此处要排除流文件类型,比如上传的文件
        if ("POST".equals(method) && !contentType.startsWith("multipart/form-data")){
            String bodyStr = resolveBodyFromRequest(request);

            //下面将请求体再次封装写回到 request 里,传到下一级.
            URI ex = UriComponentsBuilder.fromUri(requestUri).build(true).toUri();
            ServerHttpRequest newRequest = request.mutate().uri(ex).build();
            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
            newRequest = new ServerHttpRequestDecorator(newRequest) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            accessRecord.setBody(formatStr(bodyStr));
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
            return returnMono(chain, newExchange, accessRecord);
        } else {
            return returnMono(chain, exchange, accessRecord);
        }
    }

    private Mono<Void> returnMono(GatewayFilterChain chain,ServerWebExchange exchange, AccessRecord accessRecord){
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            Long startTime = exchange.getAttribute("startTime");
            if (startTime != null){
                long executeTime = (System.currentTimeMillis() - startTime);
                accessRecord.setExpendTime(executeTime);
                accessRecord.setHttpCode(Objects.requireNonNull(exchange.getResponse().getStatusCode()).value());
                writeAccessLog(JSON.toJSONString(accessRecord) + "\r\n");
            }
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 获取请求体中的字符串内容
     * @param serverHttpRequest
     * @return
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest){
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();

        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();

    }

    /**
     * 去掉空格,换行和制表符
     * @param str
     * @return
     */
    private String formatStr(String str){
        if (str != null && str.length() > 0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
        return str;
    }

    private DataBuffer stringBuffer(String value){
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 访问记录对象
     */
    @Data
    private class AccessRecord{
        private String path;
        private String body;
        private MultiValueMap<String,String> queryString;
        private long expendTime;
        private int httpCode;
    }

    private void writeAccessLog(String str){
        File file = new File("access.log");
        if (!file.exists()){
            try {
                if (file.createNewFile()){
                    file.setWritable(true);
                }
            } catch (IOException e) {
                log.error("创建访问日志文件失败.{}",e.getMessage(),e);
            }
        }

        try(FileWriter fileWriter = new FileWriter(file.getName(),true)){
            fileWriter.write(str);
        } catch (IOException e) {
            log.error("写访问日志到文件失败. {}", e.getMessage(),e);
        }

    }
}