<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:login>

<jsp:attribute name="headImport">
<style type="text/css">
.login-card-wide.mdl-card {
	width: 512px;
	margin: 100 auto;
	background-color: #f7f7f7;
}
form {
margin: 0 100;
}

.login-card-wide > .mdl-card__title {
	background-color: #37474f;
	color: white;
}

</style>
</jsp:attribute>
    <jsp:body>
    <div class="container">
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
			
			<c:forEach items="${errors}" var="error">
		 			<div class="alert alert-danger" role="alert">
		 			<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		 			<p>${error.message}</p>
		 			<p>Please let us know. Send an email to gianlucamajor@gmail.com and tell us what happened.</p>
		 			
		 			</div>
			</c:forEach>
		</div>  
	  <div class="jumbotron">
			<form  class="form-horizontal" action="<c:url value='/login/login'/>" method="post">
			  <div class="form-group">
			    <label for="userEmail" class="col-sm-2 control-label">E-mail</label>
			    <div class="col-sm-10">
			      <input type="email" name="userName" class="form-control" id="userEmail" placeholder="Email">
			    </div>
			  </div>
			  
			  <div class="form-group">
			    <label for="userPassword" class="col-sm-2 control-label">Password</label>
			    <div class="col-sm-10">
			      <input type="password" name="password" class="form-control" id="userPassword" placeholder="Password">
			    </div>
			  </div>
		
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" class="btn btn-default">Sign in</button>
			    </div>
			  </div>
			</form>
	  </div>
	</div>
    	</jsp:body>
</t:login>


