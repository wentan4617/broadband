<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<hr />
<%-- <div class="form-group">
	<div class="col-md-offset-4">
		<ul class="list-unstyled">
			<c:forEach var="topup" items="${topups}">
				<li>
					<div class="checkbox">
						<label> 
							<form:checkbox path="topupArray"
								value="${topup.id}" /> ${topup.topup_name}, &dollar;
							${topup.topup_fee}, ${topup.topup_data_flow}GB
						</label>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div> --%>

<div class="form-group">
	<label for="topupArray" class="control-label col-md-4">Choose Top-Up Items for Plan Prepay</label>
	<div class="col-md-6">
		<form:select data-width="255px" data-style="btn-success" path="topupArray" items="${topups }" itemLabel="topup_name" itemValue="id" multiple="true" data-selected-text-format="count" class="selectpicker" ></form:select>
	</div>
</div>
<hr />