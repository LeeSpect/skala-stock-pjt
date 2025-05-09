#!/bin/bash

#SOCKET_GID=$(ls -la /var/run/docker.sock | awk '{print $4}' | xargs getent group | cut -d: -f3)
  #--group-add ${DOCKER_GID} \
  #-v $(pwd)/jenkins_home:/var/jenkins_home \

sudo docker run -d \
  --name jenkins \
  -p 8888:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  --group-add 0 \
  --restart always \
  jenkins-custom
