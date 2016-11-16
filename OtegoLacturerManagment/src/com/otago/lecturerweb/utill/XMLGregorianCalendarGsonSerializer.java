/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Chandresh
 */
public class XMLGregorianCalendarGsonSerializer implements
		JsonDeserializer<XMLGregorianCalendar>,
		JsonSerializer<XMLGregorianCalendar> {

	@Override
	public XMLGregorianCalendar deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		String value = json.getAsString();
		XMLGregorianCalendar calendar = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = sdf.parse(value);
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			gregorianCalendar.setTime(date);
			calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(
					gregorianCalendar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return calendar;
	}

	@Override
	public JsonElement serialize(XMLGregorianCalendar src, Type typeOfSrc,
			JsonSerializationContext context) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date time = src.toGregorianCalendar().getTime();
		return context.serialize(sdf.format(time));
	}

}
