/**
 * Component to represent maps
 * 
 * @class
 * @extends Biojs
 * 
 * @author <a href="rkottman@mpi-bremen.de">Renzo Kottmann</a>
 * @version 1.0.0
 * @category 1
 * 
 * @dependency <link rel="stylesheet" href="../biojs/css/gms.css" />
 * @dependency <link rel="stylesheet" href="../biojs/css/style.css" />
 * 
 * @requires <a
 *           href='../biojs/dependencies/MegxMapWidgetLayers.js'>MegxMapWidgetLayers</a>
 * @dependency <script language="JavaScript" type="text/javascript"
 *             src="../biojs/dependencies/MegxMapWidgetLayers.js"></script>
 * 
 * @requires <a
 *           href='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'>jQuery
 *           1.10.2</a>
 * @dependency <script language="JavaScript" type="text/javascript"
 *             src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
 * 
 * @requires <a href='http://openlayers.org/api/2.13.1/OpenLayers.js'>OpenLayers</a>
 * @dependency <script language="JavaScript" type="text/javascript"
 *             src="http://openlayers.org/api/2.13.1/OpenLayers.js"></script>
 * 
 * @requires <a
 *           href='http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css'>jQuery-ui
 *           css</a>
 * @dependency <link rel="stylesheet"
 *             href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
 * 
 * @requires <a href='http://code.jquery.com/ui/1.10.3/jquery-ui.js'>jQuery-ui
 *           1.10.3</a>
 * @dependency <script language="JavaScript" type="text/javascript"
 *             src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 * 
 * 
 * @param {Object}
 *          options Configuration object.
 * 
 * @option {string} target Identifier of the DIV tag where the component should
 *         be displayed.
 * 
 * @option {string} [layerSet="ena"] Name of the layerSet.
 * 
 * @option {string} [gmsBaseURL="http://mb3is.megx.net/wms"] Base URL of the
 *         mapserver.
 * 
 * @example var megxMapWidget = new Biojs.MegxMapWidget({ 'layerSet' : 'ena',
 *          'target' : 'YourOwnDivId', 'gmsBaseURL' :
 *          'http://mb3is.megx.net/wms' });
 * 
 */

Biojs.MegxMapWidget = Biojs
  .extend({
    /** @lends Biojs.MegxMapWidget# */

    constructor : function( options ) {
      this._init(options);
    },

    opt : {
      target : 'megxMapWidget',
      layerSet : 'osd-registry',
      gmsBaseURL : 'http://mb3is.megx.net/wms',
      log : true
    },

    eventTypes : [],

    _init : function( options ) {

      this.opt = options || this.opt;

      // did not know, how to otherwise change the log behavior depending on
      // options
      Biojs.MegxMapWidget.prototype._log.enabled = this.opt.log;

      this._renderWidget(this.opt.target);

      this._log.error('MegxMapWidget 0.9');

      this._initializeLayerDialog();
      this._initializeAddRemoveLink();
      this.displayedLayers = {};

      this.map = new OpenLayers.Map('megxMap', {
        controls : [],
        numZoomLevels : 16,
        projection : "EPSG:4326",
        allOverlays : true
      });

      this.LAYERSET = {
        // order is bottom to top layer from left to right
        'osd-app' : [ 'boundaries', 'osd_app' ],
        'ena' : [ 'woa05_temperature', 'boundaries', 'ena_samples' ],
        'osd-registry' : [ 'boundaries', 'osd_registry' ],
        'gms' : ['boundaries', 'phages','genomes', 'metagenomes']
      };

      this.infoPanel = '#layersAccordion';

      this._initMap(this.map);

      this.layers = new MegxMapWidgetLayers({
        gms_wms_url : this.opt.gmsBaseURL,
        sam_genomes : 'genome',
        sam_metagenomes : 'metagenome',
        sam_phages : 'phage',
        sam_rrna : '',
        extent : new OpenLayers.Bounds(-180, -90, 180, 90)
      });

      var layerset = this.LAYERSET[this.opt.layerSet.toLowerCase()];
      
      this._log.message('layerset' + layerset);

      if ( layerset ) {
        for ( var i = 0; i < layerset.length; i++ ) {
          
          var l = this.layers.get(layerset[i]);
          if ( l ) {
            this.map.addLayer(l);
            this._addLayerPanel(layerset[i]);
            this.displayedLayers[layerset[i]] = true;
            // As per ticket
            // https://colab.mpi-bremen.de/its/browse/MB3_IS-159,
            // display legend data for the topmost layer only
            this._redrawLegend(layerset[layerset.length - 1]);
            this._log.message('Added layer ' + layerset[i]);
          } else {
            this._log.error('An error occured while adding layer '
              + layerset[i] + '!');
          }
        }
      } else {
        this._log.error('Requested set of layers does not exist: ' + this.opt.layerSet.toLowerCase());
        return;
      }
      this._createWMSFeatureInfo(this.layers.get('ena_samples'), this.map);
      this._accordifyLayerPanel();

      // map.setBaseLayer(this.layers.get('satellite_mod'));
      // map.setCenter(new OpenLayers.LonLat(lon, lat), 0);
      if ( !this.map.getCenter() ) {
        this.map.zoomToMaxExtent();
      }
    },

    /**
     * Creates WMS Feature Info
     * 
     * @param {Object}
     *          layer Description.
     * @param {object}
     *          map Description.
     * @example _createWMSFeatureInfo(this.layers.get('ena_samples'), this.map);
     * 
     */
    _createWMSFeatureInfo : function( layer, map ) {

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
          getfeatureinfo : function( event ) {
            var lonlat = map.getLonLatFromPixel(event.xy);
            map.addPopup(new OpenLayers.Popup.FramedCloud("chicken", lonlat,
              null, event.text ? event.text : 'No data at ' + event.text + ': '
                + lonlat + event.features + event.xy, null, true));
          }
        }
      });

      map.addControl(featureInfo);
      featureInfo.activate();
    },

    /**
     * Renders the widget in the element with the specified id.
     * 
     * @param {String}
     *          layoutParent Description.
     * @example _renderWidget(options.target);
     * 
     */
    _renderWidget : function( layoutParent ) {
      var layoutParentSelector = '#' + layoutParent;
      
      var legendHTML = ['<legend>', '<h6>',
      'Legend', '</h6>', '<div id="legendDataPlaceholder">', '</div>',
      '</legend>'].join('');
      
      
      var layoutHtml = [
        '<table style="width: 99%; margin-top:5px; margin-bottom:5px;">',
        '<tr>',
        '<td colspan="2">',
        '<button id="manipulateLayers" style="margin-left: 12px; margin-bottom: 10px;">Add layers to the map</button>',
        '</td>',
        '</tr>',
        '<tr>',
        '<td id="layersAccordion" style="width: 35%; padding-left:5px;">',
        '</td>',
        '<td class="mapPlaceholder" style="width: 65%">',
        '<div id="megxMap" style="width: 600px; height: 400px"></div>',
        '</td>',
        '</tr>',
        '<tr>',
        '<td></td><td>',
        
        '<div id="messagesPlaceholder" style="border: 1px solid; border-color: grey; border-radius: 5px; max-height: 120px; overflow-y: auto;">',
        '</div>', '</td>', '</tr>', '<tr>',
        
        '<td colspan="2" style="padding-left: 16%;">', '</td>', '</tr>', '</table>', '<div id="layerDialog">',
        '</div>' ].join('');

      $(layoutParentSelector).append(layoutHtml);
      this._buttonizeAddIcon();
    },

    _log : {
      // per default logging is disabled
      enabled : false,

      message : function( msg ) {
        this.log(msg, 'green');
      },
      error : function( msg ) {
        this.log(msg, 'red');
      },

      log : function( msg, color ) {

        if ( !this.enabled ) return;

        if ( ! msg || msg == '' ) return;

        if ( console && console.log ) {
          console.log(msg);
        }
        ;

        var timeStamp = new Date();
        var msgToDisplay = [
          '<p style="color:' + color + '; padding-left: 10px;">',
          timeStamp.toLocaleString(), ' ', msg, '</p>' ].join('');

        var nbDisplayedMsgs = $('#messagesPlaceholder').children().length;
        var childrenHeight = $('#messagesPlaceholder').children().height();

        $('#messagesPlaceholder').append(msgToDisplay);

        $('#messagesPlaceholder').animate({
          scrollTop : nbDisplayedMsgs * childrenHeight
        }, "slow");

      }
    },

    /**
     * Initializes the Layer Dialog
     */
    _initializeLayerDialog : function() {
      var self = this;

      $("#layerDialog").dialog({
        resizable : false,
        autoOpen : false,
        height : 600,
        width : 400,
        title : 'Add or remove a map layer.',
        modal : true,
        buttons : {
          Ok : function() {
            self._addLayers();
            self._log.message('New layer added to map');
            $(this).dialog("close");
          },
          Cancel : function() {
            self._log.message('No layer added to map');
            $(this).dialog("close");
          }
        }
      });
      $('.ui-dialog').css('z-index', '10000');
    },

    /**
     * Initializes Add/Remove Link
     */
    _initializeAddRemoveLink : function() {
      var self = this;

      $('#manipulateLayers').click(function() {
        $("#layerDialog").dialog("open");
        self._populateLayersDialog();
        return false;
      });

      $(document).on('click', 'button.removeLayer', function() {
        var layerToRemove = $(this).closest('div.mx-layer').attr('id');
        self._removeLayer(layerToRemove);
        self._removeLayerPanel(layerToRemove);
        self._log.message('Layer ' + layerToRemove + ' removed from map');
      });
    },

    /**
     * Populates Layers Dialog
     */
    _populateLayersDialog : function() {
      var allLayers = this.layers.getLayersNameDesc();
      var htmlToRender = [];
      for ( var layer in allLayers ) {
        var currentLayer = allLayers[layer];
        if ( !this.displayedLayers[currentLayer.genericName] ) {
          htmlToRender.push('<input class="layer" type="radio" value="'
            + currentLayer.genericName + '" name="layerChoice" />'
            + currentLayer.niceName + '<br/>');
        }
      }

      $("#layerDialog").html(htmlToRender.join(''));
    },

    /**
     * Adds Layers
     */
    _addLayers : function() {
      var self = this;
      var selectedLayer = $('input.layer:checked').val();
      this.map.addLayer(this.layers.get(selectedLayer));
      this._addLayerPanel(selectedLayer, function() {
        $(this.infoPanel).accordion('refresh');
      });
      this.displayedLayers[selectedLayer] = true;

      var newLayerOrder = this._getNewLayerOrder();
      this._redrawLegend(newLayerOrder[0]);

    },

    /**
     * Adds Layer Panel
     * 
     * @param {Object}
     *          layer Description.
     * @param {object}
     *          success Description.
     * @example this._addLayerPanel(selectedLayer, function(){
     *          $(this.infoPanel).accordion('refresh'); });
     */
    _addLayerPanel : function( layer, success ) {
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
        '</div>', '</h3>', '<div class="mx-layer-configuration">',
        '<p class="mx-layer-description">' + layerInfo.description + '</p>',
        '<div class="mx-layer-control"></div>', '</div>', '</div>' ].join('');

      $(this.infoPanel).prepend(layerPanelHtml);
      this._buttonizeRemoveIcon();
      if ( success ) {
        success.call(this);
      }
    },

    /**
     * Remove Layer Panel
     * 
     * @param {Object}
     *          layer Description.
     * @example _removeLayerPanel(layerToRemove);
     */
    _removeLayerPanel : function( layer ) {
      $('#' + layer).remove();
      $(this.infoPanel).accordion('refresh');

      var newLayerOrder = this._getNewLayerOrder();
      this._redrawLegend(newLayerOrder[0]);
    },

    /**
     * Buttonize Add Icon
     */
    _buttonizeAddIcon : function() {
      $('#manipulateLayers').button({
        icons : {
          primary : "ui-icon-plusthick"
        },
        text : false
      });
    },

    /**
     * Buttonize Remove Icon
     */
    _buttonizeRemoveIcon : function() {
      $('button.removeLayer').button({
        icons : {
          primary : "ui-icon-close"
        },
        text : false
      });
    },

    /**
     * Initializes the Map
     * 
     * @param {object}
     *          map Description.
     * 
     * @example
     * 
     */
    _initMap : function( map ) {
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

    /**
     * Accordify Layer Panel
     */
    _accordifyLayerPanel : function() {
      var self = this;
      $(this.infoPanel).accordion({
        collapsible : true,
        heightStyle : "content",
        header : "> div > h3"
      }).sortable({
        axis : "y",
        handle : "h3",
        stop : function( event, ui ) {
          // IE doesn't register the blur when sorting
          // so trigger focusout handlers to remove
          // .ui-state-focus
          self._reorder(self._getNewLayerOrder());
          ui.item.children("h3").triggerHandler("focusout");
        }
      });
    },

    /**
     * Returns the New Layer Order
     */
    _getNewLayerOrder : function() {
      var newLayerOrder = [];
      $('.mx-layer').each(function() {
        newLayerOrder.push($(this).attr('id'));
      });

      return newLayerOrder;
    },

    /**
     * Remove Control
     * 
     * @param {String}
     *          controlName Description.
     */
    _removeControl : function( controlName ) {
      var controls = this.map.getControlsByClass(controlName);
      for ( var i = 0; i < controls.length; i++ ) {
        controls[i].deactivate();
      }
    },

    /**
     * Adds Layer
     * 
     * @param {Object}
     *          layer Description.
     */
    _addLayer : function( layer ) {
      this.map.addLayer(layer);
    },

    /**
     * Removes Layer
     * 
     * @param {Object}
     *          layer Description.
     * 
     * @example
     * 
     */
    _removeLayer : function( layer ) {
      var currentZoomLevel = this.map.getZoom();
      this.displayedLayers[layer] = false;
      this.map.removeLayer(this.layers.get(layer));
      this.map.zoomTo(currentZoomLevel);
    },

    /**
     * Shows Layer
     * 
     * @param {String}
     *          name Description.
     * 
     * @example
     * 
     */
    _showLayer : function( name ) {
      this.layers.get(name).setVisibility(true);
    },

    /**
     * Hides Layer
     * 
     * @param {String}
     *          name Description.
     * 
     * @example
     * 
     */
    _hideLayer : function( name ) {
      this.layers.get(name).setVisibility(false);
    },

    /**
     * Hides All Layers
     * 
     * @example
     * 
     */
    _hideAll : function() {
      var names = this.layers.getLayersNames();
      for ( var i = 0; i < names.length; i++ ) {
        this._hideLayer(names[i]);
      }
    },

    /**
     * Reorders the layers
     * 
     * @param {String[]}
     *          arr Description.
     * 
     * @example
     * 
     */
    _reorder : function( arr ) {
      var currentZoomLevel = this.map.getZoom();

      this._log.message('Reordering layers as per new layer order...');

      // First remove all layers from the map
      for ( var i = 0; i < arr.length; i++ ) {
        this.map.removeLayer(this.layers.get(arr[i]));
      }

      // Then add the layers to the map as per the newly chosen order
      for ( var i = arr.length - 1; i >= 0; i-- ) {
        this.map.addLayer(this.layers.get(arr[i]));
      }
      this.map.zoomTo(currentZoomLevel);

      this._log.message('Redrawing legend...');
      this._redrawLegend(arr[0]);
    },

    /**
     * Redraws Legend
     */

    _redrawLegend : function( layerName ) {
      var self = this;

      // try {
      // OpenLayers.Request.GET({
      // url: self.gmsBaseURL,
      // async: false,
      // params: {
      // LAYER: layerName,
      // MODE: 'LEGEND'
      // },
      // success: function (response) {
      // self._log.message('Legend data successfully retrieved from
      // map server');
      // $('#legendDataPlaceholder').html(response.responseText);
      // },
      // failure: function (response) {
      // self._log.error('Error occured while retrieveing legend data
      // for layer ' + layerName + '. Error details: ' +
      // JSON.stringify(response));
      // }
      // });
      // } catch (err) {
      // console.log('error ocured', JSON.stringify(err));
      // }
    },

    /**
     * Set clickable layer
     * 
     * @param {String}
     *          name Description.
     */
    _setTopClickable : function( name ) {
    // TODO: set clickable layer, show on top order ...
    },

    /**
     * Redraw the layer
     * 
     * @param {String}
     *          name Description.
     */
    _redraw : function( name ) {
      name = name || '';
      return this.layers.get(name).redraw();
    }

  });
