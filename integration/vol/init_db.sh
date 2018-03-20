#!/usr/bin/env bash
echo "Init cassandra"
sleep 30
echo "Configuring cassandra"
cqlsh -f /vol/cql/init_db.cql
echo "Cassandra is configured"
