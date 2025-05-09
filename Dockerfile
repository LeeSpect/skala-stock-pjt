# 베이스 이미지로 OpenJDK 17 버전의 JRE 이미지 사용
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 포트 (기본 8080) 및 Actuator 포트(기본 8080, 또는 management.server.port로 설정된 값) 노출
# 참고용 service.yaml에서 8081을 Actuator/management 포트로 사용하고 있어 이를 포함합니다.
# 실제 Actuator 포트 설정에 따라 조정하세요. (application.yml에서 management.server.port로 변경 가능)
EXPOSE 8080
EXPOSE 8081

# 빌드된 JAR 파일을 컨테이너에 추가
# target/*.jar 패턴을 사용하여 정확한 JAR 파일 이름을 하드코딩하지 않도록 합니다.
COPY target/skala-stock-console2-1.0-SNAPSHOT.jar app.jar

# 애플리케이션 실행
# SPRING_PROFILES_ACTIVE는 Kubernetes 환경 변수 또는 ConfigMap을 통해 설정하는 것이 더 유연합니다.
# 여기서는 기본값으로 'prod'를 사용하지만, 필요에 따라 Docker 실행 시점에 -e 옵션으로 변경 가능합니다.
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]