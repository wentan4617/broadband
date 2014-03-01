<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="panel panel-default">
	<div class="panel-heading">Transaction Detail</div>
	<div id="txContainer"></div>
	<%-- <c:if test="${fn:length(transactionPage.results) > 0 }">
		<table class="table">
			<thead>
				<tr>
					<th>Transaction Date</th>
					<th>Amount</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tx" items="${transactionPage.results }">
					<tr>
						<td><fmt:formatDate  value="${tx.transaction_date }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>${tx.amount}</td>
						<td>&nbsp;</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="11">
						<ul class="pagination">
							<c:forEach var="num" begin="1" end="${transactionPage.totalPage }" step="1">
								<li class="${transactionPage.pageNo == num ? 'active' : ''}"><a
									href="${ctx}/broadband-user/crm/customer/view/${num}">${num}</a>
								</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
			</tfoot>
		</table>
	</c:if>
	<c:if test="${fn:length(transactionPage.results) <= 0 }">
		<div class="panel-body">
			<div class="alert alert-warning">No records have been found.</div>
		</div>
	</c:if> --%>
</div>