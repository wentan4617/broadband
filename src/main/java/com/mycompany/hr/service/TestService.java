package com.mycompany.hr.service;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.mycompany.hr.definitions.HumanResource;
import com.mycompany.hr.schemas.EmployeeType;
import com.mycompany.hr.schemas.HolidayRequest;
import com.mycompany.hr.schemas.HolidayType;
import com.tm.broadband.util.DateUtils;

public class TestService {

	public TestService() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		

		HolidayType holiday = new HolidayType();
		holiday.setStartDate(DateUtils.dateToXmlDate(new Date()));
		holiday.setEndDate(DateUtils.dateToXmlDate(new Date()));

		EmployeeType employee = new EmployeeType();
		employee.setFirstName("xiong");
		employee.setLastName("yifan");
		employee.setNumber(BigInteger.valueOf(1));
		
		HolidayRequest hr = new HolidayRequest();
		hr.setHoliday(holiday);
		hr.setEmployee(employee);

		com.mycompany.hr.definitions.HumanResourceService hrs = new com.mycompany.hr.definitions.HumanResourceService();
		HumanResource hrce = (HumanResource) hrs.getHumanResourceSoap11();
		hrce.holiday(hr);
		
		/*ApplicationContext ac = new ClassPathXmlApplicationContext(
				"spring/application-config.xml");*/
		
	/*	String message = "<HolidayRequest xmlns=\"http://mycompany.com/hr/schemas\">";
		message += "<Holiday>";
		message += "<StartDate>2006-07-03</StartDate>";
		message += "<EndDate>2006-07-07</EndDate>";
		message += "</Holiday>";
		message += "<Employee>";
		message += "<Number>42</Number>";
		message += "<FirstName>xiong</FirstName>";
		message += "<LastName>yifan</LastName>";
		message += "</Employee>";
		message += "</HolidayRequest>";
		WebServiceTemplate simpleService = (WebServiceTemplate) ac.getBean("webServiceTemplate");
		StreamSource source = new StreamSource(new StringReader(message));
		StreamResult result = new StreamResult(System.out);
		simpleService.sendSourceAndReceiveToResult(source, result);*/

	}

}
