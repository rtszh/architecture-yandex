#!/bin/bash

###
# 1. добавляем в роутер информацию про наши два шарда
# 2. включаем шардирование для somedb и выбираем тип шардирования
###
docker compose exec -T mongos_router mongosh --port 27020 --quiet <<EOF
sh.addShard( "shard1/shard11:27018");
sh.addShard( "shard2/shard21:27018");

sh.enableSharding("somedb");
sh.shardCollection("somedb.helloDoc", { "name" : "hashed" } )
exit();
EOF
