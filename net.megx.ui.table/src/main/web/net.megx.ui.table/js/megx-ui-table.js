/**
 * The MegxTable plugin transforms the table given by tableID to a Datatables
 * table.
 * 
 * @param tableID
 *          id of the HTML table to be transformed
 * @param columns
 *          the column definitions (see Datatables manual), for columns which
 *          should not be visible initially the following two attributes have to
 *          be used {@code className: "none"} and {@code visible: false}
 * @param buttons
 *          definition of the buttons to be shown. Supports dataButtons and
 *          toolbarButtons. The following toolbarButtons are supported: download
 *          selected rows, download all, create new entry, show/hide colums.
 *          DataButtons are shown in a table column and trigger actions for
 *          corresponding table row. For further details see the documentation
 *          in the wiki
 *          (https://colab.mpi-bremen.de/wiki/display/microb3/net.megx.ui.table).
 * @param url
 *          the URL used by Datatables to retrieve the table data.
 * @param data
 *          the data used by Datatables to render the table data.
 * @param datatablesConfiguration
 *          allows to override all Datatables options set by this module. The
 *          usage of this parameter is discouraged, better extend this module to
 *          provide the functionality need. If this parameter is used, is must
 *          be used with extreme care as it can easily break the module.
 * 
 */
Megx.MegxTable = function(tableID, columns, buttons, url, data, datatablesConfiguration) {

  /**
   * Default Bootstrap CSS classes for simple buttons.
   */
  var BUTTON_CLASSES = "btn btn-default";

  /**
   * Creates a dataButton.
   * 
   * @param buttonDescription
   *          the JSON object describing the button to be created
   * @param data
   *          the ID of the entry corresponding to the table row
   * @param icon
   *          the font-awesome CSS class for the icon (see
   *          http://fortawesome.github.io/Font-Awesome/icons/)
   * @returns {String} the HTML code for the button
   */
  var createButton = function(buttonDescription, data, icon) {
    if (buttonDescription) {
      if (buttonDescription.link) {
        return '<a role="button" type="button"'   
             + ' class="' + BUTTON_CLASSES + '"'     
             + ' data-tooltip="' + buttonDescription.label + '"'
             + ' aria-label="' + buttonDescription.label + '"'
             + ' href="' + buttonDescription.link.url + data + '"'
             + '><span aria-hidden="true"'
             + ' class="fa ' + icon + '"'
             + '></span></a>';
      } else if (buttonDescription.modal) {
        
        return '<button type="button" data-toggle="modal"'
             + ' class="' + BUTTON_CLASSES + '"' 
             + ' aria-label="' + buttonDescription.label + '"'
             + ' data-tooltip="' + buttonDescription.label+ '"'
             + ' data-target="#' + buttonDescription.modal.target + '"'
             + ' data-action-id="' + data + '"'
             + '><span'
             + ' class="fa ' + icon +'"'
             + '></span></button>';
      } else {
        console.error("Definition of " + buttonDescription.text +  " button for table " + tableID + " is incorrect.");
      }
    }
    return "";
  }
  
  /**
   * Registers events for a modal dialog.
   * 
   * @param button
   *          the JSON object containing the configuration of the button whichs
   *          triggers the dialog.
   */
  var registerListenersToModal = function (button) {
    
    var modal = jQuery('#' + button.modal.target);
    
    // bind data
    // show.bs.modal is a bootstrap event
    modal.on('show.bs.modal', function(event) {
      id = jQuery(event.relatedTarget).data("action-id");
      modal.bindData({"action-id": id});
      modal.data("action-id", id );
    });
    
    // focus cancel/close button when dialog is shown
    // shown.bs.modal is a bootstrap event
    modal.on('shown.bs.modal', function() {
      jQuery('[data-dismiss="modal"]', modal).focus();
    });

    // bind function given in the button configuration to the primary button of
    // the dialog
    if (button.modal.callback) {
      modal.find('#' + button.modal.callback.button).on('click', function(event) {
        // Call JS function from string documentation, based on
        // http://stackoverflow.com/a/359910
        var namespaces = button.modal.callback.action.split(".");
        var action = namespaces.pop();
        var context = window;
        for (var i = 0; i < namespaces.length; i++) {
            context = context[namespaces[i]];
        }
        return context[action](modal.data("action-id"));
      });
    }
  }
  
  /**
   * Create toolbar buttons.
   * 
   * @param toolbarButtons
   *          the JSON object defining the toolbar buttons.
   * @param oSettings
   *          Datatables setting object - required by some Datatables function
   *          which will be called in this function
   * @returns the HTML code of the toolbar for the MegxTable.
   */
  var createToolbarButtons = function(toolbarButtons, oSettings) {
    
    /**
     * Creates the show/hide column button from the ColVis extension of
     * Datatables. The toolbar is created programmatically to gain full control
     * about its style and positing.
     * 
     * @param oSettings
     *          Databables setting object - required by the constructor of the
     *          ColVis extension.
     * @returns the HTML code for the show/hide column button.
     */
    var createColVisButton = function(oSettings) {
      
      // create ColVisButton and extract only the button element and discard the
      // rest of the generated HTML
      var init = oSettings.oInit;
      var colvis = new jQuery.fn.dataTable.ColVis ( oSettings, init.colVis || {} );
      var colvisButton =  jQuery("button",colvis.button());
      
      // change classes to bootstrap classes, add tooltip and icon
      colvisButton.attr("class",BUTTON_CLASSES);
      colvisButton.attr("data-tooltip", "Show/hide columns");
      jQuery("span", colvisButton).addClass("fa fa-cog");
      
      return colvisButton;
    }
    
    /**
     * Creates the filter field for the toolbar. The field is created
     * programmatically to gain full control about its style and positioning.
     * 
     * @param oSettings
     *          Databables setting object - required by the constructor of the
     *          ColVis extension.
     * @returns the HTML code for the filter field.
     */
    var createFilterField = function(oSettings) {
      // create filter field and extract only the input element
      var filterField = jQuery("input",jQuery.fn.dataTable.ext.internal._fnFeatureHtmlFilter(oSettings));
      // add placeholder (HTML5 attribute)
      filterField.attr("placeholder", "search");
      
      return filterField;
    }
    
    /**
     * Create the select element which is used to set the page size of the
     * table. The select element is created programmatically to gain full
     * control about its style and positioning.
     * 
     * @param oSettings
     *          Databables setting object - required by the constructor of the
     *          ColVis extension.
     * @returns the HTML code for the select element.
     */
    var createLengthField = function(oSettings) {
      // create length field and extract only select element
      var lengthField = jQuery("select", jQuery.fn.dataTable.ext.internal._fnFeatureHtmlLength(oSettings));
      
      return lengthField;
    }
    
    /**
     * Creates one of the Megx specific buttons in the toolbar (download
     * selected, download all, create new entry).
     * 
     * @param buttonDefinition
     *          the JSON object containing the definition of the button.
     * @param iconClass
     *          the font-awesome CSS class for the icon (see
     *          http://fortawesome.github.io/Font-Awesome/icons/)
     * @returns the HTML code for the button
     */
    var createMegxButton = function(buttonDefinition, iconClass) {
      var button = jQuery('<a role="button"></a>');
      button.attr("class", BUTTON_CLASSES);
      if (buttonDefinition.tooltip) {
        button.attr("data-tooltip", buttonDefinition.tooltip);
      }
      button.append(jQuery('<span class="fa">').addClass(iconClass));
      if (buttonDefinition.link && buttonDefinition.link.url) {
        button.attr("href", buttonDefinition.link.url);
      }
      return button;
    }
    
    /**
     * Create the download all button for the toolbar.
     * 
     * @param downloadAllButton
     *          the JSON object describing the button.
     * @returns the HTML code of the button.
     */
    var createDownloadAllButton = function(downloadAllButton) {
      if (downloadAllButton) {
        return createMegxButton(downloadAllButton, "fa-download download-all");
      } else {
        return "";
      }
    }
    
    /**
     * Create the download selected button for the toolbar.
     * 
     * @param downloadSelectedButton
     *          the JSON object describing the button.
     * @returns the HTML code of the button.
     */
    var createDownloadSelectedButton = function(downloadSelectedButton) {
      if (downloadSelectedButton) {
        return createMegxButton(downloadSelectedButton, "fa-download download-selected");
      } else {
        return "";
      }
    }
    
    /**
     * Create the create button for the toolbar.
     * 
     * @param createButton
     *          the JSON object describing the button.
     * @returns the HTML code of the button.
     */
    var createCreateButton = function(createButton) {
      if (createButton) {
        return createMegxButton(createButton,"fa-plus-square");
      } else {
        return "";
      }
    }
    
    // return the HTML code for the toolbar.
    return jQuery('<div class="row">')
             // first five columns will contain the page selection widget
             .append(
                  jQuery('<div class="col-xs-5">')
                    .append(createLengthField(oSettings))
              ).append(
                  // the last seven columdn contain the filter field and the
                  // toolbar buttons.
                  jQuery('<div class="col-xs-7">')
                    .append(  
                        // create an input group
                        jQuery('<div class="megx-table-button-bar input-group">')
                        // from the filter field
                        .append(createFilterField(oSettings))
                        // and a button group
                        .append(
                            jQuery('<div class="btn-group btn-group-sm tcell no-word-break">')
                              // containing these buttons (when defined in the
                              // JSON)
                              .append(createDownloadSelectedButton(toolbarButtons.downloadSelected))
                              .append(createDownloadAllButton(toolbarButtons.downloadAll))
                              .append(createColVisButton(oSettings))
                              .append(createCreateButton(toolbarButtons.create))
                        )
                    )
              );
  }

  var table = jQuery('#' + tableID);
  
  // define the default configuration of the MegxTable,see Datables
  // documentation for details (http://datatables.net/reference/option/)
  var defaultConfig = {
    columns : columns,
    responsive : true,
    paging : true,
    ordering : true,
    pagingType : "full_numbers",
    width : "100%",
    autoWidth : false,
    defer : true,
    lengthMenu: [ [10, 25, 50, 100], ["10 per page", "25 per page", "50 per page", "100 per page"] ],
    dom : "R<M>" +
            "<'row'<'col-sm-12'tr>><'row'<'col-md-4'i><'col-md-8'p>>",
    colVis: {
      buttonText: '',
    }
  };
  
  // create buttons
  if (buttons) {
    if (buttons.dataButtons) {
      // added render information to button column
      jQuery.extend(columns[buttons.dataButtons.column], {
        targets : buttons.dataButtons.column,
        render : function(data, type, full, meta) {
          if (type === 'display') {
            return '<div class="btn-group btn-group-sm" role="group">' 
                + createButton(buttons.dataButtons.edit, data, "fa-pencil")
                + createButton(buttons.dataButtons.delete, data, "fa-trash-o")
                +'</div>';
          } else {
            return data;
          }
        }
      });
      
      // register listener for all modal dialogs referred to by the buttons
      if (buttons.dataButtons.edit.modal) {
        registerListenersToModal(buttons.dataButtons.edit);
      }
      if (buttons.dataButtons.delete.modal) {
        registerListenersToModal(buttons.dataButtons.delete);
      }
    }
    
  }
  
  // create table buttons and register 'M' as representation for the toolbar in
  // the 'dom' attribute of the Datatables configuration
  jQuery.fn.dataTableExt.aoFeatures.push( {
    "fnInit": function( oSettings ) {
      return createToolbarButtons(buttons.toolbarButtons || {}, oSettings);
    },
    "cFeature": "M",
    "sFeature": "MegxButtons"
  } );

  // if an URL is passed to the MegxTable the default configuration has to be
  // extended to make Datatables aware that it is supposed to the get the data
  // via AJAX call. See Datatables documentation for details
  // (http://datatables.net/reference/option/)
  if (url) {
    jQuery.extend(defaultConfig, {
      "processing" : true,
      "ajax" : {
        "url" : url,
      },
      serverSide : false,
    });
  }
  
  if (data) {
	    jQuery.extend(defaultConfig, {
	      "data" : data
	    });
	  }
  
  // Merge the Datatables configuration created in this function (defaultConfig)
  // with any overrides specified by the user of the function and create the
  // Datatables object.
  // (datatablesConfiguration)
  this.dataTable = table.DataTable(jQuery.extend({}, defaultConfig, datatablesConfiguration));
};
