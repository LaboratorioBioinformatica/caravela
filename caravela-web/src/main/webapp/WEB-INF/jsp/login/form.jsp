<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form action="<c:url value='/login/login'/>" method="post">

    User name:
    <input type="text" name="user.name"/><br/>
    Password:
    <input type="password" name="user.password"/><br/>
    
    <input type="submit" value="Login" />
</form>

<a href="<c:url value='/login/logout'/>">logout</a>

