$(document).ready(function() {
	
	var tbl = $("#"+"samplesTableId");
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
		 		return '<input class=\'downloadSample\' type=\'checkbox\' value=\'' + id + '\'>';
		 	},
		 	sWidth: '80px',
		 	bSortable: false
		 }, {
		 	mData: 'collectorId',
		 	sClass: 'left data-pad',
		 	bVisible: false
		 }, { 
		 	mData: 'label',
		 	sClass: 'left data-pad'
		 }, {
		 	mData: 'taken',
		 	sClass: 'left data-pad' 
		 }, {
		 	mData: 'biome',
		 	sClass: 'left data-pad'
		 }, {
		 	mData: 'weatherCondition',
		 	sClass: 'left data-pad'
		 }, {
		 	mData: 'feature',
		 	sClass: 'left data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'airTemperature',
		 	sClass: 'right data-pad',
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'lat',
		 	sClass: 'right data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'lon',
		 	sClass: 'right data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'barcode',
		 	sClass: 'right data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'elevation',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'collection',
		 	sClass: 'left data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'permit',
		 	sClass: 'center data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'material',
		 	sClass: 'left data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'secchiDepth',
		 	sClass: 'center data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'samplingDepth',
		 	sClass: 'right data-pad',
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'waterDepth',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'sampleSize',
		 	sClass: 'right data-pad',
		 	bVisible: false
		 }, {
		 	mData: 'waterTemperature',
		 	sClass: 'right data-pad',
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'conductivity',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'windSpeed',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'salinity',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'comment',
		 	sClass: 'left data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'accuracy',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'phosphate',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'nitrate',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'nitrite',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'ph',
		 	sClass: 'right data-pad',
		 	bVisible: false,
		 	mRender: function(id){
                return id || '-';
            }
		 }, {
		 	mData: 'id',
		 	sClass: 'center',
		 	sWidth: '80px',
		 	mRender: function(id){
		 		var viewUrl = ctx.siteUrl + '/osd-app/sampleDetails?sampleId=' + id;
				return '<a href=' + viewUrl + ' class=\'viewSampleClass\'>View more</a>';
		 	}
		 }]
	    
	};
	var oTable = tbl.dataTable(cfg);
	
	$.getJSON( ctx.siteUrl + "/ws/v1/esa/v1.0.0/allSamples",
        function(samples, textStatus, jqXHR ){
		
			if(samples != null){
				
				var nbRorwsToAdd = samples.data.length;
	        	var rowsToAdd = [];
	        	
	            for(var i = 0; i < samples.data.length; i++) {
	            	
	            	rowsToAdd.push({
	 	                'id': samples.data[i].id,
	 	                'collectorId': samples.data[i].collectorId,
	 	                'label': samples.data[i].label,
	 	                'taken': samples.data[i].taken,
	 	                'biome': samples.data[i].biome,
	 	                'weatherCondition': samples.data[i].weatherCondition,
	 	                'feature': samples.data[i].feature,
	 		 	        'airTemperature': samples.data[i].airTemperature || 'hallo',
	 	     			'lat': samples.data[i].lat,
	 	     			'lon': samples.data[i].lon,
	 	     			'barcode': samples.data[i].barcode,
	 	     			'elevation': samples.data[i].elevation,
	 	     			'collection': samples.data[i].collection,
	 	     			'permit': samples.data[i].permit,
	 	     			'material': samples.data[i].material,
	 	     			'secchiDepth': samples.data[i].secchiDepth,
	 	     			'samplingDepth': samples.data[i].samplingDepth,
	 	     			'waterDepth': samples.data[i].waterDepth,
	 	     			'sampleSize': samples.data[i].sampleSize,
	 	     			'waterTemperature': samples.data[i].waterTemperature,
	 	     			'conductivity': samples.data[i].conductivity,
	 	     			'windSpeed': samples.data[i].windSpeed,
	 	     			'salinity': samples.data[i].salinity,
	 	     			'comment': samples.data[i].comment,
	 	     			'accuracy': samples.data[i].accuracy,
	 	     			'phosphate': samples.data[i].phosphate,
	 	     			'nitrate': samples.data[i].nitrate,
	 	     			'nitrite': samples.data[i].nitrite,
	 	     			'ph': samples.data[i].ph,
	 	     			'id': samples.data[i].id,
	 	            });
	            }    	 
		    	if(rowsToAdd.length === nbRorwsToAdd){
		    		oTable.dataTable().fnAddData(rowsToAdd);
		    	} 
				
			}else{
				$("#errorMessage").show();
	        	$("#customizeTableLnk").hide();
			}
        	   
        }).error(function() {
        	$("#errorMessage").show();
        	$("#customizeTableLnk").hide();
        });
});