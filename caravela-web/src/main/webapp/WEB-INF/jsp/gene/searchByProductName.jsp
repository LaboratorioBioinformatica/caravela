<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:body>
	<div class="container">
		<div class="page-header">
			<h1>Analyze -  ${sample.name} </h1>
		</div>
		<div class="panel-heading well">
			<h3>Search by function: ${productName}</h3>
		</div>
	<hr>
	<div class="panel panel-default">
		<div class="panel-heading">Function found</div>
		<table class="table">
				    <thead>
				    	<tr>
					    	<th>Product Name</th>
					    	<th>Product Source</th>
					    </tr>
					</thead>
					<tbody>
						<c:forEach var="feature" items="${featureList}">
							<tr>
								<td>${feature.productName}</td>
								<td>
									<span  class="glyphicon-class">${feature.productSource}</span>
									<a href="<c:url value="/gene/search/by/producSource/${sample.id}/${feature.productSource}"/>"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
								</td>
							</tr> 
						</c:forEach>			
					</tbody> 	
			 	</table>
	</div>		 	

	</div>	
   </jsp:body>
</t:default>


