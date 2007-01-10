<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Hello, Test</title>
    <s:head/>
</head>

<body>
<h1>Hello, Test</h1>

<s:action id="skillAction" namespace="/skill" name="list"/>

<s:form name="editForm" action="save">
    <s:textfield label="Name" name="test.name"/>
    <s:textarea label="Remark" name="test.remark" cols="50" rows="3"/>
    <s:submit value="Save" />
</s:form>
<p><a href="<s:url action="list"/>"><s:text name="employee.backtolist"/></a></p>
</body>
</html>
