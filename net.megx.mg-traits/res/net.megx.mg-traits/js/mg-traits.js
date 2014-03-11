$(document).ready(function() {
	
	var tbl = $("#"+"simpleTraitsTableId");
	tbl.addClass("megx_dataTable");
	var cfg = {
	    "bJQueryUI": true,
	    "bPaginate": true,
	    "bFilter": true,
	    "bSort": true,
	    "sPaginationType": "full_numbers",
	    "fnDrawCallback": function (oSettings) {
	        this.$('tr').hover(function () {
	            $(this).addClass('highlighted');
	        }, function () {
	            $(this).removeClass('highlighted');
	        });
	    },
	    "fnOnServerDataLoad": function () {},
        aoColumns: [{
            mData: 'sampleLabel',
            sClass: 'left data-pad'
        }, {
            mData: 'sampleEnvironment',
            sClass: 'left data-pad',
            sWidth: '140px'
        }, {
            mData: 'size',
            sClass: 'left data-pad',
            sWidth: '140px'
        }, {
            mData: 'numberOfSequences',
            sClass: 'left data-pad',
            sWidth: '140px'
        }, {
            mData: 'id',
            sClass: 'center',
            sWidth: '140px',
            mRender: function(id){
    	 		var viewUrl = ctx.siteUrl + '/mg-traits/sampleDetails?id=' + id;
    			return '<a href=' + viewUrl + ' class=\'viewSampleClass\'>View more</a>';
    	 	}
        }]
	    
	};
	var oTable = tbl.dataTable(cfg);
	
	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/all",
        function(allTraits){
        	var nbRorwsToAdd = allTraits.data.length;
        	var rowsToAdd = [];
        	
            for(var i = 0; i < allTraits.data.length; i++) {
            	
            	rowsToAdd.push({
 	                'id': allTraits.data[i].id,
 	                'sampleLabel': allTraits.data[i].sampleLabel,
 	                'sampleEnvironment': allTraits.data[i].sampleEnvironment,
 	                'size': allTraits.data[i].totalMB,
 	                'numberOfSequences': allTraits.data[i].numReads  
 	            });
            }    	 
	    	if(rowsToAdd.length === nbRorwsToAdd){
	    		oTable.dataTable().fnAddData(rowsToAdd);
	    	}    
        }).error(function() {
        	$("#errorMessage").show();
        });
});