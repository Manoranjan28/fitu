//no less than one checkbox is seleted 
function atLeaseOneCheck()
{
    var items = document.all["items"];
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
    var items = document.all["items"];
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
function saveOrUpdateAction(formName, actionPath, valid) {
	var ok = true;
	if (valid) {
		ok = validate();
	}
	if (ok) {
		window.document.forms[formName].action = actionPath + ".action";
		window.document.forms[formName].submit();
	}
}

function newAction(form, urlPath) {
	form.action = urlPath + ".action";
	form.submit();
}
function editAction(form, urlPath) {
	if (!atLeaseOneCheck()) {
    	alert("Please select only one!");
        return;
    }
    if (!moreThanOneCheck()) {
    	alert("Please select only one!");
        return;
    }
	form.action = urlPath + ".action";
	form.submit();
}
function deleteAction(form, urlPath, entityName, valid) {
	if (confirm("Confirm to delete "+ entityName + "?")) {
        if (!atLeaseOneCheck()) {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.ec_ev.value = '';
        form.ec_efn.value = '';
        form.action = urlPath + ".action";
        form.submit();
    }
}
function selectAction(form, urlPath, entityName, valid) {
	if (confirm("Confirm to select "+ entityName + "?")) {
        if (!atLeaseOneCheck()) {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.action = urlPath + ".action";
        form.submit();
    }
}
//////////////////////////////////////////////////////////
function saveOrUpdateFitu(formName, actionPath, valid) {
	var ok = true;
	if (valid) {
		ok = validate();
	}
	if (ok) {
		window.document.forms[formName].action = actionPath + ".fitu";
		window.document.forms[formName].submit();
	}
}

function newFitu(form, urlPath) {
	form.action = urlPath + ".fitu";
	form.submit();
}
function editFitu(form, urlPath) {
	if (!atLeaseOneCheck()) {
    	alert("Please select only one!");
        return;
    }
    if (!moreThanOneCheck()) {
    	alert("Please select only one!");
        return;
    }
	form.action = urlPath + ".fitu";
	form.submit();
}
function deleteFitu(form, urlPath, entityName, valid) {
	if (confirm("Confirm to delete "+ entityName + "?")) {
        if (!atLeaseOneCheck()) {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.ec_ev.value = '';
        form.ec_efn.value = '';
        form.action = urlPath + ".fitu";
        form.submit();
    }
}
function selectFitu(form, urlPath, entityName, valid) {
	if (confirm("Confirm to select "+ entityName + "?")) {
        if (!atLeaseOneCheck()) {
            alert("Please select more than one " + entityName + "!");
            return;
        }
        form.action = urlPath + ".fitu";
        form.submit();
    }
}


function hideSearch(ctxPath){
	if (document.getElementById("searchButt").style.display == 'none') {
		document.getElementById("searchButt").style.display = 'inline';
		document.getElementById("criteria").style.display = 'inline';
		document.getElementById("hideImg").src = ctxPath + '/images/icon/16x16/up.png';
	} else {
		document.getElementById("searchButt").style.display = 'none';
		document.getElementById("criteria").style.display = 'none';
		document.getElementById("hideImg").src = ctxPath + '/images/icon/16x16/down.png';
	}
}
function imageRoll(target, action) {
	if (action == "over") {
		if (target == 'new') {
			document.getElementById('img_' + target).src = "../images/toolbar/new_2.jpg";
		} else if (target == 'edit') {
			document.getElementById('img_' + target).src = "../images/toolbar/edit_2.jpg";
		} else if (target == 'delete') {
			document.getElementById('img_' + target).src = "../images/toolbar/setting_2.jpg";
		}
	} else {
		if (target == 'new') {
			document.getElementById('img_' + target).src = "../images/toolbar/new_1.jpg";
		} else if (target == 'edit') {
			document.getElementById('img_' + target).src = "../images/toolbar/edit_1.jpg";
		} else if (target == 'delete') {
			document.getElementById('img_' + target).src = "../images/toolbar/setting_1.jpg";
		}
	}
}
function dateJudge(fromDate, toDate) {
	fromDate = fromDate.replace(/-/g, "/");
	toDate = toDate.replace(/-/g, "/");

	fromDate = new Date(fromDate);
	toDate = new Date(toDate);
	var startDate = Date.parse(fromDate);
	var endDate = Date.parse(toDate);
	if (endDate - startDate < 0) {
		alert("Start date must be more than end date!");
		return false;
	}
	var internalDays = parseInt((endDate - startDate)/86400000);
	if (internalDays > 31) {
		alert("Interval days must not be more than 31!");
		return false;
	}
	return true;
}
