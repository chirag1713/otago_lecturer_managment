/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otago.lecturerweb.utill;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author omm
 */
public class DateGsonSerializer implements JsonDeserializer<Date>, JsonSerializer<Date> {

	@Override
	public Date deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
		Date date = null;
		try {
			String dateValue = je.getAsString();
			if (dateValue.contains("T") && dateValue.contains("Z")) {
				SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				date = new java.util.Date(dtf.parse(dateValue).getTime());
			} else if (dateValue.contains("-") && dateValue.contains(":")) {
				SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				date = new java.sql.Timestamp(dtf.parse(dateValue).getTime());
			} else if (dateValue.contains("-")) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				date = new java.sql.Date(df.parse(dateValue).getTime());
			} else if (dateValue.contains(":")) {
				SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
				date = new java.sql.Time(tf.parse(dateValue).getTime());
			}
		} catch (ParseException ex) {
			throw new JsonParseException(ex);
		}
		if (date != null) {
			return date;
		} else {
			return null;
		}
	}

	@Override
	public JsonElement serialize(Date t, Type type, JsonSerializationContext jsc) {
		String dateValue = null;
		if (t instanceof java.sql.Date) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dateValue = df.format(t);
		} else if (t instanceof java.sql.Timestamp) {
			SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			dateValue = dtf.format(t);
		} else if (t instanceof java.sql.Time) {
			SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
			dateValue = tf.format(t);
		} else if (t instanceof java.util.Date) {
			SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			dateValue = dtf.format(t);
		}
		if (dateValue != null) {
			return jsc.serialize(dateValue);
		} else {
			return null;
		}
	}

}
