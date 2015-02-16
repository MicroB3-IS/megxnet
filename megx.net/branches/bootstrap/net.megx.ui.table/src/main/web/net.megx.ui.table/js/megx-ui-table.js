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
// dom : "R<'pull-right'C><'clearfix'><'row'<'col-sm-6'l><'col-sm-6'f>>" +
// "<'row'<'col-sm-12'tr>><'row'<'col-md-4'i><'col-md-8'p>>",
      dom : "R<lM>" +
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

  if (url) {
    jQuery.extend(defaultConfig, {
      "processing" : true,
      "ajax" : {
        "url" : url,
      },
      serverSide : false,
    });
  }
  
  jQuery.fn.dataTableExt.aoFeatures.push( {
    "fnInit": function( oSettings ) {
      var init = oSettings.oInit;
      var colvis = new jQuery.fn.dataTable.ColVis ( oSettings, init.colVis || init.oColVis || {} );
      var colvisButton =  jQuery("button",colvis.button());
      colvisButton.attr("class","btn btn-default")
      colvisButton.attr("data-tooltip", "Show/hide columns");
      jQuery("span", colvisButton).addClass("fa fa-cog");
      var filterField = jQuery("input",jQuery.fn.dataTable.ext.internal._fnFeatureHtmlFilter(oSettings));
      filterField.attr("placeholder", "search");
        return jQuery('<div class="megx-table-button-bar input-group">').append(filterField)
          .append(jQuery('<div class="btn-group btn-group-sm tcell">')
          .append('<button class="btn btn-default" data-tooltip="Download selected"><span class="fa fa-download"></span></button></div>')
          .append('<button class="btn btn-default" data-tooltip="Download all"><span class="fa fa-cloud-download"></span></button></div>')
          .append(colvisButton)
          .append('<button class="btn btn-default" data-tooltip="New Participant"><span class="fa fa-plus-square"></span></button>')
        );
    },
    "cFeature": "M",
    "sFeature": "MegxButtons"
} );
  
  this.dataTable = table.DataTable(jQuery.extend({}, defaultConfig, datatablesConfiguration));
};
