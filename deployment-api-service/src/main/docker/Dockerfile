FROM registry.hub.docker.com/library/openjdk:8-jre-alpine
MAINTAINER Micah Noland <micah.noland@here.com>

ENV APP_HOME /opt/deployment-api-service/
ENV APP_JAR deployment-api-service.jar
ENV USER spring-boot
ENV GROUP spring-boot
ENV HOME /home/${USER}

RUN apk upgrade --update && \
	apk add \
		openssh-client \
		su-exec

RUN addgroup ${GROUP} && \
	adduser -S -G ${GROUP} ${USER}

RUN mkdir -p ${HOME}/.ssh/ && \
	mkdir -p ${APP_HOME}/lib
	
ADD maven/deployment-api-service*-exec.jar ${APP_HOME}/${APP_JAR}

RUN sh -c 'touch ${APP_HOME}/${APP_JAR}'

RUN chown -R ${USER}:${GROUP} ${APP_HOME} && \
	chown -R ${USER}:${GROUP} ${HOME} 
	
COPY docker-entrypoint.sh /docker-entrypoint.sh
COPY ssh-setup.sh /ssh-setup.sh

RUN chmod a+x /docker-entrypoint.sh && \
	chmod a+x /ssh-setup.sh

ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 8080