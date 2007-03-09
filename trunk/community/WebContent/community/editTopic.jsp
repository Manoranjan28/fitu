<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<title>Edit Topic Infomation</title>
	<link href="<c:url value="/styles/app/page.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/extremecomponents.css"/>" type="text/css" rel=stylesheet>
	<script type="text/javascript" src="<c:url value="/scripts/app/page.js"/>"></script>
</head>

<body>
<div id="title">
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">Edit Topic Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<div id="editOper">
    <span class="editOpers">
    	<input class="buttComm" type="button" onclick="saveOrUpdateAction('topic', '<c:out value="${ctxPath}"/>/saveTopic');" value="Save">
    	<input class="buttComm" type="button" onclick="history.back();" value="Back">
    </span>
</div>
<!-- Form Start -->
<div id="master">
<s:form action="topic">
	<table class="simple">
		<tr><s:hidden name="model.id"/>
			<td>Name</td><td><s:textfield name="model.name"/></td>
		</tr>
		<tr>
			<td>Desc</td><td><s:textfield name="model.descn"/></td>
		</tr>
		<tr>
			<td>Space</td><td><s:textfield name="model.space.id"/></td>
		</tr>
	</table>
</s:form>
</div>
<!-- Form End -->
</body>
</html>
