<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:attribute name="headImport">
		<link type="text/css" rel="stylesheet" href="http://parce.li/bundle/feature-viewer@0.1.26">
		<script src="https://wzrd.in/bundle/feature-viewer@0.1.26"></script>
	</jsp:attribute>
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
	 		
	 		<div>
	 			<div class="col-md-10 col-md-offset-1" style="height:800px;vertical-align:top;margin-top:15px;" id="contigViewer">
	 			</div>
	 			
	 		</div>
	 		
	 		
	 		
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
	<script type="text/javascript">
	
	var reads = ${reads};
	var features = ${features};
	var FeatureViewer = require("feature-viewer");
	var ft = new FeatureViewer('${contig.sequence}',
	    "#contigViewer", {
	    showAxis: true,
	    showSequence: true,
	    brushActive: true,
	    toolbar:true,
	    bubbleHelp:true,
	    zoomMax:10
	});
	
	//Add some features
    ft.addFeature({
        data: features,
        name: "Features",
        className: "features",
        color: "#0F8292",
        type: "rect"
    });
	
	//Add some features
    ft.addFeature({
        data: reads,
        name: "reads",
        className: "reads",
        color: "#54FEB7",
        type: "multipleRect"
    });
	
	</script>
    </jsp:body>
</t:default>


