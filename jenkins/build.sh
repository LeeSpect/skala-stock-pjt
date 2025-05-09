#!/bin/bash

# 필요한 파일들(Dockerfile, plugins.txt, security.groovy)이 있는 디렉토리에서 실행
#sudo docker build --no-cache -t jenkins-custom .
sudo docker build -t jenkins-custom .

mkdir -p ./jenkins_home
#cp -rf /config/.kube /tmp/jenkins_home/
#sudo chown -R 1000:1000 /tmp/jenkins_home
