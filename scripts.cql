CREATE KEYSPACE ctrack WITH REPLICATION = { 'class' : 'SimpleStrategy' , 'replication_factor' : 2 };

CREATE TABLE logs(
    tripid bigint,
    tripstarttimeutc TimeStamp,
    tripendtimeutc TimeStamp,
    logid bigint,
    vehiclenodeid int,
    busgrpid int,
    costcentreid int,
    gpstime bigint,
    gpstimedt TimeStamp,
    longitude Float,
    latitude Float,
    Altitude int,
    logsdistance Float,
    logsplaceid int,
    statustext text,
    streetid bigint,
    suburbid bigint,
    cityid bigint,
    countryid int,
    zipcode text,
    speed int,
    maxspeed int,
    usedeltaodo int,
    deltaodo bigint,
    drivernodeid int,
    partitionid int,
    PRIMARY KEY(partitionid, logid, busgrpid, gpstimedt)
);

CREATE TABLE position(
  lastlog bigint,
  lasttrip bigint,
  PRIMARY KEY(lastlog)
);