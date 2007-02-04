<%-- Error Messages --%>
<s:if test="%{messageChar == 'error'}">
<div class="error">	
    <s:property value="message" />
</div>
</s:if>
<%-- Success Messages --%>
<s:if test="%{messageChar == 'message'}">
<div class="message">	
    <s:property value="message" />
</div>
</s:if>