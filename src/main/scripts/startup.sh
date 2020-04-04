#!/bin/sh
## Define the node name and init.
#self_ip=`ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}'`
ps -ef|grep ${project.artifactId}.jar|grep -v grep|awk '{print $2}'|xargs kill -9
java -server -Xms256M -Xmx512M -Xss512k -XX:+UseG1GC \
#-Djava.rmi.server.hostname=$self_ip \
#-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.ssl=false \
#-Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.port=60000\
#-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/${project.artifactId}.dump  \
#-XX:MaxGCPauseMillis=200ms -XX:G1HeapRegionSize=16m -XX:+PrintGCDetails -XX:+PrintHeapAtGC -Xloggc:/tmp/${project.artifactId}.gc
-Djava.awt.headless=true -Dspring.profiles.active=${env} -jar \
../${project.artifactId}.jar > /dev/null 2>&1 &
