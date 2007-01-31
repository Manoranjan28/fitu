<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<title>Edit User Infomation</title>
	<link href="<c:url value="/styles/app/page.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
	<script type="text/javascript" src="<c:url value="/scripts/app/page.js"/>"></script>
</head>

<body>
<div id="title">
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">Edit User Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<div id="editOper">
    <span class="editOpers">
    	<input class="buttComm" type="button" onclick="saveOrUpdateAction('user', '<c:out value="${ctxPath}"/>/app/saveUser');" value="Save">
    	<input class="buttComm" type="button" onclick="history.back();" value="Back">
    </span>
</div>
<!-- Form Start -->
<div id="master">
<s:form action="user">
	<table class="simple">
		<tr>
			<td>UserID</td><td><s:textfield name="model.userId"/></td>
			<td>UserName</td><td><s:textfield name="model.secondName"/></td>
			<td>Password</td><td><s:password name="model.password" /></td>
		</tr>
		<tr>
			<td>Adress</td><td><s:textfield name="model.address"/></td>
			<td>ZipCode</td><td><s:textfield name="model.zcode"/></td>
			<td>Email</td><td><s:password name="model.email" /></td>
		</tr>
		<tr>
			<td>Fax</td><td><s:textfield name="model.fax"/></td>
			<td>Tel</td><td><s:textfield name="model.tel"/></td>
			<td>MSN</td><td><s:password name="model.imid" /></td>
		</tr>
	</table>
</s:form>
</div>
<!-- Form End -->
</body>
</html>
