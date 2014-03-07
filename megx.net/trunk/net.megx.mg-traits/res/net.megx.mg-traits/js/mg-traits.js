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
        	var trait = {};
        	var rowsToAdd = [];
        	
            for(var i = 0; i < allTraits.data.length; i++) {
            	var currId = allTraits.data[i].id;
            	var currSampleLabel = allTraits.data[i].sample_label;
            	var currSampleEnvironment = allTraits.data[i].environment;
            	trait[currId] = {
            			'id' : currId,
            			'label' : currSampleLabel,
            			'environment' : currSampleEnvironment
            	};
            	
            	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/mg" + currId + "-sample-label/simple-traits",
                    function(simpleTrait){
                    	var idReturned = simpleTrait.data.id;
                    	var size = simpleTrait.data.totalMB;
                    	var numberOfSequences = simpleTrait.data.numReads;
                    	
                    	$.extend(trait[idReturned], {
                    		'size' : size,
                    		'numberOfSequences' : numberOfSequences
                    	}); 
                    	
                    	 rowsToAdd.push({
         	                'id': trait[idReturned].id,
         	                'sampleLabel': trait[idReturned].label,
         	                'sampleEnvironment': trait[idReturned].environment,
         	                'size': trait[idReturned].size,
         	                'numberOfSequences': trait[idReturned].numberOfSequences  
         	            });
                    	 
                    	 if(rowsToAdd.length === nbRorwsToAdd){
                    		 oTable.dataTable().fnAddData(rowsToAdd);
                    	 }
                    }).error(function() {
                    	$("#errorMessage").show();
                    });
            }
        }).error(function() {
        	$("#errorMessage").show();
        
        });

});