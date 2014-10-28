MegxFormWidget = function( cfg ) {

  Alpaca.defaultUI = "jquery-ui";
  Alpaca.logLevel = Alpaca.ERROR;
  this.renderForm(cfg);

}
MegxFormWidget.prototype = {

  renderForm : function( cfg ) {

    var alpacaOptions = {
      "optionsSource" : cfg.optionsLocation,
      "schemaSource" : cfg.schemaLocation,
      "view" : {

        "parent" : "VIEW_JQUERYUI_CREATE",

      },

      "isDynamicCreation" : true,

      "postRender" : function( field ) {
        var form = field.form;

        if ( form ) {
          form.refreshValidationState(false);

          form
            .registerSubmitHandler(function( e, form ) {
              // validate the entire form (top control + all children)
              form.validate(true);
              // draw the validation state (top control + all children)
              form.refreshValidationState(true);
 
              // now display something
              if ( form.isFormValid() ) {
                var json = form.getValue();
                var formJson = JSON.stringify(field.getValue());
                //alert("formJson: " + formJson);
                $('<input/>').attr('name', 'json').attr('value', formJson).appendTo("#" + cfg.formId);
              } else {
                alert("There is wrong input.  Please make necessary corrections as (hopefully) indicated.");
                return false;
              }
              e.stopPropagation();
              return true;
            });
        }
      }
    };

    // now adding wizard to view in case we have one
    if ( typeof cfg.wizard != "undefined" && cfg.wizard ) {
      alpacaOptions.view.wizard =  cfg.wizard;
    }

    $("#" + cfg.target).alpaca(alpacaOptions);
  }
}
