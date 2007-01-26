<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>List User Infomation</title>
    <link href="<c:url value="/styles/app/page.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/app/extremecomponents.css"/>" type="text/css" rel=stylesheet>
    <script src="<c:url value="/scripts/app/page.js"/>" type="text/javascript"></script>
</head>

<body>
<table class="headerTitle"><tr>
	<td style="width: 350px;"><div id="pageTitle">List User Infomation</div></td>
	<td><%@ include file="/pages/common/messages.jsp" %></td>
</tr></table>
<!-- Search Criteria -->
<s:form action="listUser">
	<table class="searchBar">
		<tr>
			<td><div style="display: none" id="searchButt">
			<input class="buttSearch" type="submit" value="Search" name="search">&nbsp;
			<input class="buttSearch" type="reset" value="Reset" name="reset">
			</div></td>
			<td class="label">Search Criteria</td>
			<td class="labelImg"><img style="cursor: pointer; cursor: hand;" id="hideImg" onclick='hideSearch()' src="<c:url value="/images/icon/16x16/more.gif"/>" border="0"/></td>
		</tr>
	</table>
	<div style="display: none" id="criteria">
	<table class="criteria">
		<tr>
			<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
			<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
		</tr>
	</table>
	</div>
</s:form>

<div id="operation">
    <span class="operations">
    	<button style="buttComm" onclick="newAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/createUser');">New</button>
    	<button style="buttComm" onclick="editAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/editUser');">Edit</button>
    	<button style="buttComm" onclick="deleteAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/deleteUser', 'Workshift');">Delete</button>
    </span>
</div>
<!-- Search List (DETAIL) -->


</body>
</html>
