#!/bin/sh

set -e

./ssh-setup.sh
	
if [ -z "$1" -o  "${1:0:1}" = '-' ]; then
    exec su-exec ${USER} java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom  -Dloader.path=${APP_HOME}/lib -jar ${APP_HOME}/${APP_JAR} --spring.cloud.config.server.git.uri=${GIT_URI} "$@"
fi

exec "$@"
