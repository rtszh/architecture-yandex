#!/bin/bash

mkdir temp

# user1
openssl genrsa -out temp/user1.pem
openssl req -new -key temp/user1.pem -out temp/user1.csr -subj "/CN=user1"
user1csr=$(cat temp/user1.csr | base64 | tr -d '\n')

cat <<EOF> temp/user1-csr.yaml
apiVersion: certificates.k8s.io/v1
kind: CertificateSigningRequest
metadata:
  name: user1-request
spec:
  groups:
  - system:authenticated
  request: $user1csr
  signerName: kubernetes.io/kube-apiserver-client
  expirationSeconds: 315569260
  usages:
  - digital signature
  - key encipherment
  - client auth
EOF

kubectl create -f temp/user1-csr.yaml   
kubectl certificate approve user1-request
kubectl get csr user1-request -o jsonpath='{.status.certificate}' | base64 -d > user1.crt

# удаление переменной
unset user1csr

# user2
openssl genrsa -out temp/user2.pem
openssl req -new -key temp/user2.pem -out temp/user2.csr -subj "/CN=user2"
user2csr=$(cat temp/user2.csr | base64 | tr -d '\n')

cat <<EOF> temp/user2-csr.yaml
apiVersion: certificates.k8s.io/v1
kind: CertificateSigningRequest
metadata:
  name: user2-request
spec:
  groups:
  - system:authenticated
  request: $user2csr
  signerName: kubernetes.io/kube-apiserver-client
  expirationSeconds: 315569260
  usages:
  - digital signature
  - key encipherment
  - client auth
EOF

kubectl create -f temp/user2-csr.yaml   
kubectl certificate approve user2-request
kubectl get csr user2-request -o jsonpath='{.status.certificate}' | base64 -d > user2.crt

# удаление переменной
unset user2csr

# user3
openssl genrsa -out temp/user3.pem
openssl req -new -key temp/user3.pem -out temp/user3.csr -subj "/CN=user3"
user3csr=$(cat temp/user3.csr | base64 | tr -d '\n')

cat <<EOF> temp/user3-csr.yaml
apiVersion: certificates.k8s.io/v1
kind: CertificateSigningRequest
metadata:
  name: user3-request
spec:
  groups:
  - system:authenticated
  request: $user3csr
  signerName: kubernetes.io/kube-apiserver-client
  expirationSeconds: 315569260
  usages:
  - digital signature
  - key encipherment
  - client auth
EOF

kubectl create -f temp/user3-csr.yaml   
kubectl certificate approve user3-request
kubectl get csr user3-request -o jsonpath='{.status.certificate}' | base64 -d > user3.crt

# удаление переменной
unset user3csr

# удаление temp
rm -rf ./temp