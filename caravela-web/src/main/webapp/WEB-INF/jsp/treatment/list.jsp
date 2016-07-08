<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
    
	    <div class="page-header">
			<h1>Treatments</h1>
			<div class="bnt-group">
				<a href="${linkTo[TreatmentController].form}" class="btn btn-success pull-right"  role="button">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					<span class="glyphicon-class">new treatment</span>
				</a>
			</div>
		</div>
		
		
	<c:if test="${not empty treatments}">
		<div class="panel panel-default">
		 	<table class="table">
			    <thead>
			    	<tr>
				    	<th>Name</th>
				    	<th>Description</th>
				    	<th>Action</th>
				    </tr>
				</thead>
				<tbody>
					<c:forEach var="treatment" items="${treatments}">
						<tr>
							<td>${treatment.name}</td>
							<td>${treatment.description}</td>
							<td>
								<a class="btn btn-success glyphicon glyphicon-eye-open" href="<c:url value="/contig/view/${contig.id}/genus/readsOnContig"/>" role="button"></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
		 	</table>
		</div>
	</c:if>
	</div>
   </jsp:body>
</t:default>


