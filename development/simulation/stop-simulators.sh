#!/bin/bash

set -euo pipefail

docker rm -f $(docker ps -aqf label=app=thermometer-simulator)
