CREATE KEYSPACE test
  WITH REPLICATION ={
    'class' : 'SimpleStrategy', 'replication_factor' : 1
  };

CREATE TABLE test.test_table (
  first text PRIMARY KEY,
  second text);
