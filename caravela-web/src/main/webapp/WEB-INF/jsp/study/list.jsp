<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
    
	    <div class="page-header">
			<h1>Studies</h1>
			<div class="bnt-group">
				<a href="${linkTo[StudyController].form}" class="btn btn-success pull-right"  role="button">
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					<span class="glyphicon-class">new study</span>
				</a>
			</div>
		</div>
		
		
	<c:if test="${not empty studies}">
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
					<c:forEach var="study" items="${studies}">
						<tr>
							<td>${study.name}</td>
							<td>${study.description}</td>
							<td>
								<a class="btn btn-success glyphicon glyphicon-eye-open" href="${linkTo[SampleController].listByStudy}${study.id}" role="button"></a>
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


