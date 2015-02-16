// Avoid `console` errors in browsers that lack a console.
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

// define toastr style for all pages
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

// extend jQuery with a simple data binding function
// the function excepts to find elements with 'data-attribute' attributes and
// the value of this field is expected to match an attribute of the data
// object, e.g. an element having data-attribute="id" will get the value of 
// data["id"] assigned as text
jQuery.fn.extend({
  bindData : function(data) {
    if (data) {
      this.find("div[data-attribute]").each(function copyDataToView() {
        $this = jQuery(this);
        $this.text(data[$this.data("attribute")]);
      });
    } else {
      toastr.error('No data available', 'Error');
    }
  }
});

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

// define MEGX namespace
var Megx = {};
