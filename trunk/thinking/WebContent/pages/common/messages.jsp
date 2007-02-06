<%-- Error Messages --%>
<s:if test="messageChar">
<div class="message">	
    <s:property value="message" />
</div>
</s:if>
<%-- Success Messages --%>
<s:else>
<div class="error">	
    <s:property value="message" />
</div>
</s:else>