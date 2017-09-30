# Dockerfile to be used to run ATH itself.
#
# see docs/DOCKER.md for usage


FROM debian:stretch
MAINTAINER ogondza@gmail.com

RUN apt-get clean && \
    apt-get -y update && \
    apt-get install -y \
        curl \
        git \
        imagemagick \
        iptables \
        firefox-esr \
        maven \
        openjdk-8-jdk \
        unzip \
        vnc4server \
        sudo

# All we need is a statically linked client library - no need to install daemon deps: https://get.docker.com/builds/
RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz && \
    tar --strip-components=1 -xvzf docker-17.04.0-ce.tgz -C /usr/local/bin

EXPOSE 5942

# So it is owned by root and has the permissions vncserver seems to require:
RUN mkdir /tmp/.X11-unix && chmod 1777 /tmp/.X11-unix/

# When sudo'ing in the container, keep this variable
RUN echo 'Defaults env_keep += "XAUTHORITY"' >>/etc/sudoers

# Set SUID and SGID for docker binary so it can communicate with mapped socket its uid:gid we can not control.
RUN chmod ug+s "$(which docker)"

ENV DEFAULT_USER=ath-user

WORKDIR /opt/bin
COPY userSetup.sh userSetup.sh

ENTRYPOINT ["/opt/bin/userSetup.sh"]
