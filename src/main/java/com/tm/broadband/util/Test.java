package com.tm.broadband.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Test {

	public static void main(String[] args) throws Exception {

		// String[] pwds = new String[] { "1", "5", "9", "8", "7", "6", "4",
		// "3", "2", "q",
		// "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g",
		// "h", "j",
		// "k", "l", "z", "x", "c", "v", "b", "n", "m", "0"};
		// Random random = new Random();
		// System.out.println(pwds[random.nextInt(36)]);
		// String finalResult =
		// BroadbandCapability.getCapabilityResultByAddress("奥克兰New North Road945");
		// System.out.println(finalResult.substring(finalResult.lastIndexOf(",")
		// + 1));
		//
		// System.out.println(ITextFont.arial_normal_6);

		// Customer c = new Customer();
		// c.setFirst_name("<script>asdasdasd");
		// c.setLast_name("<script>asdasdasd");
		// System.out.println(CheckScriptInjection.isScriptInjection(c));

		// Map<String, Object> resultMap =
		// Calculation4PlanTermInvoice.servedMonthDetails(new
		// SimpleDateFormat("yyyy-MM-dd").parse("2014-05-14"), 89d);
		// System.out.println(resultMap.get("totalPrice"));
		// System.out.println(resultMap.get("remainingDays"));
		// System.out.println(resultMap.get("dailyPrice"));
		//
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		// boolean isServedCurrentMonth =
		// sdf.format(sdf.parse("2014-05-19")).equals(sdf.format(new Date()));
		// System.out.println(isServedCurrentMonth);

		// Calendar cal = Calendar.getInstance(Locale.CHINA);
		// String yyyy_MM = new SimpleDateFormat("yyyy-MM-dd").format(new
		// Date()).substring(0, 7);
		// cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(yyyy_MM+"-20"));
		// System.out.println(cal.getTime());

		try {
			File csv = new File("C:/TMS_20140428_EB05.csv"); // CSV文件

			BufferedReader br = new BufferedReader(new FileReader(csv));

			String line = "";
			
			while ((line = br.readLine()) != null) {
					
				// Split current line data into separate fields
				StringTokenizer st = new StringTokenizer(line, ",");
				
				while (st.hasMoreTokens()) {
					
					// Separate fields with TAB symbol
					System.out.print(st.nextToken() + "	");
					break;
				}
				System.out.println();
					
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
