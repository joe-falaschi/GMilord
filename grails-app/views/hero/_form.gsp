<%@ page import="milord.Hero" %>



<div class="fieldcontain ${hasErrors(bean: heroInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="hero.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="50" required="" value="${heroInstance?.name}"/>
</div>

