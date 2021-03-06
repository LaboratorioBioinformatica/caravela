<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1>
					<span class="label label-success">New</span> Sample</h1>
		</div>
	    
		<form role="form" name="sampleRegisterForm" action="${linkTo[SampleController].add}" method="post">
		
		  <div class="form-group">
			  <label>study: </label>
			  <span class="text-danger">${errors.from('sample.study').join(' - ')}</span>
			  <select id="select-study-id" name="studyId" class="form-control">
			  <option value="" selected="selected">Choose a study</option>
				<c:forEach var="study" items="${studyList}">
					<option value="${study.id}">${study.name} </option>
				</c:forEach>
			  </select>
		</div>
		<div class="form-group">
		    <label class="control-label" for="name">Name</label>
		    <span class="text-danger">${errors.from('sample.name').join(' - ')}</span>
		    <input type="text" class="form-control" name="sampleName" id="name" value="${sampleName}" placeholder="Sample name" maxlength="100">
		  </div>
		  
		  <div class="form-group">
		    <label for="description">Description</label>
		    <input type="text" class="form-control" name="description" id="description" value="${description}" maxlength="500" placeholder="Sample description">
		  </div>
		  <button type="submit" class="btn btn-default pull-right">Submit</button>
		</form>
	</div>
   </jsp:body>
</t:default>

