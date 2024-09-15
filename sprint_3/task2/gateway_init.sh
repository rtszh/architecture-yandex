#!/bin/bash

###
# настройка service'ов и route'ов для kong
###

# service 1 - device-management-api
# create service
curl -i -s -X POST http://localhost:8001/services \
  --data name=device-management-api \
  --data url='http://device-management-api:8080'

# create route
curl -i -X POST http://localhost:8001/services/device-management-api/routes \
  --data 'paths[]=/device-management' \
  --data name=device_management

# service 2 - smart-home-monolith
# create service
curl -i -s -X POST http://localhost:8001/services \
  --data name=smart-home-monolith \
  --data url='http://smart-home-monolith:8082'

# create route
curl -i -X POST http://localhost:8001/services/smart-home-monolith/routes \
  --data 'paths[]=/smart-home-monolith' \
  --data name=smart-home-monolith-route

# request data
#curl -X GET http://localhost:8000/device-management/device/register/data/38032bc9-5dd2-4167-8062-dbc5d6114b3c