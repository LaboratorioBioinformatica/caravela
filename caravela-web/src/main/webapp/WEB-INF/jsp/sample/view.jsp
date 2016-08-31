<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1>Samples</h1>
		</div>
		<c:if test="${empty treatmentList}">
			<h3> Before creating a sample you need register a Treatment.</h3>  
			<a href="${linkTo[TreatmentController].form}" class="btn btn-success"  role="button">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						<span class="glyphicon-class">new treatment</span>
			</a> 
		</c:if>
		
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
												<div id="sampleToBeProcess">
													<button type="button" id="bnt-process" class="btn btn-primary" value="${sample.id}" data-loading-text="Loading..." aria-label="Left Align"> 
														<span class="glyphicon glyphicon-play-circle"aria-hidden="true"></span>
														<span class="glyphicon-class">Process</span>
													</button>	
													<input type="hidden" name="sampleId" value="${sample.id}">
													
													<form action="${linkTo[SampleController].process}" name="form-sample-process" class="form-inline" role="form" method="post">
													</form>
													
												</div>			
											</c:when>
											
											<c:when test="${sampleStatus == 'PROCESSING'}">
												<div>
													<button type="button" class="btn btn-warning disabled">Processing ...</button>
												</div>	
											</c:when>
											
											<c:when test="${sampleStatus == 'PROCESSED'}">
												<div>
													<a href="<c:url value="/sample/analyze/${sample.id}"/>" class="btn btn-success" role="button" data-toggle="popover" data-trigger="hover" data-content="Click to analyze">
													<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
													</a>
												
													<a href="${linkTo[ContigReportController].report}${sample.id}" data-toggle="popover" data-trigger="hover" data-content="Click to download report taxonomically good resolved contigs" class="btn btn-primary" role="button">
														<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													</a>
													
													<a href="${linkTo[ContigReportController].taxonOnContigReport}${sample.id}" data-toggle="popover" data-trigger="hover" data-content="Click to download report taxa on contig good resolved contigs" class="btn btn-primary" role="button">
														<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													</a>
													
													<a href="${linkTo[TaxonomicReportController].report}${sample.id}" data-toggle="popover" data-trigger="hover" data-content="Click to download report reads no taxon classified by context" class="btn btn-primary" role="button">
														<span class="glyphicon glyphicon-download" aria-hidden="true"></span>
													</a>
												</div>													
												
											</c:when>
											
										</c:choose>
										<div>
											<form action="${linkTo[SampleController].deleteSample}" name="form-sample-delete" class="form-inline" role="form" method="post">
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
<script type="text/javascript">

$(document).ready(function(){
	
	$(function () {
		  $('[data-toggle="popover"]').popover()
	});
	
	$('#bnt-process').on('click', function(){
		var $btn = $(this).button('loading');
		var sampleIdToBeProcess = $(this).prop("value");
		$.post('${linkTo[SampleController].process}', {sampleId: sampleIdToBeProcess})
			.done(function(msg){
				window.location.href = "${linkTo[SampleController].listByTreatment}${treatmentSelected}";
			})
			.fail(function(xhr, status, error){
				console.log(xhr);
				console.log(status);
				console.log(error);
			});
		
	});
	
});

</script>
   </jsp:body>
</t:default>


