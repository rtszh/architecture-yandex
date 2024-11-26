#!/bin/bash

kubectl apply -f role-bindings/role-binding_user1_read-user.yml
kubectl apply -f role-bindings/role-binding_user2_write-user.yml
kubectl apply -f role-bindings/role-binding_user3_system-cluster-admin.yml