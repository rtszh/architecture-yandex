#!/bin/bash

###
# создание двух шардов, в каждом из которых по три реплики
###
docker compose exec -T shard11 mongosh --port 27018 --quiet <<EOF
rs.initiate(
    {
      _id : "shard1",
      members: [
        { _id : 0, host : "shard11:27018" },
        { _id : 1, host : "shard12:27018" },
        { _id : 2, host : "shard13:27018" }
      ]
    }
);
exit();
EOF

docker compose exec -T shard21 mongosh --port 27018 --quiet <<EOF
rs.initiate(
    {
      _id : "shard2",
      members: [
        { _id : 0, host : "shard21:27018" },
        { _id : 1, host : "shard22:27018" },
        { _id : 2, host : "shard23:27018" }
      ]
    }
  );
exit();
EOF

