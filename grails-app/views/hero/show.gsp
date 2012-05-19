
<%@ page import="milord.Hero" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'hero.label', default: 'Hero')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-hero" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<g:ifPlayer>
					<li><g:link class="hero" action="hire" parms="user.house"><g:message code="Hire This Hero"/></g:link></li>
				</g:ifPlayer>
				<g:ifAdmin>
					<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
					<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				</g:ifAdmin>
			</ul>
		</div>
		<div id="show-hero" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list hero">
			
				<g:if test="${heroInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="hero.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${heroInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:ifAdmin>
 			  <g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${heroInstance?.id}" />
					<g:link class="edit" action="edit" id="${heroInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			  </g:form>
			</g:ifAdmin>
		</div>
	</body>
</html>
