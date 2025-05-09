#!/bin/bash

NAME=sk084
IMAGE_NAME="skala-stock-console2"
VERSION="1.0.0"

DOCKER_REGISTRY="amdp-registry.skala-ai.com/skala25a"
DOCKER_REGISTRY_USER="robot\$skala25a" # env.properties에서 가져오거나 직접 설정
DOCKER_REGISTRY_PASSWORD="1qB9cyusbNComZPHAdjNIFWinf52xaBJ" # env.properties에서 가져오거나 직접 설정

# 로컬에 빌드된 이미지 이름 (docker-build.sh에서 사용한 태그)
LOCAL_IMAGE_TAG="${NAME}/${IMAGE_NAME}:${VERSION}" # 예: sk084/skala-stock-console2:1.0.0

# 레지스트리에 푸시할 전체 이미지 이름 (k8s deploy.t 와 일치)
REMOTE_IMAGE_TAG="${DOCKER_REGISTRY}/${NAME}-${IMAGE_NAME}:${VERSION}" # 예: amdp-registry.skala-ai.com/skala25a/sk084-skala-stock-console2:1.0.0

# 1. Docker 레지스트리에 로그인
echo "Docker 레지스트리 (${DOCKER_REGISTRY})에 로그인 중..."
echo "${DOCKER_REGISTRY_PASSWORD}" | docker login "${DOCKER_REGISTRY}" \
	-u "${DOCKER_REGISTRY_USER}" --password-stdin \
   	|| { echo "Docker 로그인 실패"; exit 1; }
echo "Docker 로그인 성공."

# 2. Harbor (또는 다른 레지스트리)로 푸시하기 위해 로컬 이미지를 원격 이미지 태그로 다시 태그합니다.
echo "이미지 태그 지정 중: ${LOCAL_IMAGE_TAG} -> ${REMOTE_IMAGE_TAG}"
docker tag "${LOCAL_IMAGE_TAG}" "${REMOTE_IMAGE_TAG}" \
    || { echo "Docker 이미지 태그 지정 실패: ${LOCAL_IMAGE_TAG} 이미지가 존재하는지 확인하세요."; exit 1; }
echo "이미지 태그 지정 완료."

# 3. Docker 이미지 푸시
echo "Docker 이미지 푸시 중: ${REMOTE_IMAGE_TAG}"
docker push "${REMOTE_IMAGE_TAG}" \
    || { echo "Docker 이미지 푸시 실패"; exit 1; }
echo "Docker 이미지 푸시 완료: ${REMOTE_IMAGE_TAG}"
