<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@attribute name="headImport" fragment="true" %>
<html>
<head>

<link href="https://fonts.googleapis.com/css?family=Roboto:regular,bold,italic,thin,light,bolditalic,black,medium&amp;lang=en" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.4/material.cyan-light_blue.min.css">
<jsp:invoke fragment="headImport"/>
</head>
<body>
  <jsp:doBody/>
	<script src="https://storage.googleapis.com/code.getmdl.io/1.0.4/material.min.js"></script>  
</body>
</html>

