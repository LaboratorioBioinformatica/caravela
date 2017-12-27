<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1><span class="label label-success">New</span> study</h1>
		</div>
	    
		<form role="form" name="studyRegisterForm" action="${linkTo[StudyController].add}" method="post">
		
		  <div class="form-group">
		    <label class="control-label" for="name">Name</label>
		    <span class="text-danger">${errors.from('study.name').join(' - ')}</span>
		    <input type="text" class="form-control" name="study.name" id="name" value="${study.name}" placeholder="study name" maxlength="100">
		  </div>
		  
		  <div class="form-group">
		    <label for="description">Description</label>
		    <input type="text" class="form-control" name="study.description" id="description" value="${study.description}" maxlength="500" placeholder="study description">
		  </div>
		  <button type="submit" class="btn btn-default pull-right">Submit</button>
		</form>
	</div>
   </jsp:body>
</t:default>

