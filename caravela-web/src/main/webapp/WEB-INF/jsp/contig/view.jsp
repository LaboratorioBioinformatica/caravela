<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
    <jsp:body>
    <div class="container">
		<div class="page-header">
			<h1>Contig analyze - ${sample.name} - ${contig.reference} </h1>
		</div>
	<div class="panel panel-default">
			<div class="panel-heading">Contig</div>
		 	<table class="table">
			    <thead>
			    	<tr>
				    	<th>Size</th>
				    	<th>Total number of reads</th>
				    	<th>Total number of reads classified</th>
				    	<th>Total number of features</th>
				    </tr>
				</thead>
				<tbody>
					<tr>
						<td>${contig.size}</td>
						<td>${contig.numberOfreads}</td>
						<td>${contig.numberOfReadsClassified}</td>
						<td>${contig.numberOfFeatures}</td>
					</tr> 
				</tbody> 	
		 	</table>
	 		
	 		<div class="panel-heading">DNA Sequence</div>
	 		<textarea class="form-control" rows="10">${contig.sequence}</textarea>
	 		
	 		
	 		<div class="panel-heading">Features</div>
		 	<table class="table">
		 	    <thead>
			    	<tr>
				    	<th>type</th>
				    	<th>start</th>
				    	<th>end</th>
				    	<th>source product name</th>
				    	<th>product name</th>
				    	<th>product source</th>
				    </tr>
				</thead>
				<tbody>
					<c:forEach var="feature" items="${contig.features}">
						<tr>
							<td>${feature.type}</td>
							<td>${feature.start}</td>
							<td>${feature.end}</td>
							<td>${feature.source}</td>
							<td>${feature.geneProduct.product}</td>
							<td>
								<c:if test="${not empty feature.geneProduct.source}">
								<span  class="glyphicon-class">${feature.geneProduct.source}</span>
								<a href="<c:url value="/gene/search/by/producSource/${sample.id}/${feature.geneProduct.source}"/>"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
								</c:if>
							</td>
						</tr>
		 			</c:forEach>
				</tbody> 	
		 	</table>
	 		
	 		
	 		
		 	
		 	<div class="panel-heading">Taxonomy</div>
		 	<table class="table">
		 	    <thead>
			    	<tr>
				    	<th>Sequence id</th>
				    	<th>Read size</th>
				    	<th>Pair</th>
				    	<th>Alignment start</th>
				    	<th>Alignment end</th>
				    	<th>Táxon</th>
				    </tr>
				</thead>
				<tbody>
					<c:forEach var="readsOnCotig" items="${contig.readsOnCotig}">
						<tr>
							<td>${readsOnCotig.reference}</td>
							<td>${readsOnCotig.size}</td>
							<td>
								${readsOnCotig.pair}
							</td>
							<td>${readsOnCotig.startAlignment}</td>
							<td>${readsOnCotig.endAlignment}</td>
							<td>
								<c:choose> 
  									<c:when test="${not empty readsOnCotig.taxon.hank}">
    									[${readsOnCotig.taxon.hank}]  
										<span  class="glyphicon-class">${readsOnCotig.taxon.scientificName}</span>
										<a href="<c:url value="/taxonomy/search/${sample.id}/${readsOnCotig.taxon.scientificName}"/>"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a> 
  									</c:when>
								  <c:otherwise>
								    No taxon
								  </c:otherwise>
								</c:choose>
								 
							</td>
								
						</tr>
		 			</c:forEach>
				</tbody> 	
		 	</table>
		</div>
	</div>
    </jsp:body>
</t:default>


