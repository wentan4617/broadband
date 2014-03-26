<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container" id="alertContainer">
	<c:if test="${success != null }">
		<div class="alert alert-success alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			${success }
		</div>
	</c:if>
	<c:if test="${error != null }">
		<div class="alert alert-danger alert-dismissable">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			${error}
		</div>
	</c:if>
	
	<!--style="display:none;" alert-dismissable data-dismiss="alert" aria-hidden="true"-->
</div>

<div id="tempAlertContainer" style="display:none;">
	<div id="alert-error" class="alert alert-danger alert-dismissable" >
		<button type="button" class="close" data-dismiss="alert" aria-hidden="true" >&times;</button>
		<span id="text-error"></span>
	</div>
</div>
