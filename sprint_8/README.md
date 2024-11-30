#### 1. Запуск сервисов:
1. сервисы запускаются через docker-compose
`docker compose up`
2. порты:
   3. 8080 - keycloak
   4. 3000 - frontend
   5. 8000 - api

#### 2. Security-флоу:
1. frontend - это oauth2-client, который использует authorization code flow + pkce exchange;
2. api - это oauth2-resource server. Он проверяет:
   1. проверяет валидность access token;
   2. если access token невалидный, то возвращает 401 (Unauthorized);
   3. если access token - валидный, но у user'а недостаточно прав для просмотра защищенного ресурса, то сервер возвращает 403 (Forbidden);

#### 3. Проверка работы security-флоу:
1. запускаем сервисы через docker compose;
2. проверить все кейсы, кроме невалидного access token можно через frontend (http://localhost:3000)
3. проверить кейс, в котором resource server возвращает 401 (Unauthorized) для невалидного access token можно с помощью curl:
- curl с просроченным токеном для пользователя: prothetic1
```
curl -v -XGET 'http://localhost:8000/reports' \
-H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJzNnRPUkV3TThWTUZnc2hkb0xPVk1tUENKbVBhaXdSWmlhdVRzdGZBMFprIn0.eyJleHAiOjE3MzI5OTU5NjgsImlhdCI6MTczMjk5NTY2OCwiYXV0aF90aW1lIjoxNzMyOTk1MzY4LCJqdGkiOiJlYTc2MjZlOC0yNzY3LTQ0MzAtOGMzYS1jODk0MzEyNjIzMmYiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvcmVhbG1zL3JlcG9ydHMtcmVhbG0iLCJzdWIiOiI4Zjg1OWY3ZC04NWQ4LTQwOTQtODRlNy0zY2U1OGM2NWZiMDUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJyZXBvcnRzLWZyb250ZW5kIiwibm9uY2UiOiIxN2E0YmJjZS05ODMwLTQ3OGUtOGIxNy03MTlmN2QxMGVhZmQiLCJzZXNzaW9uX3N0YXRlIjoiMTA5NWE0OTgtMWFmMy00NmY2LWIwMDUtNjE2ZDJkNjFhNTU0IiwiYWNyIjoiMCIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjMwMDAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbInByb3RoZXRpY191c2VyIl19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwic2lkIjoiMTA5NWE0OTgtMWFmMy00NmY2LWIwMDUtNjE2ZDJkNjFhNTU0IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiUHJvdGhldGljIE9uZSIsInByZWZlcnJlZF91c2VybmFtZSI6InByb3RoZXRpYzEiLCJnaXZlbl9uYW1lIjoiUHJvdGhldGljIiwiZmFtaWx5X25hbWUiOiJPbmUiLCJlbWFpbCI6InByb3RoZXRpYzFAZXhhbXBsZS5jb20ifQ.fqwyrEzQO3gVu8ovQi2WRdTggy9iwCYHl-3j9FI7aAbp6EUpcs4zClgGXrjE2viSBlUScfovDWmKEE2Jfh_46Ro2JJoQ9eqYwTEV5b7sA1mOWLs0UuJT3_aMMfURX4PrnJWQbxjTcChNT5pZPqQltBqa_MmuPdDUeTLyn2z6qY46tvwd-orqT_OwI_s7qWZcb2HSoc5O-pQMsRwvw4dXywJjz8Ad1Tx3L5DvHCZrXbpGVZlZFsIWu_2ShoUnMdPFPTbHXn5d-hIUtYWvU8uViX9xCJOhs4JnzIRiqa56DCpkplZqifdhQYPK19KEuQG4Zsp9uyuzL9ozWjGNLl7zAw'
```


