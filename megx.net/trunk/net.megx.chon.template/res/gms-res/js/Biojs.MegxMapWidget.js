/** 
 * Component to represent maps
 * 
 * @class
 * @extends Biojs 
 * 
 * @author <a href="mailto:example@interworks.com.mk">Name Lasname</a>
 * @version 1.0.0
 * @category 1
 * 
 * @dependency <link rel="stylesheet" href="../biojs/css/gms.css" />
 * @dependency <link rel="stylesheet" href="../biojs/css/style.css" />
 * 
 * @requires <a href='../biojs/dependencies/MegxMapWidgetLayers.js'>MegxMapWidgetLayers</a>
 * @dependency <script language="JavaScript" type="text/javascript" src="../biojs/dependencies/MegxMapWidgetLayers.js"></script>
 * 
 * @requires <a href='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'>jQuery 1.10.2</a>
 * @dependency <script language="JavaScript" type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
 *
 * @requires <a href='http://openlayers.org/api/2.13.1/OpenLayers.js'>OpenLayers</a>
 * @dependency <script language="JavaScript" type="text/javascript" src="http://openlayers.org/api/2.13.1/OpenLayers.js"></script>
 *
 * @requires <a href='http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css'>jQuery-ui css</a>
 * @dependency <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" /> 
 *
 * @requires <a href='http://code.jquery.com/ui/1.10.3/jquery-ui.js'>jQuery-ui 1.10.3</a>
 * @dependency <script language="JavaScript" type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
 *
 * 
 * @param {Object} options Configuration object.  
 * 
 * 
 * @example
 * var megxMapWidget = new Biojs.MegxMapWidget({
 * 		'layerSet' : 'ena',
 * 		'target' : 'YourOwnDivId',
 * 		'gmsBaseURL' : 'http://mb3is.megx.net/wms'
 * 		});
 * 
 */
Biojs.MegxMapWidget = Biojs.extend ({
	/** @lends Biojs.MegxMapWidget# */
	 
	constructor: function (options) {
		 this._init(options);
	 },
	
	 opt: {
		target : 'YourOwnDivId',
		layerSet : 'ena',
		gmsBaseURL : 'http://mb3is.megx.net/wms'
	 },
	
	 eventTypes: [
	 ],
 
	 _init : function(options) {
		 
		 options = options || this.opt;
	
	     this._renderWidget(options.target);
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
	         'esa' : [ 'satellite_mod', 'esa' ],
	         'ena' : [ 'boundaries', 'woa05_temperature', 'ena_samples' ]
	     };
	
	     this.infoPanel = '#layersAccordion';
	
	     this._initMap(this.map);
	
	     this.layers = new MegxMapWidgetLayers({
	         gms_wms_url : options.gmsBaseURL,
	         sam_genomes : 'genome',
	         sam_metagenomes : 'metagenome',
	         sam_phages : 'phage',
	         sam_rrna : '',
	         extent : new OpenLayers.Bounds(-180, -90, 180, 90)
	     });
	
	     var layerset = this.LAYERSET[options.layerSet.toLowerCase()];
	
	     if (layerset) {
	         for ( var i = 0; i < layerset.length; i++ ) {
	             this.map.addLayer(this.layers.get(layerset[i]));
	             this._addLayerPanel(layerset[i]);
	             this.displayedLayers[layerset[i]] = true;
	         }
	     }
	     this._createWMSFeatureInfo(this.layers.get('ena_samples'), this.map);
	     this._accordifyLayerPanel();
	
	     // map.setBaseLayer(this.layers.get('satellite_mod'));
	     // map.setCenter(new OpenLayers.LonLat(lon, lat), 0);
	     if (!this.map.getCenter()) {
	         this.map.zoomToMaxExtent();
	     }
	 },
	
	 /**
	 * Creates WMS Feature Info 
	 * 
	 * @param {Object} layer Description.
	 * @param {object} map Description.
	 * @example 
	 * _createWMSFeatureInfo(this.layers.get('ena_samples'), this.map);
	 * 
	 */ 
	 _createWMSFeatureInfo : function(layer, map) {
	
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
	
	 /**
	 * Renders the widget in the element with the specified id. 
	 * 
	 * @param {String} layoutParent Description.
	 * @example 
	 * _renderWidget(options.target);
	 * 
	 */ 
	 _renderWidget : function(layoutParent) {
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
	         				'<div id="megxMap" style="width: 600px; height: 400px"></div>',
	     				'</td>',
	 				'</tr>',
					'</table>',
					'<div id="layerDialog">',
					'</div>'].join('');
	
	     $(layoutParentSelector).append(layoutHtml);
	     this._buttonizeAddIcon();
	 },
	 
	 /**
	 *  Initializes the Layer Dialog 
	 */
	 _initializeLayerDialog: function(){
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
			        	self._addLayers();
			        	$(this).dialog("close");
			        },
			        Cancel: function() {
			        	$(this).dialog( "close" );
			        }
			      }
			});
	 	$('.ui-dialog').css('z-index', '10000');
	 },
	 
	 /**
	 * Initializes Add/Remove Link 
	 */
	 _initializeAddRemoveLink: function(){
	 	var self = this;
	 	
	 	$('#manipulateLayers').click(function(){
	 		$("#layerDialog").dialog("open");
	 		self._populateLayersDialog();
	 		return false;
	 	});
	 	
	 	$(document).on('click', 'button.removeLayer', function(){
	 		var layerToRemove = $(this).closest('div.mx-layer').attr('id');
	 		self.removeLayer(layerToRemove);
	 		self._removeLayerPanel(layerToRemove);
	 	});
	 },
	 
	 /**
	 * Populates Layers Dialog  
	 */
	 _populateLayersDialog: function(){
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
	 
	 /**
	 * Adds Layers
	 */
	 _addLayers: function(){
	 	var self = this;
	 	var selectedLayer = $('input.layer:checked').val();
	 	this.map.addLayer(this.layers.get(selectedLayer));
	 	this._addLayerPanel(selectedLayer, function(){
	 		$(this.infoPanel).accordion('refresh');
	 	});
	     this.displayedLayers[selectedLayer] = true;
	 },
	 
	 /**
	 * Adds Layer Panel
	 * 
	 * @param {Object} layer Description.
	 * @param {object} success Description.
	 * @example 
	 * this._addLayerPanel(selectedLayer, function(){
	 * 		$(this.infoPanel).accordion('refresh');
	 * });
	 */
	 _addLayerPanel : function(layer, success) {
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
	     this._buttonizeRemoveIcon();
	     if(success){
	     	success.call(this);
	     }
	 },
	 
	 /**
	 * Remove Layer Panel
	 * 
	 * @param {Object} layer Description.
	 * @example 
	 * _removeLayerPanel(layerToRemove);
	 */
	 _removeLayerPanel: function(layer){
	 	$('#' + layer).remove();
	 	$(this.infoPanel).accordion('refresh');
	 },
	 
	 /**
	 * Buttonize Add Icon
	 */
	 _buttonizeAddIcon: function(){
	 	$( '#manipulateLayers' ).button({
			    icons: {
			      primary: "ui-icon-plusthick"
			    },
			    text: false
		    });
	 },
	 
	 /**
	 * Buttonize Remove Icon
	 */
	 _buttonizeRemoveIcon: function(){
	 	$( 'button.removeLayer' ).button({
			    icons: {
			      primary: "ui-icon-close"
			    },
			    text: false
		    });
	 },
	 
	 /**
	 * Initializes the Map 
	 * 
	 * @param {object} map Description.
	 * 
	 * @example 
	 * 
	 */
	 _initMap : function(map) {
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
	 * _accordifyLayerPanel
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
	         stop : function(event, ui) {
	             // IE doesn't register the blur when sorting
	             // so trigger focusout handlers to remove .ui-state-focus
	         	var newLayerOrder = [];
	         	$('.mx-layer').each(function(){
	         		newLayerOrder.push($(this).attr('id'));
	     		});
	         	self._reorder(newLayerOrder);
	             ui.item.children("h3").triggerHandler("focusout");
	         }
	     });
	 },
	
	 /**
	 * Remove Control 
	 * @param {String} controlName Description.
	 */
	 _removeControl : function(controlName) {
	     var controls = this.map.getControlsByClass(controlName);
	     for ( var i = 0; i < controls.length; i++) {
	         controls[i].deactivate();
	     }
	 },
	
	 /**
	 * Adds Layer
	 * 
	 * @param {Object} layer Description.
	 */
	 _addLayer : function(layer) {
	     this.map.addLayer(layer);
	 },
	 
	 /**
	 * Removes Layer
	 * 
	 * @param {Object} layer Description.
	 * 
	 * @example 
	 * 
	 */
	 _removeLayer: function(layer){
	 	var currentZoomLevel = this.map.getZoom();
	 	this.displayedLayers[layer] = false;
	 	this.map.removeLayer(this.layers.get(layer));
	 	this.map.zoomTo(currentZoomLevel);
	 },
	
	 /**
	 * Shows Layer
	 * 
	 * @param {String} name Description.
	 * 
	 * @example 
	 * 
	 */
	 _showLayer : function(name) {
	     this.layers.get(name).setVisibility(true);
	 },
	
	 /**
	 * Hides Layer
	 * 
	 * @param {String} name Description.
	 * 
	 * @example 
	 * 
	 */
	 _hideLayer : function(name) {
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
	     for ( var i = 0; i < names.length; i++) {
	         this._hideLayer(names[i]);
	     }
	 },
	
	 /**
	 * Reorders the layers 
	 * 
	 * @param {String[]} arr Description.
	 * 
	 * @example 
	 * 
	 */
	 _reorder : function(arr) {
	 	var currentZoomLevel = this.map.getZoom();
	 	
	 	//First remove all layers from the map
	     for(var i=0; i<arr.length; i++){
	     	this.map.removeLayer(this.layers.get(arr[i]));
	     }
	     
	     //Then add the layers to the map as per the newly chosen order
	     for(var i=arr.length - 1; i>=0; i--){
	     	this.map.addLayer(this.layers.get(arr[i]));
	     }
	     this.map.zoomTo(currentZoomLevel);
	     
	     this._redrawLegend(arr[0]);
	 },
	 
	 _redrawLegend: function(layerName){
	 	var self = this;
	 	var legend = OpenLayers.Request.GET({
	         url : self.gmsBaseURL,
	         async : false,
	         params : {
	             LAYER : layerName,
	             MODE : 'LEGEND'
	         }
	     });
	 	var a = legend;
	 },
	
	 /**
	 * Set clickable layer
	 * 
	 * @param {String} name Description. 
	 */
	 _setTopClickable : function(name) {
	     // TODO: set clickable layer, show on top order ...
	 },
	
	  /**
	 * Redras the layer 
	 * 
	 * @param {String} name Description.
	 */
	 _redraw : function(name) {
	     name = name || '';
	     return this.layers.get(name).redraw();
	 }
 
});