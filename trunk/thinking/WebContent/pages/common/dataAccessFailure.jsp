<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/pages/common/taglibs.jsp"%>

<title>Data Access Error</title>
<meta name="menu" content="AdminMenu"/>

<p>
    <c:out value="${requestScope.exception.message}"/>
</p>

<!--
<% 
//Exception exception = (Exception) request.getAttribute("exception");
//exception.printStackTrace(new java.io.PrintWriter(out)); 
%>
-->

<a href="main.action" onclick="history.back();return false">&#171; Back</a>
