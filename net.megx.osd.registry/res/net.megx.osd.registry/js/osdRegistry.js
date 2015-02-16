jQuery(document).ready(function() {

  jQuery("#submitOSDParticipant").click(function() {
    jQuery("#participantForm").validate({
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
    if (jQuery("#participantForm").valid()) {
      submitParticipant();
    }
  });

  var ajaxCall = function(httpVerb, url, data, successHandler, errorHandler) {
    jQuery.ajax({
      type : httpVerb,
      url : url,
      data : data,
      contentType : "application/json",
      success : successHandler,
      // function(data) {
      // successHandler.call(this, data);
      // },
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

  var loadParticipantData = function(id, handler) {
    var url = ctx.siteUrl + "/ws/v1/OSDRegistry/v1.0.0/getParticipant";

    ajaxCall("GET", url, {
      id : id
    }, handler);
  };

  var submitParticipant = function() {

    var url = "";
    var redirectUrl = ctx.siteUrl + "/osd-registry/list";

    if (isInEditMode) {
      url = ctx.siteUrl + "/ws/v1/OSDRegistry/v1.0.0/updateParticipant";
    } else {
      url = ctx.siteUrl + "/ws/v1/OSDRegistry/v1.0.0/addParticipant";
    }

    ajaxCall("POST", url, {
      "participant" : JSON.stringify(retrieveFormData())
    }, function() {
      window.location.href = redirectUrl;
    });
  };

  var retrieveFormData = function() {
    var populatedObject = {
      "osdID" : jQuery("#osdID").val(),
      "siteName" : jQuery("#siteName").val(),
      "institution" : jQuery("#institution").val(),
      "institutionAddress" : jQuery("#institutionAddress").val(),
      "country" : jQuery("#country").val(),
      "institutionWebAddress" : jQuery("#institutionWebAddress").val(),
      "siteCoordinator" : jQuery("#siteCoordinator").val(),
      "coordinatorEmail" : jQuery("#coordinatorEmail").val(),
      "siteLat" : jQuery("#siteLat").val(),
      "siteLong" : jQuery("#siteLong").val(),
      "institutionLat" : jQuery("#institutionLat").val(),
      "institutionLong" : jQuery("#institutionLong").val(),
      "id" : isInEditMode == true ? participantID : ""
    };

    return populatedObject;
  };

  var populateFormData = function(retrievedData) {
    jQuery("#osdID").val(retrievedData.osdID);
    jQuery("#siteName").val(retrievedData.siteName);
    jQuery("#institution").val(retrievedData.institution);
    jQuery("#institutionAddress").val(retrievedData.institutionAddress);
    jQuery("#country").val(retrievedData.country);
    jQuery("#institutionWebAddress").val(retrievedData.institutionWebAddress);
    jQuery("#siteCoordinator").val(retrievedData.siteCoordinator);
    jQuery("#coordinatorEmail").val(retrievedData.coordinatorEmail);
    jQuery("#siteLat").val(retrievedData.siteLat);
    jQuery("#siteLong").val(retrievedData.siteLong);
    jQuery("#institutionLat").val(retrievedData.institutionLat);
    jQuery("#institutionLong").val(retrievedData.institutionLong);
  };

  if (typeof isInEditMode != 'undefined' && isInEditMode) {
    loadParticipantData(participantID, populateFormData);
  }

  var deleteParticipant = function(id) {
    console.log("deleting " + id);
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

  jQuery('#deleteDialog').on('shown.bs.modal', function() {
    jQuery('#deleteDialog-cancelButton').focus();
  });

  jQuery('#deleteDialog').on('show.bs.modal', function(event) {
    var button = jQuery(event.relatedTarget) // Button that triggered the modal
    var id = button.data('action-id') // Extract info from data-* attributes
    var modal = jQuery(this)
    modal.find('.modal-body').find('.delete-id').text(id);
    modal.find('#deleteDialog-deleteButton').off().on('click', function(event) {
      deleteParticipant(id)
    });
  });


  jQuery(".addNewParticipant").click(function() {
    window.location.href = ctx.siteUrl + "/osd-registry/add";
  });

});