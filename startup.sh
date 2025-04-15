# SkyWalking Agent 配置
export SW_AGENT_NAME=demo-application # 配置 Agent 名字。一般来说，我们直接使用 Spring Boot 项目的 `spring.application.name` 。
export SW_AGENT_COLLECTOR_BACKEND_SERVICES=127.0.0.1:11800 # 配置 Collector 地址。
export SW_AGENT_SPAN_LIMIT=2000 # 配置链路的最大 Span 数量。一般情况下，不需要配置，默认为 300 。主要考虑，有些新上 SkyWalking Agent 的项目，代码可能比较糟糕。
export JAVA_AGENT=-javaagent:/home/yxlm/software/skywalking-agent/skywalking-agent.jar # SkyWalking Agent jar 地址。

# Jar 启动
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-auth -jar -Xms64M -Xmx256M anynote-auth/target/anynote-auth.jar > logs/anynote-auth.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-gateway -jar -Xms64M -Xmx256M anynote-gateway/target/anynote-gateway.jar > logs/anynote-gateway.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-ai -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-ai/target/anynote-modules-ai.jar > logs/anynote-modules-ai.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-ai-nio -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-ai-nio/target/anynote-modules-ai-nio.jar > logs/anynote-modules-ai-nio.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-file -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-file/target/anynote-modules-file.jar > logs/anynote-modules-file.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-manage -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-manage/target/anynote-modules-manage.jar > logs/anynote-modules-manage.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-note -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-note/target/anynote-modules-note.jar > logs/anynote-modules-note.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-notify -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-notify/target/anynote-modules-notify.jar > logs/anynote-modules-notify.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-system -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-system/target/anynote-modules-system.jar > logs/anynote-modules-system.log &
nohup java -jar $JAVA_AGENT -Dskywalking.collector.backend_service=$SW_AGENT_COLLECTOR_BACKEND_SERVICES -Dskywalking.agent.service_name=anynote-job -jar -Xms64M -Xmx256M anynote-modules/anynote-modules-job/target/anynote-modules-job.jar > logs/anynote-modules-job.log &