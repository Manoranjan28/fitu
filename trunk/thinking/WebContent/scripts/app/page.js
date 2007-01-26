//no less than one checkbox is seleted 
function atleaseOneCheck()
{
    var items = document.all["itemlist"];
    if(items.length>0){
	    for (var i = 0; i < items.length; i++)
	    {
	        if (items[i].checked == true)
	        {
	            return true;
	        }
	    }
	}else{
		if(items.checked == true){
			  return true;		   	 
		}
	}
    return false;
}
function moreThanOneCheck() {
    var items = document.all["itemlist"];
    var editFlag = false;
    if(items.length > 0) {
	    for (var i = 0; i < items.length; i++) {
	        if (items[i].checked == true) {
	        	if (editFlag) return false;
	            editFlag = true;
	        }
	    }
	    return editFlag;
	} else {
		if(items.checked == true) {
			  return true;		   	 
		}
	}
    return false;
}
function batch_del(form, entityName, action)
{
    if (confirm("Confirm to delete "+ entityName + "?"))
    {
        if (!atleaseOneCheck())
        {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.ec_ev.value = '';
        form.ec_efn.value = '';
        form.action = action;
        form.submit();
    }
}

function batch_do(form, entityName, action)
{
    if (confirm("Confirm to "+ entityName + "?"))
    {
        if (!atleaseOneCheck())
        {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.ec_ev.value = '';
        form.ec_efn.value = '';
        form.action = action;
        form.submit();
    }
}
function batch_auth(form, entityName, action)
{
    if (confirm("Confirm to authorize "+ entityName + "?"))
    {
        if (!atleaseOneCheck())
        {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.action = action;
        form.submit();
    }
}

function openwin(url, width, height, scroll)
{
    if (!document.all)
    {
        document.captureEvents(Event.MOUSEMOVE);
        x = e.pageX + width - 30;
        y = e.pageY + height - 30;
    }
    else
    {
        x = document.body.scrollLeft + event.clientX + width - 30;
        y = document.body.scrollTop + event.clientY + height - 30;
    }
    window.open(url, "newWindow", "height=" + height + ", width=" + width + ", toolbar =no, menubar=no, scrollbars=" + scroll + ", resizable=no, location=no, status=no, top=" + y + ", left=" + x + "") //????????????
}
/* Save Data */
function saveAction(form, actionPath, valid) {
	var ok = true;
	if (valid) {
		ok = validate();
	}
	if (ok) {
		form.action = actionPath + ".do?method=save";
		form.submit();
	}
}
function updateAction(form, actionPath, valid) {
	var ok = true;
	if (valid) {
		ok = validate();
	}
	if (ok) {
		form.action = actionPath + ".do?method=update";
		form.submit();
	}
}
function newAction(form, urlPath) {
	form.action = urlPath + ".do?method=new";
	form.submit();
}
function editAction(form, urlPath) {
	if (!atleaseOneCheck()) {
    	alert("Please select only one!");
        return;
    }
    if (!moreThanOneCheck()) {
    	alert("Please select only one!");
        return;
    }
	form.action = urlPath + ".do?method=edit";
	form.submit();
}
function deleteAction(form, urlPath, entityName, valid) {
	if (confirm("Confirm to delete "+ entityName + "?")) {
        if (!atleaseOneCheck()) {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.ec_ev.value = '';
        form.ec_efn.value = '';
        form.action = urlPath + ".do?method=delete";
        form.submit();
    }
}
function hideSearch(){
	if ($("searchButt").style.display == 'none') {
		$("searchButt").style.display = 'inline';
		$("criteria").style.display = 'inline';
		$("hideImg").src = '../../images/icon/16x16/less.gif';
	} else {
		$("searchButt").style.display = 'none';
		$("criteria").style.display = 'none';
		$("hideImg").src = '../../images/icon/16x16/more.gif';
	}
}
