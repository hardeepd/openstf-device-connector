#!/bin/bash

STF_URL=http://localhost:7100
STF_TOKEN=a78271ac72f04867885fee21f591d0ba44c27ff94e264afd8cae07781001456f

function usage()
{
    echo "OpenSTF device connector helper"
    echo "./openstf-connector.sh status|connect|disconnect"
    echo ""
}

while [ "$1" != "" ]; do
    case $1 in
        -h | --help)
            usage
            exit
            ;;
        status)
            java -jar openstf-connector.jar -u $STF_URL -t $STF_TOKEN -status
            ;;
        connect)
            java -jar openstf-connector.jar -u $STF_URL -t $STF_TOKEN -connect
            ;;
        disconnect)
            java -jar openstf-connector.jar -u $STF_URL -t $STF_TOKEN -disconnect
            ;;
        *)
            usage
            exit 1
            ;;
    esac
    shift
done
