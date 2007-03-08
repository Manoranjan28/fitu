<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="/FCKeditor" prefix="FCK"%>
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
    	<input class="buttComm" type="button" onclick="saveOrUpdateAction('article', '<c:out value="${ctxPath}"/>/saveArticle');" value="Save">
    	<input class="buttComm" type="button" onclick="history.back();" value="Back">
    </span>
</div>
<!-- Form Start -->
<div id="master">
<s:form action="user">
	<table class="simple">
		<tr><s:hidden name="model.id"/>
			<td>Title</td><td><s:textfield name="model.title" size="80"/></td>
		</tr>
		<tr>
			<td>Topic/Label</td>
			<td>
				<s:textfield name="model.topic.id" size="8"/>
				<s:textfield name="model.label" size="50"/>
			</td>
		</tr>
		<tr>
			<td>Content</td>
			<td><FCK:editor id="content" width="80%" height="320"
				fontNames="Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana"
				imageBrowserURL="/community/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector"
				linkBrowserURL="/community/FCKeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector"
				flashBrowserURL="/community/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector"
				imageUploadURL="/community/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Image"
				linkUploadURL="/community/FCKeditor/editor/filemanager/upload/simpleuploader?Type=File"
				flashUploadURL="/community/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Flash">
			</FCK:editor></td>
		</tr>
		<tr>
			<td>Content</td>
			<td><s:textarea name="model.content" /></td>
		</tr>
	</table>
</s:form>
</div>
<!-- Form End -->
</body>
</html>
