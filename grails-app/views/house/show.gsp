
<%@ page import="milord.House" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'house.label', default: 'House')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-house" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<g:ifAdmin>
		  <div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		  </div>
		</g:ifAdmin>
		<div id="show-house" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list house">
			
				<g:if test="${houseInstance?.house_name}">
				<li class="fieldcontain">
					<span id="house_name-label" class="property-label"><g:message code="house.house_name.label" default="Housename" /></span>
					
						<span class="property-value" aria-labelledby="house_name-label"><g:fieldValue bean="${houseInstance}" field="house_name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:set var="innName" value="${message(code: 'inn.label', default: 'Inn')}" />
		
			<div id="list-inn">
				<h1>Inns:</h1>
				
				<g:each in="${innInstanceList}" status="i" var="innInstance">
					<br/><g:link controller="inn" action="show" id="${innInstance.id}">${fieldValue(bean: innInstance, field: "name")}</g:link>						
				</g:each>
			</div>
			
			<g:ifAdmin>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${houseInstance?.id}" />
					<g:link class="edit" action="edit" id="${houseInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
			</g:ifAdmin>
		</div>
	</body>
</html>
