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

        if (layerset) {
            for ( var i = 0, j = layerset.length - 1; i < layerset.length; i++, j--) {
                this.map.addLayer(this.layers.get(layerset[i]));
                this.addLayerPanel(layerset[j]);
                this.displayedLayers[layerset[i]] = true;
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
                			'<button id="manipulateLayers" style="margin-left: 12px;">Add layers to the map</button>',
                		'</td>',
                	'</tr>',
                	'<tr>',
                		'<td id="layersAccordion" style="width: 30%; padding-left:10px;">',
            			'</td>', 
            			'<td class="mapPlaceholder">',
            				'<div id="megxMap" style="width: 800px; height: 400px"></div>',
        				'</td>',
    				'</tr>',
				'</table>',
				'<div id="layerDialog">',
				'</div>'].join('');

        $(layoutParentSelector).append(layoutHtml);
        this.buttonizeAddIcon();
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
		        	$(this).dialog("close");
		        },
		        Cancel: function() {
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

        $(this.infoPanel).append(layerPanelHtml);
        this.buttonizeRemoveIcon();
        if(success){
        	success.call(this);
        }
    },
    
    removeLayerPanel: function(layer){
    	$('#' + layer).remove();
    	$(this.infoPanel).accordion('refresh');
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
            	var newLayerOrder = [];
            	$('.mx-layer').each(function(){
            		newLayerOrder.push($(this).attr('id'));
        		});
            	self.reorder(newLayerOrder);
                ui.item.children("h3").triggerHandler("focusout");
            }
        });
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
    	
    	//First remove all layers from the map
        for(var i=0; i<arr.length; i++){
        	this.map.removeLayer(this.layers.get(arr[i]));
        }
        
        //Then add the layers to the map as per the newly chosen order
        for(var i=0; i<arr.length; i++){
        	this.map.addLayer(this.layers.get(arr[i]));
        }
        this.map.zoomTo(currentZoomLevel);
    },

    setTopClickable : function(name) {
        // TODO: set clickable layer, show on top order ...
    },

    redraw : function(name) {
        name = name || '';
        return this.layers.get(name).redraw();
    }
};
