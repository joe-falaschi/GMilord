<%@ page import="milord.Inn" %>



<div class="fieldcontain ${hasErrors(bean: innInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="inn.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="50" required="" value="${innInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: innInstance, field: 'heroes', 'error')} ">
	<label for="heroes">
		<g:message code="inn.heroes.label" default="Heroes" />
		
	</label>
	<g:select name="heroes" from="${milord.Hero.list()}" multiple="multiple" optionKey="id" size="5" value="${innInstance?.heroes*.id}" class="many-to-many"/>
</div>

