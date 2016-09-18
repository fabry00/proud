###
#
# Dex Dockerfile installation:
#
# build:
#
# docker build -t dex .
#
# run:
#
# docker run -v /tmp/.X11-unix:/tmp/.X11-unix -e DISPLAY=unix$DISPLAY dex
#
###
 
# pull base image
FROM frolvlad/alpine-oraclejdk8:slim
 
RUN apk --update add git tar bash

RUN echo "===> installing broker"
RUN mkdir -p /home/broker 
RUN mkdir -p /home/broker/zookeper-data
RUN mkdir -p /home/broker/kafka-logs
RUN chmod -R 755 /home/broker /home/broker/zookeper-data /home/broker/kafka-logs

COPY kafka_2.11-0.10.0.1.tgz /tmp
RUN cd /home/broker/ &&  tar -xvzf /tmp/kafka_2.11-0.10.0.1.tgz
RUN rm /tmp/kafka_2.11-0.10.0.1.tgz
COPY custom.server.properties /home/broker/kafka_2.11-0.10.0.1/config 
COPY custom.zookeeper.properties /home/broker/kafka_2.11-0.10.0.1/config

#RUN echo "===> starting zookeeper"
#RUN /home/broker/kafka_2.11-0.10.0.1/bin/zookeeper-server-start.sh ..\..\config\zookeeper.properties &

#RUN echo "===> starting broker"
# SETTING USER
#RUN mkdir -p /home/user
#RUN echo "user:x:1000:1000:Developer,,,:/home/user:/bin/bash" >> /etc/passwd
#RUN echo "user:x:1000:" >> /etc/group
#RUN echo "user ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/user
#RUN chmod 0440 /etc/sudoers.d/user
#RUN chown user:user -R /home/user

EXPOSE 5000:5000
RUN echo "===> exposing port 2181 for zookeeper"
EXPOSE 2181:2181
#USER user
#ENV HOME /home/user
WORKDIR /home/broker
#CMD echo "===> starting zookeeper"  && \
#     cd /home/broker/kafka_2.11-0.10.0.1/ && \
#     bin/zookeeper-server-start.sh config/custom.zookeeper.properties &  && \
#     bin/kafka-server-start.sh config/custom.server.properties &
#     bin/kafka-topics.sh  --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topictest
CMD /bin/bash
