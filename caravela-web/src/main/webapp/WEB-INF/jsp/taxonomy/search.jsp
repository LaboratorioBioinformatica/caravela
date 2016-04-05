<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:attribute name="headImport">
		 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	</jsp:attribute>
	<jsp:body>
	<div class="container">
		<div class="page-header">
			<h1>Analyze - ${sample.name} </h1>
		</div>
		<div class="panel-heading well">
			<h3>
				Search by contigs <small> where at least </small> ${taxonCoverage}% <small> of contig was mapped by </small>reads<small> assigned to taxon 
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
	
	
	<div class="panel panel-default">
		<div class="panel-heading">All functions found in contigs</div>
		
		<div id="donutFeatureChart" style="width: 900px; height: 500px;" class="center-block"></div>
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
	
	<script type="text/javascript">
	
	$(document).ready(function(){
		var geneProductCounter = ${geneProductCounterJson};
		google.charts.load('current', {'packages':['corechart']});
	    google.charts.setOnLoadCallback(drawChart);
	    
	    function drawChart() {
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', 'function');
	        data.addColumn('number', 'total');
	        
	        $.each(geneProductCounter, function(k, v) {
	        	data.addRow([v.geneProductTO.product, v.total]);
	        });
	        

	        var options = {
	          title: 'All functions found on contigs',
	          pieHole: 0.4,
	        };

	        var chart = new google.visualization.PieChart(document.getElementById('donutFeatureChart'));
	        chart.draw(data, options);
	      }
		
	});
		
		
	</script>
	
   </jsp:body>
</t:default>
