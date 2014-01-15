//Absract widget
MegxMapWidget = function(layerSetName, config) {

    this.gmsBaseURL = 'http://mb3is.megx.net/wms';

    this.init(layerSetName, config, this.gmsBaseURL);
}

MegxMapWidget.prototype = {
    init : function(layerSetName, config, gu) {

        this.renderWidget('megxMapWidget');
        this.initializeLayerDialog();
        this.initializeAddRemoveLink();
        this.displayedLayers = {};

        this.map = new OpenLayers.Map('megxMap', {
            controls : [],
            numZoomLevels : 16,
            projection : "EPSG:4326",
            allOverlays : true
        });

        this.LAYERSET = {
            'esa' : [ 'satellite_mod', 'esa' ],
            'ena' : [ 'boundaries', 'woa05_temperature', 'ena_samples' ]
        };

        this.infoPanel = '#layersAccordion';

        this.initMap(this.map);

        this.layers = new MegxMapWidgetLayers({
            gms_wms_url : 'http://mb3is.megx.net/wms',
            sam_genomes : 'genome',
            sam_metagenomes : 'metagenome',
            sam_phages : 'phage',
            sam_rrna : '',
            extent : new OpenLayers.Bounds(-180, -90, 180, 90)
        });

        var layerset = this.LAYERSET[layerSetName.toLowerCase()];
        
        this.log.message('Chosen layerset is ' + layerset);
        this.log.message('Adding layers to map...');
        
        if (layerset) {
            for ( var i = 0; i < layerset.length; i++ ) {
                var addMap = this.map.addLayer(this.layers.get(layerset[i]));
                if(addMap){
                	this.addLayerPanel(layerset[i]);
                    this.displayedLayers[layerset[i]] = true;
                    //As per ticket https://colab.mpi-bremen.de/its/browse/MB3_IS-159, display legend data for the topmost layer only
                    this.redrawLegend(layerset[layerset.length - 1]);
                    this.log.message('Added layer ' + layerset[i] + ' to map');
                } else{
                	this.log.error('An error occured while adding layer ' + layerset[i] + ' to map!');
                }
            }
        }
        this.createWMSFeatureInfo(this.layers.get('ena_samples'), this.map);
        this.accordifyLayerPanel();

        // map.setBaseLayer(this.layers.get('satellite_mod'));
        // map.setCenter(new OpenLayers.LonLat(lon, lat), 0);
        if (!this.map.getCenter()) {
            this.map.zoomToMaxExtent();
        }
    },

    createWMSFeatureInfo : function(layer, map) {

        var featureInfo = new OpenLayers.Control.WMSGetFeatureInfo({
            /*
             * vendorParams : { RRNAS : sam_rrna, GENOMES : sam_genomes, PHAGES :
             * sam_phages, METAGENOMES : sam_metagenomes },
             */
            url : this.gmsBaseURL,
            queryVisible : true,
            infoFormat : 'text/html',
            layers : [ layer ],
            title : 'Identify features by clicking',
            // queryVisible : true,
            // format: new OpenLayers.Format.WMSGetFeatureInfo(),
            eventListeners : {
                getfeatureinfo : function(event) {
                    var lonlat = map.getLonLatFromPixel(event.xy);
                    map.addPopup(new OpenLayers.Popup.FramedCloud("chicken",
                            lonlat, null, event.text ? event.text
                                    : 'No data at ' + event.text + ': '
                                            + lonlat + event.features
                                            + event.xy, null, true));
                }
            }
        });

        map.addControl(featureInfo);
        featureInfo.activate();
    },

    renderWidget : function(layoutParent) {
        var layoutParentSelector = '#' + layoutParent;
        var layoutHtml = [
                '<table style="width: 99%; margin-top:10px; margin-bottom:10px;">',
                	'<tr>',
                		'<td colspan="2">',
                			'<button id="manipulateLayers" style="margin-left: 12px; margin-bottom: 10px;">Add layers to the map</button>',
                		'</td>',
                	'</tr>',
                	'<tr>',
                		'<td id="layersAccordion" style="width: 30%; padding-left:10px;">',
            			'</td>', 
            			'<td class="mapPlaceholder">',
            				'<div id="megxMap" style="width: 800px; height: 400px"></div>',
        				'</td>',
    				'</tr>',
    				'<tr>',
    					'<td colspan="2" style="padding-left: 16%;">',
    						'<div id="messagesPlaceholder" style="border: 1px solid; border-color: grey; border-radius: 5px; max-height: 120px; overflow-y: auto;">',
    						'</div>',
    					'</td>',
    				'</tr>',
    				'<tr>',
    					'<td colspan="2" style="padding-left: 16%;">',
    						'<legend>',
    							'<h6>',
    								'Legend',
    							'</h6>',
    							'<div id="legendDataPlaceholder">',
    							'</div>',
    						'</legend>',
    					'</td>',
					'</tr>',
				'</table>',
				'<div id="layerDialog">',
				'</div>'].join('');

        $(layoutParentSelector).append(layoutHtml);
        this.buttonizeAddIcon();
    },
    
    log: {
    	message: function(msg){
    		msg = msg || '';
    		var timeStamp = new Date();
    		var msgToDisplay = ['<p style="color: green; padding-left: 10px;">', timeStamp.toLocaleString(), ' ',  msg, '</p>'].join('');
    		var nbDisplayedMsgs = $('#messagesPlaceholder').children().length;
    		var childrenHeight = $('#messagesPlaceholder').children().height();
    		$('#messagesPlaceholder').append(msgToDisplay);
    		$('#messagesPlaceholder').animate({ scrollTop: nbDisplayedMsgs * childrenHeight }, "slow");
    	},
    	error: function(msg){
    		msg = msg || '';
    		var timeStamp = new Date();
    		var msgToDisplay = ['<p style="color: red; padding-left: 10px;">', timeStamp.toLocaleString(), ' ',   msg, '</p>'].join('');
    		var nbDisplayedMsgs = $('#messagesPlaceholder').children().length;
    		var childrenHeight = $('#messagesPlaceholder').children().height();
    		$('#messagesPlaceholder').append(msgToDisplay);
    		$('#messagesPlaceholder').animate({ scrollTop: nbDisplayedMsgs * childrenHeight }, "slow");
    	}
    },
    
    initializeLayerDialog: function(){
    	var self = this;
    	
    	$("#layerDialog").dialog({
			  resizable: false,
		      autoOpen: false,
			  height: 600,
			  width: 400,
			  title: 'Add/remove layers from the map',
		      modal: true,
		      buttons: {
		        Ok: function() {
		        	self.addLayers();
		        	self.log.message('Closing dialog window. New layer is added on map');
		        	$(this).dialog("close");
		        },
		        Cancel: function() {
		        	self.log.message('Closing dialog window. No layer is added on map');
		        	$(this).dialog( "close" );
		        }
		      }
		});
    	$('.ui-dialog').css('z-index', '10000');
    },
    
    initializeAddRemoveLink: function(){
    	var self = this;
    	
    	$('#manipulateLayers').click(function(){
    		$("#layerDialog").dialog("open");
    		self.populateLayersDialog();
    		return false;
    	});
    	
    	$(document).on('click', 'button.removeLayer', function(){
    		var layerToRemove = $(this).closest('div.mx-layer').attr('id');
    		self.removeLayer(layerToRemove);
    		self.removeLayerPanel(layerToRemove);
    		self.log.message('Layer ' + layerToRemove + ' removed from map');
    	});
    },
    
    populateLayersDialog: function(){
    	var allLayers = this.layers.getLayersNameDesc();
    	var htmlToRender = [];
    	for(var layer in allLayers){
    		var currentLayer = allLayers[layer];
    		if(!this.displayedLayers[currentLayer.genericName]){
    			htmlToRender.push('<input class="layer" type="radio" value="' + currentLayer.genericName + '" name="layerChoice" />' + currentLayer.niceName + '<br/>');
    		}
    	}
    	
    	$("#layerDialog").html(htmlToRender.join(''));
    },
    
    addLayers: function(){
    	var self = this;
    	var selectedLayer = $('input.layer:checked').val();
    	this.map.addLayer(this.layers.get(selectedLayer));
    	this.addLayerPanel(selectedLayer, function(){
    		$(this.infoPanel).accordion('refresh');
    	});
        this.displayedLayers[selectedLayer] = true;
        
        var newLayerOrder = this.getNewLayerOrder();
        this.redrawLegend(newLayerOrder[0]);
        
    },
    
    addLayerPanel : function(layer, success) {
        // TODO check if layer exists and take pre-cautions
        var layerInfo = this.layers.getLayerNameDesc(layer);
    	var layerPanelHtml = [
                '<div id="' + layerInfo.genericName + '" class="mx-layer">',
                	'<h3>',
                		layerInfo.niceName,
                		'<span class="ui-icon-trash"></span>',
                		'<div class="mx-layer-configuration-header">',
                			'<button class="removeLayer" style="width: 19px; height: 20px;"></button>',
                			'<!-- icon for visibility| icon for external layer description document -->',
            			'</div>',
        			'</h3>',
        			'<div class="mx-layer-configuration">',
        				'<p class="mx-layer-description">' + layerInfo.description + '</p>',
        				'<div class="mx-layer-control"></div>',
    				'</div>',
				'</div>' ].join('');

        $(this.infoPanel).prepend(layerPanelHtml);
        this.buttonizeRemoveIcon();
        if(success){
        	success.call(this);
        }
    },
    
    removeLayerPanel: function(layer){
    	$('#' + layer).remove();
    	$(this.infoPanel).accordion('refresh');
    	
    	var newLayerOrder = this.getNewLayerOrder();
    	this.redrawLegend(newLayerOrder[0]);
    },
    
    buttonizeAddIcon: function(){
    	$( '#manipulateLayers' ).button({
		    icons: {
		      primary: "ui-icon-plusthick"
		    },
		    text: false
	    });
    },
    
    buttonizeRemoveIcon: function(){
    	$( 'button.removeLayer' ).button({
		    icons: {
		      primary: "ui-icon-close"
		    },
		    text: false
	    });
    },
    
    initMap : function(map) {
        var navContr = new OpenLayers.Control.Navigation();
        navContr.setMap(map);

        map.addControl(navContr);
        map.addControl(new OpenLayers.Control.ScaleLine());
        map.addControl(new OpenLayers.Control.MousePosition());
        map.addControl(new OpenLayers.Control.KeyboardDefaults());
        var panZoomContr = new OpenLayers.Control.PanZoomBar();
        panZoomContr.setMap(map);
        map.addControl(panZoomContr);
        map.addControl(new OpenLayers.Control.Attribution());
        var permalinkContr = new OpenLayers.Control.Permalink('permalink');
        permalinkContr.setMap(map);
        map.addControl(permalinkContr);
    },

    accordifyLayerPanel : function() {
        var self = this;
    	$(this.infoPanel).accordion({
            collapsible : true,
            heightStyle : "content",
            header : "> div > h3"
        }).sortable({
            axis : "y",
            handle : "h3",
            stop : function(event, ui) {
                // IE doesn't register the blur when sorting
                // so trigger focusout handlers to remove .ui-state-focus
            	self.reorder(self.getNewLayerOrder());
                ui.item.children("h3").triggerHandler("focusout");
            }
        });
    },
    
    getNewLayerOrder: function(){
    	var newLayerOrder = [];
    	$('.mx-layer').each(function(){
    		newLayerOrder.push($(this).attr('id'));
		});
    	
    	return newLayerOrder;
    },
    
    removeControl : function(controlName) {
        var controls = this.map.getControlsByClass(controlName);
        for ( var i = 0; i < controls.length; i++) {
            controls[i].deactivate();
        }
    },

    addLayer : function(layer) {
        this.map.addLayer(layer);
    },
    
    removeLayer: function(layer){
    	var currentZoomLevel = this.map.getZoom();
    	this.displayedLayers[layer] = false;
    	this.map.removeLayer(this.layers.get(layer));
    	this.map.zoomTo(currentZoomLevel);
    },

    showLayer : function(name) {
        this.layers.get(name).setVisibility(true);
    },

    hideLayer : function(name) {
        this.layers.get(name).setVisibility(false);
    },

    hideAll : function() {
        var names = this.layers.getLayersNames();
        for ( var i = 0; i < names.length; i++) {
            this.hideLayer(names[i]);
        }
    },

    reorder : function(arr) {
    	var currentZoomLevel = this.map.getZoom();
    	
    	this.log.message('Reordering layers as per new layer order...');
    	
    	//First remove all layers from the map
        for(var i=0; i<arr.length; i++){
        	this.map.removeLayer(this.layers.get(arr[i]));
        }
        
        //Then add the layers to the map as per the newly chosen order
        for(var i=arr.length - 1; i>=0; i--){
        	this.map.addLayer(this.layers.get(arr[i]));
        }
        this.map.zoomTo(currentZoomLevel);
        
        this.log.message('Redrawing legend...');
        this.redrawLegend(arr[0]);
    },
    
    redrawLegend: function(layerName){
    	var self = this;
    	OpenLayers.Request.GET({
            url : self.gmsBaseURL,
            async : false,
            params : {
                LAYER : layerName,
                MODE : 'LEGEND'
            },
            success: function(response){
            	self.log.message('Legend data successfully retrieved from map server');
            	$('#legendDataPlaceholder').html(response.responseText);
            },
            failure: function(response){
            	self.log.error('Error occured while retrieveing legend data for layer ' + layerName + '. Error details: ' + JSON.stringify(response));
            }
        });
    },

    setTopClickable : function(name) {
        // TODO: set clickable layer, show on top order ...
    },

    redraw : function(name) {
        name = name || '';
        return this.layers.get(name).redraw();
    }
};
