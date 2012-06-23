This page is related to JIRA task [Menta 26](https://tu-project.atlassian.net/browse/MENTA-26)

## Summary
The following questions were asked from OpenCog guru and **waiting their answer** about

* transaction policy for concurrent access to storage
* serialized objects in current implementation
* Data access

[Answers](http://groups.google.com/group/opencog/browse_thread/thread/be3f37e079020357)

Object serialization could not be performed via current OpenCog architecture.

XML and SCM file formats is used by default.

Scala is not compatible with C++ (excluding JNI cases).

OpenCog could be connected via storages (see below) or AtomSpace API.

## Storage types for OpenCog
Currently they're choosing Key-Value storage like Neo4j, DEX.
Data could be stored in the following ways:

1. SQL via ODBC. It's possible to save the atomtable on the SQL database (see in OpenCog UI). This option must be setup.

2. [HyperTable](http://wiki.opencog.org/w/Storing_the_AtomSpace_in_HyperTable).
Hypertable has a lot in common with a typical RDBMS but also has many important differences.<br>
No Types (yet): Hypertable cells do not have types. **All data stored in Hypertable are strings**. <br>
No Transactions (yet): Hypertable does not yet support transactions. <br>
No Secondary Indexes.<br>
_The information is taken from HyperTable home page code.google.com/p/hypertable/wiki/HyperRecord_

3. XML  (formerly NM-XML) 

4. File (as s-expressions, i.e. scheme)