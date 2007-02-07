<%-- ActionError Messages - usually set in Actions --%>
<s:if test="hasActionErrors()">
    <div class="error" id="errorMessages">    
	    <s:iterator value="actionErrors">
	    	<img src="<c:url value="/images/iconWarning.gif"/>" alt="Warnning" class="icon" />
	    	<s:property escape="false"/><br />
	    </s:iterator>
   </div>
</ww:if>

<%-- FieldError Messages - usually set by validation rules --%>
<s:if test="hasFieldErrors()">
    <div class="error" id="errorMessages">    
    	<s:iterator value="fieldErrors">
	        <s:iterator value="value">
	            <img src="<c:url value="/images/iconWarning.gif"/>" alt="Warnning" class="icon" />
	             <s:property escape="false"/><br />
	        </s:iterator>
    	</s:iterator>
	</div>
</s:if>

<%-- Success Messages --%>
<c:if test="hasActionMessages()">
    <div class="message" id="successMessages">
    	<s:iterator value="actionMessages">
	    	<img src="<c:url value="/images/iconInformation.gif"/>" alt="Information" class="icon" />
	    	<s:property escape="false"/><br />
	    </s:iterator>    
    </div>
</c:if>