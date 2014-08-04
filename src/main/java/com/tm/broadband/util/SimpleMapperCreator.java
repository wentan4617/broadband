package com.tm.broadband.util;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author DON CHEN
 *
 */
public class SimpleMapperCreator {

	private String modelPath = "com.tm.broadband.model.";
	private String namespace = "com.tm.broadband.mapper.";
	private String jMapperPackage = "src/main/java/com/tm/broadband/mapper/";
	private String xMapperPath = "src/main/resources/mappers/";
	private String table = "";
	private String model = "";
	private String lowerModel = "";
	private String abbrModel = "";
	private String author = "";
	private String attributes[] = null;
	
	public void initial(){
		writer(this.getModel()+"Mapper","x");
		writer(this.getModel()+"Mapper","j");
	}

	// WRITER
	public void writer(String fileName, String type){
		StringBuffer context = new StringBuffer();
		switch (type) {
		case "x":
			
			/* HEADER */
			context.append(this.xHeader());
			
			/* START xMapper */
			context.append("<mapper namespace=\""+namespace+fileName+"\">\n\n");
			
			// CONTENTS
			// BEGIN DEFINITION AREA
			context.append("\t<!-- DEFINITION AREA -->\n\n");
			
			// COLUMN
			context.append("\t<sql id=\""+this.getLowerModel()+"Columns\">\n");
			for(int i=0; i<this.getAttributes().length; i++){
				context.append("\t\t"+this.getAbbrModel()+".`"+this.getAttributes()[i]+"`\t\t\t\tas "+this.getAbbrModel()+"_"+this.getAttributes()[i]);
				if(i<this.getAttributes().length-1){
					context.append(",\n");
				} else {
					context.append("\n");
				}
			}
			context.append("\t</sql>\n\n");
			
			// RESULTMAP
			context.append("\t<resultMap type=\""+this.getModel()+"\" id=\""+this.getLowerModel()+"ResultMap\">\n");
			for (int i = 0; i < this.getAttributes().length; i++) {
				if(i==0){
					context.append("\t\t<id property=\""+this.getAttributes()[i]+"\" column=\""+this.getAbbrModel()+"_"+this.getAttributes()[i]+"\" />\n");
				} else {
					context.append("\t\t<result property=\""+this.getAttributes()[i]+"\" column=\""+this.getAbbrModel()+"_"+this.getAttributes()[i]+"\" />\n");
				}
			}
			context.append("\t</resultMap>\n\n");
			
			// WHERE
			context.append("\t<sql id=\""+this.getLowerModel()+"Where\">\n");
			context.append("\t\t<where>\n");
			for (int i = 0; i < this.getAttributes().length; i++) {
				if(i==0){
					context.append("\t\t\t<if test=\"params."+this.getAttributes()[i]+" != null\">"+this.getAbbrModel()+"."+this.getAttributes()[i]+" = #{params."+this.getAttributes()[i]+"}</if>\n");
				} else {
					context.append("\t\t\t<if test=\"params."+this.getAttributes()[i]+" != null\">and "+this.getAbbrModel()+"."+this.getAttributes()[i]+" = #{params."+this.getAttributes()[i]+"}</if>\n");
				}
			}
			context.append("\t\t</where>\n");
			context.append("\t</sql>\n\n");
			
			context.append("\t<!-- // DEFINITION AREA -->\n\n");
			// END DEFINITION AREA
			
			context.append("\t<!-- =================================================================================== -->\n\n");
			
			// BEGIN CRUD
			
			// SELECT ALL
			context.append("\t<!-- SELECT AREA -->\n\n");
			context.append("\t<select id=\"select"+this.getModel()+"\" parameterType=\""+this.getModel()+"\" resultType=\""+this.getModel()+"\">\n");
			context.append("\t\tselect * from "+this.getTable()+" as "+this.getAbbrModel()+"\n");
			context.append("\t\t<include refid=\""+this.getLowerModel()+"Where\"/>\n");
			context.append("\t</select>\n\n");
			
			// SELECT PAGE
			context.append("\t<!-- select "+this.getLowerModel()+" page -->\n");
			context.append("\t<select id=\"select"+this.getModel()+"sByPage\" parameterType=\"Page\" resultMap=\""+this.getLowerModel()+"ResultMap\">\n");
			context.append("\t\tselect \n");
			context.append("\t\t\t<include refid=\""+this.getLowerModel()+"Columns\"/>\n");
			context.append("\t\t\tfrom "+this.getTable()+" as "+this.getAbbrModel()+"\n");
			context.append("\t\t\t<include refid=\""+this.getLowerModel()+"Where\"/>\n");
			context.append("\t\t\t<if test=\"params.orderby != null\">${params.orderby}</if>\n");
			context.append("\t\t\tlimit #{pageOffset}, #{pageSize}\n");
			context.append("\t</select>\n\n");
			
			// SELECT COUNT
			context.append("\t<!-- select "+this.getLowerModel()+" amount -->\n");
			context.append("\t<select id=\"select"+this.getModel()+"sSum\" parameterType=\"Page\" resultType=\"int\">\n");
			context.append("\t\tselect count(*) from "+this.getTable()+" as "+this.getAbbrModel()+"\n");
			context.append("\t\t<include refid=\""+this.getLowerModel()+"Where\"/>\n");
			context.append("\t</select>\n\n");
			
			context.append("\t<!-- // END SELECT AREA -->\n\n");
			
			context.append("\t<!-- =================================================================================== -->\n\n");
			
			// INSERT
			context.append("\t<!-- INSERT AREA -->\n\n");
			
			context.append("\t<insert id=\"insert"+this.getModel()+"\" parameterType=\""+this.getModel()+"\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n");
			context.append("\t\tINSERT INTO `"+this.getTable()+"`(\n");
			int separator = 5;
			for (int i = 0; i < this.getAttributes().length; i++) {
				if(i==0){
					context.append("\t\t\t");
				}
				if(i==separator){
					separator+=5;
					context.append("\n");
					context.append("\t\t\t");
				}
				context.append("`"+this.getAttributes()[i]+"`");
				if(i<this.getAttributes().length-1){
					context.append(", ");
				}
			}
			context.append("\n");
			context.append("\t\t) VALUES (\n");
			separator = 5;
			for (int i = 0; i < this.getAttributes().length; i++) {
				if(i==0){
					context.append("\t\t\t");
				}
				if(i==separator){
					separator+=5;
					context.append("\n");
					context.append("\t\t\t");
				}
				context.append("#{"+this.getAttributes()[i]+"}");
				if(i<this.getAttributes().length-1){
					context.append(", ");
				}
			}
			context.append("\n");
			context.append("\t\t)\n");
			context.append("\t</insert>\n\n");
			
			context.append("\t<!-- // END INSERT AREA -->\n\n");
			
			context.append("\t<!-- =================================================================================== -->\n\n");

			// UPDATE
			context.append("\t<!-- UPDATE AREA -->\n\n");
			
			context.append("\t<!-- update "+this.getLowerModel()+" -->\n");
			context.append("\t<update id=\"update"+this.getModel()+"\" parameterType=\""+this.getModel()+"\">\n");
			context.append("\t\tupdate "+this.getTable()+"\n");
			context.append("\t\t<set>\n");
			for (int i = 1; i < this.getAttributes().length; i++) {
				if(i<this.getAttributes().length-1){
					context.append("\t\t\t<if test=\""+this.getAttributes()[i]+" != null\">"+this.getAttributes()[i]+" = #{"+this.getAttributes()[i]+"},</if>\n");
				} else {
					context.append("\t\t\t<if test=\""+this.getAttributes()[i]+" != null\">"+this.getAttributes()[i]+" = #{"+this.getAttributes()[i]+"}</if>\n");
				}
			}
			context.append("\t\t</set>\n");
			context.append("\t\t<where>\n");
			context.append("\t\t\t<if test=\"params.id != null\">id = #{params.id}</if>\n");
			context.append("\t\t</where>\n");
			context.append("\t</update>\n\n");
			
			context.append("\t<!-- // END UPDATE AREA -->\n\n");
			
			context.append("\t<!-- =================================================================================== -->\n");
			
			//DELETE
			context.append("\t<!-- DELETE AREA -->\n\n");
			
			context.append("\t<delete id=\"delete"+this.getModel()+"ById\" parameterType=\"int\">\n");
			context.append("\t\tDELETE FROM "+this.getTable()+" WHERE id = #{0}\n");
			context.append("\t</delete>\n\n");
			
			context.append("\t<!-- // END DELETE AREA -->\n\n");
			// END CRUD
			
			/* END xMapper */
			context.append("</mapper>");
			
			// FILE PATH
			fileName=xMapperPath+fileName+".xml";
			break;
			
		case "j":
			context.append(jHeader());
			
			/* FILE PATH */
			fileName=jMapperPackage+fileName+".java";
			break;
			
		}
		try {
			FileWriter writer = new FileWriter(fileName);
			writer.write(context.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String xHeader(){
		StringBuffer buff = new StringBuffer();
		buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buff.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n");
		buff.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n\n");
		buff.append("<!-- DEFINITION AREA --><!-- // DEFINITION AREA -->\n");
		buff.append("<!-- =================================================================================== -->\n");
		buff.append("<!-- SELECT AREA --><!-- // END SELECT AREA -->\n");
		buff.append("<!-- =================================================================================== -->\n");
		buff.append("<!-- INSERT AREA --><!-- // END INSERT AREA -->\n");
		buff.append("<!-- =================================================================================== -->\n");
		buff.append("<!-- UPDATE AREA --><!-- // END UPDATE AREA -->\n");
		buff.append("<!-- =================================================================================== -->\n");
		buff.append("<!-- DELETE AREA --><!-- // END DELETE AREA -->\n\n");
		return buff.toString();
	}
	
	public String jHeader(){
		StringBuffer buff = new StringBuffer();
		buff.append("package com.tm.broadband.mapper;\n\n\n");
		buff.append("import java.util.List;\n\n");
		buff.append("import com.tm.broadband.model."+this.getModel()+";\n");
		buff.append("import com.tm.broadband.model.Page;\n\n");
		buff.append("public interface "+this.getModel()+"Mapper {\n\n");
		buff.append("/**\n");
		buff.append(" * mapping "+this.getTable()+", "+this.getLowerModel()+" DAO component\n");
		buff.append(" * \n");
		buff.append(" * @author "+this.getAuthor()+"\n");
		buff.append(" * \n");
		buff.append("  */\n\n");
		buff.append("\t/* SELECT AREA */\n\n");
		buff.append("\tList<"+this.getModel()+"> select"+this.getModel()+"("+this.getModel()+" "+this.getAbbrModel()+");\n");
		buff.append("\tList<"+this.getModel()+"> select"+this.getModel()+"sByPage(Page<"+this.getModel()+"> page);\n");
		buff.append("\tint select"+this.getModel()+"sSum(Page<"+this.getModel()+"> page);\n\n");
		buff.append("\t/* // END SELECT AREA */\n");
		buff.append("\t/* =================================================================================== */\n");
		buff.append("\t/* INSERT AREA */\n\n");
		buff.append("\tvoid insert"+this.getModel()+"("+this.getModel()+" "+this.getAbbrModel()+");\n\n");
		buff.append("\t/* // END INSERT AREA */\n");
		buff.append("\t/* =================================================================================== */\n");
		buff.append("\t/* UPDATE AREA */\n\n");
		buff.append("\tvoid update"+this.getModel()+"("+this.getModel()+" "+this.getAbbrModel()+");\n\n");
		buff.append("\t/* // END UPDATE AREA */\n");
		buff.append("\t/* =================================================================================== */\n");
		buff.append("\t/* DELETE AREA */\n\n");
		buff.append("\tvoid delete"+this.getModel()+"ById(int id);\n\n");
		buff.append("\t/* // END DELETE AREA */\n\n");
		buff.append("}\n");
		return buff.toString();
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
		
		try {
			Class<?> classType = Class.forName(this.getModelPath()+this.getModel());
			Field[] fields = classType.getDeclaredFields();
			this.setAttributes(new String[fields.length]);
			for (int j = 0; j < fields.length; j++) {
				// If not serialVersionUID
				if(!"serialVersionUID".equals(fields[j].getName())){
					this.getAttributes()[j] = fields[j].getName();
				// Else reassemble fields array
				} else {
					this.setAttributes(new String[fields.length-1]);
					Field[] tempFields = new Field[fields.length-1];
					for (int i = 0; i < fields.length; i++) {
						if(i<fields.length-1){
							tempFields[i] = fields[i+1];
						}
					}
					fields = tempFields;
					j--;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getjMapperPackage() {
		return jMapperPackage;
	}

	public void setjMapperPackage(String jMapperPackage) {
		this.jMapperPackage = jMapperPackage;
	}

	public String getxMapperPath() {
		return xMapperPath;
	}

	public void setxMapperPath(String xMapperPath) {
		this.xMapperPath = xMapperPath;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	public String getLowerModel() {
		this.setLowerModel(this.getModel().substring(0, 1).toLowerCase()+this.getModel().substring(1));
		return lowerModel;
	}

	public void setLowerModel(String lowerModel) {
		this.lowerModel = lowerModel;
	}

	public String getAbbrModel() {
		Pattern p = Pattern.compile("[a-z]");
		Matcher m = p.matcher(this.getModel());
		this.setAbbrModel(m.replaceAll("").toLowerCase());
		return abbrModel;
	}

	public void setAbbrModel(String abbrModel) {
		this.abbrModel = abbrModel;
	}
	

}
