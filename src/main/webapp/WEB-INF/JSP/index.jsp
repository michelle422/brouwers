<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='v' uri='http://vdab.be/tags'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@taglib prefix='security' uri='http://www.springframework.org/security/tags'%> 
<!doctype html>
<html lang='nl'>
<head>
	<v:head title="Brouwers"/>
</head>
<body>
	<h1>Brouwers</h1>
	<nav>
		<ul>
			<li><a href="<c:url value='/brouwers'/>">Alle brouwers</a></li>
			<security:authorize access="hasAuthority('administrator')">
				<li><a href="<c:url value='/brouwers/toevoegen'/>">Brouwers toevoegen</a></li>
			</security:authorize>
			<li><a href="<c:url value='/brouwers/beginnaam'/>">Brouwers op naam</a></li>
			<li><a href="<c:url value='/brouwers/opalfabet'/>">Brouwers op alfabet</a></li>
			<security:authorize access="isAnonymous()">
				<li><a href="<c:url value='/login'/>">Aanmelden</a></li>
			</security:authorize>
			<security:authorize access="isAuthenticated()">
				<li> 
					<form method='post' action='<c:url value="/logout"/>' id='logoutform'> 
						<input type='submit' value='<security:authentication property="name"/> afmelden' id='logoutbutton'> 
						<security:csrfInput/> 
					</form> 
				</li>
			</security:authorize>
		</ul>
	</nav>
	<h2><fmt:message key="${groet}"/></h2>
</body>
</html>