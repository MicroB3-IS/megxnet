MegxFormWidget = function(cfg) {
  Alpaca.logLevel = Alpaca.ERROR;
  this.cfg = cfg;
  this.renderForm(cfg);

}

MegxFormWidget.prototype.submitForm = function(field) {

  var self = this;
  var form = field.form;
  var url = form.attributes.action;
  var ajaxMethod = form.attributes.method;

  if (form) {

    // move Error bar between form and button bar
    jQuery('.alpaca-form-buttons-container').prev().append(jQuery('#' + MegxFormWidget.cfg.errorTarget));

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
        }).success(function(data, textStatus, jqXHR) {

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
        }).error(function(data, textStatus, jqXHR) {

          var errorElement = jQuery('#' + MegxFormWidget.cfg.errorTarget);
          if (data.responseJSON) {

            var responseMsg = data.responseJSON;

            if (responseMsg.redirectUrl) {
              document.location.href = responseMsg.redirectUrl;
            } else {

              if (responseMsg.message && responseMsg.message != "") {
                errorElement.children('.error-text').text(responseMsg.message).show();
                errorElement.removeClass('hidden');
              } else {
                errorElement.children('.error-text').text("Server error, please try again later.");
                errorElement.removeClass('hidden');
              }
            }
          } else {
            errorElement.children('.error-text').text("Server error, please try again later.");
            errorElement.removeClass('hidden');
          }
        }).always(function(data, textStatus, jqXHR) {
          form.enableSubmitButton();
        });
      }
    });
  }
}

MegxFormWidget.prototype.renderForm = function(cfg) {
  var self = this;

  var alpacaOptions = {
    "optionsSource" : cfg.optionsLocation,
    "schemaSource" : cfg.schemaLocation,
    "view" : {
      "parent" : cfg.parentView,
      "displayReadonly" : true,
    },
    "isDynamicCreation" : true
  };

  var buts = {
    "done" : {
      "validateOnClick" : true,
      "onClick" : function() {
        // alert("done clicked.");
        self.submitForm(jQuery("#" + cfg.formId))
      }
    },

    "next" : {
      "validateOnClick" : true,
      "onClick" : function() {
        // alert("Fired next.onClick handler");
      }
    },
    "prev" : {
      "validateOnClick" : true,
      "onClick" : function() {
        // alert("Fired prev.onClick handler");
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
    alpacaOptions.postRender = self.submitForm;
  }

  jQuery("#" + cfg.target).alpaca(alpacaOptions);

}