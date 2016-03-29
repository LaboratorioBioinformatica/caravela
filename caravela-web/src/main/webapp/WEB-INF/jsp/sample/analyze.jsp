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
		        		<form action="<c:url value="/gene/search/by/productName"/>" class="form-inline" role="form" method="post">
							<input type="hidden" name="sampleId" value="${sample.id}">
							<label>Name</label>
							<input class="form-control" size="30" placeholder="Topoisomerase IA" type="text" name="productName">
							<button type="submit" class="btn btn-default">Find</button>
						</form>
							or
						<form action="<c:url value="/gene/search/by/producSource"/>" class="form-inline" role="form" method="post">
							<input type="hidden" name="sampleId" value="${sample.id}">
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
		<h3>Filter By:</h3>
		<div class="form-group">
			<form action="<c:url value="/sample/analyze/by"/>" class="form-inline" role="form" method="post">
				<input type="hidden" name="sampleId" value="${sample.id}">
				<div class="col-md-*">
					<label> TII (between 0.0 and 1) - greater or equals</label>
					<input class="form-control" value="${tiiGreaterOrEqualsThan}" size="3" maxlength="3"  type="text" name="tiiGreaterOrEqualsThan">
				</div>
				
				<div class="col-md-*">
					<label> Number of features (CDSs) - Greater or equals</label>
					<input class="form-control" value="${numberOfFeaturesGreaterOrEqualsThan}" size="4" maxlength="4" min=0 type="number" name="numberOfFeaturesGreaterOrEqualsThan">
				</div>
				
				<div class="col-md-*">
					<label> Number of boundaries - Less or equals</label>
					<input class="form-control" value="${numberOfBoundariesLessOrEqualsThan}" size="4" maxlength="2" min=0 type="number" name="numberOfBoundariesLessOrEqualsThan">
				</div>
				
				<div class="col-md-*">
					<label> Percent of contig is unclassified - (between 0 and 100) - Less or equals </label>
					<input class="form-control" value="${unclassifiedLessOrEqualsThan}" size="3" maxlength="3" min=0 max=100 type="number" name="unclassifiedLessOrEqualsThan">
				</div>
				<div class="col-md-*">
					<label> Percent of contig is undefined - (between 0 and 100) - Less or equals </label>
					<input class="form-control" value="${undefinedLessOrEqualsThan}" size="3" maxlength="3" min=0 max=100 type="number" name="undefinedLessOrEqualsThan">
				</div>
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
