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
	<div class="jumbotron">
	        <h3>Search by taxon on contig</h3>
					<form action="<c:url value="/taxonomy/search/fragment"/>" class="form-horizontal" role="form" method="post">
						<input type="hidden" name="sampleId" value="${sample.id}">
						<div class="form-group">
							<label class="col-sm-2 control-label">Scientific name</label>
							<div class="col-sm-6">
								<input class="form-control" size="25" type="text" placeholder="Bacillus amyloliquefaciens" name="scientificName">
							</div>
							<span class="col-sm-4"></span>
						</div>
						<div class="form-group">
						
							<label for="taxonCoverage" class="col-sm-2 control-label"> Taxon covarage - (between 0 and 1) - Less or equals </label>
							<div class="col-sm-6">
								<fmt:formatNumber type="number" maxIntegerDigits="4" value="${taxonCoverage}" var="taxonCoverageFormated" />
								<input class="form-control" value="${taxonCoverageFormated}" size="4" maxlength="4" type="text" id="taxonCoverage" name="taxonCoverage">
							</div>
							<span class="col-sm-4 checkbox">
								<label class="checkbox-inline"><input name="exclusively" type="checkbox" value="true">Exclusively</label>
							</span>
						</div>
						
						<div class="form-group">
							<div class="col-sm-12 center-block">
								<button type="submit" class="btn btn-success center-block">Find</button>
							</div>
						</div>	
					</form>
	  </div>
	     
	<hr>
	      
	      <div class="jumbotron">
		      	<h3>Search by function on contig</h3>
		        		
		        		<form action="<c:url value="/gene/search/by/productName"/>" class="form-horizontal" role="form" method="post">
							<input type="hidden" name="sampleId" value="${sample.id}">
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
	        
	<hr>

	<div class="jumbotron">
		<h3>Filter By:</h3>
			<a class="btn-lg btn-primary" href="<c:url value="/filter/contig/tbr/view/${sample.id}"/>" role="button">Filter by Contigs TBR</a>
			<a class="btn-lg btn-danger" href="<c:url value="/filter/contig/pq/view/${sample.id}"/>" role="button">Filter by Contigs PQ</a>
		</div>	
	<hr>
		      <div class="jumbotron">
		      	<h3>Search by contig name</h3>
		      										
		        		<form action="<c:url value="/sample/analyze/by/contigName"/>" class="form-horizontal" role="form" method="post">
							<input type="hidden" name="sampleId" value="${sample.id}">
							<div class="form-group">
								<label class="col-sm-2 control-label">Contig Name</label>
								<div class="col-sm-6">
									<input class="form-control" size="30" placeholder="contig reference" type="text" name="contigName">
								</div>
								<div class="col-sm-4">
									<button type="submit" class="btn btn-success">Find</button>
								</div>
							</div>
						</form>
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

<script type="text/javascript">

$(document).ready(function(){
	
	$(function () {
		  $('[data-toggle="popover"]').popover()
	});
	
});

</script>	
    </jsp:body>
</t:default>
