<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>List Space Infomation</title>
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
		<td style="width: 350px;"><div id="pageTitle">List Space Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<!-- Search Criterias START -->
<div id="search">
<ww:form action="listSpace" onsubmit="return validate();">
	<table class="searchBar">
		<tr>
			<td class="labelImg"><img style="cursor: pointer; cursor: hand;" id="hideImg" onclick="hideSearch('<c:out value='${ctxPath}'/>')" src="<c:url value="/images/icon/16x16/up.png"/>" border="0" /></td>
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
			<td class="label">Name:</td><td><ww:textfield name="s_name" size="10"/></td>
			<td class="label">Type:</td><td><ww:textfield name="s_type" size="10"/></td>
		</tr>
	</table>
	</div>
</ww:form>
</div>
<!-- Search Criterias END -->
<div id="operation"><table class="operation"><tr><td>
   	<input class="buttComm" type="button" onclick="newFitu(document.forms.ec, '<c:out value="${ctxPath}"/>/createSpace');" value="Create">
   	<input class="buttComm" type="button" onclick="editFitu(document.forms.ec, '<c:out value="${ctxPath}"/>/editSpace');" value="Edit">
   	<input class="buttComm" type="button" onclick="deleteFitu(document.forms.ec, '<c:out value="${ctxPath}"/>/removeSpace', 'Space');" value="Remove">
</td></tr></table></div>

<!-- Search List Start -->
<div id="result"><table class="result"><tr><td>
<ec:table items="list" var="space" action="${ctxPath}/listSpace.fitu"
	xlsFileName="Space.xls"
	showPrint="true"
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
		<ec:column property="name"/>
		<ec:column property="subject"/>
		<ec:column property="type"/>
		<ec:column property="category"/>
		<ec:column property="owner"/>
		<ec:column property="descn" title="Desc" />
		<ec:column width="25" property="edit" title=" " viewsAllowed="html">
	        <a href="<c:url value="/app/editSpace.fitu?modelId=${space.id}"/>">
	            <img align="absmiddle" alt="Edit" src="<c:url value="/images/icon/16x16/modify.gif"/>" border="0"/>
	        </a>
	    </ec:column>
		<ec:column width="25" cell="checkbox" headerCell="checkbox" alias="items" value="${space.id}" viewsAllowed="html" />
	</ec:row>
</ec:table>
</td></tr></table></div>
<!-- Search List End -->
</body>
</html>
