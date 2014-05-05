package com.tm.broadband.util;


public class Test {

	public static void main(String[] args) throws Exception {

		// String[] pwds = new String[] { "1", "5", "9", "8", "7", "6", "4",
		// "3", "2", "q",
		// "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g",
		// "h", "j",
		// "k", "l", "z", "x", "c", "v", "b", "n", "m", "0"};
		// Random random = new Random();
		// System.out.println(pwds[random.nextInt(36)]);
		String finalResult = new BroadbandCapability().getCapabilityResultByAddress("290 Queen Street, Auckland");
		System.out.println(finalResult);
	}

}
