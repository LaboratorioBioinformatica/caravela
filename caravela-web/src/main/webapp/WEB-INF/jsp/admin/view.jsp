<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
	    	<c:forEach items="${errors}" var="error">
		 			<div class="alert alert-danger" role="alert">
		 			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		 			<p>${error.message}</p>
		 			</div>
			</c:forEach>
		    <div class="alert alert-warning alert-dismissible" role="alert">
		  		<p><strong>TAKE CARE!</strong> NCBI Taxonomic file should be uploaded just only one time! Updates it after samples processed maybe introduce errors of inconsistency between assignment taxonomy submitted and  that it show in the application.</p>
		  		<p>Currently there are <strong>${numberOfTaxon}</strong> taxa loaded on database</p>
			</div>
			<div class="row">
  				<div class="col-md-6">
  					<h1><span class="label label-success">Admin</span> NCBI Taxonomy</h1>	
  				</div>
  				<div class="col-md-6">
  					<h1><span class="label label-default pull-right">load</span></h1>	
  				</div>
			</div>
			
		</div>	
		<div>
			<form role="form" name="ncbiTaxonomyFileForm" action="${linkTo[AdminController].load}" method="post">
			  <div class="form-group">
			    <label class="control-label" for="name">Scientific name file</label>
			    <input type="text" class="form-control" name="scientificaNameFile" id="scientificaNameFile" value="/data/caravela/config/ncbiScientificNames.txt">
			  </div>
			  
			  <div class="form-group">
			    <label class="control-label" for="name">Node file</label>
			    <input type="text" class="form-control" name="nodesFile" id="nodesFile" value="/data/caravela/config/ncbiNodes.txt">
			  </div>
			  <button type="submit" class="btn btn-primary pull-right">
			  	<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
			  	<span  class="glyphicon-class">To load</span>
			  </button>
			</form>
		</div>
	</div>
   </jsp:body>
</t:default>
