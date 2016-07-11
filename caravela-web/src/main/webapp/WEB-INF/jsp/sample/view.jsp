<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1>Samples</h1>
		</div>
		<c:if test="${not empty treatmentList}">
		
			<div class="form-group">
				<form action="<c:url value="/sample/list"/>" name="form-sample-treatment" class="form-inline" role="form"
							method="post">
					<label>Choose a treatment: </label>
					<select id="select-treatment-id" name="treatmentId"
								class="form-control">
						<c:forEach var="treatment" items="${treatmentList}">
							<option
										${treatmentSelected eq treatment.id ? 'selected="selected"' : ''}
										value="${treatment.id}">${treatment.name}</option>
						</c:forEach>
					</select>
					<button type="submit" class="btn btn-default">Search samples</button>
					<a href="${linkTo[SampleController].form}" class="btn btn-success"  role="button">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						<span class="glyphicon-class">new sample</span>
					</a>
		
				</form>
			</div>
		</c:if>
		
		<c:if test="${not empty sampleList}">
			<div class="panel panel-default">
				<div class="panel-heading">Samples</div>
			 	<table class="table">
				    <thead>
				    	<tr>
					    	<th>Sample name</th>
					    	<th>Sample info</th>
					    	<th>Action</th>
					    </tr>
					</thead>
					<tbody>
						<c:forEach var="sample" items="${sampleList}">
							<c:set var="sampleStatus" scope="request" value="${sample.sampleStatus}" />
							<tr>
								<td>${sample.name}</td>
								<td>${sample.description}</td>
								
								<td>
									
										<c:choose>
											<c:when test="${sampleStatus == 'CREATED'}">
											<div>
												<a href="${linkTo[SampleController].uploadSampleFileForm}?sampleId=${sample.id}" class="btn btn-default"  role="button">
													<span class="glyphicon glyphicon-upload" aria-hidden="true"></span>
													<span class="glyphicon-class">Upload</span>
												</a>
											</div>	
	
											</c:when>
											<c:when test="${sampleStatus == 'UPLOADED'}">
												<form action="${linkTo[SampleController].process}" name="form-sample-process" class="form-inline" role="form" method="post">
													<input type="hidden" name="sampleId" value="${sample.id}">
													<button type="submit" class="btn btn-warning" aria-label="Left Align"> 
														<span class="glyphicon glyphicon-play-circle"aria-hidden="true"></span>
														<span class="glyphicon-class">To process</span>
													</button>
												</form>		
											</c:when>
											<c:when test="${sampleStatus == 'PROCCESSED'}">
												<a class="btn btn-success glyphicon glyphicon-eye-open"
														href="<c:url value="/sample/analyze/${sample.id}"/>"
														role="button">
													<span>Analyze</span>
												</a>
											</c:when>
										</c:choose>
		
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


