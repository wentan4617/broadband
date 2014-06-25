package com.tm.broadband.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StevenTest {
	
	public static void main(String[] args) {
		List<String> strs = new ArrayList<String>();
		strs.add("123");
		strs.add("321");
		int index = 0;
		for (String string : strs) {
			string +=index;
			Collections.replaceAll(strs, strs.get(index), string);
			index++;
		}
		for (String string : strs) {
			System.out.println(string);
		}
	}
}
