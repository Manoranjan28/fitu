<%@ include file="/pages/common/taglibs.jsp" %>

<html>
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<title>Edit Space Infomation</title>
	<link href="<c:url value="/styles/app/page.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
	<script type="text/javascript" src="<c:url value="/scripts/app/page.js"/>"></script>
</head>
<body>
<div id="title">
	<table class="headerTitle"><tr>
		<td style="width: 350px;"><div id="pageTitle">Edit Space Infomation</div></td>
		<td><%@ include file="/pages/common/messages.jsp" %></td>
	</tr></table>
</div>
<div id="editOper">
    <span class="editOpers">
    	<input class="buttComm" type="button" onclick="saveOrUpdateAction('space', '<c:out value="${ctxPath}"/>/saveSpace');" value="Save">
    	<input class="buttComm" type="button" onclick="history.back();" value="Back">
    </span>
</div>
<!-- Form Start -->
<div id="master">
<ww:form action="space">
	<table class="simple">
		<tr><ww:hidden name="model.id"/>
			<td>Name</td><td><ww:textfield name="model.name"/></td>			
			<td>Subject</td><td><ww:textfield name="model.subject"/></td>
		</tr>
		<tr>
			<td>Type</td><td><ww:textfield name="model.type"/></td>			
			<td>Category</td><td><ww:textfield name="model.category"/></td>
		</tr>
		<tr>
			<td>Owner</td><td><ww:textfield name="model.owner"/></td>			
			<td>Descn</td><td><ww:textarea name="model.descn" cols="30" rows="4"/></td>
		</tr>
	</table>
</ww:form>
</div>
<!-- Form End -->
</body>
</html>
