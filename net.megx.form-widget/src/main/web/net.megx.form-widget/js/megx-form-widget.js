// root namespace
Megx = Megx || {};

Megx.FormWidget = function(cfg) {
  // add to static context, cause need to have access from static Alpaca
  // context
  Megx.FormWidget.cfg = cfg;

};

Megx.FormWidget.prototype.submitForm = function(field) {

  var self = this;
  var form = field.form;
  var url = form.attributes.action;
  var ajaxMethod = form.attributes.method;

  form.enableSubmitButton();

  if (form) {

    form.registerSubmitHandler(function(e, form) {
      return false;
    });
    var id = "#" + form.id;

    var submitButton = form.getButtonEl("submit");

    submitButton.removeClass("btn-default");
    submitButton.addClass("btn-primary");

    submitButton
        .click(function() {


          field.refreshValidationState(true);

          if ( ! form.isFormValid() ) {

            toastr.error("Some field(s) above are not correct or missing. <br /> Please scroll up and apply corrections.", "Form Error", {
              "closeButton" : true,
              "positionClass" : "toast-bottom-center",
              "timeOut" : 0,
              "extendedTimeOut" : 0,
            });

            form.focus();
            return;
          }

          if (form.isFormValid(true)) {

            var data = field.getValue();
            data.json = JSON.stringify(field.getValue());
            jQuery
                .ajax({
                  type : ajaxMethod,
                  url : url,
                  dataType : "json",
                  data : data
                })
                .done(
                    function(data, textStatus, jqXHR) {

                      var buttonHtml = "<br /><button onclick=\"javascript:location.reload(true)\" type=\"button\" class=\"btn btn-primary clear\">Reload</button>";
                      var msg = "Your submission was successfull. Thanks! <br /> Click to reload page"
                          + buttonHtml;
                      // default redirect to itself
                      var redirectUrl = document.location.href;

                      if (data.message) {
                        msg = data.message + buttonHtml;
                      }

                      var title = data.title ? data.title : "Success";

                      if (data.redirectUrl) {
                        redirectUrl = data.redirectUrl;
                      }

                      toastr.success(msg, title, {
                        "closeButton" : true,
                        "positionClass" : "toast-bottom-center",
                        "timeOut" : 0,
                        "extendedTimeOut" : 0
                      });

                      form.disableSubmitButton();

                    }).fail(function(jqXHR, textStatus, errorThrown) {
                  form.enableSubmitButton();
                  var data = {};

                  var msg = "Server error, please try again later.";

                  if (jqXHR.responseJSON) {
                    data = jqXHR.responseJSON;
                  }

                  var redirectUrl = document.location.href;

                  if (data.message) {
                    msg = data.message;
                  }

                  var title = data.title ? data.title : textStatus;
                  title = title + " " + errorThrown;

                  if (data.redirectUrl) {
                    redirectUrl = data.redirectUrl;
                  }

                  toastr.error(msg, title, {
                    "closeButton" : true,
                    "positionClass" : "toast-bottom-center",
                    "timeOut" : 0,
                    "extendedTimeOut" : 0,
                  });

                }).always(function(data, textStatus, jqXHR) {
                });
          }// end if(isValid)
        }); // end submit click
    if ($(id).alpaca("exists")) {
      jQuery("#" + Megx.FormWidget.animId).remove();
    }
  }
}

Megx.FormWidget.prototype.renderForm = function() {

  // getting config from static context
  var cfg = Megx.FormWidget.cfg;
  // add loading animation
  Megx.FormWidget.animId = cfg.target + "-loading-animation";
  jQuery(
      '<div id="'
          + Megx.FormWidget.animId
          + '"><span class="glyphicon glyphicon-refresh glyphicon-refresh-animate glyphicon-spin"></span> Loading form</div>')
      .insertBefore("#" + cfg.target);

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


  if (typeof cfg.view != "undefined" && cfg.view) {
    alpacaOptions.view = cfg.view;
  }
  if (typeof cfg.locale != "undefined" && cfg.locale) {
    alpacaOptions.view.locale = cfg.locale;
  }

  if (typeof cfg.layout != "undefined" && cfg.layout) {
    alpacaOptions.view.layout = cfg.layout;
  }


  // pre-fill form if data is given
  if (typeof cfg.data != "undefined" && cfg.data && !cfg.dataSource) {
    alpacaOptions.data = cfg.data;
  }
//pre-fill form if datasource is given
  if (typeof cfg.dataSource != "undefined" && cfg.dataSource && !cfg.data) {
//    console.log("datSource=%s",cfg.dataSource);
    alpacaOptions.dataSource = cfg.dataSource;
  } else {
    console.log("No dataSource");
  }

  // actual loading of alpaca based form
  Alpaca.logLevel = Alpaca.ERROR;
  jQuery("#" + cfg.target).alpaca(alpacaOptions);
}
