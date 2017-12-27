<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1>Samples</h1>
		</div>
		
		<div class="jumbotron">
		      	<h3>Search by function on contig</h3>
		        		
		        		<form action="<c:url value="/sample/add"/>" name="form-new-sample" class="form-inline" role="form" method="post">
		        		
							<input type="hidden" name="sampleId" value="${sample.id}">
							<div class="form-group">
								<label>Choose a study: </label>
								<select id="select-study-id" name="studyId" class="form-control">
									<c:forEach var="study" items="${studyList}">
										<option ${studySelected eq study.id ? 'selected="selected"' : ''} value="${study.id}">${study.name}</option>
									</c:forEach>
								</select>
							</div>	
							
							
							<div class="form-group">
								<label class="col-sm-2 control-label">Name</label>
								<div class="col-sm-6">
									<input class="form-control" size="30" placeholder="Topoisomerase IA" type="text" name="productName">
								</div>
								<div class="col-sm-4">
									<button type="submit" class="btn btn-success">Find</button>
								</div>
							</div>
						</form>
					
						<form action="<c:url value="/gene/search/by/producSource"/>" class="form-horizontal" role="form" method="post">
							<input type="hidden" name="sampleId" value="${sample.id}">
							<div class="form-group">
								<label class="col-sm-2 control-label">Source</label>
								<div class="col-sm-6">
									<input class="form-control" size="10" placeholder="COG0550" type="text" name="productSource">
								</div>	
								<div class="col-sm-4">
									<button type="submit" class="btn btn-success">Find</button>
								</div>	
							</div>
						</form>
	      </div>

		
	</div>
   </jsp:body>
</t:default>


