<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/pages/common/taglibs.jsp"%>

<html>
<head>
<title>FitU - Thinking - Main~~</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/styles/main.css" />"></link>
</head>
<body scroll="no" id="thinking">
	<div id="loading">
		<div class="loading-indicator">Loading...</div>
	</div>
	<!-- include everything after the loading indicator -->
	<script type="text/javascript" src="<c:url value="/scripts/yahoo/utilities_2.1.0.js" />"></script>
	<script type="text/javascript" src="<c:url value="/scripts/cssQuery.js" />"></script>
	<script type="text/javascript" src="<c:url value="/scripts/yui-ext/yui-ext.js" />"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/reset-min.css" />" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/layout.css" />"/>
  
  	<script type="text/javascript" src="<c:url value="/scripts/main.js" />"></script>
	
	<div id="header" class="ylayout-inactive-content">
		<div style="padding-top:3px;">FitU - Thinking App Framework</div>
	</div>
  
	<div id="classes" class="ylayout-inactive-content">
	 <!-- BEGIN TREE -->
	<a id="welcome-link" href="welcome.html">Documentation Home</a>
	<div class="pkg">
	<h3>App Management</h3>
		<div class="pkg-body">
			<div class="pkg">
			<h3>User Management</h3>
				<div class="pkg-body">
					<a href="role.action">Role Management</a>
					<a href="group.action">User Group Management</a>
					<a href="user.action">User Management</a> 
				</div>                  
				<a href="output/YAHOO.ext.Actor.html">Actor</a> 
			</div>
		</div>        
	</div>
	<a id="help-forums" href="http://www.commonfarm.org/forum/">Help Forums</a>     
	<!-- END TREE -->
	</div>
  
	<iframe id="main" id="main" frameborder="no"></iframe>
</body>
</html>
