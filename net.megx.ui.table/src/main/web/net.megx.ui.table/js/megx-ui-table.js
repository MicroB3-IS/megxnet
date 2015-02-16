/**
 * 
 */
Megx.MegxTable = function(tableID, columns, buttons, url, datatablesConfiguration) {

  BUTTON_CLASSES = "btn btn-xs btn-default";

  createButton = function(button, data) {
    if (button) {
      if (button.link) {
        return '<a class="' + BUTTON_CLASSES + '" type="button" data-href="' + button.link.url + data + '">' + button.text
            + '</a>';
      } else if (button.modal) {
        return '<button class="' + BUTTON_CLASSES + ' type="button" data-toggle="modal" data-target="#'
            + button.modal.target + '" data-action-id="' + data + '">' + button.text + '</button>';
      } else {
        console.error("Definition of view button for table " + tableID + " is incorrect.");
      }
    }
    return "";
  }

  var table = jQuery('#' + tableID);
  table.addClass("megx-table");

  var defaultConfig = {
    columns : columns,
    responsive : true,
    paging : true,
    ordering : true,
    pagingType : "full_numbers",
    width : "100%",
    autoWidth : false,
    defer : true,
    dom : "R<'row'<'col-sm-6'l><'col-sm-6'fC>><'row'<'col-sm-12'tr>><'row'<'col-md-4'i><'col-md-8'p>>"
  };

  // create buttons
  if (buttons) {
    if (buttons.dataButtons) {
      jQuery.extend(columns[buttons.dataButtons.column], {
        targets : buttons.dataButtons.column,
        render : function(data, type, full, meta) {
          if (type === 'display') {
            return createButton(buttons.dataButtons.view, data)
                + createButton(buttons.dataButtons.edit, data)
                + createButton(buttons.dataButtons.delete, data);
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
  this.dataTable = table.DataTable(jQuery.extend({}, defaultConfig, datatablesConfiguration));

};
