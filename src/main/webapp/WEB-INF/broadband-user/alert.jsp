<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container" id="alertContainer">
	<c:if test="${success != null }">
		<div class="alert alert-success alert-dismissable fade in">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			${success }
		</div>
	</c:if>
	<c:if test="${error != null }">
		<div class="alert alert-danger alert-dismissable fade in">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			${error}
		</div>
	</c:if>
</div>

<div id="tempAlertSuccessContainer" style="display:none;">
	<div id="alert-success" class="alert alert-success alert-dismissable fade in" style="display:none;">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
		<span id="text-success"></span>
	</div>
</div>
<div id="tempAlertErrorContainer" style="display:none;">
	<div id="alert-error" class="alert alert-danger alert-dismissable fade in" style="display:none;">
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
		<span id="text-error"></span>
	</div>
</div>
