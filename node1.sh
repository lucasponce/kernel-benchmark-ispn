#!/usr/bin/env bash
NAME="node1"
CLUSTER_SIZE="2"
THREADS="200"
REQUESTS_PER_THREAD="10000"
FINISH="true"

./run.sh ${NAME} ${CLUSTER_SIZE} ${THREADS} ${REQUESTS_PER_THREAD} ${FINISH}