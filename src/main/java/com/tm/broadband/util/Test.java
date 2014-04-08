package com.tm.broadband.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		String[] pwds = new String[] { "1", "5", "9", "8", "7", "6", "4", "3", "2", "q",
				"w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j",
				"k", "l", "z", "x", "c", "v", "b", "n", "m", "0"};
		Random random = new Random();
		System.out.println(pwds[random.nextInt(36)]);
	}

	public static void test1() {

	}

}
