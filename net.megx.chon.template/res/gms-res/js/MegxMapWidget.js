//Absract widget
MegxMapWidget = function(layerSetName, config) {

    this.gmsBaseURL = 'http://mb3is.megx.net/wms';

    this.init(layerSetName, config, this.gmsBaseURL);
}

MegxMapWidget.prototype = {
    init : function(layerSetName, config, gu) {

        this.renderWidget('megxMapWidget');

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
                this.addLayerPanel(this.layers.get(layerset[j]));
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
                '<td id="layersAccordion" style="width: 30%; padding-left:10px;">',
                '</td>', '<td class="mapPlaceholder">',

                '<div id="megxMap" style="width: 800px; height: 400px"></div>',

                '</td>', '</tr>', '</table>' ].join('');

        $(layoutParentSelector).append(layoutHtml);
    },

    addLayerPanel : function(layer) {
        // TODO check if layer exists and take pre-cautions
        var layerPanelHtml = [
                '<div id="mx-layer" class="group">',
                '<h3>' + layer.niceName,
                '<div class="mx-layer-configuration-header">',
                '<!-- icon for visibility| icon for external layer description document -->',
                '</div>', '</h3>', '<div class="mx-layer-configuration">',
                '<p class="mx-layer-description">' + layer.desc + '</p>',
                '<div class="mx-layer-control"></div>', '</div>', '</div>' ]
                .join('');

        $(this.infoPanel).append(layerPanelHtml);
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
        // argument array, new order of layers on the map
        // TODO: showLayer
    },

    setTopClickable : function(name) {
        // TODO: set clickable layer, show on top order ...
    },

    redraw : function(name) {
        name = name || '';
        return this.layers.get(name).redraw();
    }
};
