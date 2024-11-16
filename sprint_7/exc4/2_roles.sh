#!/bin/bash

kubectl apply -f roles/role_read-user.yml
kubectl apply -f roles/role_write-user.yml
kubectl apply -f roles/role_system-cluster-admin.yml