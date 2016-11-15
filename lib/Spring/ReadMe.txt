Jar: spring-core-4.1.2.RELEASE _SujavTech
File: GenericConversionService.java
Change: public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
			......
		if (targetType.getType().equals(java.sql.Time.class)) {
			return new java.sql.Time(((Date)source).getTime());
		} else if (targetType.getType().equals(java.sql.Date.class)) {
			return new java.sql.Date(((Date)source).getTime());
		} else if (targetType.getType().equals(java.sql.Timestamp.class)) {
			return new java.sql.Timestamp(((Date)source).getTime());
		}

