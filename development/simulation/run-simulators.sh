#!/bin/bash

set -euo pipefail

if [[ ! ("$#" == 1) ]]; then
    echo 'Configuration file not specified'
    exit 1
fi

config_json=$(cat "${1}")

docker_network=$(echo $config_json | jq -r '.dockerNetwork')
baseTemp=$(echo $config_json | jq -r '.baseTemp')
simulators=$(echo $config_json | jq '.simulators')
simulators_length=$(echo $simulators | jq -r '. | length')

for (( i = 0; i < simulators_length; i++ ))
do
  simulator_config=$(echo $simulators | jq ".[${i}]")
  docker run -d \
    --name "thermometer-simulator-${i}" \
    --label "app=thermometer-simulator" \
    --network "$docker_network" \
    thermometer-simulator:latest \
    --spring.profiles.active=docker \
    -Dspring-boot.run.arguments= \
      --spring.main.banner-mode=off, \
      --thermometer-simulator.thermometer-id="$(echo $simulator_config | jq -r '.id')" \
      --thermometer-simulator.room-id="$(echo $simulator_config | jq -r '.roomId')" \
      --thermometer-simulator.temp-base="${baseTemp}" \
      --thermometer-simulator.anomaly-rate="$(echo $simulator_config | jq -r '.anomalyRate')" \
      --thermometer-simulator.read-rate-millis="$(echo $simulator_config | jq -r '.readRateMillis')"
done
