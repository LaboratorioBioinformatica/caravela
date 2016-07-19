<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="headImport" fragment="true" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="a new approach to metagenomic browsing">
    <meta name="author" content="Gianluca Major">
    <link rel="icon" href="images/favicon.ico">
    <c:if test="${empty pageTitle}">
    	<c:set var="pageTitle" value="Caravela: a new approach to metagenomic browsing"/>
    </c:if>
    
    <title>${pageTitle}</title>

    <!-- Bootstrap -->
    
        <!-- Bootstrap core CSS -->
    <link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="<c:url value="/bootstrap/css/bootstrap-theme.min.css"/>" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src=<c:url value="/bootstrap/js/bootstrap.min.js"/>></script>
    
    <!-- Custom styles for this template -->
    <link href=<c:url value="/css/theme.css"/> rel="stylesheet">




    
    
    <!-- others CSS import -->
    <jsp:invoke fragment="headImport"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->    
  </head>
  <body>
	  <nav class="navbar navbar-inverse">
	        <div class="container">
	          <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
	              <span class="sr-only">Toggle navigation</span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="${linkTo[HomeController].home}">CARAVELA</a>
	          </div>
	          <div class="navbar-collapse collapse">
	            <ul class="nav navbar-nav">
	              <li><a href="${linkTo[HomeController].home}">Home</a></li>
	              <li><a href="${linkTo[SampleController].view}">Samples</a></li>
	              <li><a href="${linkTo[AdminController].view}">Admin</a></li>
	              <li><a href="${linkTo[LoginController].logout}">Logout</a></li>
	              <!--li><a href="#contact">Contact</a></li -->
	            </ul>
	          </div><!--/.nav-collapse -->
	          <div>${userLoggedIn.name}</div>
	        </div>
	      </nav>
	    <div>
			 <c:forEach items="${vmessages.success}" var="msg">
				<div class="alert alert-success" role="alert">${msg.message}</div>	
			</c:forEach>
		
			<c:forEach items="${vmessages.info}" var="info">
				<div class="alert alert-info" role="alert">${info.message}</div>	
			</c:forEach>
			
			<c:forEach items="${vmessages.warnings}" var="warn">
		 			<div class="alert alert-warning" role="alert">${warn.message}</div>
			</c:forEach>
		</div>  
	      
    <jsp:doBody/>
    
    <hr>
	<footer>
		<p>&copy; CARAVELA</p>
	</footer>
    
  </body>
</html>