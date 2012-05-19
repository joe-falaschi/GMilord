<%@ page import="milord.House" %>



<div class="fieldcontain ${hasErrors(bean: houseInstance, field: 'house_name', 'error')} required">
	<label for="house_name">
		<g:message code="house.house_name.label" default="Housename" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="house_name" maxlength="15" required="" value="${houseInstance?.house_name}"/>
</div>

