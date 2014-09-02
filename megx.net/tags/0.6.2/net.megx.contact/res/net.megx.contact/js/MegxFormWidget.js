MegxFormWidget = function(cfg) {
	
	Alpaca.defaultUI = "jquery-ui";
    Alpaca.logLevel = Alpaca.ERROR;
    this.renderForm(cfg);
    
}
MegxFormWidget.prototype = {

    renderForm : function (cfg) {
    	
        var schemaLocation = cfg.schemaLocation;
        var optionsLocation = cfg.optionsLocation;
        var inputBundle = {
          "optionsSource" : optionsLocation,
          "schemaSource" : schemaLocation,
          "view" : "VIEW_JQUERYUI_CREATE",
          "isDynamicCreation" : true,
          "postRender" : function( field ) {
            var form = field.form;

            if ( form ) {
              form.refreshValidationState(false);

              form.registerSubmitHandler(function( e, form ) {
                  // validate the entire form (top control + all children)
                  form.validate(true);
                  // draw the validation state (top control + all children)   
                  form.refreshValidationState(true);

                  // now display something 
                  if ( form.isFormValid() ) {
                    var json = form.getValue();
                    var formJson = JSON.stringify(field.getValue());
                   // $('<input/>').attr('name', 'json').attr('value', formJson)
                   // .appendTo('#contact-form');
                  } else {
                    alert("There are problems with the form.  Please make necessary corrections as (hopefully) indicated.");
                  }
                  e.stopPropagation();
                  return true;
                });
            }
          }
        };
    	
      $("#" + cfg.target).alpaca(inputBundle);
    }   	   
}
