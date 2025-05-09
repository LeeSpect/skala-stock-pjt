# k8s/hpa.t 또는 k8s/hpa.yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: ${USER_NAME}-${SERVICE_NAME}-hpa
  namespace: ${NAMESPACE}
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: ${USER_NAME}-${SERVICE_NAME} # k8s/deploy.t 에서 생성한 Deployment 이름
  minReplicas: 2 # 최소 Pod 수 (deploy.t의 replicas와 일치시키거나 더 낮게 설정 가능)
  maxReplicas: 5 # 최대 Pod 수
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70 # CPU 사용률이 70%에 도달하면 Pod 확장
  # 필요시 메모리 기반 스케일링 또는 커스텀 메트릭 추가 가능
  # - type: Resource
  #   resource:
  #     name: memory
  #     target:
  #       type: AverageValue # 또는 Utilization
  #       averageValue: 500Mi # 예시: 평균 메모리 사용량 500Mi