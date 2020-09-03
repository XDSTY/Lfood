#!/bin/bash

project_name=productservice
repository=47.107.99.226:5000
hostname=$HOSTNAME

# 停止对应的容器
docker container  stop `docker container ls | grep "${project_name}" | awk '{print $1}'`
# 删除停止的容器
# docker container  rm `docker ps -a | grep Exited | awk '{print $1}'`
docker rm $(docker ps -a -q)
# 删除对应的镜像
docker rmi `docker image ls | grep "${project_name}" | awk '{print $1}'`

docker pull ${repository}/${project_name}
docker run -d -v /etc/hosts:/etc/hosts --hostname ${hostname} -v /home/workspace/${project_name}/log:/log -p 8083:8083 -p 20880:20880  ${repository}/${project_name}