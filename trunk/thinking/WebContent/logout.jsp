<%@ include file="/pages/common/taglibs.jsp"%>
<%
	session.setAttribute("LOGIN_USER", null);
	session.invalidate();
%>

<c:redirect url="/main.action"/>
