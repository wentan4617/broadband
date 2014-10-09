package com.tm.broadband.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BroadbandController {

	public BroadbandController() {
	}
	
	// download application form and DD Form
	@RequestMapping(value = "/download/{type}")
    public ResponseEntity<byte[]> downloadPDF(@PathVariable(value = "type") String type) throws IOException {
    	
		String name = "";

		if ("ADSL".equals(type)) {
			name = "broadband-app-form(business)ADSL.pdf";
		} else if ("VDSL".equals(type)) {
			name = "broadband-app-form(business)VDSL.pdf";
		} else if ("UFB".equals(type)) {
			name = "broadband-app-form(business)UFB.pdf";
		} else if ("ddform".equals(type)) {
			name = "NZ-DDR-cyberpark.pdf";
		}

		String uri = "C:\\Users\\Administrator\\Desktop\\Installation File\\apache-tomcat-7.0.53-windows-x64\\apache-tomcat-7.0.53\\" + name;
		//String uri = "C:\\Users\\AAA\\Desktop\\New folder\\" + name;
		Path path = Paths.get(uri);
		byte[] contents = Files.readAllBytes(path);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = name;
		headers.setContentDispositionFormData(filename, filename);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
		return response;
    }

}
