#!/bin/bash
set -e
./mvnw spotless:apply
./mvnw pmd:check
./mvnw test -Dpmd.skip=true