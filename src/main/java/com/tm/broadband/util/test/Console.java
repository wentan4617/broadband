package com.tm.broadband.util.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Console {
	
	public static void log(Object obj){
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
				System.out.println(clazzName+" = {");
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
								System.out.print("   "+capital(methodName.substring(3, methodName.length()))+" : {");
								if(allSub!=null && allSub.size()>0){
									allSubs(method, obj, methodName);
								} else {
									System.out.print("   }");
								}
							} else {
								System.out.print("   "+capital(methodName.substring(methodName.startsWith("is") ? 2 : 3, methodName.length()))+" : ");
								if(method.invoke(obj) instanceof String){
									System.out.print("\""+method.invoke(obj)+"\"");
								} else {
									System.out.print(method.invoke(obj));
								}
							}
						} catch (IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
						if(countComma<countGetMethod-1){
							System.out.println(",");
							countComma++;
						} else {
							System.out.println();
						}
					}
				}
				System.out.println("}");
				
			} else {
				System.out.println("Methods is empty!");
			}
			
		} else {
			System.out.println("undefined");
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
			System.out.print("\n");
			if(subObj instanceof String){
				System.out.print("     \""+subObj+"\"");
				if(countPrimitiveComma<allSub.size()-1){
					System.out.print(",");
					countPrimitiveComma++;
				} else {
					System.out.println();
				}
			} else if(subObj instanceof Number){
				System.out.print("      "+subObj+"");
				if(countPrimitiveComma<allSub.size()-1){
					System.out.print(",");
					countPrimitiveComma++;
				} else {
					System.out.println();
				}
			} else if(subObj instanceof Boolean){
				System.out.print("      "+subObj+"");
				if(countPrimitiveComma<allSub.size()-1){
					System.out.print(",");
					countPrimitiveComma++;
				} else {
					System.out.println();
				}
			} else  if(subObj instanceof Character){
				System.out.print("      "+subObj+"");
				if(countPrimitiveComma<allSub.size()-1){
					System.out.print(",");
					countPrimitiveComma++;
				} else {
					System.out.println();
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
									System.out.print("      "+capital(subMethodName.substring(subMethodName.startsWith("is") ? 2 : 3, subMethodName.length()))+" : "+subMethod.invoke(obj));
							} catch (IllegalAccessException | IllegalArgumentException
									| InvocationTargetException e) {
								e.printStackTrace();
							}
							if(countComma<countGetMethod-1){
								System.out.println(",");
								countComma++;
							} else {
								System.out.println();
							}
						}
					}
				}
			}
		}
		System.out.print("   }");
	}
}
