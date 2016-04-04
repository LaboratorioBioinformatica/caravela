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
					<label for="tiiGreaterOrEqualsThan" class="col-sm-4 control-label"> TII (between 0.0 and 1) - greater or equals</label>
					<div class="col-sm-2">
						<input class="form-control" value="${tiiGreaterOrEqualsThan}" size="3" maxlength="3"  type="text" id="tiiGreaterOrEqualsThan" name="tiiGreaterOrEqualsThan">
					</div>
					<span class="col-sm-6"></span>
				</div>
				
				<div class="form-group">
						<label class="col-sm-4 control-label" for="numberOfFeaturesGreaterOrEqualsThan"> Number of features (CDSs) - Greater or equals</label>
					<div class="col-sm-2">
						<input class="form-control" value="${numberOfFeaturesGreaterOrEqualsThan}" size="4" maxlength="4" min=0 type="number" id="numberOfFeaturesGreaterOrEqualsThan" name="numberOfFeaturesGreaterOrEqualsThan">
					</div>
					<span class="col-sm-6"></span>
				</div>
				
				<div class="form-group">
						<label for="numberOfBoundariesLessOrEqualsThan" class="col-sm-4 control-label"> Number of boundaries - Less or equals</label>
					<div class="col-sm-2">
						<input class="form-control" value="${numberOfBoundariesLessOrEqualsThan}" size="4" maxlength="2" min=0 type="number" id="numberOfBoundariesLessOrEqualsThan" name="numberOfBoundariesLessOrEqualsThan">
					</div>
					<span class="col-sm-6"></span>
				</div>
				
				<div class="form-group">
						<label for="unclassifiedLessOrEqualsThan" class="col-sm-4 control-label"> Percent of contig is unclassified - (between 0 and 100) - Less or equals </label>
					<div class="col-sm-2">
						<input class="form-control" value="${unclassifiedLessOrEqualsThan}" size="3" maxlength="3" min=0 max=100 type="number" id="unclassifiedLessOrEqualsThan" name="unclassifiedLessOrEqualsThan">
					</div>
						<label for="undefinedLessOrEqualsThan" class="col-sm-4 control-label"> Percent of contig is undefined - (between 0 and 100) - Less or equals </label>
					<div class="col-sm-2">
						<input class="form-control" value="${undefinedLessOrEqualsThan}" size="3" maxlength="3" min=0 max=100 type="number" id="undefinedLessOrEqualsThan" name="undefinedLessOrEqualsThan">
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
    </jsp:body>
</t:default>
