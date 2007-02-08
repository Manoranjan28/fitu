<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>List UserGroup Infomation</title>
    <link href="<c:url value="/styles/app/page.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/app/extremecomponents.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
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
		<td style="width: 350px;"><div id="pageTitle">List UserGroup Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<!-- Search Criterias START -->
<div id="search">
<s:form action="listRole" onsubmit="return validate();">
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
			<td class="label">Group Name:</td><td><s:textfield name="s_name" size="12"/></td>
		</tr>
	</table>
	</div>
</s:form>
</div>
<!-- Search Criterias END -->
<div id="operation">
    <span class="operations">
    	<input class="buttComm" type="button" onclick="newAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/createUserGroup');" value="Create">
    	<input class="buttComm" type="button" onclick="editAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/editUserGroup');" value="Edit">
    	<input class="buttComm" type="button" onclick="deleteAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/removeUserGroup', 'UserGroup');" value="Remove">
    </span>
</div>

<!-- Search List Start -->
<div id="result">
<ec:table items="list" var="userGroup" retrieveRowsCallback="limit" 
	sortRowsCallback="limit" autoIncludeParameters="false" action="${ctxPath}/app/listUserGroup.action">
    <ec:exportXls view="xls" fileName="Roles.xls" tooltip="Export Excel"/>
    <ec:row>
        <ec:column property="name"  title="GroupName" />
		<ec:column property="descn" title="Desc" />
        <ec:column property="edit" title=" " sortable="false" viewsAllowed="html" style="width: 20px">
	        <a href="<c:url value="/app/editUserGroup.action?modelId=${userGroup.id}"/>">
	            <img align="absmiddle" src="<c:url value="/images/icon/16x16/modify.gif"/>" border="0"/>
	        </a>
	    </ec:column>
	    <ec:column property="checkbox" title=" " sortable="false" viewsAllowed="html" style="width: 20px;">
	        <input type="checkbox" name="items" value="<c:out value="${userGroup.id}" />" style="border:0px"/>
	    </ec:column>
    </ec:row>
</ec:table>
</div>
<!-- Search List End -->

<div id="operation">
    <span class="operations">
    	<input class="buttComm" type="button" onclick="newAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/createUserGroup');" value="Create">
    	<input class="buttComm" type="button" onclick="editAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/editUserGroup');" value="Edit">
    	<input class="buttComm" type="button" onclick="deleteAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/removeUserGroup', 'UserGroup');" value="Remove">
    </span>
</div>
</body>
</html>
