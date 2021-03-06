<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<c:forEach items="${errors}" var="error">
		 			<div class="alert alert-danger" role="alert">
		 			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		 			<p>${error.message}</p>
		 			</div>
			</c:forEach>
	    
			<h1>Samples</h1>
		</div>
		<c:if test="${empty studyList}">
			<h3> Before creating a sample you need register a study.</h3>
			<a href="${linkTo[StudyController].form}" class="btn btn-success"  role="button">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						<span class="glyphicon-class">new study</span>
			</a> 
		</c:if>
		
		<c:if test="${not empty studyList}">
		
			<div class="form-group">
				<form action="<c:url value="/sample/list"/>" name="form-sample-study" class="form-inline" role="form"
							method="post">
					<label>Choose a study: </label>
					<select id="select-study-id" name="studyId"
								class="form-control">
						<c:forEach var="study" items="${studyList}">
							<option
										${studySelected eq study.id ? 'selected="selected"' : ''}
										value="${study.id}">${study.name}</option>
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
									<div class="btn-group" role="group">
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
												<div>
												<c:choose>
													<c:when test="${isSampleLoaderRunning}">
														<button type="button" class="btn btn-warning disabled">Processing ...</button>
													</c:when>
													
													<c:otherwise>
														<button type="button" class="btn btn-default disabled">Waiting to be processed</button>
													</c:otherwise>
												</c:choose>
												</div>
															
											</c:when>
											
											<c:when test="${sampleStatus == 'PROCESSING'}">
												<div>
													<button type="button" class="btn btn-warning disabled">Processing ...</button>
												</div>	
											</c:when>
											
											<c:when test="${sampleStatus == 'PROCESSED'}">
												<div class="col-md-3">
                                                    <ul class="list-unstyled">
                                                        <li>

                                                            <a href="<c:url value="/sample/analyze/${sample.id}"/>" class="btn btn-success" role="button" data-toggle="popover" data-trigger="hover" data-content="Click to analyze">
                                                                <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                                                                <span class="glyphicon-class">Analyze</span>
                                                            </a>
                                                        </li>
                                                        <li>

                                                            <a href="${linkTo[ContigReportController].report}${sample.id}" data-toggle="popover" data-trigger="hover" data-content="Click to download report taxonomically good resolved contigs" class="btn btn-primary" role="button">
                                                                <span class="glyphicon glyphicon-download" aria-hidden="true"></span>
                                                                <span class="glyphicon-class">Download taxonomically good resolved contigs</span>
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="${linkTo[TaxonomicReportController].taxonomicReport}${sample.id}" data-toggle="popover" data-trigger="hover" data-content="Click to download report reads no taxon classified by context" class="btn btn-primary" role="button">
                                                                <span class="glyphicon glyphicon-download" aria-hidden="true"></span>
                                                                <span class="glyphicon-class">Download reads classified by context</span>
                                                            </a>
                                                        </li>
                                                    </ul>
												</div>
												
											</c:when>
											
										</c:choose>
										<div>
											<form action="${linkTo[SampleController].delete}" name="form-sample-delete" class="form-inline" role="form" method="post">
													<input type="hidden" name="sampleId" value="${sample.id}">
													<button type="submit" class="btn btn-danger" aria-label="Left Align" data-toggle="popover" data-trigger="hover" data-content="Click to delete"> 
														<span class="glyphicon glyphicon-trash"aria-hidden="true"></span>
													</button>
											</form>		
										</div>
									</div>	
										
		
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


