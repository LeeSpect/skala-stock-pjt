#!/bin/bash

NAME=sk084
IMAGE_NAME="my-app"
VERSION="2.0.0"

# Maven을 사용하여 프로젝트 빌드 (JAR 파일 생성)
echo "Spring Boot 애플리케이션 빌드 중..."
mvn clean package -DskipTests

# Docker 이미지 빌드
echo Docker 이미지 빌드 중: ${NAME}/${IMAGE_NAME}:${VERSION}
docker build \
  --tag ${NAME}/${IMAGE_NAME}:${VERSION} \
  --file Dockerfile \
  --platform linux/amd64 \
  ${IS_CACHE} .

echo "Docker 이미지 빌드 완료: ${NAME}/${IMAGE_NAME}:${VERSION}"