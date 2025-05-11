apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${USER_NAME}-${SERVICE_NAME}
  namespace: ${NAMESPACE}
spec:
  replicas: 2
  selector:
    matchLabels:
      app: ${USER_NAME}-${SERVICE_NAME}
  template:
    metadata:
      annotations:
        prometheus.io/scrape: 'true'
        prometheus.io/port: '8081'
        prometheus.io/path: '/actuator/prometheus'
        update: ${HASHCODE}
      labels:
        app: ${USER_NAME}-${SERVICE_NAME}
    spec:
      serviceAccountName: default
      containers:
      - name: ${IMAGE_NAME}
        image: ${DOCKER_REGISTRY}/${USER_NAME}-${IMAGE_NAME}:${VERSION}
        imagePullPolicy: Always
        env:
        - name: LOGGING_LEVEL_ROOT
          value: ${LOGGING_LEVEL}
        - name: USER_NAME
          value: ${USER_NAME}
        - name: NAMESPACE
          value: ${NAMESPACE}
        - name: SPRING_PROFILES_ACTIVE
          value: ${PROFILE}
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            configMapKeyRef:
              name: sk000-my-app-config
              key: SPRING_DATASOURCE_URL
        - name: SPRING_DATASOURCE_USERNAME
          valueFrom:
            configMapKeyRef:
              name: sk000-my-app-config
              key: SPRING_DATASOURCE_USERNAME
        envFrom:
        - secretRef:
            name: sk000-my-app-secrets
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness # Actuator liveness endpoint
            port: 8080 # 또는 8081 (management.server.port 설정에 따라)
          initialDelaySeconds: 60 # 컨테이너 시작 후 60초 뒤에 첫 Probe 시작
          periodSeconds: 10       # 10초마다 Probe 실행
          failureThreshold: 3     # 3번 연속 실패 시 컨테이너 재시작
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness # Actuator readiness endpoint
            port: 8080 # 또는 8081
          initialDelaySeconds: 30 # 컨테이너 시작 후 30초 뒤에 첫 Probe 시작
          periodSeconds: 10       # 10초마다 Probe 실행
          failureThreshold: 3     # 3번 연속 실패 시 Pod를 Unready 상태로 변경