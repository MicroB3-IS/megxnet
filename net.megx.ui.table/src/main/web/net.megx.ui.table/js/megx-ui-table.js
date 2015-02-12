/**
 * 
 */
var MegxTable = function(tableID, columns, buttons, url, datatablesConfiguration) {
  
  BUTTON_CLASSES = "btn btn-xs btn-default";
  
   createButton = function(dataButtons, type, name) {
    button = dataButtons[type]
    if (button) {
      if (button.link) {
        return '<a class="' +BUTTON_CLASSES +  '" type="button" data-href="' + button.link.url + '">' + name + '</a>';
      } else if (dataButtons.view.modal) {
        return '<button class="' + BUTTON_CLASSES + ' type="button" data-toggle="modal" data-target="#' + button.modal.target + '">' + name +'</button>';
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
    dom: "R<'row'<'col-sm-6'l><'col-sm-6'fC>><'row'<'col-sm-12'tr>><'row'<'col-md-4'i><'col-md-8'p>>" 
  };

  // create buttons
  if (buttons) {
    if (buttons.dataButtons) {
      defaultContent = createButton(buttons.dataButtons, "view", "View");
      defaultContent += createButton(buttons.dataButtons, "edit", "Edit");
      defaultContent += createButton(buttons.dataButtons, "delete", "Delete");
      var column = columns[buttons.dataButtons.column];
      column.data = null;
      column.defaultContent = defaultContent;
      column.createdCell = function (td, cellData, rowData, row, col) {
        jQuery("button", td).data('action-id', rowData['id']);
        jQuery("a", td).each(function() {this.href = jQuery(this).data('href') + rowData['id'];});
      };
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
