<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
	<div class="container">
		<div class="page-header">
			<h1>Analyze -  ${sample.name} </h1>
		</div>
		<div class="panel-heading"><h3></h3></div>
	<div class="form-group">	
		<form action="<c:url value="/sample/analyze"/>" class="form-inline" role="form" method="post">
			<input type="hidden" name="sampleId" value="${sample.id}">
			<label>Taxonomic Identification Index (between 0 and 1)</label>
			<input class="form-control" value="${tiiValue}" size="4" maxlength="4" type="text" name="tiiValue">
				
			<button type="submit" class="btn btn-default">Filter</button>
		</form>
	</div>
	<hr>

	<c:if test="${not empty contigList}">
		<div class="panel panel-default">
			<div class="panel-heading">Contigs</div>
		 	<table class="table">
			    <thead>
			    	<tr>
				    	<th>Reference</th>
				    	<th>Size</th>
				    	<th >TII</th>
				    	<th>reads</th>
				    	<th>reads classified</th>
				    	<th>features</th>
				    	<th>Action</th>
				    </tr>
				</thead>
				<tbody>
					<c:forEach var="contig" items="${contigList}">
						<tr>
							<td>${contig.reference}</td>
							<td>${contig.size}</td>
							<td>${contig.taxonomicIdentificationIndex}</td>
							<td>${contig.numberOfReads}</td>
							<td>${contig.numberOfReadsClassified}</td>
							<td>${contig.numberOfFeatures}</td>
							<td>
								<a class="btn btn-success glyphicon glyphicon-eye-open" href="<c:url value="/contig/view/${contig.id}"/>" role="button"></a>
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


