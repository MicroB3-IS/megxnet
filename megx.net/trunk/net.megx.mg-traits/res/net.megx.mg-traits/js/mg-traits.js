$(document).ready(function() {

	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/all",
        function(allTraits, textStatus, jqXHR ){
		
			if(allTraits != null){
				
				var nbRorwsToAdd = allTraits.data.length;
	        	var rowsToAdd = [];
	        	
	            for(var i = 0; i < allTraits.data.length; i++) {
	            	
	            	rowsToAdd.push({
	 	                'id': allTraits.data[i].id,
	 	                'sampleLabel': allTraits.data[i].sampleLabel,
	 	                'sampleName': allTraits.data[i].sampleName,
	 	                'sampleEnvironment': allTraits.data[i].sampleEnvironment,
	 	                'size': allTraits.data[i].totalMB,
	 	                'numberOfSequences': allTraits.data[i].numReads  
	 	            });
	            }    	 
		    	if(rowsToAdd.length === nbRorwsToAdd){
		    		createDataTable(rowsToAdd);
		    		traitsDownload();
		    	} 
				
			}else{
				$("#errorMessage").show();
	        	$(".downloadAllTraits").hide();
			}
        	   
        }).error(function() {
        	$("#errorMessage").show();
        	$(".downloadAllTraits").hide();
        });
	
	function createDataTable(data){
		MegxDatatableWidget.createTable(
				{
			tableDivId:"simpleTraitsTableId",
			columns:["","Sample Label","Sample Name","Environment","Size","Number of sequences","Action"],
			   aoColumns: [{ 
		    	 	mData: 'id',
		    	 	sClass: 'center data-pad',
		    	 	mRender: function(id){
		    	 		return '<input class=\'downloadTrait\' type=\'checkbox\' value=\'' + id + '\'>';
		    	 	},
		    	 	sWidth: '80px',
		    	 	bSortable: false
		    	}, {
		             mData: 'sampleLabel',
		             sClass: 'left data-pad'
		        }, {
		            mData: 'sampleName',
		            sClass: 'left data-pad'
		        }, {
		            mData: 'sampleEnvironment',
		            sClass: 'left data-pad',
		            sWidth: '120px'
		        }, {
		            mData: 'size',
		            sClass: 'left data-pad',
		            sWidth: '120px'
		        }, {
		            mData: 'numberOfSequences',
		            sClass: 'left data-pad',
		            sWidth: '120px'
		        }, {
		            mData: 'id',
		            sClass: 'center',
		            sWidth: '120px',
		            mRender: function(id){
		    	 		var viewUrl = ctx.siteUrl + '/mg-traits/sampleDetails?id=' + id;
		    			return '<a href=' + viewUrl + ' class=\'viewSampleClass\'>View more</a>';
		    	 	}
			    	}],
	    	data: data
		});
				
	}
	
	function traitsDownload(){

	    var traitsTable = $(".megx_dataTable");
	    
		$(document).on('click', '.downloadTrait', function(){
	        var parentRow = $(this).parent().parent();
	        var nbRows = traitsTable.dataTable().fnGetNodes();
	        $(parentRow).toggleClass('row_selected');
	        var nbSelectedRows = $(nbRows).filter('tr.row_selected').length;

	        if (nbSelectedRows === $(nbRows).length) {
	            $('a.deselectAllTraitsLnk').show();
	            $('a.selectAllTraitsLnk').hide();
	        } else {
	            $('a.deselectAllTraitsLnk').hide();
	            $('a.selectAllTraitsLnk').show();
	        }
	    });

	    $('div.dataTables_length').append('<a class="selectAllTraitsLnk" href="#">Select all</a>');
	    $('div.dataTables_length').append('<a class="deselectAllTraitsLnk" href="#">Deselect all</a>');
	    $('div.dataTables_paginate').before('<a class="downloadSelected" href="#">Download selected data</a>');

	    $('a.selectAllTraitsLnk').click(function () {
	        var allRows = traitsTable.dataTable().fnGetNodes();
	        $.each(allRows, function (i, row) {
	            var selectChb = $(row).find('input.downloadTrait');
	            if (!$(selectChb).prop('checked')) {
	                $(row).addClass('row_selected');
	                $(selectChb).prop('checked', true);
	            }
	        });
	        $(this).hide();
	        $('a.deselectAllTraitsLnk').show();
	        return false;
	    });

	    $('a.deselectAllTraitsLnk').click(function () {
	        var allRows = traitsTable.dataTable().fnGetNodes();
	        $.each(allRows, function (i, row) {
	            var selectChb = $(row).find('input.downloadTrait');
	            if ($(selectChb).prop('checked')) {
	                $(row).removeClass('row_selected');
	                $(selectChb).prop('checked', false);
	            }
	        });
	        $(this).hide();
	        $('a.selectAllTraitsLnk').show();
	        return false;
	    });

	    $("#noTraitsSelected").dialog({
	        resizable: false,
	        autoOpen: false,
	        height: 150,
	        width: 376,
	        modal: true,
	        title: "Download traits",
	        buttons: {
	            Ok: function () {
	                $(this).dialog("close");
	            }
	        }
	    });
	    
	    

	    $('a.downloadSelected').click(function () {
	        var allRows = traitsTable.dataTable().fnGetNodes();
	        var selectedRows = $(allRows).filter('tr.row_selected');

	        if (selectedRows.length === 0) {
	            $("#noTraitsSelected").dialog('open');
	        } else {
	            var traitIds = [];
	            $.each(selectedRows, function (i, row) {
	                var traitId = $(row).find('input.downloadTrait').val();
	                traitIds.push(traitId);
	            });
	            $('input.traitIdsHolder').val(traitIds.join(','));
	            $('#sf').submit();
	        }
	        return false;
	    });
	}
	
});