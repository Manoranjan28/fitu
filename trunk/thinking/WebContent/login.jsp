<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title>FitU - Thinking - Login~~</title>
		<meta http-equiv=content-type content="text/html; charset=utf-8">
		<link href="<c:url value="/styles/login.css" />" type=text/css rel=stylesheet>

		<%
			session.invalidate();
		%>
		<script language='javascript'>
        function efoc() {
            var elem = window.event.srcElement;
            elem.style.border = '1 solid #9e9e9e';
        }

        function eblur() {
            var elem = window.event.srcElement;
            elem.style.border = '1px solid #cccccc';
        }
	    </script>
		<%
			if(request.getSession(false) == null){
		%>
		<script language="javascript">
			if(window.top.mainframe){
				alert('Out time!');
				window.top.location.href = "login.jsp";
			}
		</script>
		<% 
			}
		%>
	</head>

	<body id="login">
		<div id="loginHead">
			<span class="logo"><img src="<c:url value="/images/logo.jpg" />" /></span>
			<span class="topbar">&nbsp;&nbsp;|&nbsp;&nbsp; <a href="<c:url value="/" />">首页</a> &nbsp;&nbsp;|&nbsp;&nbsp; <a href="#">帮助</a> &nbsp;&nbsp;|&nbsp;&nbsp; <a href="#">联系我们</a>
				&nbsp;&nbsp;|&nbsp;&nbsp; </span>
		</div>

		<div id="loginContent">
			<div id="loginHint">
				<div class="qualityTitle">
					<table class="login_table" cellspacing=0 cellpadding=5 width="100%" border=0>
						<tr>
							<td style="font-size: 14px; font-weight: bold; color: #336699k; text-align: center;">品质政策(Quality Policy)</td>
						</tr>
					</table>
				</div>
				<div class="hintTitle">
					品质优先<br>Quality is the First Priority
				</div>
				<div class="hintTitle">
					持续改善<br>Continuous Improvement is Our Commitment
				</div>
				<div class="hintTitle">
					建立高效率的管理系统<br>Efficient Management System is the Key for Success
				</div>
			</div>

			<div id="loginForm">
				<div class="login_table_bg">
					<form name="form1" method="post" action="<c:url value="/main.action" />">
						<table class="login_table" cellspacing=0 cellpadding=5 width="100%" border=0>
							<tr>
								<td align="right">帐&nbsp;号(Account)&nbsp;&nbsp;：</td>
								<td>
									<input type="text" name="os_username" onkeydown="if(event.keycode==13) event.keycode=9" onfocus="efoc();" onblur="eblur();" style="width: 120px;">
								</td>
							</tr>
							<tr>
								<td align="right">密&nbsp;码(Password)：</td>
								<td>
									<input type="password" name="os_password" onkeydown="if(event.keycode==13) event.keycode=9" onfocus="efoc();" onblur="eblur();" style="width: 120px;">
								</td>
							</tr>
							<!-- 
							<tr>
								<td></td>
								<td>
									<input style="border: 0px;" type="checkbox" name="rememberme">
									&nbsp;记住我(Remember me)
								</td>
							</tr>
							-->
							<tr>
								<td>&nbsp;</td>
								<td>
									<button type="submit">Login</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="reset">Reset</button>
								</td>
							</tr>
							<tr>
								<td colspan=2 height=10></td>
							</tr>
						</table>
					</form>
				</div>
				<br>
				
				<div class="login_table_bg">
					<table class="login_table" cellspacing=0 cellpadding=5 width="100%" border=0>
						<tr>
							<td style="font-size: 20px; font-weight: bold; color: black;">We're on your side</td>
							<!-- <td>test / test</td> -->
						</tr>
					</table>
				</div>
			</div>
		</div>

		<div id="loginFoot">
			Copyright&copy;2006 - 2007 CommonFarm.org ALL Rights Reserved
		</div>
	</body>
</html>
