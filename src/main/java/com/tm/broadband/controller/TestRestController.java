package com.tm.broadband.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map; 
 
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tm.broadband.model.FileMeta;
import com.tm.broadband.model.Page;
import com.tm.broadband.model.Plan;
import com.tm.broadband.service.PlanService;

@RestController
public class TestRestController {

	private PlanService planService;

	@Autowired
	public TestRestController(PlanService planService) {
		this.planService = planService;
	}
	
	@RequestMapping(value = "/test/plan/view/page/{pageNo}")
	public Page<Plan> planView(Model model,
			@PathVariable(value = "pageNo") int pageNo) {

		Page<Plan> page = new Page<Plan>();
		page.setPageNo(pageNo);
		page.setPageSize(10);
		page.getParams().put("orderby", "order by plan_status desc, plan_type");
		this.planService.queryPlansByPage(page);

		return page;
	}
	
	@RequestMapping(value = "/test/upload/server", method = RequestMethod.POST)
	public List<FileMeta> uploadServer(MultipartHttpServletRequest request) {
		
		String plan_pic_path = request.getServletContext().getRealPath("/public/upload");
		
		// 1. build an iterator
		
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		List<FileMeta> files = new ArrayList<FileMeta>();

		// 2. get each file
		while (itr.hasNext()) {

			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			System.out.println(mpf.getOriginalFilename() + " uploaded! " + files.size());

			// 2.2 if files > 10 remove the first from the list
			/*
			 * if(files.size() >= 10) files.pop();
			 */

			// 2.3 create new fileMeta
			FileMeta fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize() / 1024 + " Kb");
			fileMeta.setFileType(mpf.getContentType());

			try {
				fileMeta.setBytes(mpf.getBytes());

				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(plan_pic_path + "/" + mpf.getOriginalFilename()));

			} catch (IOException e) {
				e.printStackTrace();
			}
			// 2.4 add to files
			files.add(fileMeta);
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		return files;
	}

}
