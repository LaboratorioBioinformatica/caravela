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
			<c:choose>
				<c:when test="${isSampleProcessorRunning}">
					<div class="alert alert-info" role="alert"><strong>Sample Processor is Running!!!</strong></div>
				</c:when>
  			</c:choose>		
			<h1><span class="label label-success">Sample Processor</span></h1>
			
		</div>
		<div class="page-header">
			<div class="row">
  				<div class="col-md-6">
  					<h3>Processor all samples wait to be processed</h3>	
  				</div>
  				<div class="col-md-6">
  					<h3><span class="label label-default pull-right">Processor</span></h3>	
  				</div>
  				
  				<c:choose>
  					<c:when test="${isSampleProcessorRunning}">
  						 <button class="btn btn-lg btn-warning disabled"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span> processing...</button>
  					</c:when>
  					<c:otherwise>
	  					<form role="form" name="sampleProcessor" action="${linkTo[AdminController].processAllSample}" method="post">
				  
						  <button type="submit" class="btn btn-primary">
						  	<span class="glyphicon glyphicon-play-circle" aria-hidden="true"></span>
						  	<span  class="glyphicon-class">Process</span>
						  </button>
						</form>
  					</c:otherwise>
  				</c:choose>
  				
			</div>
		
		</div>
	
	</div>
   </jsp:body>
</t:default>
