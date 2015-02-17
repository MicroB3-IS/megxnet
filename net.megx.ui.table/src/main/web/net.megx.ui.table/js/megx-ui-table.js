/**
 * 
 */
Megx.MegxTable = function(tableID, columns, buttons, url, datatablesConfiguration) {

  BUTTON_CLASSES = "btn btn-default";

  createButton = function(button, data, icon) {
    if (button) {
      if (button.link) {
        return '<a class="' + BUTTON_CLASSES + '" type="button" role="button" data-tooltip="' + button.label+ '" aria-label="'+button.label+'" href="' + button.link.url + data + 
        '"><span aria-hidden="true" class="fa ' + icon + '"></span></a>';
      } else if (button.modal) {
        return '<button class="' + BUTTON_CLASSES + '" aria-label="' +button.label +'" data-tooltip="' + button.label+ '" type="button" data-toggle="modal" data-target="#'
            + button.modal.target + '" data-action-id="' + data + '"><span class="fa '+icon+'"></span></button>';
      } else {
        console.error("Definition of " + button.text +  " button for table " + tableID + " is incorrect.");
      }
    }
    return "";
  }
  
  
  
  createToolbarButtons = function(toolbarButtons, oSettings) {
    
    createColVisButton = function(oSettings) {
      
      // create ColVisButton and extract only button element
      var init = oSettings.oInit;
      var colvis = new jQuery.fn.dataTable.ColVis ( oSettings, init.colVis || {} );
      var colvisButton =  jQuery("button",colvis.button());
      
      // change classes to bootstrap classes, add tooltip and icon
      colvisButton.attr("class","btn btn-default")
      colvisButton.attr("data-tooltip", "Show/hide columns");
      jQuery("span", colvisButton).addClass("fa fa-cog");
      
      return colvisButton;
    }
    
    createFilterField = function(oSettings) {
      // create filter field and extract only the input element
      var filterField = jQuery("input",jQuery.fn.dataTable.ext.internal._fnFeatureHtmlFilter(oSettings));
      // add placeholder
      filterField.attr("placeholder", "search");
      
      return filterField;
    }
    
    createMegxButton = function(buttonDefinition, iconClass) {
      var button = jQuery('<a role="button" class="btn btn-default"></a>');
      if (buttonDefinition.tooltip) {
        button.attr("data-tooltip", buttonDefinition.tooltip);
      }
      button.append(jQuery('<span class="fa">').addClass(iconClass));
      if (buttonDefinition.link && buttonDefinition.link.url) {
        button.attr("href", buttonDefinition.link.url);
      }
      return button;
    }
    
    createLengthField = function(oSettings) {
      // create length field and extract only select element
      var lengthField = jQuery("select", jQuery.fn.dataTable.ext.internal._fnFeatureHtmlLength(oSettings));
      
      return lengthField;
    }
    
    createDownloadAllButton = function(downloadAllButton) {
      if (downloadAllButton) {
        return createMegxButton(downloadAllButton, "fa-download download-all");
      } else {
        return "";
      }
    }
    
    createDownloadSelectedButton = function(downloadSelectedButton) {
      if (downloadSelectedButton) {
        return createMegxButton(downloadSelectedButton, "fa-download");
      } else {
        return "";
      }
    }
    
    createCreateButton = function(createButton) {
      if (createButton) {
        return createMegxButton(createButton,"fa-plus-square");
      } else {
        return "";
      }
    }
    
    return jQuery('<div class="row">')
             .append(
                  jQuery('<div class="col-xs-5">')
                    .append(createLengthField(oSettings))
              ).append(
                  jQuery('<div class="col-xs-7">')
                    .append(  
                        jQuery('<div class="megx-table-button-bar input-group">')
                        .append(createFilterField(oSettings))
                        .append(
                            jQuery('<div class="btn-group btn-group-sm tcell no-word-break">')
                              .append(createDownloadSelectedButton(toolbarButtons.downloadSelected))
                              .append(createDownloadAllButton(toolbarButtons.downloadAll))
                              .append(createColVisButton(oSettings))
                              .append(createCreateButton(toolbarButtons.create))
                        )
                    )
              );
    
  }

  var table = jQuery('#' + tableID);
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
      jQuery.extend(columns[buttons.dataButtons.column], {
        targets : buttons.dataButtons.column,
        render : function(data, type, full, meta) {
          if (type === 'display') {
            return '<div class="btn-group" role="group">' 
                + createButton(buttons.dataButtons.edit, data, "fa-pencil")
                + createButton(buttons.dataButtons.delete, data, "fa-trash-o")
                +'</div>';
          } else {
            return data;
          }
        }
      });
    }
  }
  
  // create table buttons
  jQuery.fn.dataTableExt.aoFeatures.push( {
    "fnInit": function( oSettings ) {
      return createToolbarButtons(buttons.toolbarButtons || {}, oSettings);
    },
    "cFeature": "M",
    "sFeature": "MegxButtons"
  } );

  if (url) {
    jQuery.extend(defaultConfig, {
      "processing" : true,
      "ajax" : {
        "url" : url,
      },
      serverSide : false,
    });
  }
  
  this.dataTable = table.DataTable(jQuery.extend({}, defaultConfig, datatablesConfiguration));
};
