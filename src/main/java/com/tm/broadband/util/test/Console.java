package com.tm.broadband.util.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Console {
	
	private static boolean isDebugMode = false;
	
	// PRODUCTION MODE
	/**
	 * Production Mode
	 */
	public static void log(Object obj){
		log(obj, false);
	}

	// DEVELOPMENT MODE
	/**
	 * Debugging Mode
	 */
	public static void logD(Object obj){
		log(obj, true);
	}

	public static void log(Object obj, boolean debugMode){
		
		isDebugMode = debugMode;
		
		// If is user-defined type
		if(obj!=null && !obj.toString().startsWith("class")){
				
			Class<? extends Object> clazz = obj.getClass();

			// if methods not empty, output methods
			if(clazz.getDeclaredMethods().length>0){
				Method[] methods = clazz.getDeclaredMethods();
				int countGetMethod = 0;
				for (int i = 0; i < methods.length; i++) {
					if(!methods[i].getName().startsWith("set")){
						countGetMethod++;
					}
				}
				String clazzName = clazz.getName();
				if(clazzName.contains(".")){
					clazzName = clazzName.substring(clazzName.lastIndexOf(".")+1, clazzName.length());
				}
				println(clazzName+" = {");
				int countComma = 0;
				for (int i = 0; i < methods.length; i++) {
					String methodName = methods[i].getName();
					String returnTypeStr = methods[i].getReturnType().toString();
					if(returnTypeStr.contains(".")){
						returnTypeStr = returnTypeStr.substring(returnTypeStr.lastIndexOf(".")+1, returnTypeStr.length());
					}
					Method method = null;
					if(!methodName.startsWith("set")){
						try {
								method = clazz.getDeclaredMethod(methodName);
						} catch (SecurityException | NoSuchMethodException e) {
							e.printStackTrace();
						}
						try {
							if("List".equals(returnTypeStr)){
								@SuppressWarnings("unchecked")
								List<Object> allSub = (List<Object>) method.invoke(obj);
								print("   "+capital(methodName.substring(3, methodName.length()))+" : {");
								if(allSub!=null && allSub.size()>0){
									allSubs(method, obj, methodName);
								} else {
									print("   }");
								}
							} else {
								print("   "+capital(methodName.substring(methodName.startsWith("is") ? 2 : 3, methodName.length()))+" : ");
								if(method.invoke(obj)==null){
									print(method.invoke(obj));
								} else {
									if(method.invoke(obj) instanceof String){
										print("\""+method.invoke(obj)+"\"");
									} else if(method.invoke(obj) instanceof Character){
										print("\'"+method.invoke(obj)+"\'");
									} else {
										print(method.invoke(obj));
									}
								}
							}
						} catch (IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
						if(countComma<countGetMethod-1){
							println(",");
							countComma++;
						} else {
							println();
						}
					}
				}
				println("}");
				
			} else {
				println("Methods is empty!");
			}
			
		} else if(isPrimitiveType(obj)){
			
		} else {
			println("undefined");
		}
		
	}
	
	public static String capital(String str){
		byte[] items = str.getBytes();  
        items[0] = (byte) ((char) items[0] - 'A' + 'a');  
        return new String(items);
	}
	
	public static void allSubs(Method method
			, Object obj, String methodName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		@SuppressWarnings("unchecked")
		List<Object> allSub = (List<Object>) method.invoke(obj);
		int countPrimitiveComma = 0;
		for (Object subObj : allSub) {
			print("\n");
			if(isPrimitiveInAll(subObj)){
				if(countPrimitiveComma<allSub.size()-1){
					print(",");
					countPrimitiveComma++;
				} else {
					println();
				}
			} else {
				
				Class<? extends Object> clazz = subObj.getClass();
				// if methods not empty, output methods
				if(clazz.getDeclaredMethods().length>0){
					Method[] methods = clazz.getDeclaredMethods();
					int countGetMethod = 0;
					for (int i = 0; i < methods.length; i++) {
						if(!methods[i].getName().startsWith("set")){
							countGetMethod++;
						}
					}
					int countComma = 0;
					for (int i = 0; i < methods.length; i++) {
						String subMethodName = methods[i].getName();
						String returnTypeStr = methods[i].getReturnType().toString();
						if(returnTypeStr.contains(".")){
							returnTypeStr = returnTypeStr.substring(returnTypeStr.lastIndexOf(".")+1, returnTypeStr.length());
						}
						Method subMethod = null;
						if(!subMethodName.startsWith("set")){
							try {
								subMethod = clazz.getDeclaredMethod(subMethodName);
							} catch (SecurityException | NoSuchMethodException e) {
								e.printStackTrace();
							}
							try {
								Object val = subMethod.invoke(obj);
								if("String".equals(returnTypeStr)){
									val = (val!=null ? "\"" : "")+val+(val!=null ? "\"" : "");
								} else if("Character".equals(returnTypeStr)){
									val = (val!=null ? "\'" : "")+val+(val!=null ? "\'" : "");
								}
									print("      "+capital(subMethodName.substring(subMethodName.startsWith("is") ? 2 : 3, subMethodName.length()))+" : "+val);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							}
							if(countComma<countGetMethod-1){
								println(",");
								countComma++;
							} else {
								println();
							}
						}
					}
				}
			}
		}
		print("   }");
	}
	
	public static boolean isPrimitiveType(Object obj){
		boolean flag = false;
		if(obj instanceof String){
			println("String : "+obj); flag = true;
		} else if(obj instanceof Short){
			println("Short : "+obj); flag = true;
		} else if(obj instanceof Byte){
			println("Byte : "+obj); flag = true;
		} else if(obj instanceof Integer){
			println("Integer : "+obj); flag = true;
		} else if(obj instanceof Long){
			println("Long : "+obj); flag = true;
		} else if(obj instanceof Float){
			println("Float : "+obj); flag = true;
		} else if(obj instanceof Double){
			println("Double : "+obj); flag = true;
		} else if(obj instanceof Character){
			println("Character : "+obj); flag = true;
		} else if(obj instanceof Boolean){
			println("Boolean : "+obj); flag = true;
		} else if(obj instanceof BigInteger){
			println("BigInteger : "+obj); flag = true;
		} else if(obj instanceof BigDecimal){
			println("BigDecimal : "+obj); flag = true;
		} else if(obj instanceof Number){
			println("Number : "+obj); flag = true;
		}
		return flag;
	}
	
	public static boolean isPrimitiveInAll(Object obj){
		boolean flag = false;
		if(obj instanceof String){
			print("     "+(obj!=null ? "\"" : "")+obj+(obj!=null ? "\"" : "")); flag = true;
		} else if(obj instanceof Number){
			print("      "+obj+""); flag = true;
		} else if(obj instanceof Boolean){
			print("      "+obj+""); flag = true;
		} else  if(obj instanceof Character){
			print("      "+(obj!=null ? "\'" : "")+obj+(obj!=null ? "\'" : "")); flag = true;
		}
		return flag;
	}
	
	public static void print(Object content){
		if(!isDebugMode){
			System.out.print(content);
		}
	}
	
	public static void println(Object content){
		if(!isDebugMode){
			System.out.println(content);
		}
	}
	
	public static void println(){
		if(!isDebugMode){
			System.out.println();
		}
	}
}
