MegxMapWidgetLayers = function(cfg) {

    this.init(cfg);
}
MegxMapWidgetLayers.prototype = {
    getLayersNames : function() {
        var rv = [];
        for ( var k in this.layers) {
            rv.push(k);
        }
        return rv;
    },

    getLayersArray : function() {
        var rv = [];
        for ( var k in this.layers) {
            rv.push(this.layers[k]);
        }
        return rv;
    },

    getLayers : function() {
        return this.layers;
    },

    get : function(layerName) {
        return this.layers[layerName];
    },
    
    getLayersNameDesc : function(){
    	return this.layersNames;
    },
    
    getLayerNameDesc: function(layer){
    	return this.layersNames[layer];
    },

    init : function(cfg) {
        var gms_wms_url = cfg.gms_wms_url;
        var extent = cfg.extent;
        var sam_genomes = cfg.sam_genomes;
        var sam_rrna = cfg.sam_rrna;
        var sam_phages = cfg.sam_phages;
        var sam_metagenomes = cfg.sam_metagenomes;

        var layers = {};
        
        this.layersNames = {
        		'bathymetry' : {
        			'genericName': 'bathymetry',
        			'niceName': 'Bathymetry',
        			'description' : 'This is a layer for bathymetry'
        		},
        		'ena_samples' : {
        			'genericName': 'ena_samples',
        			'niceName': 'ENA Samples',
        			'description' : 'European Nucleotide Archive Samples'
        		},
        		'esa' : {
        			'genericName': 'esa',
        			'niceName': 'Earth Sampling App layer',
        			'description' : 'This is a layer representing geographical posotions of samples fro all over the world!!'
        		},
        		'osdRegistry' : {
        			'genericName': 'osdRegistry',
        			'niceName': 'OSD Registry Layer',
        			'description' : 'Map of OSD participating Sites and Institutes'
        		},
        		'samplingsites' : {
        			'genericName': 'samplingsites',
        			'niceName': 'samplingsites layer',
        			'description' : 'This is a layer for samplingsites'
        		},
        		'woa05_temperature' : {
        			'genericName': 'woa05_temperature',
        			'niceName': 'WOA Temperature',
        			'description' : 'World Ocean Atlas Temperature'
        		},
        		'satellite' : {
        			'genericName': 'satellite',
        			'niceName': 'satellite layer',
        			'description' : 'desc for satellite layer'
        		},
        		'satellite_mod' : {
        			'genericName': 'satellite_mod',
        			'niceName': 'satellite_mod layer',
        			'description' : 'This is a layer for satellite_mod'
        		},
        		'undersea_arc' : {
        			'genericName': 'undersea_arc',
        			'niceName': 'undersea_arc layer',
        			'description' : 'This is a layer for undersea_arc'
        		},
        		'undersea_point' : {
        			'genericName': 'undersea_point',
        			'niceName': 'undersea_point layer',
        			'description' : 'This is a layer for undersea_point'
        		},
        		'lakes' : {
        			'genericName': 'lakes',
        			'niceName': 'lakes layer',
        			'description' : 'This is a layer for lakes'
        		},
        		'boundaries' : {
        			'genericName': 'boundaries',
        			'niceName': 'Boundaries',
        			'description' : 'This is a layer for boundaries'
        		},
        		'limitsoceans' : {
        			'genericName': 'limitsoceans',
        			'niceName': 'limitsoceans layer',
        			'description' : 'This is a layer for limitsoceans'
        		},
        		'coordinates' : {
        			'genericName': 'coordinates',
        			'niceName': 'coordinates layer',
        			'description' : 'This is a layer for coordinates'
        		}
        };

        layers.bathymetry = new OpenLayers.Layer.WMS(this.layersNames["bathymetry"].genericName, gms_wms_url,
                {
                    layers : 'bathymetry',
                    format : 'image/png',
                    transparent : "true"
                }, {
                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.ena_samples = new OpenLayers.Layer.WMS(this.layersNames["ena_samples"].genericName,
                gms_wms_url, {
                    layers : 'ena_samples',
                    format : 'image/png',
                    transparent : "true"
                }, {

                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });
        layers.ena_samples.desc = "European Nucleotide Archive Samples";
        layers.ena_samples.niceName = "ENA Samples";
        layers.ena_samples.controlHtml = "";

        layers.esa = new OpenLayers.Layer.WMS(this.layersNames["esa"].genericName, gms_wms_url, {
            layers : 'esa',
            format : 'image/png',
            transparent : "true"
        }, {
            isBaseLayer : false,
            singleTile : true,
            maxExtent : extent,
            maxResolution : "auto"
        });

        layers.osdRegistry = new OpenLayers.Layer.WMS(this.layersNames["osdRegistry"].genericName,
                gms_wms_url, {
                    layers : 'osd_registry',
                    format : 'image/png',
                    transparent : "true"
                }, {
                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.samplingsites = new OpenLayers.Layer.WMS(
        		this.layersNames["samplingsites"].genericName,
                gms_wms_url,
                {
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
                },
                {
                    isBaseLayer : false,
                    attribution : 'Provided by <a style="color:black" href="http://www.megx.net/">megx.net</a>',
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.woa05_temperature = new OpenLayers.Layer.WMS(this.layersNames["woa05_temperature"].genericName,
                gms_wms_url, {
                    layers : 'woa05_temperature',
                    format : 'image/png',
                    transparent : "false",
                    DEPTH: "0",
                    SEASON : "0",
                    isBaseLayer : false
                }, {
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });
        layers.woa05_temperature.desc = "World Ocean Atlas Temperature";
        layers.woa05_temperature.niceName = "WOA Temperature";
        layers.woa05_temperature.controlHtml = "";

        layers.satellite = new OpenLayers.Layer.WMS(this.layersNames["satellite"].genericName, gms_wms_url, {
            layers : 'satellite',
            format : 'image/png',
            transparent : "true"
        }, {
            isBaseLayer : false,
            singleTile : true,
            maxExtent : extent,
            maxResolution : "auto"
        });

        layers.satellite_mod = new OpenLayers.Layer.WMS(this.layersNames["satellite_mod"].genericName,
                gms_wms_url, {
                    layers : 'satellite_mod',
                    format : 'image/png',
                    transparent : "false"
                }, {
                    isBaseLayer : true,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.undersea_arc = new OpenLayers.Layer.WMS(this.layersNames["undersea_arc"].genericName,
                gms_wms_url, {
                    layers : 'undersea_arc',
                    format : 'image/png',
                    transparent : "true"
                }, {
                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.undersea_point = new OpenLayers.Layer.WMS(this.layersNames["undersea_point"].genericName,
                gms_wms_url, {
                    layers : 'undersea_point',
                    format : 'image/png',
                    transparent : "true"
                }, {
                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.lakes = new OpenLayers.Layer.WMS(this.layersNames["lakes"].genericName, gms_wms_url, {
            layers : 'lakes',
            format : 'image/png',
            transparent : "true"
        }, {
            isBaseLayer : false,
            singleTile : true,
            maxExtent : extent,
            maxResolution : "auto"
        });

        layers.boundaries = new OpenLayers.Layer.WMS(this.layersNames["boundaries"].genericName, gms_wms_url,
                {
                    layers : 'boundaries',
                    format : 'image/png',
                    transparent : "true"
                }, {
                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });
        layers.boundaries.niceName = "Boundaries";
        layers.boundaries.description = "Non-authoritative boundaries of the World Oceans";

        layers.limitsoceans = new OpenLayers.Layer.WMS(this.layersNames["limitsoceans"].genericName,
                gms_wms_url, {
                    layers : 'limitsoceans',
                    format : 'image/png',
                    transparent : "true"
                }, {
                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.coordinates = new OpenLayers.Layer.WMS(this.layersNames["coordinates"].genericName,
                gms_wms_url, {
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