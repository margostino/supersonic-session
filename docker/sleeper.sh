#!/bin/sh
set -eu

if [ "$#" -ne 3 ]; then
  echo "Usage: $0 HOST PORT TIMEOUT" >&2
  echo "Got: $#"
  exit 1
fi

wait_for_connection() {
  local host="$1"
  local port="$2"
  local timeout="$3"
  for attempt in $(seq ${timeout}); do
    if $(nc -z "$host" "$port"); then
      echo "Successfully connected to ${host}:${port}"
      exit 0
    fi
    echo "Waiting for service to be available on ${host}:${port}"
    sleep 1
  done
  echo "ERROR: Failed to connect to ${host}:${port} in ${timeout} seconds" >&2
  exit 1
}

quit() {
  echo "Waiting was interrupted, exiting..." >&2
  exit 1
}
trap quit SIGTERM SIGINT

wait_for_connection $1 $2 $3
