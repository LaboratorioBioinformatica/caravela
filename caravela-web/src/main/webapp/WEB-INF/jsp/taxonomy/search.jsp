<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
	<div class="container">
		<div class="page-header">
			<h1>Analyze - ${sample.name} </h1>
		</div>
		<div class="panel-heading well">
			<h3>
				Search by contigs <small> where at least </small> <fmt:formatNumber type="percent" maxIntegerDigits="3" value="${taxonCoverage}" /> <small> of contig was mapped by </small>reads<small> assigned to taxon 
				</small> ${scientificName}. 
			</h3>
			<p>Number of contig found: <b>${numberOfContigFound}</b></p>
		</div>
	<hr>
	
	<div class="panel panel-default">
		<div class="panel-heading">Top 10 - functions found in contigs features</div>
		<table class="table">
				    <thead>
				    	<tr>
					    	<th>Total</th>
					    	<th>Product name</th>
					    	<th>Product source</th>
					    </tr>
					</thead>
					<tbody>
						<c:forEach var="geneProductCounterTo" end="10" items="${geneProductCounterToList}">
							<tr>
								<td>${geneProductCounterTo.total}</td>
								<td>${geneProductCounterTo.productName}</td>
								<td>

									<c:if test="${not empty geneProductCounterTo.productSource}">
										<span  class="glyphicon-class">${geneProductCounterTo.productSource}</span>
										<a href="<c:url value="/gene/search/by/producSource/${sample.id}/${geneProductCounterTo.productSource}"/>"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
			 	</table>
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
				    	<th>reads</th>
				    	<th>reads classified</th>
				    	<th>features</th>
				    	<th >TII</th>
				    	<th>Action</th>
				    </tr>
				</thead>
				<tbody>
					<c:forEach var="contig" items="${contigList}">
						<tr>
							<td>${contig.reference}</td>
							<td>${contig.size}</td>
							<td>${contig.numberOfReads}</td>
							<td>${contig.numberOfReadsClassified}</td>
							<td>${contig.numberOfFeatures}</td>
							<td>
								<fmt:formatNumber type="number" maxIntegerDigits="3" value="${contig.taxonomicIdentificationIndex}" />	
							</td>
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
