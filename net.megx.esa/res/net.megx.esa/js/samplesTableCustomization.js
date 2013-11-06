$(document).ready(function(){
	var MAX_VISIBLE_COLUMNS = 7;
	var MIN_VISIBLE_COLUMNS = 4;
	
	var samplesTable = $(".megx_dataTable");
	var allColumnsLength = samplesTable.dataTable().fnSettings().aoColumns.length;
	var allCollumns = samplesTable.dataTable().fnSettings().aoColumns.slice(1, allColumnsLength-1);//Zero index column will always be visible and shouldn't be manipulated;
	var columnVisibility = {};
	var changeColumnVisibility = function(){
		$("input.sampleData").each(function(){
			columnVisibility[$(this).val()] = $(this).is(":checked");
		});
		
		for(var i = 0; i < allCollumns.length; i++){
			samplesTable.dataTable().fnSetColumnVis(i+1, columnVisibility[allCollumns[i].mData]);
		}
	};
	var enableDisableChb = function(){
		var nbDisplayedColumns = $("input.sampleData:checked").length;
		if(nbDisplayedColumns === MAX_VISIBLE_COLUMNS){
			//Don't display more columns than MAX_VISIBLE_COLUMNS
			$("input.sampleData").not(":checked").attr("disabled", true);
		} else if(nbDisplayedColumns < MAX_VISIBLE_COLUMNS && nbDisplayedColumns > MIN_VISIBLE_COLUMNS){
			$("input.sampleData").not(":checked").removeAttr("disabled");
			$("input.sampleData:checked").removeAttr("disabled");
		} else if(nbDisplayedColumns === MIN_VISIBLE_COLUMNS){
			//Don't display less columns than MIN_VISIBLE_COLUMNS
			$("input.sampleData:checked").attr("disabled", true);
		}
	};
	
	$.fn.dataTableExt.afnFiltering.push(function (oSettings, aData, iDataIndex) {
	    var showScientist = $('#filterScientist').is(':checked');
	    var showCitizen = $('#filterCitizen').is(':checked');
	    
	    return showCitizen;
	});
	
	$('input.filterRows').change(function(){
		samplesTable.dataTable().fnDraw();
	});
	
	$("#columnsDialog").dialog({
		  resizable: false,
	      autoOpen: false,
		  height: 780,
		  width: 320,
	      modal: true,
	      title: "Turn on/off column visibility",
	      buttons: {
	        Ok: function() {
	        	$(this).dialog("close");
	        	changeColumnVisibility();
	        },
	        Cancel: function() {
	        	$(this).dialog( "close" );
	        }
	      }
	});
	
	$("#customizeTableLnk").click(function(){
		$("#columnsDialog").dialog("open");
		return false;
	});
	
	$.each(allCollumns, function(i, column){
		var isChecked = column.bVisible ? 'checked' : ''; 
		var htmlToAppend = '<input class="sampleData" type="checkbox" name="' + column.mData + '" value="' + column.mData + '"' + isChecked + '>' + column.sTitle + '<br>'; 
		$("#columnsDialog").append(htmlToAppend);
	});
	
	enableDisableChb();
	
	$("input.sampleData").change(function(){
		enableDisableChb();
	});
	
	$('.viewSampleClass').live('click', function(){
		
	});
});