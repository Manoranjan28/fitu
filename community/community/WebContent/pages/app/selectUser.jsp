<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>Select User</title>
    <link href="<c:url value="/styles/app/page.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/app/ecside_ec.css"/>" type="text/css" rel=stylesheet>
    <link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
    <script src="<c:url value="/scripts/app/page.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/scripts/ecside/ecside.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/scripts/ecside/prototypeajax.js"/>" type="text/javascript"></script>
	<script type="text/javascript">
	function validate() {
		var valid = true;
		return valid;
	}
	function init(){
		var ecside=new ECSide(); 
		ecside.init();
	}
	</script>
</head>

<body onload="init()">
<div id="title">
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">Select User</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>

<!-- Search Criterias START -->
<div id="search">
<s:form action="selectUser" onsubmit="return validate();">
	<table class="searchBar">
		<tr>
			<td class="labelImg"><img style="cursor: pointer; cursor: hand;" id="hideImg" onclick="hideSearch('<c:out value='${ctxPath}'/>')" src="<c:url value="/images/icon/16x16/down.png"/>" border="0" /></td>
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
<table class="operation"><tr><td>
   	<input class="buttComm" type="button" onclick="selectAction(document.forms.ec, '<c:out value="${ctxPath}"/>/app/selectUser', 'user');" value="Select">
</td></tr></table>
</div>
<div id="master">
<table class="master">
	<tr>
		<th style="width: 100px;">Group Name</th><td><c:out value="${GROUP.name}" /></td>
		<th style="width: 100px;">Desc</th><td><c:out value="${GROUP.descn}" /></td>
	</tr>
</table>
</div>

<!-- Search List Start -->
<div id="result"><table class="result"><tr><td>
<ec:table items="list" var="user" action="${ctxPath}/app/selectUser.action"
	minColWidth="80"
	resizeColWidth="true"
	maxRowsExported="10000000"
	pageSizeList="10,100,1000,all"
	sortable="true"
	nearPageNum="0"
	width="100%" listHeight="100%"
>
	<ec:row>
		<ec:column width="38" property="_0" title="No."  value="${GLOBALROWCOUNT}" />
		<ec:column property="userId"     title="UserID" />
		<ec:column property="secondName" title="Name" />
		<ec:column property="selected" title=" " width="25">
			<c:choose>
				<c:when test="${user.selected}">
					<img align="absmiddle" src="<c:url value="/images/icon/16x16/box_checked.gif"/>" border="0" />
				</c:when>
				<c:otherwise>
					<img align="absmiddle" src="<c:url value="/images/icon/16x16/box_unchecked.gif"/>" border="0" />
				</c:otherwise>
			</c:choose>
		</ec:column>
		<ec:column width="25" cell="checkbox" headerCell="checkbox" alias="items" value="${user.id}_${user.selected}" viewsAllowed="html" />
	</ec:row>
</ec:table>
</td></tr></table></div>
<!-- Search List End -->

</body>
</html>
