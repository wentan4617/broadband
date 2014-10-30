package com.tm.broadband.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CheckScriptInjection {

	public CheckScriptInjection() {
	}

	public static boolean isScriptInjection(Object obj) {
		
		boolean flag = false;

		// get object class
		Class<?> clz = obj.getClass();

		// get class attributes
		Field[] fields = clz.getDeclaredFields();

		// iterates class attributes
		for (Field field : fields) {

			// if attribute type matched String type
			if (field.getGenericType().equals(String.class)) {
				
				Method m = null;
				String attributeValue = "";
				try {

					// get method name by field name
					m = obj.getClass().getMethod("get" + getMethodName(field.getName()));

					// get attribute value by method
					attributeValue = (String) m.invoke(obj);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				// If contains "<" or "</" or "/>" then this is a script
				// injection
				if (attributeValue != null
						&& (attributeValue.contains("<") || attributeValue.contains("</") || attributeValue.contains("/>"))) {
					flag = true;

					// If found first containing "<" or "</" or "/>" attribute
					// then break loop
					break;
				}
			}
		}

		return flag;
	}

	private static String getMethodName(String fildeName) {
		
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
		
	}

}
