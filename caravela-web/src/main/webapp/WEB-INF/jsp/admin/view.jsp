<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
<jsp:attribute name="headImport">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/treatment-behavior.js'/>"></script>
</jsp:attribute>

    <jsp:body>
    	<div class="jumbotron">
  			<h3>TAKE CARE! NCBI Taxonomic file should be uploaded just only one time! Updates it after samples processed maybe introduce errors of inconsistency between assignment taxonomy submitted and  that it show in the application.</h3>
  			<p>Número de taxon na base: ${numberOfTaxon}</p>
    		<span  class="glyphicon-class">Register</span>
			<a href="<c:url value="/admin/register"/>"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></a>
		</div>
    	
    
    </jsp:body>
</t:default>


