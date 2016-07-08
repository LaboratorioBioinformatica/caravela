<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1><span class="label label-success">New</span> Treatment</h1>
		</div>
	    
		<form role="form" name="treatmentRegisterForm" action="${linkTo[TreatmentController].add}" method="post">
		
		  <div class="form-group">
		    <label class="control-label" for="name">Name</label>
		    <span class="text-danger">${errors.from('treatment.name').join(' - ')}</span>
		    <input type="text" class="form-control" name="treatment.name" id="name" value="${treatment.name}" placeholder="Treatment name" maxlength="100">
		  </div>
		  
		  <div class="form-group">
		    <label for="description">Description</label>
		    <input type="text" class="form-control" name="treatment.description" id="description" value="${treatment.description}" maxlength="500" placeholder="Treatment description">
		  </div>
		  <button type="submit" class="btn btn-default pull-right">Submit</button>
		</form>
	</div>
   </jsp:body>
</t:default>

