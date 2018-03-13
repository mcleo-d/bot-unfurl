#!/bin/bash

BRANCH_NAME=$1
TRAVIS_PULL_REQUEST=$2

if [[ $BRANCH_NAME =~ master ]]; then
	export SYMPHONY_POD_HOST="foundation.symphony.com"
	export SYMPHONY_API_HOST="foundation-api.symphony.com"
    export BOT_NAME="botunfurl-prod"
    export OC_PROJECT_NAME="ssf-prod"
    export JOLOKIA_NODE_PORT=30030

elif [[ $BRANCH_NAME =~ dev ]]; then
	export SYMPHONY_POD_HOST="foundation-dev.symphony.com"
	export SYMPHONY_API_HOST="foundation-dev-api.symphony.com"
    export BOT_NAME="botunfurl-dev"
    export OC_PROJECT_NAME="ssf-dev"
    export JOLOKIA_NODE_PORT=30031
else
	echo "Skipping deployment for branch $BRANCH_NAME"
	exit 0
fi

export OC_BINARY_FOLDER="./target/oc"
export OC_ENDPOINT="https://api.pro-us-east-1.openshift.com"
export OC_TEMPLATE_PROCESS_ARGS="BOT_NAME,SYMPHONY_POD_HOST,SYMPHONY_API_HOST,JOLOKIA_NODE_PORT"

if [[ "$TRAVIS_PULL_REQUEST" = "false" ]]; then
	curl -s https://raw.githubusercontent.com/symphonyoss/contrib-toolbox/master/scripts/oc-deploy.sh | bash
fi
