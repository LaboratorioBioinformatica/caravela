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
			<h1><span class="label label-success">Admin</span></h1>
			
		</div>
		<div class="page-header">
			<div class="row">
  				<div class="col-md-6">
  					<h3>NCBI Taxonomy</h3>	
  				</div>
  				<div class="col-md-6">
  					<h3><span class="label label-default pull-right">load</span></h3>	
  				</div>
			</div>
		
		</div>
		<div class="alert alert-info alert-dismissible" role="alert">
		  		<p><strong>FIRST TIME?</strong> Currently there are no taxa on database (<strong> number of taxa: ${numberOfTaxon}</strong>), probably because this is first time that perform Caravela.</p>
		  		<p> To load the taxa just click the <strong>Load button</strong> and wait for the message to succeed</p>
		</div>	
			
		<div>
			<form role="form" name="ncbiTaxonomyFileForm" action="${linkTo[AdminController].load}" method="post">
			  <div class="form-group">
			    <label class="control-label" for="name">Scientific name file</label>
			    <input type="text" class="form-control" name="scientificaNameFile" id="scientificaNameFile" value="${ncbiFilePathTaxonomyName}">
			  </div>
			  
			  <div class="form-group">
			    <label class="control-label" for="name">Node file</label>
			    <input type="text" class="form-control" name="nodesFile" id="nodesFile" value="${ncbiFilePathTaxonomyNode}">
			  </div>
			  <button type="submit" class="btn btn-primary pull-right">
			  	<span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>
			  	<span  class="glyphicon-class">Load</span>
			  </button>
			</form>
		</div>
	</div>
   </jsp:body>
</t:default>
