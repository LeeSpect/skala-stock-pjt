#!/bin/bash

TARGET_NAME="jenkins"

echo "모든 컨테이너 목록:"
docker ps -a --format "table {{.ID}}\t{{.Names}}"

# 대상 컨테이너 ID 조회
CONTAINER_ID=$(docker ps -a --filter "name=^/${TARGET_NAME}$" --format "{{.ID}}")

if [ -n "$CONTAINER_ID" ]; then
  echo "컨테이너 '$TARGET_NAME' (ID: $CONTAINER_ID) 중지 중..."
  docker stop "$CONTAINER_ID"

  echo "컨테이너 '$TARGET_NAME' 삭제 중..."
  docker rm "$CONTAINER_ID"

  echo "완료되었습니다."
else
  echo "컨테이너 '$TARGET_NAME' 를 찾을 수 없습니다."
fi
