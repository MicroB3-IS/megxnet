CMapLayers = function(cfg) {
	
	this.init(cfg);
}
CMapLayers.prototype = {
	getLayersNames: function() {
		var rv = [];
		for(var k in this.layers) {
			rv.push(k);
		}
		return rv;
	},
	
	getLayersArray: function() {
		var rv = [];
		for(var k in this.layers) {
			rv.push(this.layers[k]);
		}
		return rv;
	},
	
	getLayers: function() {
		return this.layers;
	},
	
	get: function(layerName) {
		return this.layers[layerName];
	},
	
	init: function(cfg) {
		var gms_wms_url = cfg.gms_wms_url;
		var extent = cfg.extent;
		var sam_genomes = cfg.sam_genomes;
		var sam_rrna = cfg.sam_rrna; 
		var sam_phages = cfg.sam_phages;
		var sam_metagenomes = cfg.sam_metagenomes;
		
		var layers = {};
		
		layers.bathymetry = new OpenLayers.Layer.WMS("bathymetry", gms_wms_url, {
			layers : 'bathymetry',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});
		
		layers.esa = new OpenLayers.Layer.WMS("esa", gms_wms_url, {
			layers : 'esa',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});
				
		layers.samplingsites = new OpenLayers.Layer.WMS("samplingsites", gms_wms_url, {
			layers : 'samplingsites',
			format : 'image/png',
			transparent : "true",
			// BBOX : extent.toBBOX(),
			// WIDTH : '800',
			// HEIGHT : '600',
			rrnas : sam_rrna,
			genomes : sam_genomes,
			phages : sam_phages,
			metagenomes : sam_metagenomes
		}, {
			isBaseLayer : false,
			attribution : 'Provided by <a style="color:black" href="http://www.megx.net/">megx.net</a>',
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});
		
		layers.thematiclayer = new OpenLayers.Layer.WMS("thematiclayer", gms_wms_url, {
			layers : '',
			format : 'image/png',
			transparent : "false",
			//time : cur_year + '-' + cur_season,
			isBaseLayer : false
		}, {
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});

		layers.satellite = new OpenLayers.Layer.WMS("satellite", gms_wms_url, {
			layers : 'satellite',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});

		layers.satellite_mod = new OpenLayers.Layer.WMS("satellite_mod", gms_wms_url, {
			layers : 'satellite_mod',
			format : 'image/png',
			transparent : "false"
		}, {
			isBaseLayer : true,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});

		layers.undersea_arc = new OpenLayers.Layer.WMS("undersea_arc", gms_wms_url, {
			layers : 'undersea_arc',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});

		layers.undersea_point = new OpenLayers.Layer.WMS("undersea_point", gms_wms_url, {
			layers : 'undersea_point',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});

		layers.lakes = new OpenLayers.Layer.WMS("lakes", gms_wms_url, {
			layers : 'lakes',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});

		layers.boundaries = new OpenLayers.Layer.WMS("boundaries", gms_wms_url, {
			layers : 'boundaries',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});
/*
 * not used yet gdemLayer = new OpenLayers.Layer.WMS("gdem", gms_wms_url, {
 * layers : 'gdem', format : 'image/png', transparent : "true", map :
 * gms_map_file }, { singleTile : true, maxExtent : extent, maxResolution :
 * "auto" });
 */
		layers.limitsoceans = new OpenLayers.Layer.WMS("limitsoceans", gms_wms_url, {
			layers : 'limitsoceans',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});

		layers.coordinates = new OpenLayers.Layer.WMS("coordinates", gms_wms_url, {
			layers : 'coordinates',
			format : 'image/png',
			transparent : "true"
		}, {
			isBaseLayer : false,
			singleTile : true,
			maxExtent : extent,
			maxResolution : "auto"
		});
		
		this.layers = layers;
	}
}