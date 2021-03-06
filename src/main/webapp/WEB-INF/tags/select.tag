<%--
 * select.tag
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty"%>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Attributes --%>

<%@ attribute name="path" required="true"%>
<%@ attribute name="code" required="false"%>
<%@ attribute name="items" required="true" type="java.util.Collection"%>
<%@ attribute name="itemLabel" required="false"%>
<%@ attribute name="itemsAsCodes" required="false"%>
<%@ attribute name="id" required="false"%>
<%@ attribute name="onchange" required="false"%>

<jstl:if test="${id == null}">
	<jstl:set var="id" value="${UUID.randomUUID().toString()}" />
</jstl:if>

<jstl:if test="${onchange == null}">
	<jstl:set var="onchange" value="javascript: return true;" />
</jstl:if>

<%-- Definition --%>

<div>
	<jstl:if test="${not empty code}">
		<form:label path="${path}">
			<spring:message code="${code}" />
		</form:label>
	</jstl:if>
	<form:select id="${id}" path="${path}" onchange="${onchange}">
		<!-- object list -->
		<jstl:if test="${itemLabel!=null}">
			<form:options items="${items}" itemValue="id"
				itemLabel="${itemLabel}" />
		</jstl:if>
		<!-- string list as is-->
		<jstl:if test="${itemLabel==null && itemsAsCodes == null}">
			<form:options items="${items}" />
		</jstl:if>
		<!-- string list as codes-->
		<jstl:if test="${itemLabel==null && itemsAsCodes!=null}">
			<jstl:forEach items="${items}" var="item">
				<form:option value="${item}">
					<spring:message code="${item}" />
				</form:option>
			</jstl:forEach>
		</jstl:if>
	</form:select>
	<form:errors path="${path}" cssClass="error" />
</div>


