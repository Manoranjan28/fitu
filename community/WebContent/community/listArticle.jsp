<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
    <%@ include file="/pages/common/meta.jsp"%>
	<title>List Article Infomation</title>
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
		<td style="width: 350px;"><div id="pageTitle">List Article Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<!-- Search Criterias START -->
<div id="search">
<ww:form action="listArticle.fitu" onsubmit="return validate();">
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
			<td class="label">Title:</td><td><ww:textfield name="s_title" size="10"/></td>
			<td class="label">Topic:</td><td><ww:textfield name="s_topic$name" size="10"/></td>
			<td class="label">Summary:</td><td><ww:textfield name="s_summary" size="10"/></td>
			<td class="label">Level:</td><td><ww:textfield name="s_level" size="5"/></td>
			<td class="label">Good:</td><td><ww:checkbox name="s_goodFlg" /></td>
		</tr>
	</table>
	</div>
</ww:form>
</div>
<!-- Search Criterias END -->
<div id="operation"><table class="operation"><tr><td>
	<input class="buttComm" type="button" onclick="newFitu(document.forms.ec, '<c:out value="${ctxPath}"/>/createArticle');" value="Create">
	<input class="buttComm" type="button" onclick="editFitu(document.forms.ec, '<c:out value="${ctxPath}"/>/editArticle');" value="Edit">
	<input class="buttComm" type="button" onclick="deleteFitu(document.forms.ec, '<c:out value="${ctxPath}"/>/removeArticle', 'Article');" value="Remove">
</td></tr></table></div>
<!-- Search List Start -->
<div id="result"><table class="result"><tr><td>
<ec:table items="list" var="article" action="${ctxPath}/listArticle.fitu"
	xlsFileName="Article.xls"
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
		<ec:column property="title" />
		<ec:column property="level" />
		<ec:column property="goodFlg" />
		<ec:column property="viewCnt" />
		<ec:column property="createUser" />
		<ec:column property="createDate" />
		<ec:column property="updateDate" />
		<ec:column width="25" property="edit" title=" " viewsAllowed="html">
	        <a href="<c:url value="/editArticle.fitu?modelId=${article.id}"/>">
	            <img align="absmiddle" src="<c:url value="/images/icon/16x16/modify.gif"/>" border="0"/>
	        </a>
	    </ec:column>
		<ec:column width="25" cell="checkbox" headerCell="checkbox" alias="items" value="${article.id}" viewsAllowed="html" />
	</ec:row>
</ec:table>
</td></tr></table></div>
<!-- Search List End -->
</body>
</html>
