MegxFormWidget = function(cfg) {

  Alpaca.logLevel = Alpaca.ERROR;
  this.renderForm(cfg);

}
MegxFormWidget.prototype = {

  renderForm : function(cfg) {

    var alpacaOptions = {
      "optionsSource" : cfg.optionsLocation,
      "schemaSource" : cfg.schemaLocation,
      "view" : {
        "parent" : cfg.parentView,
        "displayReadonly" : true,
      },
      "isDynamicCreation" : true
    };

    var postRender = function(field) {

      var form = field.form;
      var url = form.attributes.action;
      var ajaxMethod = form.attributes.method;

      if (form) {

        form.registerSubmitHandler(function(e, form) {
          return false;
        });
        var id = "#" + form.id;
        jQuery('button[type="submit"]', id).click(function() {
          if (field.isValid(true)) {

            var data = field.getValue();
            data.json = JSON.stringify(field.getValue());
            jQuery.ajax({
              type : ajaxMethod,
              url : url,
              data : data
            }).done(function(data, textStatus, jqXHR) {

              if (data.redirectUrl) {
                alert("Your submission was successfull. Thanks!");
                document.location.href = data.redirectUrl;
              } else {

                if (data.message) {
                  alert(data.message);
                  document.location.href = ctx.siteUrl;
                } else {
                  alert("Your submission was successfull. Thanks!");
                  document.location.href = ctx.siteUrl;
                }
              }
            }).fail(function(data, textStatus, jqXHR) {

              var errorElement = jQuery('#' + cfg.errorTarget);
              if (data.responseJSON && data.responseJSON != null) {

                var responseMsg = data.responseJSON;

                if (responseMsg.redirectUrl) {
                  document.location.href = responseMsg.redirectUrl;
                } else {

                  if (responseMsg.message) {
                    errorElement.text(responseMsg.message).show();
                  } else {
                    errorElement.text("Server error, please try again later.").show();
                  }
                }

              } else {
                errorElement.text("Server error, please try again later.").show();
              }
            });
          }
        });
      }
    };

    var buts = {
      "done" : {
        "validateOnClick" : true,
        "onClick" : function() {
          submitForm(jQuery("#" + cfg.formId))
        }
      },

      "next" : {
        "validateOnClick" : true,
        "onClick" : function() {
        }
      },
      "prev" : {
        "validateOnClick" : true,
        "onClick" : function() {
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

    jQuery("#" + cfg.target).alpaca(alpacaOptions);
  }
}