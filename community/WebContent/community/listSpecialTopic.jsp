<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>List Special Topic Infomation</title>
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
		<td style="width: 350px;"><div id="pageTitle">List Special Topic Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<!-- Search Criterias START -->
<div id="search">
<s:form action="listSpecialTopic" onsubmit="return validate();">
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
	<table class="criteria" >
		<tr>
			<td class="label">Name:</td><td><s:textfield name="s_name" size="12"/></td>
		</tr>
	</table>
	</div>
</s:form>
</div>
<!-- Search Criterias END -->
<div id="operation"><table class="operation"><tr><td>
   	<input class="buttComm" type="button" onclick="newAction(document.forms.ec, '<c:out value="${ctxPath}"/>/createSpecialTopic');" value="Create">
   	<input class="buttComm" type="button" onclick="editAction(document.forms.ec, '<c:out value="${ctxPath}"/>/editSpecialTopic');" value="Edit">
   	<input class="buttComm" type="button" onclick="deleteAction(document.forms.ec, '<c:out value="${ctxPath}"/>/removeSpecialTopic', 'SpecialTopic');" value="Remove">
</td></tr></table></div>

<!-- Search List Start -->
<div id="result"><table class="result"><tr><td>
<ec:table items="list" var="specialTopic" action="${ctxPath}/listSpecialTopic.action"
	xlsFileName="SpecialTopic.xls"
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
		<ec:column property="name" title="Name"/>
		<ec:column property="descn" title="Desc"/>
		<ec:column property="createUser" title="Creator"/>
		<ec:column width="25" property="edit" title=" " viewsAllowed="html">
	        <a href="<c:url value="/editSpecialTopic.action?modelId=${specialTopic.id}"/>">
	            <img align="absmiddle" alt="Edit" src="<c:url value="/images/icon/16x16/modify.gif"/>" border="0"/>
	        </a>
	    </ec:column>
	    <ec:column width="40" property="articles" title="Arti" viewsAllowed="html">
	        <a href="<c:url value="/selectArticle.action?modelId=${specialTopic.id}"/>">
	            <img align="absmiddle" alt="Select Article" src="<c:url value="/images/icon/16x16/modify.gif"/>" border="0"/>
	        </a>
	    </ec:column>
		<ec:column width="25" cell="checkbox" headerCell="checkbox" alias="items" value="${specialTopic.id}" viewsAllowed="html" />
	</ec:row>
</ec:table>
</td></tr></table></div>
<!-- Search List End -->
</body>
</html>
