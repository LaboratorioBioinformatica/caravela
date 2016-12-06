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
			<h3>Search by taxon: ${scientificName}</h3>
		</div>
	<hr>
	<div class="panel panel-default">
		<div class="panel-heading">Taxons found</div>
		<table class="table">
				    <thead>
				    	<tr>
					    	<th>Rank</th>
					    	<th>Scientific name</th>
					    	<th>Total</th>
					    </tr>
					</thead>
					<tbody>
						<c:forEach var="taxonCounter" items="${taxonCounterTOList}">
							<tr>
								<td>${taxonCounter.taxon.rank}</td>
								<td>
									<span  class="glyphicon-class">${taxonCounter.taxon.scientificName}</span>
									<a href="<c:url value="/taxonomy/search/${sample.id}/${taxonCounter.taxon.id}/${taxonCoverage}/${exclusively}"/>"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a>
								</td>
								<td>${taxonCounter.total}</td>
							</tr> 
						</c:forEach>			
					</tbody> 	
			 	</table>
	</div>		 	

	</div>	
   </jsp:body>
</t:default>


