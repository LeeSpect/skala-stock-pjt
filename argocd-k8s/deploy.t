# k8s/deploy.t
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${USER_NAME}-${SERVICE_NAME}
  namespace: ${NAMESPACE}
  labels:
    app: ${USER_NAME}-${SERVICE_NAME}
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
      affinity:
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
          - weight: 100
            podAffinityTerm:
              labelSelector:
                matchExpressions:
                - key: app
                  operator: In
                  values:
                  - ${USER_NAME}-${SERVICE_NAME}
              topologyKey: "kubernetes.io/hostname"
      containers:
      - name: ${IMAGE_NAME}
        image: ${DOCKER_REGISTRY}/${USER_NAME}-${IMAGE_NAME}:${VERSION}
        imagePullPolicy: Always
        ports:
        - containerPort: ${CONTAINER_PORT}
        - containerPort: 8081
        env:
        - name: LOGGING_LEVEL_ROOT
          value: ${LOGGING_LEVEL}
        - name: USER_NAME
          value: ${USER_NAME}
        - name: NAMESPACE
          value: ${NAMESPACE}
        - name: SPRING_PROFILES_ACTIVE
          value: ${PROFILE}
        - name: SPRING_APPLICATION_JSON
          value: '{"management":{"server":{"port":"8081"}}}'
        # === 데이터소스 관련 환경 변수 주석 처리 시작 ===
        # - name: SPRING_DATASOURCE_URL
        #   valueFrom:
        #     configMapKeyRef:
        #       name: ${USER_NAME}-${SERVICE_NAME}-config
        #       key: SPRING_DATASOURCE_URL
        # - name: SPRING_DATASOURCE_USERNAME
        #   valueFrom:
        #     configMapKeyRef:
        #       name: ${USER_NAME}-${SERVICE_NAME}-config
        #       key: SPRING_DATASOURCE_USERNAME
        # - name: SPRING_DATASOURCE_DRIVER
        #   valueFrom:
        #     configMapKeyRef:
        #       name: ${USER_NAME}-${SERVICE_NAME}-config
        #       key: SPRING_DATASOURCE_DRIVER
        # === 데이터소스 관련 환경 변수 주석 처리 끝 ===

        # === 데이터소스 Secret 주석 처리 시작 ===
        # envFrom:
        # - secretRef:
        #     name: ${USER_NAME}-${SERVICE_NAME}-secrets
        # === 데이터소스 Secret 주석 처리 끝 ===
        resources:
          requests:
            cpu: "100m"
          limits:
            cpu: "500m"
        startupProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8081
          failureThreshold: 30
          periodSeconds: 10
          initialDelaySeconds: 5
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness
            port: 8081
          failureThreshold: 3
          periodSeconds: 10
          initialDelaySeconds: 60
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8081
          failureThreshold: 3
          periodSeconds: 10
          initialDelaySeconds: 10
