<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	<%@ include file="/pages/common/meta.jsp"%>
	<title>Edit Article Infomation</title>
	<link href="<c:url value="/styles/app/page.css"/>" type=text/css rel=stylesheet>
	<link href="<c:url value="/styles/app/messages.css"/>" type=text/css rel=stylesheet>
	<script type="text/javascript" src="<c:url value="/scripts/app/page.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/prototype.js"/>"></script>
<script type="text/javascript">
function new_attach(){
  var count = parseInt($('attach_count').innerHTML);
  if(count<3){
    number = count + 1;
    $('attach_count').innerHTML = number;
   new Insertion.Bottom('new_attach_div', '<table> <tr height="30"><td colspan="2"><b>Attachment '+number
    +'</b> </td></tr><tr height="30"><td > <b>File</b> </td><td>'
    +'<input id="uploadFiles" name="uploadFiles" size="30" type="file">'
    +'</td></tr> <tr valign="top"><td> <b>Desc</b>  </td><td>'
    +' <textarea id="fileDescs" name="fileDescs" rows="3" cols="35"  size="40" style="width:300px;height:80px;"></textarea></td></tr></table><br/>');
  
   }else{
     $('attach_msg').innerHTML = 'Less than three files';
     Element.show('attach_msg');
   }
}
function validate() {
	return true;
}
</script>
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
<ww:form action="article" method="POST" enctype="multipart/form-data">
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
		<tr>
			<td>Attach</td>
			<td>
			<span class="gen">Allow jpg, gif, png, bmp and zip, rar files. Less than three files.</span><br/><br/>
		        Now <span id="attach_count">0</span> attachment.&nbsp;&nbsp;
		        <input name="add_attachment" value="New Attachment"  onclick="new_attach();" type="button"/>&nbsp;&nbsp;
		        <span id="attach_msg" style="color:red;display:none;"></span>
		        <br/><br/>
		        
		       <div id="new_attach_div" style="margin:10px 0px 0px 20px;">
		       </div>
		     <br/>
			</td>
		</tr>
	</table>
</ww:form>
</div>
<!-- Form End -->
</body>
</html>
