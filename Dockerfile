FROM maven:3.8.8-eclipse-temurin-8 AS build

WORKDIR /workspace
ARG MODULE=anynote-gateway

COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY anynote-common/pom.xml anynote-common/pom.xml
COPY anynote-common/anynote-common-ai-fastapi/pom.xml anynote-common/anynote-common-ai-fastapi/pom.xml
COPY anynote-common/anynote-common-canal/pom.xml anynote-common/anynote-common-canal/pom.xml
COPY anynote-common/anynote-common-core/pom.xml anynote-common/anynote-common-core/pom.xml
COPY anynote-common/anynote-common-datascope/pom.xml anynote-common/anynote-common-datascope/pom.xml
COPY anynote-common/anynote-common-elasticsearch/pom.xml anynote-common/anynote-common-elasticsearch/pom.xml
COPY anynote-common/anynote-common-green/pom.xml anynote-common/anynote-common-green/pom.xml
COPY anynote-common/anynote-common-redis/pom.xml anynote-common/anynote-common-redis/pom.xml
COPY anynote-common/anynote-common-rocketmq/pom.xml anynote-common/anynote-common-rocketmq/pom.xml
COPY anynote-common/anynote-common-security/pom.xml anynote-common/anynote-common-security/pom.xml
COPY anynote-common/anynote-common-swagger/pom.xml anynote-common/anynote-common-swagger/pom.xml
COPY anynote-api/pom.xml anynote-api/pom.xml
COPY anynote-api/anynote-api-ai/pom.xml anynote-api/anynote-api-ai/pom.xml
COPY anynote-api/anynote-api-file/pom.xml anynote-api/anynote-api-file/pom.xml
COPY anynote-api/anynote-api-note/pom.xml anynote-api/anynote-api-note/pom.xml
COPY anynote-api/anynote-api-notify/pom.xml anynote-api/anynote-api-notify/pom.xml
COPY anynote-api/anynote-api-system/pom.xml anynote-api/anynote-api-system/pom.xml
COPY anynote-modules/pom.xml anynote-modules/pom.xml
COPY anynote-modules/anynote-modules-ai/pom.xml anynote-modules/anynote-modules-ai/pom.xml
COPY anynote-modules/anynote-modules-ai-nio/pom.xml anynote-modules/anynote-modules-ai-nio/pom.xml
COPY anynote-modules/anynote-modules-external-api/pom.xml anynote-modules/anynote-modules-external-api/pom.xml
COPY anynote-modules/anynote-modules-file/pom.xml anynote-modules/anynote-modules-file/pom.xml
COPY anynote-modules/anynote-modules-job/pom.xml anynote-modules/anynote-modules-job/pom.xml
COPY anynote-modules/anynote-modules-manage/pom.xml anynote-modules/anynote-modules-manage/pom.xml
COPY anynote-modules/anynote-modules-note/pom.xml anynote-modules/anynote-modules-note/pom.xml
COPY anynote-modules/anynote-modules-notify/pom.xml anynote-modules/anynote-modules-notify/pom.xml
COPY anynote-modules/anynote-modules-system/pom.xml anynote-modules/anynote-modules-system/pom.xml
COPY anynote-auth/pom.xml anynote-auth/pom.xml
COPY anynote-gateway/pom.xml anynote-gateway/pom.xml
COPY anynote-admin/pom.xml anynote-admin/pom.xml

# 仅在根 POM 预取插件与依赖管理相关依赖，避免子模块快照包在源码复制前被错误解析。
RUN chmod +x mvnw && unset MAVEN_CONFIG && ./mvnw -N dependency:go-offline -DskipTests

COPY anynote-common anynote-common
COPY anynote-api anynote-api
COPY anynote-modules anynote-modules
COPY anynote-auth anynote-auth
COPY anynote-gateway anynote-gateway
COPY anynote-admin anynote-admin

RUN unset MAVEN_CONFIG && ./mvnw -pl ${MODULE} -am package -DskipTests

FROM amazoncorretto:8-alpine-jre

WORKDIR /app
ARG MODULE=anynote-gateway

RUN apk add --no-cache curl tzdata \
    && addgroup -S anynote \
    && adduser -S anynote -G anynote \
    && chown -R anynote:anynote /app

COPY --chown=anynote:anynote --from=build /workspace/${MODULE}/target/*.jar /app/app.jar

ENV JAVA_OPTS="" \
    TZ=Asia/Shanghai

EXPOSE 8080
USER anynote

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
