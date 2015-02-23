/*******************************************************************************
 * CONSOLE STUBS
 ******************************************************************************/
/* prevents JS errors in browsers which lack console features */
(function() {
  var method;
  var noop = function() {
  };
  var methods = [ 'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error', 'exception', 'group',
      'groupCollapsed', 'groupEnd', 'info', 'log', 'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
      'timeStamp', 'trace', 'warn' ];
  var length = methods.length;
  var console = (window.console = window.console || {});

  while (length--) {
    method = methods[length];

    // Only stub undefined methods.
    if (!console[method]) {
      console[method] = noop;
    }
  }
}());

/*******************************************************************************
 * TOASTR defaults
 ******************************************************************************/
toastr.options = {
  "closeButton" : true,
  "debug" : false,
  "newestOnTop" : false,
  "progressBar" : true,
  "positionClass" : "toast-top-full-width",
  "preventDuplicates" : false,
  "onclick" : null,
  "showDuration" : "300",
  "hideDuration" : "1000",
  "timeOut" : "10000",
  "extendedTimeOut" : "20000",
  "showEasing" : "swing",
  "hideEasing" : "linear",
  "showMethod" : "fadeIn",
  "hideMethod" : "fadeOut",
  "closeHtml" : '<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
};

/*******************************************************************************
 * Data binding
 ******************************************************************************/

/*
 * Extend jQuery with a simple data binding function the function expects to
 * find elements with 'data-megx-bind' attributes and the value of this field is
 * expected to match an attribute of the data object, e.g. an element having
 * data-megx-bind="id" will get the value of data["id"] assigned as text/value.
 * 
 * Bind data to a non-form HTML structure, e.g. a modal dialog:
 * 
 * jQuery("deleteDialog").bindData(data);
 * 
 * Bind data to HTML form:
 * 
 * jQuery("OSDSubmissionForm").bindData(data, true);
 * 
 * data is the variable containing the data as a Javascript object.
 */
jQuery.fn.extend({
  bindData : function(data, isForm) {
    if (data) {
      this.find("[data-megx-bind]").each(function copyDataToView() {
        $this = jQuery(this);
        if (isForm) {
          $this.val(data[$this.data("megx-bind")]);
        } else {
          $this.text(data[$this.data("megx-bind")]);
        }
      });
    } else {
      toastr.error('No data available', 'Error');
    }
  }
});

/*
 * Corresponding method to retrieve the values of a form. A Javascript object
 * containing the values of the form is returned. The caller has to stringify
 * the result before sending.
 * 
 * Example usage: var formData = jQuery("OSDSubmissionForm").getJsonData();
 */
jQuery.fn.extend({
  getJsonData : function() {
    json = {};
    this.find("[data-megx-bind]").each(function getDataFromView() {
      $this = jQuery(this);
      json[$this.data("megx-bind")] = $this.val();
    });
    return json;
  }
});

/*******************************************************************************
 * Document ready function of the base template
 ******************************************************************************/
/* contains commands which must be executed on each page load */
jQuery(document).ready(function() {

  main = jQuery('main');
  mainMenu = jQuery('#main-menu');
  mainOriginalPadding = parseInt(main.css("padding-top"));

  // fix padding of main element depending on whether the navbar is fixed or not
  mainMenu.on('affixed.bs.affix', function() {
    main.css("padding-top", mainMenu.height() + mainOriginalPadding);
  });
  mainMenu.on('affix-top.bs.affix', function() {
    main.css("padding-top", mainOriginalPadding);
  });

  // make navbar sticky on-scroll
  mainMenu.affix({
    offset : {
      top : jQuery('header').height() - mainMenu.height()
    }
  });

  // set active menu item
  jQuery('#main-menu-navbar > ul > li:has(a[href|="' + ctx.base + '"])').addClass("active")
});

/*******************************************************************************
 * NAMESPACE DEFINITION
 ******************************************************************************/
var Megx = {};
