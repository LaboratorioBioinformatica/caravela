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
							<span class="col-sm-4"></span>
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
		<div class="form-group">
			<form action="<c:url value="/sample/analyze/by"/>" class="form-horizontal" role="form" method="post">
				<input type="hidden" name="sampleId" value="${sample.id}">
				
				<div class="form-group">
					<label for="tiiGreaterOrEqualsThan" class="col-sm-4 control-label"> GTII: </label>
					<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="GTII" data-content="GTII: General Taxonomic Identification Index - Put a value between 0.00 and 1"> 
							<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
					</button>
					<div class="col-sm-2">
						<fmt:formatNumber type="number" maxIntegerDigits="3" value="${tiiGreaterOrEqualsThan}" var="tiiGreaterOrEqualsThanFormated" />
						<input class="form-control" value="${tiiGreaterOrEqualsThanFormated}" size="3" maxlength="3"  type="text" id="tiiGreaterOrEqualsThan" name="tiiGreaterOrEqualsThan">
						
					</div>
					<span class="col-sm-6"></span>
				</div>
				
				<div class="form-group">
						<label class="col-sm-4 control-label" for="numberOfFeaturesGreaterOrEqualsThan"> Features</label>
						<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="Features" data-content="Minimum number of CDSs or Genes annotated on contig"> 
							<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
						</button>
					<div class="col-sm-2">
						<input class="form-control" value="${numberOfFeaturesGreaterOrEqualsThan}" size="4" maxlength="4" min=0 type="number" id="numberOfFeaturesGreaterOrEqualsThan" name="numberOfFeaturesGreaterOrEqualsThan">
					</div>
					<span class="col-sm-6"></span>
				</div>
				
				
				<div class="form-group">
					<label for="numberOfBoundariesLessOrEqualsThan" class="col-sm-4 control-label">Rank</label>
					
					<div class="col-sm-2">
						 <select class="form-control" name="rank" id="selectRank">
							  <option value="species" ${rankSelected eq "SPECIES" ? 'selected="selected"' : ''}>SPECIES</option>
							  <option value="genus" ${rankSelected eq "GENUS" ? 'selected="selected"' : ''}>GENUS</option>
							  <option value="family" ${rankSelected eq "FAMILY" ? 'selected="selected"' : ''}>FAMILY</option>
							  
						</select> 					
					</div>
					<span class="col-sm-6"></span>
				</div>
				
				<div class="form-group">
						<label for="numberOfBoundariesLessOrEqualsThan" class="col-sm-4 control-label">Borders:</label>
						<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="Borders" data-content="Borders are unclassified or undefined regions that are flanked by different taxa."> 
							<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
						</button>
					<div class="col-sm-2">
						<input class="form-control" value="${numberOfBoundariesLessOrEqualsThan}" size="4" maxlength="2" min=0 type="number" id="numberOfBoundariesLessOrEqualsThan" name="numberOfBoundariesLessOrEqualsThan">
					</div>
					<span class="col-sm-6"></span>
				</div>
				
				<div class="form-group">
						<label for="ICTCRGreaterOrEqualsThan" class="col-sm-2 control-label">ICTCR:</label>
					<div class="col-sm-2">
						<fmt:formatNumber type="number" maxIntegerDigits="4" value="${ICTCRGreaterOrEqualsThan}" var="ICTCRGreaterOrEqualsThanFormated" />
						<input class="form-control" value="${ICTCRGreaterOrEqualsThanFormated}" size="4" maxlength="4" type="text" id="ICTCRGreaterOrEqualsThan" name="ICTCRGreaterOrEqualsThan">
					</div>
					<div class="col-sm-2">
						<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="ICTCR" data-content="ICTCR: Index of Consistency Taxonomic by Count Reads - Put a value between 0.00 and 1"> 
							<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
						</button>
					</div>
				
					<label for="IVCTGreaterOrEqualsThan" class="col-sm-2 control-label">IVCT:</label>
					<div class="col-sm-2">
						<fmt:formatNumber type="number" maxIntegerDigits="4" value="${IVCTGreaterOrEqualsThan}" var="IVCTGreaterOrEqualsThanFormated" />
						<input class="form-control" value="${IVCTGreaterOrEqualsThanFormated}" size="4" maxlength="4"  type="text" id="IVCTGreaterOrEqualsThan" name="IVCTGreaterOrEqualsThan">
						
					</div>
					<div class="col-sm-2">
						<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="IVCT" data-content="IVCT: Index of Vertical Consistency Taxonomic - Put a value between 0.00 and 1"> 
							<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
						</button>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-12 center-block">
						<button type="submit" class="btn btn-success center-block">Filter</button>
					</div>
				</div>
				
				
			</form>
		</div>
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
	</div>
<script type="text/javascript">

$(document).ready(function(){
	
	$(function () {
		  $('[data-toggle="popover"]').popover()
	});	
	
});


</script>	
    </jsp:body>
</t:default>
