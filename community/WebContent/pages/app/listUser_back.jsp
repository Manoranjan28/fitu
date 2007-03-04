<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>List User Infomation</title>
    <link href="<c:url value="/styles/app/page.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/app/extremecomponents.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/yui-ext/yui-ext.css"/>" type="text/css" rel=stylesheet>
    <script src="<c:url value="/scripts/app/page.js"/>" type="text/javascript"></script>
    <script type="text/javascript" src="<c:url value="/scripts/yahoo/utilities_2.1.0.js" />"></script>
	<script type="text/javascript" src="<c:url value="/scripts/yui-ext/yui-ext.js" />"></script>
    <script type="text/javascript" src="<c:url value="/scripts/app/searchDialog.js"/>"></script>
</head>

<body>
<table class="headerTitle"><tr>
	<td style="width: 350px;"><div id="pageTitle">List User Infomation</div></td>
	<td><%@ include file="/pages/common/messages.jsp" %></td>
</tr></table>
<!-- Search Criterias START -->
<div id="search">
<s:form action="listUser" onsubmit="return validate();">
	<table class="searchBar">
		<tr>
			<td class="labelImg">
			<img style="cursor: pointer; cursor: hand;" id="hideImg" onclick="hideSearch('<c:out value='${ctxPath}'/>')" src="<c:url value="/images/icon/16x16/arrowdown.gif"/>" border="0" /></td>
			<td class="label">Search Criterias</td>
			<td><div id="searchButt">
				<input class="buttSearch" type="submit" value="<fmt:message key='butt.search'/>" name="search">&nbsp;
				<input class="buttSearch" type="reset" value="<fmt:message key='butt.reset'/>" name="reset">
			</div></td>
		</tr>
	</table>
	<div id="criteria">
	<table class="criteria">
		<tr>
			<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
			<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
		</tr>
	</table>
	</div>
</s:form>
</div>
<!-- Search Criterias END -->
<div id="operation">
    <span class="operations">
    	<button style="buttComm" width="100px" onclick="newAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/createUser');">New</button>
    	<button style="buttComm" onclick="editAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/editUser');">Edit</button>
    	<button style="buttComm" onclick="deleteAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/deleteUser', 'Workshift');">Delete</button>
    	
    	<input type="button" id="searchBtn" value="Hello World" />
    </span>
</div>
<div id="searchDialog" style="visibility: hidden;position: absolute;top: 0px;">
	<div class="ydlg-hd">Search Criterias</div>
	<div class="ydlg-bd">
		<s:form action="listUser">
			<table class="searchBar">
				<tr><td>
					<input class="buttSearch" type="submit" value="Search" name="search">&nbsp;
					<input class="buttSearch" type="reset" value="Reset" name="reset">
				</td></tr>
			</table>
	        <table class="criteria">
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
				<tr>
					<td class="label">User ID:</td><td><s:textfield name="s_userId"/></td>
				</tr>
			</table>
			<table class="searchBar">
				<tr><td>
					<input class="buttSearch" type="submit" value="Search" name="search">&nbsp;
					<input class="buttSearch" type="reset" value="Reset" name="reset">
				</td></tr>
			</table>
		</s:form>
	</div>
</div>

<!-- Search List (DETAIL) -->

<ec:table items="list" var="user" retrieveRowsCallback="limit" sortRowsCallback="limit" autoIncludeParameters="false" action="${ctxPath}/app/listUser.action">
    <ec:exportXls view="xls" fileName="Users.xls" tooltip="Export Excel"/>
    <ec:row>
        <ec:column property="userId" title="UserID"   style="width: 68px"/>

        <ec:column property="edit" title="Edt" sortable="false" viewsAllowed="html" style="width: 20px">
	        <a href="<c:url value="/ws/workshiftAction.do?method=edit&id=${workshift.id}"/>">
	            <img src="<c:url value="/images/icon/16x16/modify.gif"/>" border="0"/>
	        </a>
	    </ec:column>
	    <ec:column property="checkbox" title="Slt" sortable="false" viewsAllowed="html" style="width: 20px;">
	        <input type="checkbox" name="itemlist" value="${workshift.id}" style="border:0px"/>
	    </ec:column>
    </ec:row>
</ec:table>
</body>
</html>
