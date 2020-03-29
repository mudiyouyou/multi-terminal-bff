ps -ef|grep ${project.artifactId}.jar|grep -v grep|awk '{print $2}'|xargs kill -9
