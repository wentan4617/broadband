<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<form:form modelAttribute="companyDetail" method="post" action="${ctx}${action }" class="form-horizontal">
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel-group" id="companyDetailAccordion">
				<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#companyDetailAccordion" href="#collapseCompanyDetail">${panelheading }</a>
							</h4>
						</div>
						<div id="collapseCompanyDetail" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="domain" class="control-label col-md-4">
												<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="Website address, e.g.: www.cyberpark.co.nz">Domain</a>
											</label>
											<div class="col-md-6">
												<form:input path="domain" class="form-control" placeholder="Domain" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="domain" cssErrorClass="error"/>
											</p>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="gst_registration_number" class="control-label col-md-4">
												<a href="javascript:void(0);" data-toggle="tooltip" data-placement="bottom" data-original-title="GST Registration Number">Gst Number</a>
											</label>
											<div class="col-md-6">
												<form:input path="gst_registration_number" class="form-control" placeholder="Gst Registration Number" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="gst_registration_number" cssErrorClass="error"/>
											</p>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="name" class="control-label col-md-4">Company Name</label>
											<div class="col-md-6">
												<form:input path="name" class="form-control" placeholder="Company Name" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="name" cssErrorClass="error"/>
											</p>
											
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											
											<label for="company_email" class="control-label col-md-4">Company Email</label>
											<div class="col-md-6">
												<form:input path="company_email" class="form-control" placeholder="Company Email" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="company_email" cssErrorClass="error"/>
											</p>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="bank_name" class="control-label col-md-4">Bank Name</label>
											<div class="col-md-6">
												<form:input path="bank_name" class="form-control" placeholder="Bank Name" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="bank_name" cssErrorClass="error"/>
											</p>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											
											<label for="company_email_password" class="control-label col-md-4">Email Password</label>
											<div class="col-md-6">
												<form:input path="company_email_password" class="form-control" placeholder="Company Email Password" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="company_email_password" cssErrorClass="error"/>
											</p>
										</div>
									</div>
								</div>
								
								
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="bank_account_name" class="control-label col-md-4">Bank Account Name</label>
											<div class="col-md-6">
												<form:input path="bank_account_name" class="form-control" placeholder="Bank Account Name" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="bank_account_name" cssErrorClass="error"/>
											</p>
										</div>
								
									</div>
									<div class="col-md-6">
										<div class="form-group">
											
											<label for="telephone" class="control-label col-md-4">Telephone</label>
											<div class="col-md-6">
												<form:input path="telephone" class="form-control" placeholder="Telephone" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="telephone" cssErrorClass="error"/>
											</p>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label for="bank_account_number" class="control-label col-md-4">Bank Account Number</label>
											<div class="col-md-6">
												<form:input path="bank_account_number" class="form-control" placeholder="Bank Account Number" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="bank_account_number" cssErrorClass="error"/>
											</p>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="fax" class="control-label col-md-4">Fax</label>
											<div class="col-md-6">
												<form:input path="fax" class="form-control" placeholder="Fax" />
											</div>
											<p class="help-block col-md-2">
												<form:errors path="fax" cssErrorClass="error"/>
											</p>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="billing_address" class="control-label col-md-2">Billing Address</label>
									<div class="col-md-9">
										<form:input id="billing_address" path="billing_address" class="form-control" placeholder="Billing Address" />
									</div>
									<p class="help-block col-md-1">
										<form:errors path="billing_address" cssErrorClass="error"/>
									</p>
								</div>
								<div class="form-group">
									<label for="address" class="control-label col-md-2">Address</label>
									<div class="col-md-9">
										<form:input id="address" path="address" class="form-control" placeholder="Address" />
									</div>
									<p class="help-block col-md-1">
										<form:errors path="address" cssErrorClass="error"/>
									</p>
								</div>
								<div class="form-group">
									<label for="google_map_address" class="control-label col-md-2">Google Map Address</label>
									<div class="col-md-9">
										<form:input path="google_map_address" class="form-control" placeholder="Google Map Address" />
									</div>
									<p class="help-block col-md-1">
										<form:errors path="google_map_address" cssErrorClass="error"/>
									</p>
								</div>
								<hr/>
								<div class="form-group">
									<div class="col-md-2 col-md-offset-2">
										<button type="submit" class="btn btn-success btn-lg btn-block">Save</button>
									</div>
								</div>
								<hr/>
								<div class="form-group">
									<label for="term_contracts" class="control-label col-md-2">Term Contracts</label>
									<div class="col-md-10">
										<form:textarea path="term_contracts" class="form-control" rows="30"/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="panel-group" id="tcBusinessRetailsAccordion" style="margin-top:20px;">
					<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-toggle="collapse"
										data-parent="#tcBusinessRetailsAccordion" href="#collapsetcBusinessRetails">T and C Business Retails</a>
								</h4>
							</div>
							<div id="collapsetcBusinessRetails" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<div class="form-group">
											<label for="tc_business_retails" class="control-label col-md-2">Terms &#38; Conditions<br/>(Business Retails)</label>
											<div class="col-md-10">
												<form:textarea path="tc_business_retails" class="form-control" rows="30"/>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				<div class="panel-group" id="tcBusinessWifiAccordion">
					<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-toggle="collapse"
										data-parent="#tcBusinessWifiAccordion" href="#collapsetcBusinessWifi">T and C Business Wifi</a>
								</h4>
							</div>
							<div id="collapsetcBusinessWifi" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<div class="form-group">
											<label for="tc_business_wifi" class="control-label col-md-2">Terms &#38; Conditions<br/>(Business Wifi)</label>
											<div class="col-md-10">
												<form:textarea path="tc_business_wifi" class="form-control" rows="30"/>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				<div class="panel-group" id="tcPersonalAccordion">
					<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-toggle="collapse"
										data-parent="#tcPersonalAccordion" href="#collapsetcPersonal">T and C Perosnal</a>
								</h4>
							</div>
							<div id="collapsetcPersonal" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<div class="form-group">
											<label for="tc_personal" class="control-label col-md-2">Terms &#38; Conditions<br/>(Personal)</label>
											<div class="col-md-10">
												<form:textarea path="tc_personal" class="form-control" rows="30"/>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				<div class="panel-group" id="tcUFBAccordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-toggle="collapse"
									data-parent="#tcUFBAccordion" href="#collapsetcUFB">T and C UFB</a>
							</h4>
						</div>
						<div id="collapsetcUFB" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="row">
									<div class="form-group">
										<label for="tc_ufb" class="control-label col-md-2">Terms &#38; Conditions<br/>(UFB)</label>
										<div class="col-md-10">
											<form:textarea path="tc_ufb" class="form-control" rows="30"/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
<div id="map_canvas" style="width:720px;height:600px;display:none;"></div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=places&region=NZ" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/autoCompleteAddress.js"></script>
<jsp:include page="../footer-end.jsp" />