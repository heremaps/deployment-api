#!/bin/sh

# borrowed from https://stackoverflow.com/a/6174447
GIT_PROTO="$(echo $GIT_URI | grep :// | sed -e's,^\(.*://\).*,\1,g')"
GIT_URL=$(echo $GIT_URI | sed -e s,$GIT_PROTO,,g)
GIT_USER="$(echo $GIT_URL | grep @ | cut -d@ -f1)"
GIT_HOST=$(echo $GIT_URL | sed -e s,$GIT_USER@,,g | cut -d/ -f1)

export GIT_PORT="$(echo $GIT_HOST | sed -e 's,^.*:,:,g' -e 's,.*:\([0-9]*\).*,\1,g' -e 's,[^0-9],,g')"
export GIT_HOST=$(echo $GIT_HOST | cut -d: -f1)

echo "${SSH_PRIVATE_KEY}" > ${HOME}/.ssh/id_rsa

chmod 400 ${HOME}/.ssh/id_rsa

ssh-keygen -y -f ${HOME}/.ssh/id_rsa > ${HOME}/.ssh/id_rsa.pub

ssh-keyscan `if [ ! -z $GIT_PORT ]; then echo "-p $GIT_PORT"; fi` $GIT_HOST > ${HOME}/.ssh/known_hosts 2>/dev/null

chown -R ${USER}:${GROUP} ${HOME}/.ssh/