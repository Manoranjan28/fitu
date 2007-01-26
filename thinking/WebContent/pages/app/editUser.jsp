<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<title>Edit User Infomation</title>
	<link href="<c:url value="/styles/app/page.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
	<script type="text/javascript" src="<c:url value="/scripts/page.js"/>"></script>
</head>

<body>
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">Edit User Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
	<table class="actionBar">
		<tr>
			<td>
				<button styleClass="butt" onclick="updateAction(this.form, '<c:out value="${ctxPath}"/>/menu/itemAction');">Save</button>
				&nbsp;
				<button onclick="history.back();">Back</button>
			</td>
		</tr>
	</table>
	<!-- Form Start -->
	<s:form action="user">
		<s:textfield label="UserName" name="userId"/>
		<s:password label="Password" name="password" />
	</s:form>
	<!-- Form End -->
</body>
</html>
