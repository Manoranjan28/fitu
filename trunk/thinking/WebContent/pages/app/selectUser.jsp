<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>Select User</title>
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
		<td style="width: 350px;"><div id="pageTitle">Select User</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<table class="master">
	<tr>
		<th style="width: 100px;">Group Name</th><td><c:out value="${GROUP.name}" /></td>
		<th style="width: 100px;">Desc</th><td><c:out value="${GROUP.descn}" /></td>
	</tr>
</table>
<!-- Search Criterias START -->
<div id="search">
<s:form action="selectUser" onsubmit="return validate();">
	<table class="searchBar">
		<tr>
			<td class="labelImg"><img style="cursor: pointer; cursor: hand;" id="hideImg" onclick="hideSearch('<c:out value='${ctxPath}'/>')" src="<c:url value="/images/icon/16x16/arrowright.gif"/>" border="0" /></td>
			<td class="labelSearch">Search Criterias</td>
			<td style="text-align: right;"><div id="searchButt" style="display: none;">
				<input class="buttSearch" type="submit" value="<fmt:message key='butt.search'/>" name="search">&nbsp;
				<input class="buttSearch" type="reset" value="<fmt:message key='butt.reset'/>" name="reset">
			</div></td>
		</tr>
	</table>
	<div id="criteria" style="display: none;">
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
    	<input class="buttComm" type="button" onclick="selectAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/selectUser', 'user');" value="Select">
    </span>
</div>
<!-- Search List Start -->
<div id="result">
<ec:table items="list" var="user" retrieveRowsCallback="limit" sortRowsCallback="limit" autoIncludeParameters="false" action="${ctxPath}/app/selectUser.action">
    <ec:row>
        <ec:column property="userId"     title="UserID" />
		<ec:column property="secondName" title="Name" />
		<ec:column property="selected" title=" " style="width: 26px;text-align: center">
			<c:choose>
				<c:when test="${user.selected}">
					<img align="absmiddle" src="<c:url value="/images/icon/16x16/box_checked.gif"/>" border="0" />
				</c:when>
				<c:otherwise>
					<img align="absmiddle" src="<c:url value="/images/icon/16x16/box_unchecked.gif"/>" border="0" />
				</c:otherwise>
			</c:choose>
		</ec:column>
	    <ec:column property="checkbox" title=" " sortable="false" viewsAllowed="html" style="width: 20px;">
	        <input type="checkbox" name="items" value="${user.id}_${user.selected}" style="border:0px"/>
	    </ec:column>
    </ec:row>
</ec:table>
</div>
<!-- Search List End -->

<div id="operation">
    <span class="operations">
    	<input class="buttComm" type="button" onclick="selectAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/selectUser', 'user');" value="Select">
    </span>
</div>
</body>
</html>
