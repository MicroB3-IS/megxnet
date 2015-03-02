// TODO create generic AJAX function in CHON template to avoid re-inventing the
// wheel in each module 
var ajaxCall = function(httpVerb, url, data, successHandler, errorHandler) {
  jQuery.ajax({
    type : httpVerb,
    url : url,
    data : data,
    contentType : "application/json",
    success : successHandler,
    // TODO move to global error handler in megx.js
    error : function(jqXHR, textStatus, errorThrown) {
      var message = 'Your request could not be completed.';
      if (jqXHR.status == 500) {
        var data = JSON.parse(jqXHR.responseText).data;
        var cause = data.cause;
        while (cause && cause.cause) {
          cause = cause.cause;
        }
        if (cause && cause.message) {
          message += " " + cause.message;
        }
      }
      toastr.error(message, errorThrown);
      if (errorHandler) {
        errorHandler.call(this, data, jqXHR, textStatus, errorThrown);
      }
    }
  });
};

// OSD Registry module
Megx.osdRegistry = {};

Megx.osdRegistry.loadParticipantData = function(id, handler) {
  var url = ctx.siteUrl + "/ws/v1/OSDRegistry/v1.0.0/getParticipant";
  ajaxCall("GET", url, {id : id}, handler);
};

Megx.osdRegistry.validateParticipantSubmissionForm = function() {
  var form = jQuery("#participantForm");
  form.validate({
    highlight : function(element, errorClass, validClass) {
      jQuery(element).parent().parent().addClass(errorClass).removeClass(validClass);
    },
    unhighlight : function(element, errorClass, validClass) {
      jQuery(element).parent().parent().removeClass(errorClass).addClass(validClass);
    },
    errorClass : 'has-error',
    validClass : 'has-success',
    errorPlacement : function(error, element) {
      error.appendTo(element.parent().parent().children(".help-block"));
    },
    rules : {
      osdID : {
        required : true
      },
      siteName : {
        required : true
      },
      institution : {
        required : true
      },
      institutionAddress : {
        required : true
      },
      country : {
        required : true
      },
      institutionWebAddress : {
        required : false,
        url : true
      },
      siteCoordinator : {
        required : true
      },
      coordinatorEmail : {
        required : false,
        email : true
      },
      siteLat : {
        required : true,
        number : true
      },
      siteLong : {
        required : true,
        number : true
      },
      institutionLat : {
        required : true,
        number : true
      },
      institutionLong : {
        required : true,
        number : true
      }
    }
  });
  if (form.valid()) {
    Megx.osdRegistry.submitParticipant(form.getJsonData());
  }
};

Megx.osdRegistry.submitParticipant = function(json) {
  var url = ctx.siteUrl + "/ws/v1/OSDRegistry/v1.0.0/";
  var redirectUrl = ctx.siteUrl + "/osd-registry/list";

  if (Megx.osdRegistry.isInEditMode) {
    url += "updateParticipant";
  } else {
    url += "addParticipant";
  }

  ajaxCall("POST", url, {
    "participant" : JSON.stringify(json)
  }, function() {
    window.location.href = redirectUrl;
  });
};

Megx.osdRegistry.deleteParticipant = function(id) {
  var url = ctx.siteUrl + "/ws/v1/OSDRegistry/v1.0.0/deleteParticipant";
  var redirectUrl = ctx.siteUrl + "/osd-registry/list";

  ajaxCall("POST", url, {
    id : id
  }, function() {
    jQuery('#deleteDialog').modal('hide');
    window.location.href = redirectUrl;
  }, function() {
    jQuery('#deleteDialog').modal('hide');
  });
};
