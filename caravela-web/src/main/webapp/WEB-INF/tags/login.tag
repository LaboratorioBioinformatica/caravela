<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
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
    <link href="../css/bootstrap/bootstrap.min.css" rel="stylesheet">
    
        <!-- Bootstrap core CSS -->
    <link href="../css/bootstrap/bootstrap.min.css" rel="stylesheet">
    
    <!-- Bootstrap theme -->
    <link href="../css/bootstrap/bootstrap-theme.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <!-- link href="../css/theme.css" rel="stylesheet"-->

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../js/bootstrap/ie-emulation-modes-warning.js"></script>
    
    <!-- others CSS import -->
    <jsp:invoke fragment="headImport"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->    
  </head>
  <body>
 
  	<jsp:doBody/>
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../js/bootstrap/bootstrap.min.js"></script>
    <!-- script src="js/bootstrap/docs.min.js"></script-->
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../js/bootstrap/ie10-viewport-bug-workaround.js"></script>
    
  </body>
</html>




