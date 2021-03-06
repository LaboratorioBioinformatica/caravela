<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
<jsp:attribute name="headImport">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/study-behavior.js'/>"></script>
</jsp:attribute>

    <jsp:body>
        <h2>=P</h2>
	      <c:if test="${not empty studies}">
			<div class="form-group">
				<form action="<c:url value="/"/>" name="form-study-sample" class="form-inline" role="form" method="post">
					<!-- label></label -->
					<select id="select-study-id" name="studyId" class="form-control">
							<option value="0" >Choose a study</option>
						<c:forEach var="study" items="${studies}">
							<option ${studySelected eq study.id ? 'selected="selected"' : ''}
								value="${study.id}">${study.name}</option>
						</c:forEach>
					</select> 
				</form>
			</div>
		</c:if>	
		<div id="div-form-sample" class="form-group">
			<form action="<c:url value="/all"/>" name="form-sample" class="form-inline" role="form" method="post">
				<label>Choose a sample</label>
				<select id="select-sample" name="sampleId" class="form-control">
				</select> 
				<input type="submit" name="ok" value="oks">
			</form>
		</div>
    </jsp:body>
</t:default>


