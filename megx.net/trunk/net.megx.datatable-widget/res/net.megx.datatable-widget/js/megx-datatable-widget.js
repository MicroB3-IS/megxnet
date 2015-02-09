MegxDatatableWidget = function(config) {
	var self = this;
	self.tableId = "t_" + config.tableDivId;
	self.generateDatatableHtml(config);
	self.renderDatatable(config);

}

MegxDatatableWidget.prototype.generateDatatableHtml = function(config){
	var self = this;
	var tableDiv = document.getElementById(config.tableDivId);
	var table = document.createElement("table");
	table.id = self.tableId;
	table.setAttribute("class", "display");
	var thead = document.createElement("thead");
	var tr = document.createElement("tr");
	
	for (var i = 0; i < config.columns.length; i++){
		
			var th = document.createElement("th");
			th.innerText = config.columns[i];
			tr.appendChild(th);
			
		}
	
	thead.appendChild(tr);
	var tbody = document.createElement("tbody");
	table.appendChild(thead);
	table.appendChild(tbody);
	tableDiv.appendChild(table);
	
}

MegxDatatableWidget.prototype.renderDatatable = function(config){
	var self = this;
	var tbl = $("#" + self.tableId);
	tbl.addClass("megx_dataTable");
	var cfg = {
		"bJQueryUI": true,
		"bPaginate": true,
		"bFilter": true,
		"bSort": true,
		"sPaginationType": "full_numbers",
		"fnDrawCallback": function( oSettings ) {
			this.$('tr').hover( function() {
		        $(this).addClass('highlighted');
		    }, function() {
		    	$(this).removeClass('highlighted');
		    });
		},
		"fnOnServerDataLoad": function() {},
		aoColumns: config.aoColumns
	};
	var oTable = tbl.dataTable(cfg);
	oTable.dataTable().fnAddData(config.data);

}
