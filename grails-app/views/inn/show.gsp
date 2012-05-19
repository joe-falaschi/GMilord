
<%@ page import="milord.Inn" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'inn.label', default: 'Inn')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-inn" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<!-- li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li -->
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<!-- li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li -->
			</ul>
		</div>
		<div id="show-inn" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<ol class="property-list inn">			
				<g:if test="${innInstance?.name}">
					<li class="fieldcontain">
						<span id="name-label" class="property-label"><g:message code="inn.name.label" default="Name" /></span>
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${innInstance}" field="name"/></span>						
					</li>
				</g:if>
			
				<g:if test="${innInstance?.heroes}">
				<li class="fieldcontain">
					<span id="heroes-label" class="property-label"><g:message code="inn.heroes.label" default="Heroes" /></span>
					
						<g:each in="${innInstance.heroes}" var="h">
						<span class="property-value" aria-labelledby="heroes-label"><g:link controller="hero" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${innInstance?.id}" />
					<g:link class="edit" action="edit" id="${innInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
