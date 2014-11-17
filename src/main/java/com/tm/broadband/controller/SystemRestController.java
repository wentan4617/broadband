package com.tm.broadband.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tm.broadband.model.JSONBean;
import com.tm.broadband.model.PlanIntroductions;
import com.tm.broadband.model.TermsConditions;
import com.tm.broadband.model.User;
import com.tm.broadband.model.WebsiteEditableDetails;
import com.tm.broadband.service.SystemService;
import com.tm.broadband.util.TMUtils;
import com.tm.broadband.validator.mark.UserLoginValidatedMark;

@RestController
public class SystemRestController {

	private SystemService systemService;

	@Autowired
	public SystemRestController(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@RequestMapping(value = "/broadband-user/login", method = RequestMethod.POST)
	public JSONBean<User> userLogin(
			@Validated(UserLoginValidatedMark.class) User user, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<User> json = new JSONBean<User>();
		json.setModel(user);

		if (result.hasErrors()) {
			TMUtils.setJSONErrorMap(json, result);
			return json;
		}

		User userSession = this.systemService.queryUserLogin(user);

		if (userSession == null) {
			json.getErrorMap().put("alert-error", "Incorrect account or password");
			return json;
		}
		
		String url = "/broadband-user/index/redirect";
		
		/*if("sales".equals(userSession.getUser_role()) || "agent".equals(userSession.getUser_role())){
			url = "/broadband-user/sales/online-ordering/redirect";
		}*/
		
		req.getSession().setAttribute("userSession", userSession);
		json.setUrl(url);

		return json;
	}
	
	@RequestMapping(value = "/broadband-user/system/website_editable_details_info", method = RequestMethod.GET)
	public JSONBean<WebsiteEditableDetails> toWebsiteEditDetails(
			Model model){
		
		JSONBean<WebsiteEditableDetails> json = new JSONBean<WebsiteEditableDetails>();
		
		json.setModel(this.systemService.queryWebsiteEditableDetail(null));
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/system/website_editable_details_info", method = RequestMethod.POST)
	public JSONBean<WebsiteEditableDetails> doWebsiteEditDetails(
			WebsiteEditableDetails wed, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<WebsiteEditableDetails> json = new JSONBean<WebsiteEditableDetails>();
		
		if(wed.getCompany_name()==null || "".equals(wed.getCompany_name())){
			json.getErrorMap().put("company_name", "Company Name Couldn't be Empty!");
		} else if(wed.getCompany_name_ltd()==null || "".equals(wed.getCompany_name_ltd())){
			json.getErrorMap().put("company_name_ltd", "Company Name Ltd Couldn't be Empty!");
		} else if(wed.getCompany_hot_line_no()==null || "".equals(wed.getCompany_hot_line_no())){
			json.getErrorMap().put("company_hot_line_no", "Company Hot Line Number Couldn't be Empty!");
		} else if(wed.getCompany_hot_line_no_alphabet()==null || "".equals(wed.getCompany_hot_line_no_alphabet())){
			json.getErrorMap().put("company_hot_line_no_alphabet", "Company Hot Line Number Alphabet Couldn't be Empty!");
		} else if(wed.getCompany_address()==null || "".equals(wed.getCompany_address())){
			json.getErrorMap().put("company_address", "Company Address Couldn't be Empty!");
		} else if(wed.getCompany_email()==null || "".equals(wed.getCompany_email())){
			json.getErrorMap().put("company_email", "Company Email Couldn't be Empty!");
		} else if(wed.getWebsite_year()==null || "".equals(wed.getWebsite_year())){
			json.getErrorMap().put("website_year", "Website Year Couldn't be Empty!");
		} else {
			json.getSuccessMap().put("alert-success", "Successfully Save Website Editable Details!");
			
			WebsiteEditableDetails wedQuery = this.systemService.queryWebsiteEditableDetail(null);
			if(wedQuery==null){
				this.systemService.createWebsiteEditableDetails(wed);
			} else {
				this.systemService.editWebsiteEditableDetails(wed);
			}
			
		}
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/system/plan_introductions_info", method = RequestMethod.GET)
	public JSONBean<PlanIntroductions> toPlanIntroductions(
			Model model){
		
		JSONBean<PlanIntroductions> json = new JSONBean<PlanIntroductions>();
		
		json.setModel(this.systemService.queryPlanIntroduction(null));
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/system/plan_introductions_info", method = RequestMethod.POST)
	public JSONBean<PlanIntroductions> doPlanIntroductions(
			PlanIntroductions pi, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<PlanIntroductions> json = new JSONBean<PlanIntroductions>();
		
		if(pi.getAdsl_title()==null || "".equals(pi.getAdsl_title())){
			json.getErrorMap().put("adsl_title", "ADSL Title Couldn't be Empty!");
		} else if(pi.getAdsl_content()==null || "".equals(pi.getAdsl_content())){
			json.getErrorMap().put("adsl_content", "ADSL Content Couldn't be Empty!");
		} else if(pi.getVdsl_title()==null || "".equals(pi.getVdsl_title())){
			json.getErrorMap().put("vdsl_title", "VDSL Title Couldn't be Empty!");
		} else if(pi.getVdsl_content()==null || "".equals(pi.getVdsl_content())){
			json.getErrorMap().put("vdsl_content", "VDSL Content Couldn't be Empty!");
		} else if(pi.getUfb_title()==null || "".equals(pi.getUfb_title())){
			json.getErrorMap().put("ufb_title", "UFB Title Couldn't be Empty!");
		} else if(pi.getUfb_content()==null || "".equals(pi.getUfb_content())){
			json.getErrorMap().put("ufb_content", "UFB Content Couldn't be Empty!");
		} else {
			json.getSuccessMap().put("alert-success", "Successfully Save Plan Introductions!");
			
			PlanIntroductions piQuery = this.systemService.queryPlanIntroduction(null);
			if(piQuery==null){
				this.systemService.createPlanIntroductions(pi);
			} else {
				this.systemService.editPlanIntroductions(pi);
			}
			
		}
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/system/terms_conditions_info", method = RequestMethod.GET)
	public JSONBean<TermsConditions> toTermsConditions(
			Model model){
		
		JSONBean<TermsConditions> json = new JSONBean<TermsConditions>();
		
		json.setModel(this.systemService.queryTermsCondition(null));
		
		return json;
	}
	
	@RequestMapping(value = "/broadband-user/system/terms_conditions_info", method = RequestMethod.POST)
	public JSONBean<TermsConditions> doTermsConditions(
			TermsConditions tc, BindingResult result,
			HttpServletRequest req) {
		
		JSONBean<TermsConditions> json = new JSONBean<TermsConditions>();
		
		if(tc.getTerm_contracts_title()==null || "".equals(tc.getTerm_contracts_title())){
			json.getErrorMap().put("term_contracts_title", "Term & Contracts Title Couldn't be Empty!");
		} else if(tc.getTerm_contracts()==null || "".equals(tc.getTerm_contracts())){
			json.getErrorMap().put("term_contracts", "Term & Contracts Couldn't be Empty!");
		} else if(tc.getTerms_conditions_business_retails_title()==null || "".equals(tc.getTerms_conditions_business_retails_title())){
			json.getErrorMap().put("terms_conditions_business_retails_title", "Terms Conditions Business Retails Title Couldn't be Empty!");
		} else if(tc.getTerms_conditions_business_retails()==null || "".equals(tc.getTerms_conditions_business_retails())){
			json.getErrorMap().put("terms_conditions_business_retails", "Terms Conditions Business Retails Couldn't be Empty!");
		} else if(tc.getTerms_conditions_business_wifi_title()==null || "".equals(tc.getTerms_conditions_business_wifi_title())){
			json.getErrorMap().put("terms_conditions_business_wifi_title", "Terms Conditions Business Wifi Title Couldn't be Empty!");
		} else if(tc.getTerms_conditions_business_wifi()==null || "".equals(tc.getTerms_conditions_business_wifi())){
			json.getErrorMap().put("terms_conditions_business_wifi", "Terms Conditions Business Wifi Couldn't be Empty!");
		} else if(tc.getTerms_conditions_personal_title()==null || "".equals(tc.getTerms_conditions_personal_title())){
			json.getErrorMap().put("terms_conditions_personal_title", "Terms Conditions Personal Title Couldn't be Empty!");
		} else if(tc.getTerms_conditions_personal()==null || "".equals(tc.getTerms_conditions_personal())){
			json.getErrorMap().put("terms_conditions_personal", "Terms Conditions Personal Couldn't be Empty!");
		} else if(tc.getTerms_conditions_ufb_title()==null || "".equals(tc.getTerms_conditions_ufb_title())){
			json.getErrorMap().put("terms_conditions_ufb_title", "Terms Conditions UFB Title Couldn't be Empty!");
		} else if(tc.getTerms_conditions_ufb()==null || "".equals(tc.getTerms_conditions_ufb())){
			json.getErrorMap().put("terms_conditions_ufb", "Terms Conditions UFB Couldn't be Empty!");
		} else {
			
			TermsConditions tcQuery = this.systemService.queryTermsCondition(null);
			if(tcQuery==null){
				this.systemService.createTermsConditions(tc);
			} else {
				this.systemService.editTermsConditions(tc);
			}
			
			json.getSuccessMap().put("alert-success", "Successfully Save Terms Conditions!");
		}
		
		return json;
	}
	
}
