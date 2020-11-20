# Sample application for a Neo4j performance issue in version 4.x

## Purpose
This repo documents a performance issue we have encountered with an embedded neo4j database.
We recognized that from version 4.x on the time it takes to add properties to nodes increases with
the number of properties already attached to a node.

We do not see that issue with version 3.x. With version 3.x the time it takes to add properties is almost linear
and therefore not dependent on the number of properties already attached to a node.

## Branches
This repo contains two branches:
* `3.x` for the test using the 3.x version of neo4j (currently 3.5.24)
* `4.x` for the test using the 4.x version of neo4j (currently 4.1.4)
