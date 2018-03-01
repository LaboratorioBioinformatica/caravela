<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:default>
	<jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1> Create a new user</h1>
		</div>

		<form role="form" name="userRegisterForm" action="${linkTo[UserController].add}" method="post">

		<div class="form-group">
		    <label class="control-label" for="name">Name</label>
		    <span class="text-danger">${errors.from('name').join(' - ')}</span>
		    <input type="text" class="form-control" name="name" id="name" value="${name}" placeholder="Maria" maxlength="100">
		 </div>
		 <div class="form-group">
            <label class="control-label" for="email">E-mail</label>
            <span class="text-danger">${errors.from('email').join(' - ')}</span>
            <input type="text" class="form-control" name="email" id="email" value="${email}" placeholder="maria@gmail.com" maxlength="100">
         </div>

         <div class="form-group">
             <label class="control-label" for="password">Password</label>
             <span class="text-danger">${errors.from('password').join(' - ')}</span>
             <input type="password" class="form-control" name="password" id="password" maxlength="100">
          </div>

          <div class="form-group">
               <label class="control-label" for="confirmPassword">Confirm Password</label>
               <span class="text-danger">${errors.from('confirm-password').join(' - ')}</span>
               <input type="password" class="form-control" name="confirmPassword" id="confirmPassword" maxlength="100">
           </div>

		  <button type="submit" class="btn btn-default pull-right">Submit</button>
		</form>
	</div>
   </jsp:body>
</t:default>



