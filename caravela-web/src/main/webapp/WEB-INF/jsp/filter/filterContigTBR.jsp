<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
	<div class="container">
		<div class="page-header">
			<h1>Filter contigs TBR</h1>
			<hr>
			<h3>Sample: <strong>${sample.name}</strong></h3>
		</div>
		
		<div class="form-group">
					<form action="<c:url value="/filter/contig/tbr"/>" class="form-horizontal" role="form" method="post">
						<input type="hidden" name="sampleId" value="${sample.id}">
						<span></span>
						<div class="form-group">
							<label for="tiiGreaterOrEqualsThan" class="col-sm-4 control-label"> ITG: ( &ge; ) </label>
							<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="ITG" data-content="ITG: General Taxonomic Identification - Put a value between 0.00 and 1"> 
									<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
							</button>
							<div class="col-sm-2">
								<fmt:formatNumber type="number" maxIntegerDigits="3" value="${filterTBRParameters.itg}" var="ITGFormated" />
								<input class="form-control" value="${ITGFormated}" size="3" maxlength="3"  type="text" id="ITG" name="filterTBRParameters.itg">
								
							</div>
							<span class="col-sm-6"></span>
						</div>
						
						<div class="form-group">
								<label class="col-sm-4 control-label" for="numberOfFeatures">Features: ( &ge; )</label>
								<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="Features" data-content="Minimum number of CDSs or Genes annotated on contig"> 
									<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
								</button>
							<div class="col-sm-2">
								<input class="form-control" value="${filterTBRParameters.numberOfFeatures}" size="4" maxlength="4" min=0 type="number" id="numberOfFeatures" name="filterTBRParameters.numberOfFeatures">
							</div>
							<span class="col-sm-6"></span>
						</div>
						
						
						<div class="form-group">
							<label for="numberOfBoundariesLessOrEqualsThan" class="col-sm-4 control-label">Rank: </label>
							
							<div class="col-sm-4">
								 <select class="form-control" name="filterTBRParameters.taxonomicRank" id="selectRank">
								 
									  <option value="SPECIES" ${filterTBRParameters.taxonomicRank eq "SPECIES" ? 'selected="selected"' : ''}>SPECIES</option>
									  <option value="GENUS" ${filterTBRParameters.taxonomicRank eq "GENUS" ? 'selected="selected"' : ''}>GENUS</option>
									  <option value="FAMILY" ${filterTBRParameters.taxonomicRank eq "FAMILY" ? 'selected="selected"' : ''}>FAMILY</option>
									  
								</select> 					
							</div>
							<span class="col-sm-4"></span>
						</div>
						
						<div class="form-group">
								<label for="numberOfBorders" class="col-sm-4 control-label">Borders: ( &le; )</label>
								<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="Borders" data-content="Borders are unclassified or undefined regions that are flanked by different taxa."> 
									<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
								</button>
							<div class="col-sm-2">
								<input class="form-control" value="${filterTBRParameters.numberOfBorders}" size="4" maxlength="2" min=0 type="number" id="numberOfBorders" name="filterTBRParameters.numberOfBorders">
							</div>
							<span class="col-sm-6"></span>
						</div>
						
						<div class="form-group">
								<label for="ICTCRGreaterOrEqualsThan" class="col-sm-2 control-label">CT: ( &ge; )</label>
							<div class="col-sm-2">
								<fmt:formatNumber type="number" maxIntegerDigits="4" value="${filterTBRParameters.ct}" var="CTFormated" />
								<input class="form-control" value="${CTFormated}" size="4" maxlength="4" type="text" id="CT" name="filterTBRParameters.ct">
							</div>
							<div class="col-sm-2">
								<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="CT" data-content="CT: Index of Consistency Taxonomic by Count Reads - Put a value between 0.00 and 1"> 
									<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
								</button>
							</div>
						
							<label for="IVCTGreaterOrEqualsThan" class="col-sm-2 control-label">CTV: ( &ge; )</label>
							<div class="col-sm-2">
								<fmt:formatNumber type="number" maxIntegerDigits="4" value="${filterTBRParameters.ctv}" var="CTVFormated" />
								<input class="form-control" value="${CTVFormated}" size="4" maxlength="4"  type="text" id="CTV" name="filterTBRParameters.ctv">
								
							</div>
							<div class="col-sm-2">
								<button type="button" id="bnt-info" class="btn btn-default" aria-label="Left Align"  data-container="body" data-toggle="popover" data-placement="top" title="CTV" data-content="CTV: Index of Vertical Consistency Taxonomic - Put a value between 0.00 and 1"> 
									<span class="glyphicon glyphicon-info-sign"aria-hidden="true"></span>
								</button>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-12 center-block">
								<button type="submit" class="btn btn-success center-block">Filter</button>
								<a class="btn btn-default right-block" href="<c:url value="/filter/contig/tbr/view/${sample.id}"/>" role="button">Default values</a>
							</div>
							
						</div>
						
						
					</form>
					
				      					        
				</div>

	

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
