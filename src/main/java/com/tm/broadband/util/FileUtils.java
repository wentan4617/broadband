package com.tm.broadband.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	
	public static void copy(String inPath, String outPath){
		
		InputStream fis = null;
		OutputStream fos = null;
		
		try {
			
			File file = new File(outPath);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			
			fis = new BufferedInputStream(new FileInputStream(inPath));
			fos = new BufferedOutputStream(new FileOutputStream(outPath));
			byte[] buf = new byte[2048];
			int i;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
