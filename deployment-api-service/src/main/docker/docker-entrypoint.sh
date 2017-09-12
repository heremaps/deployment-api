#!/bin/sh

set -e

./ssh-setup.sh
	
if [ -z "$1" -o  "${1:0:1}" = '-' ]; then
    exec su-exec ${USER} java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar ${APP_HOME}/${APP_JAR} --loader.path=${APP_HOME}/lib --spring.cloud.config.server.git.uri=${GIT_URI} "$@"
fi

exec "$@"
