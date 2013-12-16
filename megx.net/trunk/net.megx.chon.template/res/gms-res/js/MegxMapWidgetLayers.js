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

    init : function(cfg) {
        var gms_wms_url = cfg.gms_wms_url;
        var extent = cfg.extent;
        var sam_genomes = cfg.sam_genomes;
        var sam_rrna = cfg.sam_rrna;
        var sam_phages = cfg.sam_phages;
        var sam_metagenomes = cfg.sam_metagenomes;

        var layers = {};

        layers.bathymetry = new OpenLayers.Layer.WMS("bathymetry", gms_wms_url,
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

        layers.ena_samples = new OpenLayers.Layer.WMS("ena_samples",
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

        layers.osdRegistry = new OpenLayers.Layer.WMS("osdRegistry",
                gms_wms_url, {
                    layers : 'osdRegistry',
                    format : 'image/png',
                    transparent : "true"
                }, {
                    isBaseLayer : false,
                    singleTile : true,
                    maxExtent : extent,
                    maxResolution : "auto"
                });

        layers.samplingsites = new OpenLayers.Layer.WMS(
                "samplingsites",
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

        layers.woa05_temperature = new OpenLayers.Layer.WMS("woa05_temperature",
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

        layers.satellite_mod = new OpenLayers.Layer.WMS("satellite_mod",
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

        layers.undersea_arc = new OpenLayers.Layer.WMS("undersea_arc",
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

        layers.undersea_point = new OpenLayers.Layer.WMS("undersea_point",
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

        layers.boundaries = new OpenLayers.Layer.WMS("boundaries", gms_wms_url,
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
        layers.boundaries.desc = "Non-authoritative boundaries of the World Oceans";

        layers.limitsoceans = new OpenLayers.Layer.WMS("limitsoceans",
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

        layers.coordinates = new OpenLayers.Layer.WMS("coordinates",
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