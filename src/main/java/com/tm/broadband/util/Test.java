package com.tm.broadband.util;

import com.tm.broadband.util.broadband.BroadbandCapability;
import com.tm.broadband.util.itext.ITextFont;


public class Test {

	public static void main(String[] args) throws Exception {

		// String[] pwds = new String[] { "1", "5", "9", "8", "7", "6", "4",
		// "3", "2", "q",
		// "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g",
		// "h", "j",
		// "k", "l", "z", "x", "c", "v", "b", "n", "m", "0"};
		// Random random = new Random();
		// System.out.println(pwds[random.nextInt(36)]);
		String finalResult = BroadbandCapability.getCapabilityResultByAddress("奥克兰New North Road945");
		System.out.println(finalResult.substring(finalResult.lastIndexOf(",") + 1));
		
		System.out.println(ITextFont.arial_normal_6);
	}

}
