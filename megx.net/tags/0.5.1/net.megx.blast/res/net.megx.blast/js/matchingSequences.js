$(document).ready(function() {
	
	var tbl = $("#"+"matchingSequencesTableId");
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
    	 	mData: 'id',
    	 	sClass: 'center data-pad',
    	 	mRender: function(id){
    	 		return '<input class=\'downloadTrait\' type=\'checkbox\' value=\'' + id + '\'>';
    	 	},
    	 	sWidth: '80px',
    	 	bSortable: false
    	}, {
             mData: 'evalue',
             sClass: 'left data-pad'
        }, {
            mData: 'score',
            sClass: 'left data-pad'
        }, {
            mData: 'query',
            sClass: 'left data-pad',
            sWidth: '120px'
        }, {
            mData: 'subject',
            sClass: 'left data-pad',
            sWidth: '120px'
        }, {
            mData: 'hitNeighborhood',
            sClass: 'left data-pad',
            sWidth: '120px'
        }]
	    
	};
	var oTable = tbl.dataTable(cfg);
	
	$.get( ctx.siteUrl + "/ws/v1/megx-blast/v1.0.0/matching-sequences",
        function(matchingSequences, textStatus, jqXHR ){
		
			if(matchingSequences != null){
				
			    var jsonobject = csvjson.csv2json(matchingSequences);
				var nbRorwsToAdd = jsonobject.rows.length;
	        	var rowsToAdd = [];
	        	
	            for(var i = 0; i < jsonobject.rows.length; i++) {
	            	
	            	rowsToAdd.push({
	 	                'id': jsonobject.rows[i]['Query Id'],
	 	                'evalue': jsonobject.rows[i]['Hsp Evalue'],
	 	                'score': jsonobject.rows[i]['Hsp Bit Score'],
	 	                'query': jsonobject.rows[i]['Query Id'],
	 	                'subject': jsonobject.rows[i]['Hit Id'],
	 	                'hitNeighborhood': jsonobject.rows[i]['Hit Neighborhood Count']  
	 	            });
	            }    	 
		    	if(rowsToAdd.length === nbRorwsToAdd){
		    		oTable.dataTable().fnAddData(rowsToAdd);
		    	} 
				
			}else{
				$("#errorMessage").show();
	        	$(".downloadAllTraits").hide();
			}
        	   
        }).error(function() {
        	$("#errorMessage").show();
        	$(".downloadAllTraits").hide();
        });
});