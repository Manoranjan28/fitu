<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Test</title></head>

<body>
<h1>Available Test</h1>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Remark</th>
    </tr>
    <s:iterator value="items">
        <tr>
            <td><a href="<s:url action="edit-%{id}" />"><s:property value="id"/></a></td>
            <td><s:property value="name"/></td>
            <td><s:property value="remark"/></td>
        </tr>
    </s:iterator>
</table>
<p><a href="<s:url action="edit-" includeParams="none"/>">Create new Employee</a></p>
<p><a href="<s:url action="showcase" namespace="/" includeParams="none"/>">Back to Showcase Startpage</a></p>
</body>
</html>
