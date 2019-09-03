package com.talbrain.vegas.gatewayservicenew.tool.handler;

import com.talbrain.vegas.domain.Result;
import com.talbrain.vegas.domain.SystemResponseStatus;
import com.talbrain.vegas.domain.exception.BizException;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jbzm
 * @date 2019-06-14 16:40
 */
@Slf4j
public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {
  /**
   * Create a new {@code DefaultErrorWebExceptionHandler} instance.
   *
   * @param errorAttributes the error attributes
   * @param resourceProperties the resources configuration properties
   * @param errorProperties the error configuration properties
   * @param applicationContext the current application context
   */
  public CustomErrorWebExceptionHandler(
      ErrorAttributes errorAttributes,
      ResourceProperties resourceProperties,
      ErrorProperties errorProperties,
      ApplicationContext applicationContext) {
    super(errorAttributes, resourceProperties, errorProperties, applicationContext);
  }

  @Override
  protected Map<String, Object> getErrorAttributes(
      ServerRequest request, boolean includeStackTrace) {
    Map<String, Object> errorAttributes = new LinkedHashMap<>();
    Throwable error = getError(request);
    errorAttributes.put(Result.CODE, determineCode(error));
    errorAttributes.put(Result.MESSAGE, determineMessage(error));
    handleException(errorAttributes, error, includeStackTrace);
    return errorAttributes;
  }

  @Override
  @SuppressWarnings("NullableProblems")
  protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
    return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
  }

  @Override
  protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
    if (errorAttributes.get(Result.CODE).equals(SystemResponseStatus.SERVICE_EXCEPTION)) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.OK;
  }

  private String determineMessage(Throwable error) {
    if (error instanceof BizException) {
      String message = null;
      try {
        log.error(error.getMessage());
        message =
            new String(
                ((BizException) error).getErrorResult().getMessage().getBytes(),
                CharsetUtil.UTF_8.name());
      } catch (UnsupportedEncodingException e) {
        log.error(ExceptionUtils.getFullStackTrace(e));
      }
      log.error(message);
      return message;
    }
    return error.getMessage();
  }

  private void handleException(
      Map<String, Object> errorAttributes, Throwable error, boolean includeStackTrace) {
    if (includeStackTrace) {
      errorAttributes.put(Result.RESULT, ExceptionUtils.getFullStackTrace(error));
    }
  }

  private int determineCode(Throwable error) {
    if (error instanceof BizException) {
      return ((BizException) error).getErrorResult().getCode();
    }
    return SystemResponseStatus.SERVICE_EXCEPTION;
  }
}
