<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<title>Edit Article Infomation</title>
	<link href="<c:url value="/styles/app/page.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
	<script type="text/javascript" src="<c:url value="/scripts/app/page.js"/>"></script>
</head>
<body>
<div id="title">
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">Edit Article Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<div id="editOper">
    <span class="editOpers">
    	<input class="buttComm" type="button" onclick="saveOrUpdateFitu('article', '<c:out value="${ctxPath}"/>/saveArticle');" value="Save">
    	<input class="buttComm" type="button" onclick="history.back();" value="Back">
    </span>
</div>
<!-- Form Start -->
<div id="master">
<ww:form action="article">
	<table class="simple">
		<tr><ww:hidden name="model.id"/>
			<td>Title</td><td><ww:textfield name="model.title" size="80"/></td>
		</tr>
		<tr>
			<td>Topic/Label</td>
			<td>
				<ww:textfield name="model.topic.id" size="8"/>
				<ww:textfield name="model.label" size="50"/>
			</td>
		</tr>
		<tr>
			<td>Content</td>
			<td>
			<ww:richtexteditor name="model.content" 
				basePath="%{#request.ctxPath}/webwork/richtexteditor/" toolbarCanCollapse="false" 
				defaultLanguage="zh-cn" height="500"/>
			</td>
		</tr>
	</table>
</ww:form>
</div>
<!-- Form End -->
</body>
</html>
