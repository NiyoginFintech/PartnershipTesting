#!/bin/bash

cd automation-testing/PartnershipTesting || exit 1

while true; do
  echo "Running tests at $(date)"
  java -cp "target/id-test-tests.jar:target/dependency/*" org.testng.TestNG -testclass testPartnership.Production
  echo "Waiting 10 seconds..."
  sleep 10
done
