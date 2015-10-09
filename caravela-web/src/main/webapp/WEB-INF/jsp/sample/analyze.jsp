<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
	<div class="container">
		<div class="page-header">
			<h1>Analyze - ${sample.name} </h1>
		</div>
		
	<div class="jumbotron">
		<div class="row">
		  <div class="col-sm-12">
		    <div class="row">
		      <div class="col-xs-8 col-sm-5">
		        <h4>Search by taxon</h4>
					<div class="form-group">	
						<form action="<c:url value="/taxonomy/search/fragment"/>" class="form-inline" role="form" method="post">
							<input type="hidden" name="sampleId" value="${sample.id}">
							<label>Scientific name</label>
							<input class="form-control" size="25" type="text" placeholder="Bacillus amyloliquefaciens" name="scientificName">
							<button type="submit" class="btn btn-default">Find</button>
						</form>
					</div>
		      </div>
		      <div class="col-xs-4 col-sm-7">
		        <h4>Search by function</h4>
		        <div class="form-group">	
						<form action="<c:url value="/function/search"/>" class="form-inline" role="form" method="post">
							<input type="hidden" name="sampleId" value="${sample.id}">
							<label>Name</label>
							<input class="form-control" size="30" placeholder="Topoisomerase IA" type="text" name="productName">
							or
							<label>Source</label>
							<input class="form-control" size="10" placeholder="COG0550" type="text" name="productSource">
							<button type="submit" class="btn btn-default">Find</button>
						</form>
				</div>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
	<hr>
	
	<div class="jumbotron">
		<h3>Filter By Taxonomic Identification Index (TII)</h3>
		<div class="form-group">	
			<form action="<c:url value="/sample/analyze"/>" class="form-inline" role="form" method="post">
				<input type="hidden" name="sampleId" value="${sample.id}">
				<label> TII (between 0.0 and 0.99)</label>
				<input class="form-control" placeholder="0.8" value="${tiiValue}" size="4" maxlength="4" type="text" name="tiiValue">
					
				<button type="submit" class="btn btn-default">Filter</button>
			</form>
		</div>
	</div>	
	<hr>
	

	<c:if test="${not empty contigList}">
		<div class="panel panel-default">
			<div class="panel-heading"><h4>Contigs</h4></div>
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


