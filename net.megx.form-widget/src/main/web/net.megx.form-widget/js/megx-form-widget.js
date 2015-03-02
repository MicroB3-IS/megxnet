Megx.FormWidget = function(cfg) {
  jQuery(
      "<div id='"
          + cfg.target
          + "-loading-animation'><span class='glyphicon glyphicon-refresh glyphicon-refresh-animate'></span> Loading form</div>")
      .insertBefore("#" + cfg.target);
  Alpaca.logLevel = Alpaca.ERROR;
  Megx.FormWidget.cfg = cfg;
  this.renderForm(cfg);
}

Megx.FormWidget.prototype.submitForm = function(field) {

  var self = this;
  var form = field.form;
  var url = form.attributes.action;
  var ajaxMethod = form.attributes.method;

  if (form) {

    jQuery(".alpaca-control").keyup(function() {
      if (field.isValid(true)) {
        form.enableSubmitButton();
      }
    });

    form.registerSubmitHandler(function(e, form) {
      return false;
    });
    var id = "#" + form.id;

    var submitButton;
    if (Megx.FormWidget.cfg.wizard) {
      submitButton = jQuery('button[data-alpaca-wizard-button-key="submit"]', id);
    } else {
      submitButton = jQuery('button[type="submit"]', id);
    }
    // hack to change the CSS classes of the submit button
    // might be possible to do this using Alpaca templates
    // but could not figure out how
    submitButton.removeClass("btn-default");
    submitButton.addClass("btn-primary");

    submitButton.click(function() {

      form.disableSubmitButton();
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
          if (data.responseJSON) {

            var responseMsg = data.responseJSON;

            if (responseMsg.redirectUrl) {
              document.location.href = responseMsg.redirectUrl;
            } else {

              if (responseMsg.message) {
                toastr.error(responseMsg.message, jqXHR);
              } else {
                toastr.error("Server error, please try again later.", jqXHR);
              }
            }
          } else {
            toastr.error("Server error, please try again later.", jqXHR);
          }
        }).always(function(data, textStatus, jqXHR) {
          form.enableSubmitButton();
        });
      }
    });
    jQuery("#" + Megx.FormWidget.cfg.target + "-loading-animation").remove();
  }
}

Megx.FormWidget.prototype.renderForm = function(cfg) {
  var self = this;

  var alpacaOptions = {
    "optionsSource" : cfg.optionsLocation,
    "schemaSource" : cfg.schemaLocation,
    "view" : {
      "parent" : cfg.parentView,
      "displayReadonly" : true
    },

    "isDynamicCreation" : true
  };

  // now adding wizard to view in case we have one
  if (typeof cfg.wizard != "undefined" && cfg.wizard) {
    alpacaOptions.view.wizard = cfg.wizard;
  }

  if (typeof cfg.postRender != "undefined" && cfg.postRender) {
    alpacaOptions.postRender = cfg.postRender;
  } else {
    alpacaOptions.postRender = self.submitForm;
  }
  
  // pre-fill form if datasource is given
  if (cfg.data) {
    alpacaOptions.data = cfg.data;
  }

  jQuery("#" + cfg.target).alpaca(alpacaOptions);

}