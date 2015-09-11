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
    	<div class="login-card-wide mdl-card mdl-shadow--2dp">
	    	<div class="mdl-card__title">
	    		<h2 class="mdl-card__title-text">Login</h2>
	  		</div>
		  		<form action="<c:url value='/login/login'/>" method="post">
		  		<div class="mdl-card__actions mdl-card--border">
		  			<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					  <input class="mdl-textfield__input" name="user.name" type="text" id="user" />
					  <label class="mdl-textfield__label" for="user">User name</label>
					</div>
					<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">  
					  <input class="mdl-textfield__input" name="user.password" type="password" id="pws" />
					  <label class="mdl-textfield__label" for="pws">Password</label>
					</div>
					<div class="mdl-textfield">
						<input class="mdl-button" type="submit" name="login">
					</div>
		  		</div>
		  		</form>
    		</div>
		
	</jsp:body>
</t:login>


