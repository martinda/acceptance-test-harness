#!/bin/bash

function showHelp {
    printf "Usage:
    docker run -it --rm -P \\
        -v /var/run/docker.sock:/var/run/docker.sock  \\
        -v \$(pwd):/home/$DEFAULT_USER/ath  \\
        jenkins/ath  \\
        --gid \$(id -g) \\
        --uid \$(id -u) \\
        bash

Launches the ATH docker container with the current user and group id.
"
}

while [[ "$1" == -* ]]; do

    case "$1" in

      --uid)
	uid="$2"
	shift 2
	;;

      --gid)
	gid="$2"
	shift 2
	;;

      -h|-help|--help)
	showHelp
	exit
	;;

      --)
	shift
	break
	;;

      -*)
	echo "Invalid command line argument $1. Aborting." >&2
	exit 1
	;;

    esac

done

if [[ ! -d /home/$DEFAULT_USER/ath ]]; then
    echo "You must set a mount point for /home/$DEFAULT_USER/ath. See --help." >&2
    exit 1
fi

if [[ ! -d /var/run/docker.sock ]]; then
    echo "This image runs docker-on-docker, you must mount '/var/run/docker.sock'. See --help." >&2
    exit 1
fi

if [[ -z "$gid" ]]; then
    gid=1000
    echo "Setting gid=1000. Use --gid GID on the command line to change."
fi

if [[ -z "$uid" ]]; then
    uid=1000
    echo "Setting uid=1000. Use --uid UID on the command line to change."
fi

set -o xtrace

# Create the DEFAULT_USER
groupadd $DEFAULT_USER -g $gid
useradd $DEFAULT_USER -u $uid -g $gid -m -d /home/$DEFAULT_USER

# Setup the DEFAULT_USER account
mkdir /home/$DEFAULT_USER/.vnc

(echo $DEFAULT_USER; echo $DEFAULT_USER) | vncpasswd /home/$DEFAULT_USER/.vnc/passwd

# Default content includes x-window-manager, which is not installed, plus other stuff we do not need (vncconfig, x-terminal-emulator, etc.):
touch /home/$DEFAULT_USER/.vnc/xstartup
chmod a+x /home/$DEFAULT_USER/.vnc/xstartup

# TODO seems this can be picked up from the host, which is unwanted:
export XAUTHORITY=/home/$DEFAULT_USER/.Xauthority

# Prevent xauth to complain in a confusing way
touch $XAUTHORITY

# User owns their own files
chown -R $gid:$uid /home/$DEFAULT_USER/.vnc
chown $gid:$uid /home/$DEFAULT_USER
chown $gid:$uid $XAUTHORITY

# Run the remaining command line as the DEFAULT_USER, from the mounted path
cd /home/$DEFAULT_USER/ath
sudo --user $DEFAULT_USER "$@"

