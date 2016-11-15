File name: BsonTypeClassMap.java 
Change:         map.put(BsonType.DATE_TIME, Date.class);
        map.put(BsonType.DATE_TIME, Timestamp.class);
        map.put(BsonType.DATE_TIME, Time.class);
        map.put(BsonType.DATE_TIME, java.sql.Date.class);

File name: ValueCodecProvider.java
Change:         addCodec(new TimeCodec());
        addCodec(new TimeStampCodec());
        addCodec(new DateSqlCodec());

File name: TimeCodec.java, TimeStampCodec.java, DateSqlCodec.java
Change: new file to convert sql datatypes into date