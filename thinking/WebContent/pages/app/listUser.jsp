<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>List User Infomation</title>
    <link href="<c:url value="/styles/app/page.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/app/extremecomponents.css"/>" type="text/css" rel=stylesheet>
    <script src="<c:url value="/scripts/app/page.js"/>" type="text/javascript"></script>
	<script type="text/javascript">
	function validate() {
		var valid = true;
		return valid;
	}
	</script>
</head>

<body>
<div id="title">
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">List User Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<!-- Search Criterias START -->
<div id="search">
<s:form action="listUser" onsubmit="return validate();">
	<table class="searchBar">
		<tr>
			<td class="labelImg"><img style="cursor: pointer; cursor: hand;" id="hideImg" onclick="hideSearch('<c:out value='${ctxPath}'/>')" src="<c:url value="/images/icon/16x16/arrowdown.gif"/>" border="0" /></td>
			<td class="labelSearch">Search Criterias</td>
			<td style="text-align: right;"><div id="searchButt">
				<input class="buttSearch" type="submit" value="<fmt:message key='butt.search'/>" name="search">&nbsp;
				<input class="buttSearch" type="reset" value="<fmt:message key='butt.reset'/>" name="reset">
			</div></td>
		</tr>
	</table>
	<div id="criteria">
	<table class="criteria">
		<tr>
			<td class="label">User ID:</td><td><s:textfield name="s_userId" size="10"/></td>
			<td class="label">Name:</td><td><s:textfield name="s_secondName" size="10"/></td>
		</tr>
	</table>
	</div>
</s:form>
</div>
<!-- Search Criterias END -->
<div id="operation">
    <span class="operations">
    	<input class="buttComm" type="button" onclick="newAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/createUser');" value="Create">
    	<input class="buttComm" type="button" onclick="editAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/editUser');" value="Edit">
    	<input class="buttComm" type="button" onclick="deleteAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/removeUser', 'Workshift');" value="Remove">
    </span>
</div>

<!-- Search List (DETAIL) -->
<div id="result">
<ec:table items="list" var="user" retrieveRowsCallback="limit" sortRowsCallback="limit" autoIncludeParameters="false" action="${ctxPath}/app/listUser.action">
    <ec:exportXls view="xls" fileName="Users.xls" tooltip="Export Excel"/>
    <ec:row>
        <ec:column property="userId"     title="UserID" />
		<ec:column property="secondName" title="Name" />
        <ec:column property="edit" title=" " sortable="false" viewsAllowed="html" style="width: 20px">
	        <a href="<c:url value="/ws/workshiftAction.do?method=edit&id=${workshift.id}"/>">
	            <img align="absmiddle" src="<c:url value="/images/icon/16x16/modify.gif"/>" border="0"/>
	        </a>
	    </ec:column>
	    <ec:column property="checkbox" title=" " sortable="false" viewsAllowed="html" style="width: 20px;">
	        <input type="checkbox" name="itemlist" value="${workshift.id}" style="border:0px"/>
	    </ec:column>
    </ec:row>
</ec:table>
</div>
</body>
</html>
