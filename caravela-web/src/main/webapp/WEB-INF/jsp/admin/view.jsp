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
		<div class="alert alert-warning alert-dismissible" role="alert">
		  		<p><strong>TAKE CARE!</strong> NCBI Taxonomic file should be uploaded just only one time! Updates it after samples processed maybe introduce errors of inconsistency between assignment taxonomy submitted and  that it show in the application.</p>
		  		<p>Currently there are <strong>${numberOfTaxon}</strong> taxa loaded on database</p>
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
			  	<span  class="glyphicon-class">To load</span>
			  </button>
			</form>
		</div>
		
		<div class="page-header">
			<h3>System properties</h3>
		</div>
		<div class="form-group">
			<label class="control-label" for="baseDirectoryPath">CARAVELA directory path property</label>
			<input type="text" class="form-control" name="baseDirectoryPath" id="baseDirectoryPath" value="${directoryBase}" disabled>
		</div>
		
		<div class="form-group">
			<label class="control-label" for="configDirectoryPath">Config directory path property</label>
			<input type="text" class="form-control" name="configDirectoryPath" id="configDirectoryPath" value="${directoryConfig}" disabled>
		</div>	
		
		<div class="form-group">
			<label class="control-label" for="uploadDirectoryPath">Upload directory path property</label>
			<input type="text" class="form-control" name="uploadDirectoryPath" id="uploadDirectoryPath" value="${directoryUpload}" disabled>
		</div>
		<div class="page-header">
			<h3>Change System properties</h3>
		</div>	
		<div class="alert alert-info" role="alert">
		  		<p><strong>How to change system properties on CARAVELA?</strong></p>
		  		  
		  		
		</div>
		<div class="panel panel-default">
			<div class="panel-heading"><strong>How to change system properties on CARAVELA?</strong></div>
		  	<div class="panel-body">
		  		<p>First, we need identify where Apache Tomcat is hosted on our system: <strong>TOMCAT_HOME</strong></p>
		  		<p>If CARAVELA is ROOT application on Tomcat the properties file should be here: TOMCAT_HOME<strong>/webapps/ROOT/WEB-INF/classes/production.properties</strong></p>
		  		<p>Otherwise, CARAVELA's properties file should be on: TOMCAT_HOME<strong>/webapps/caravela/WEB-INF/classes/production.properties</strong>
		  		<p>System properties can be edited when required. The CARAVELA should be <strong>restarted</strong> to changes take effect.</p>
		  	</div>
		</div>
	</div>
   </jsp:body>
</t:default>
