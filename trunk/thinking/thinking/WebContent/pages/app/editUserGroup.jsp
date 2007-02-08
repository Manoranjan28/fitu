<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<title>Edit UserGroup Infomation</title>
	<link href="<c:url value="/styles/app/page.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
	<script type="text/javascript" src="<c:url value="/scripts/app/page.js"/>"></script>
</head>

<body>
<div id="title">
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">Edit UserGroup Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<div id="editOper">
    <span class="editOpers">
    	<input class="buttComm" type="button" onclick="saveOrUpdateAction('userGroup', '<c:out value="${ctxPath}"/>/app/saveUserGroup');" value="Save">
    	<input class="buttComm" type="button" onclick="history.back();" value="Back">
    </span>
</div>
<!-- Form Start -->
<div id="master">
<s:form action="userGroup">
	<table class="simple">
		<tr><s:hidden name="model.id"/>
			<td>GroupName</td><td><s:textfield name="model.name"/></td>
			<td>Desc</td><td><s:textfield name="model.descn"/></td>
		</tr>
	</table>
	<br>
	<table>
		<tr>
			<td>
				<table class="simple">
					<tr>
						<td></td><td>UserID</td><td>UserName</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td><td>Hello</td><td>hello</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td><td>junhao</td><td>hello</td>
					</tr>
				</table>
			</td>
			<td>
				<table class="simple">
					<tr>
						<td></td><td>RoleName</td><td>RoleDesc</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td><td>Hello</td><td>hello</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td><td>junhao</td><td>hello</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</s:form>
</div>
<!-- Form End -->
</body>
</html>
