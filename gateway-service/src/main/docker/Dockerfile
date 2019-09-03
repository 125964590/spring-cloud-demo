FROM openjdk:8-jre-alpine

MAINTAINER jbzm

RUN echo "http://mirrors.aliyun.com/alpine/v3.8/main" > /etc/apk/repositories \
    && echo "http://mirrors.aliyun.com/alpine/v3.8/community" >> /etc/apk/repositories \
    && apk update upgrade \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

COPY ${artifactId}-${version}.jar /tmp

CMD /usr/bin/java ${JAVA_OPTS} -jar /tmp/${artifactId}-${version}.jar
