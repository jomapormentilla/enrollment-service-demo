-- Query-first design: one table per access pattern, each holding a full copy of the data it needs.
-- Cassandra has no joins and can only query efficiently by partition key, so we denormalize
-- instead of normalizing like in a relational database.

-- SimpleStrategy with RF=1 suits a single local node. Real clusters use
-- NetworkTopologyStrategy with RF=3 per datacenter.
CREATE KEYSPACE IF NOT EXISTS enrollment
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

-- Access pattern 1: GET /enrollments/{id}
-- Partition key = id, so each enrollment is its own partition and a lookup touches one node.
CREATE TABLE IF NOT EXISTS enrollment.enrollments_by_id (
    id            uuid,
    customer_id   text,
    first_name    text,
    last_name     text,
    date_of_birth date,
    status        text,
    created_at    timestamp,
    PRIMARY KEY (id)
);

-- Access pattern 2: GET /enrollments?customerId=... (newest first)
-- Partition key = customer_id: all of a customer's enrollments are stored together.
-- Clustering keys = created_at, id: rows inside the partition are kept sorted on disk,
-- newest first. id is added so two enrollments created in the same millisecond don't overwrite each other.
CREATE TABLE IF NOT EXISTS enrollment.enrollments_by_customer (
    customer_id   text,
    created_at    timestamp,
    id            uuid,
    first_name    text,
    last_name     text,
    date_of_birth date,
    status        text,
    PRIMARY KEY ((customer_id), created_at, id)
) WITH CLUSTERING ORDER BY (created_at DESC, id ASC);
