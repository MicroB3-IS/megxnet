//Absract widget
CMap = function(config) {
	/*
	config = {
		ct: '', 			 // div container
		layers: ['layer1', 'layer2']	 // named layers anabled for this map
	}	
	*/
	this.init(config);
}

CMap.prototype = {
	init: function(config) {
		
		this.map = new OpenLayers.Map(config.el, {
			controls : [],
			numZoomLevels : 16,
			projection : "EPSG:4326"
		});
		
		this.initMap(this.map);
		
		console.log("Init map on gmsURL = " + config.gmsURL);
		
		this.layers = new CMapLayers({
			gms_wms_url: config.gmsURL,
			sam_genomes: 'genome',
			sam_metagenomes: 'metagenome',
			sam_phages: 'phage',
			sam_rrna: '',
			extent: new OpenLayers.Bounds(-180, -90, 180, 90)
		});
		this.map.addLayers(this.layers.getLayersArray());
		var map = this.map;
		
		
		//map.setBaseLayer(this.layers.get('satellite_mod'));
		//map.setCenter(new OpenLayers.LonLat(lon, lat), 0);
		if (!map.getCenter()) {
			map.zoomToMaxExtent();
		}
	},
	
	initMap: function(map) {
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
	
	removeControl: function(controlName){
		var controls = this.map.getControlsByClass(controlName);
		for(var i = 0; i < controls.length; i++){
			controls[i].deactivate();
		}
	},
	
	addLayer: function(layer){
		this.map.addLayer(layer);
	},
	
	showLayer: function(name) {
		this.layers.get(name).setVisibility(true);
	},

	hideLayer: function(name) {	
		this.layers.get(name).setVisibility(false);
	},
	
	hideAll: function() {
		var names = this.layers.getLayersNames();
		for(var i=0; i<names.length; i++) {
			this.hideLayer(names[i]);
		}
	},

	reorder: function(arr) {
		// argument array, new order of layers on the map
		//TODO: showLayer
	},

	setTopClickable: function(name) {
		//TODO: set clickable layer, show on top order ...
	},
	
	redraw: function(name){
		name = name || '';
		return this.layers.get(name).redraw();
	}
};

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


