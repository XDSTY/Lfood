#!/bin/bash

project_name=algapi
repository=47.107.99.226:8081
hostname=miku_ali

# 停止对应的容器
docker container  stop `docker container ls | grep "${project_name}" | awk '{print $1}'`
# 删除停止的容器
# docker container  rm `docker ps -a | grep Exited | awk '{print $1}'`
docker rm $(docker ps -a -q)
# 删除对应的镜像
docker rmi `docker image ls | grep "${project_name}" | awk '{print $1}'`

docker pull ${repository}/${project_name}


if [ ! -d /home/workspace/${project_name}/log ]; then
  mkdir /home/workspace/${project_name}/log
fi
docker run -d -v /etc/hosts:/etc/hosts --hostname ${hostname} -v /home/workspace/${project_name}/log:/log -p 8085:8085 ${repository}/${project_name}
