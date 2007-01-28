// create the HelloWorld application (single instance)
var SearchDialog = function(){
    // everything in this space is private and only accessible in the HelloWorld block
    
    // define some private variables
    var dialog, showBtn, searchBtn;
    
    var toggleTheme = function(){
        getEl(document.body, true).toggleClass('ytheme-gray');
    };
    // return a public interface
    return {
        init : function(){
             showBtn = getEl('searchBtn');
             // attach to click event
             showBtn.on('click', this.showSearchDialog, this, true);
             
             getEl('theme-btn').on('click', toggleTheme);
        },
        
        showSearchDialog : function(){
            if(!dialog){ // lazy initialize the dialog and only create it once
                dialog = new YAHOO.ext.BasicDialog("searchDialog", { 
                        modal:true,
                        autoTabs:true,
                        width:300,
                        height:500,
                        shadow:true,
                        minWidth:300,
                        minHeight:250,
                        proxyDrag: true
                });
                dialog.addKeyListener(27, dialog.hide, dialog);
                //dialog.addButton('Close', dialog.hide, dialog);
                //searchBtn = dialog.addButton('Execute', this.submitSearch, dialog);//.disable();
            }
            dialog.show(showBtn.dom);
        },
        // submit the comment to the server
        submitSearch : function(){
            //searchBtn.disable();
            //wait.radioClass('active-msg');
            YAHOO.util.Connect.setForm(document.getElementById('listUser'));
    
            YAHOO.util.Connect.asyncRequest('POST', 'listUser.action', {});          
        	dialog.hide();
        }
    };
}();

// using onDocumentReady instead of window.onload initializes the application
// when the DOM is ready, without waiting for images and other resources to load
YAHOO.ext.EventManager.onDocumentReady(SearchDialog.init, SearchDialog, true);