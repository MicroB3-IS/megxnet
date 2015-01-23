MegxFormWidget = function(cfg) {

    Alpaca.defaultUI = "jquery-ui";
    Alpaca.logLevel = Alpaca.ERROR;
    this.renderForm(cfg);

}
MegxFormWidget.prototype = {

    submitForm: function(field) {
    	
    	var form = field.form;
        var url = form.attributes.action;
        var ajaxMethod = form.attributes.method;

        if (form) {

            form.registerSubmitHandler(function(e, form) {
                return false;
            });

            form.buttons.submit.click(function() {

            	if (field.isValid(true)) {

                    var data = field.getValue();
                    data.json = JSON.stringify(field.getValue());
                    $.ajax({
                        type: ajaxMethod,
                        url: url,
                        data: data,
                        success: function(data, textStatus, jqXHR) {
                        	
                        	if(data.redirectUrl && data.redirectUrl != null){
                        		alert("Your submission was successfull. Thanks!");
                        		document.location.href = data.redirectUrl;
                        	} else {
                        		
                        		if(data.message && data.message != null){
                        			alert(data.message);
                        			document.location.href = ctx.siteUrl;
                        		} else{
                        			alert("Your submission was successfull. Thanks!");
                            		document.location.href = ctx.siteUrl;
                        		}
                        	}
                        },
                        error: function(data, textStatus, jqXHR) {
                        	
                        	if(data.responseJSON && data.responseJSON != null){
                        		
                        		var responseMsg = data.responseJSON;
                            	
                            	if(responseMsg.redirectUrl && responseMsg.redirectUrl != null){
                            		document.location.href = responseMsg.redirectUrl;
                            	} else {
                            		
                            		if(responseMsg.message && responseMsg.message != null){
                            			$('#errorMsg').text(responseMsg.message).show();
                            		} else{
                            			$('#errorMsg').text("Server error, please try again later.").show();
                            		}
                            	}
                        		
                        	} else {
                        		$('#errorMsg').text("Server error, please try again later.").show();
                        	}
                        	
                        }
                    });
                }
            });
        }
    },

    renderForm: function(cfg) {

        var alpacaOptions = {
            "optionsSource": cfg.optionsLocation,
            "schemaSource": cfg.schemaLocation,
            "view": {

                "parent": "VIEW_JQUERYUI_CREATE",
                "displayReadonly": true,

            },

            "isDynamicCreation": true
        };

        var postRender = function(field) {

            var form = field.form;
            var url = form.attributes.action;
            var ajaxMethod = form.attributes.method;

            if (form) {
            	
                form.registerSubmitHandler(function(e, form) {
                    return false;
                });

                form.buttons.submit.click(function() {

                    if (field.isValid(true)) {

                        var data = field.getValue();
                        data.json = JSON.stringify(field.getValue());
                        $.ajax({
                            type: ajaxMethod,
                            url: url,
                            data: data,
                            success: function(data, textStatus, jqXHR) {
                            	
                            	if(data.redirectUrl && data.redirectUrl != null){
                            		alert("Your submission was successfull. Thanks!");
                            		document.location.href = data.redirectUrl;
                            	} else {
                            		
                            		if(data.message && data.message != null){
                            			alert(data.message);
                            			document.location.href = ctx.siteUrl;
                            		} else{
                            			alert("Your submission was successfull. Thanks!");
                                		document.location.href = ctx.siteUrl;
                            		}
                            	}
                            },
                            error: function(data, textStatus, jqXHR) {
                            	
                            	if(data.responseJSON && data.responseJSON != null){
                            		
                            		var responseMsg = data.responseJSON;
                                	
                                	if(responseMsg.redirectUrl && responseMsg.redirectUrl != null){
                                		document.location.href = responseMsg.redirectUrl;
                                	} else {
                                		
                                		if(responseMsg.message && responseMsg.message != null){
                                			$('#errorMsg').text(responseMsg.message).show();
                                		} else{
                                			$('#errorMsg').text("Server error, please try again later.").show();
                                		}
                                	}
                            		
                            	} else {
                            		$('#errorMsg').text("Server error, please try again later.").show();
                            	}
                            }
                        });
                    }
                });
            }
        };

        var buts = {
            "done": {
                "validateOnClick": true,
                "onClick": function() {
                    //        			alert("done clicked."); 
                    submitForm($("#" + cfg.formId))
                }
            },

            "next": {
                "validateOnClick": true,
                "onClick": function() {
                    //            alert("Fired next.onClick handler");
                }
            },
            "prev": {
                "validateOnClick": true,
                "onClick": function() {
                    //alert("Fired prev.onClick handler");
                }
            }
        };
        // now adding wizard to view in case we have one
        if (typeof cfg.wizard != "undefined" && cfg.wizard) {
            alpacaOptions.view.wizard = cfg.wizard;
            alpacaOptions.view.wizard.buttons = buts;
        }

        if (typeof cfg.postRender != "undefined" && cfg.postRender) {
            alpacaOptions.postRender = cfg.postRender;
        } else {
            alpacaOptions.postRender = postRender;
        }

        $("#" + cfg.target).alpaca(alpacaOptions);
    }
}