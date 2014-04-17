<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">${panelheading }</h4>
				</div>
				<div class="panel-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label for="plan_name" class="control-label col-md-4">Plan Name</label>
							<div class="col-md-3">
								<input value="${plan.plan_name}" type="text" id="plan_name" class="form-control" placeholder="Plan Name" data-error-field/>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_group" class="control-label col-md-4">Plan Group</label>
							<div class="col-md-3">
								<select id="plan_group" class="form-control">
									<option
										<c:if test="${plan.plan_group == ''}">
											selected="selected"
										</c:if>
									>none</option>
									<c:forEach var="p" items="plan-no-term,plan-term,plan-topup">
										<option value="${p}"
											<c:if test="${p == plan.plan_group}">
												selected="selected"
											</c:if>
										>${p}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-5">
								<div class="well">
									<span class="text-danger">When choose different options, it will display other input items.</span>
								</div>
							</div>
						</div>
						
						<div id="noTermContainer" style="display: 
							<c:if test="${plan.plan_group == 'plan-no-term' || plan.plan_group == 'plan-term'}">block</c:if>">
							<hr/>
							<div class="form-group">
								<label for="plan_prepay_months" class="control-label col-md-4">Prepay Months Amount</label>
								<div class="col-md-3">
									<div class="input-group">
										<input type="text" value="${plan.plan_prepay_months}" id="plan_prepay_months" class="form-control" placeholder="" data-error-field/>
										<span class="input-group-addon">Months</span>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="term_period" class="control-label col-md-4">Term Period</label>
								<div class="col-md-3">
									<div class="input-group">
										<input type="text" value="${plan.term_period}" id="term_period" class="form-control" placeholder="" data-error-field/>
										<span class="input-group-addon">Months</span>
									</div>
								</div>
							</div>
							<hr/>
						</div>
						
						<div class="form-group">
							<label for="plan_class" class="control-label col-md-4">Plan Class</label>
							<div class="col-md-3">
								<select id="plan_class" class="form-control">
									<option
										<c:if test="${plan.plan_class == ''}">
											selected="selected"
										</c:if>
									>none</option>
									<c:forEach var="p" items="personal,business">
										<option value="${p}"
											<c:if test="${p == plan.plan_class}">
												selected="selected"
											</c:if>
										>${p}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_type" class="control-label col-md-4">Plan Type</label>
							<div class="col-md-3">
								<select id="plan_type" class="form-control">
									<option
										<c:if test="${plan.plan_type == ''}">
											selected="selected"
										</c:if>
									>None</option>
									<c:forEach var="p" items="ADSL,VDSL,UFB">
										<option value="${p}"
											<c:if test="${p == plan.plan_type}">
												selected="selected"
											</c:if>
										>${p}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_sort" class="control-label col-md-4">Plan Sort</label>
							<div class="col-md-3">
								<select id="plan_sort" class="form-control">
									<option
										<c:if test="${plan.plan_sort == ''}">
											selected="selected"
										</c:if>
									>None</option>
									<c:forEach var="p" items="CLOTHING,NAKED">
										<option value="${p}"
											<c:if test="${p == plan.plan_sort}">
												selected="selected"
											</c:if>
										>${p}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_price" class="control-label col-md-4">Monthly fee (Inc GST)</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.plan_price}" id="plan_price" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="plan_new_connection_fee" class="control-label col-md-4">New Connection fee (Inc GST)</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.plan_new_connection_fee}" id="plan_new_connection_fee" class="form-control" placeholder="" data-error-field/>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="data_flow" class="control-label col-md-4">Data Flow</label>
							<div class="col-md-3">
								<div class="input-group">
									<input type="text" value="${plan.data_flow}" id="data_flow" class="form-control" placeholder="" data-error-field/>
									<span class="input-group-addon">GB</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="pstn_count" class="control-label col-md-4">PSTN Amount</label>
							<div class="col-md-3">
								<input type="text" value="${plan.pstn_count}" id="pstn_count" class="form-control" placeholder="" data-error-field/>
							</div>
						</div>
						<div class="form-group">
							<label for="pstn_rental_amount" class="control-label col-md-4">PSTN Rental Amount</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${plan.pstn_rental_amount}" id="pstn_rental_amount" class="form-control" placeholder=""  data-error-field/>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label for="plan_status" class="control-label col-md-4">Status</label>
							<div class="col-md-3">
								<select id="plan_status" class="form-control">
									<option
										<c:if test="${plan.plan_status == ''}">
											selected="selected"
										</c:if>
									>None</option>
									<c:forEach var="p" items="active,selling,disable">
										<option value="${p}"
											<c:if test="${p == plan.plan_status}">
												selected="selected"
											</c:if>
										>${p}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="button" class="btn btn-success" id="save-btn">Save</button>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<label for="plan_desc" class="control-label col-md-4" data-error-field>Description</label>
							<div class="col-md-8">
								<textarea id="plan_desc" class="form-control" rows="12">${plan.plan_desc}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="memo" class="control-label col-md-4" data-error-field>Memo</label>
							<div class="col-md-8">
								<textarea id="memo" class="form-control" rows="6">${plan.memo}</textarea>
							</div>
						</div>
					</form>
					
					<form:form modelAttribute="plan" method="post" action="${ctx}/broadband-user/plan/pic/edit" class="form-horizontal" enctype="multipart/form-data">
						<form:hidden path="id"/>
						<hr/>
						<div class="page-header" style="margin:0;">
							<h3 class="text-success"><strong>Plan Pictures</strong></h3>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img1" class="col-sm-4 control-label">Picture 1:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 1 Path" />
						    </div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img2" class="col-sm-4 control-label">Picture 2:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 2 Path" />
						    </div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img3" class="col-sm-4 control-label">Picture 3:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 3 Path" />
						    </div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success">Save Pictures</button>
							</div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img1==null || plan.img1==''}">
								      	src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAADICAYAAABS39xVAAAJXklEQVR4Xu3bv2sUaxQG4AmiqKCl2onYai3471vZiJ3YCWIhSLDRxh+5zMKE787dmDPZqO/JfQQbOdm8+5wvL7OT8ej4+Phk8ocAAQINBI4UVoMtiUiAwE5AYTkIBAi0EVBYbVYlKAECCssZIECgjYDCarMqQQkQUFjOAAECbQQUVptVCUqAgMJyBggQaCOgsNqsSlACBBSWM0CAQBsBhdVmVYISIKCwnAECBNoIKKw2qxKUAAGF5QwQINBGQGG1WZWgBAgoLGeAAIE2AgqrzaoEJUBAYTkDBAi0EVBYbVYlKAECCssZIECgjYDCarMqQQkQUFjOAAECbQQUVptVCUqAgMJyBggQaCOgsNqsSlACBBSWM0CAQBsBhdVmVYISIKCwnAECBNoIKKw2qxKUAAGF5QwQINBGQGG1WZWgBAgoLGeAAIE2AgqrzaoEJUBAYTkDBAi0EVBYbVYlKAECCssZIECgjYDCarMqQQkQUFjOAAECbQQUVptVCUqAgMJyBggQaCOgsNqsSlACBBSWM0CAQBsBhdVmVYISIKCwnAECBNoIKKw2qxKUAAGF5QwQINBGQGG1WZWgBAgoLGeAAIE2AgqrzaoEJUBAYTkDBAi0EVBYbVYlKAECCssZIECgjYDCarMqQQkQUFjOAAECbQQUVptVCUqAgMJyBggQaCOgsNqsSlACBBSWM0CAQBsBhdVmVYISIKCwnAECBNoIKKw2qxKUAAGF5QwQINBGQGG1WZWgBAgoLGeAAIE2AgqrzaoEJUBAYTkDBAi0EVBYbVYlKAECCssZIECgjYDCarMqQQkQUFjOAAECbQQUVptVCUqAgMJyBggQaCOgsNqsSlACBBSWM0CAQBsBhdVmVYISIKCwnAECBNoIKKw2qxKUAAGF5QwQINBGQGG1WZWgBAgoLGeAAIE2AgqrzaoEJUBAYTkDBAi0EVBYbVYlKAECCssZIECgjYDCarMqQQkQUFjOAAECbQQUVptVCUqAgMJyBggQaCOgsNqsSlACBBSWM0CAQBsBhdVmVYISIKCwnAECBNoIKKw2q/p10J8/f04vX76cvnz58q/BJ0+eTA8ePPjPF79582b68OHD6b8fHR1Nz58/n27dunXQbJXzrLx37tyZnj17dlCGLe+tmtdchoDCytjDQSm+f/8+vXjxYvrx48e0FNT79++nt2/f7l730aNH0+PHj0+/x/IDvZTUp0+fdrP7SmvLbPVNHB8fT69fv57m0lryju9hXVpbMmyZreY1lyOgsHJ2ceEkSzndvHlzd3Vy/fr13Wu9evVqmsthLICxLMYiO3R2S/ilVNbFtLyPsTgT8m55b2Z/r4DC+r2+f+TV9/2gj4V1//796enTp7ssS1msy23fa2yZ3fJGx49s40fWpZxOTk5OP55uybBldkteszkCCitnF5eaZN8P73jfaH11s/6Ydu/evdN7YufN7rtHdpE3s75SvHbtWjnD38h7kffoaw4TUFiH+UV99XgfaA62LpqxsMarrnl2/Nr5o+L8d7mJf97sjRs3Tu+Xza81X73Nfz9//nzqs76PtoYbsy2zvyvveD8vaoHCnCugsM4l6jkw3nTfd2P7vBJ6+PDh6Y3882bnAljf+F+ueL5+/Xrmbx9H2eUe2lhs42uel2Fr3p5blVphXeEzsP5YOH7EOq8AtlxhLVcsS8HMV0ZzYX38+PH0t4C/Yt5XVvO8K6wrfDgv+NYU1gXhOnzZIfelLnpPaPye61KsfAwcZ9LvuXU4A1cto8K6AhtdP3u0PPy573mnLb9J2zK7MI7fc/2byLPK6KyHW+f5LRm2zF6Btf8v34LCugJrXz5SrR/8/NPPNS0fCW/fvj3dvXt39yT9vifXxyundVktmZd/9xzWFTigl/gWFNYlYv6tl9p3JXXWD/p41bIU3Lt373blUnnS/azZpYTmp+2Xh1eXIl1/NFyuhNZlNd4DG/+b0PoK8jLy/q1d+b6HCSisw/xivnosqDHUn/i/hONvJOfvPRfUt2/fdk/ZL3+WK631oxf7APd9lNzy/wO3zMYsUJCSgMIqMRkiQCBBQGElbEEGAgRKAgqrxGSIAIEEAYWVsAUZCBAoCSisEpMhAgQSBBRWwhZkIECgJKCwSkyGCBBIEFBYCVuQgQCBkoDCKjEZIkAgQUBhJWxBBgIESgIKq8RkiACBBAGFlbAFGQgQKAkorBKTIQIEEgQUVsIWZCBAoCSgsEpMhggQSBBQWAlbkIEAgZKAwioxGSJAIEFAYSVsQQYCBEoCCqvEZIgAgQQBhZWwBRkIECgJKKwSkyECBBIEFFbCFmQgQKAkoLBKTIYIEEgQUFgJW5CBAIGSgMIqMRkiQCBBQGElbEEGAgRKAgqrxGSIAIEEAYWVsAUZCBAoCSisEpMhAgQSBBRWwhZkIECgJKCwSkyGCBBIEFBYCVuQgQCBkoDCKjEZIkAgQUBhJWxBBgIESgIKq8RkiACBBAGFlbAFGQgQKAkorBKTIQIEEgQUVsIWZCBAoCSgsEpMhggQSBBQWAlbkIEAgZKAwioxGSJAIEFAYSVsQQYCBEoCCqvEZIgAgQQBhZWwBRkIECgJKKwSkyECBBIEFFbCFmQgQKAkoLBKTIYIEEgQUFgJW5CBAIGSgMIqMRkiQCBBQGElbEEGAgRKAgqrxGSIAIEEAYWVsAUZCBAoCSisEpMhAgQSBBRWwhZkIECgJKCwSkyGCBBIEFBYCVuQgQCBkoDCKjEZIkAgQUBhJWxBBgIESgIKq8RkiACBBAGFlbAFGQgQKAkorBKTIQIEEgQUVsIWZCBAoCSgsEpMhggQSBBQWAlbkIEAgZKAwioxGSJAIEFAYSVsQQYCBEoCCqvEZIgAgQQBhZWwBRkIECgJKKwSkyECBBIEFFbCFmQgQKAkoLBKTIYIEEgQUFgJW5CBAIGSgMIqMRkiQCBBQGElbEEGAgRKAgqrxGSIAIEEAYWVsAUZCBAoCSisEpMhAgQSBBRWwhZkIECgJKCwSkyGCBBIEFBYCVuQgQCBkoDCKjEZIkAgQUBhJWxBBgIESgIKq8RkiACBBAGFlbAFGQgQKAkorBKTIQIEEgQUVsIWZCBAoCSgsEpMhggQSBBQWAlbkIEAgZKAwioxGSJAIEFAYSVsQQYCBEoCCqvEZIgAgQQBhZWwBRkIECgJKKwSkyECBBIEFFbCFmQgQKAk8A+VGQo6IwyiOwAAAABJRU5ErkJggg=="
								      </c:if>
								      <c:if test="${plan.img1!=null && plan.img1!=''}">
								      	src="${ctx}/public/upload/${plan.img1}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img2==null || plan.img2==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${plan.img2!=null && plan.img2!=''}">
								      	src="${ctx}/public/upload/${plan.img2}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${plan.img3==null || plan.img3==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${plan.img3!=null && plan.img3!=''}">
								      	src="${ctx}/public/upload/${plan.img3}"
								      </c:if>
							      >
							    </a>
							</div>
						</div>
					</form:form>
						
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	$('.selectpicker').selectpicker();
	
	$('#plan_group').change(function(){
		var val = this.value;
		if (val === 'plan-topup') {
			$('#topupContainer').show('fast');
			$('#noTermContainer').hide('fast');
			//$('#termContainer').hide('fast');
		} else if (val === 'plan-no-term') {
			$('#topupContainer').hide('fast');
			$('#noTermContainer').show('fast');
			//$('#termContainer').hide('fast');
		} else if (val === 'plan-term') {
			$('#topupContainer').hide('fast');
			$('#noTermContainer').show('fast');
			//$('#termContainer').show('fast');
		} else {
			$('#topupContainer').hide('fast');
			$('#noTermContainer').hide('fast');
			//$('#termContainer').hide('fast');
		}
	});
	
	$('#save-btn').on("click", function(){
		
		var $btn = $(this);
		$btn.button('loading');
		var plan = {
			id: '${plan.id}'
			, plan_name: $('#plan_name').val()
			, plan_group: $('#plan_group option:selected').val()
			, plan_prepay_months: $('#plan_prepay_months').val()
			, term_period: $('#term_period').val()
			, plan_class: $('#plan_class').val()
			, plan_type: $('#plan_type').val()
			, plan_sort: $('#plan_sort').val()
			, plan_price: $('#plan_price').val()
			, plan_new_connection_fee: $('#plan_new_connection_fee').val()
			, data_flow: $('#data_flow').val()
			, pstn_count: $('#pstn_count').val()
			, pstn_rental_amount: $('#pstn_rental_amount').val()
			, plan_status: $('#plan_status').val()
			, plan_desc: $('#plan_desc').val()
			, memo: $('#memo').val()
		};
		$.post('${ctx}${action}', plan, function(json){
			if (json.hasErrors) {
				$.jsonValidation(json, 'right');
			} else {
				window.location.href='${ctx}' + json.url;
			}
		}, 'json').always(function () {
			$btn.button('reset');
	    });
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />