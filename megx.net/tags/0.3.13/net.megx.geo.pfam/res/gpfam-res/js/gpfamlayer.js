/**
 * This is the javascript implementing the Mapserver client on the browser site.
 * It depends on OpenLayers see
 *
 * Author: Renzo Kotmann rkottman@mpi-bremen.de
 */

/** variable section */
var lon = 5;
var lat = 40;
var zoom = 1;
var map;
var extent = new OpenLayers.Bounds(-180, -90, 180, 90);

// cur = current lname= layer name
var cur_season = "0"; // should be a single zero ?!
var cur_depth = "0";
var cur_year = "0";
var cur_layer = null;
var cur_lname = null;
var cur_pfam = 'PF00001';
// which layer reacts on mouse clicks either name or empty string
var sam_genomes = 'genome';
var sam_metagenomes = 'metagenome';
var sam_phages = 'phage';
var sam_rrna = '';

// which layer rects on mouse clicks
var clicklayer = 'geopfam';


// TODO change this to gms_host_url + "/wms"
var gms_wms_url = gms_host_url + "/wms";
// TODO eliminate this
var gms_map_file = '';

var c_mode = '';
var sid = null;
var jid = null;

var annual_selection = '<select id="season"'
		+ 'title="Select the time period for which an environmental parameter layer should be shown"'
		+ 'onclick="setSeason(this.options[this.selectedIndex].value)">'
		+ '<option value="0">Annual</option>' + '<optgroup label="Month">'
		+ '	<option value="1">January</option>'
		+ '	<option value="2">February</option>'
		+ '	<option value="3">March</option>'
		+ '	<option value="4">April</option>'
		+ '	<option value="5">May</option>'
		+ '	<option value="6">June</option>'
		+ '	<option value="7">July</option>'
		+ '	<option value="8">August</option>'
		+ '	<option value="9">September</option>'
		+ '	<option value="10">October</option>'
		+ '	<option value="11">November</option>'
		+ '	<option value="12">December</option>' + '</optgroup>'
		+ '<optgroup label="Seasons">'
		+ '	<option value="13">1. Quarter</option>'
		+ '	<option value="14">2. Quarter</option>'
		+ '	<option value="15">3. Quarter</option>'
		+ '	<option value="16">4. Quarter</option>' + '</optgroup>'
		+ '</select>'

var chloro_selection = '<select id="chloro_year" name="grid_chloro" onchange="setYear(this.options[this.selectedIndex].value)">'
		+ '<option value="" >Select year</option>'
		+ '<option value="1997">1997</option>'
		+ '<option value="1998">1998</option>'
		+ '<option value="1999">1999</option>'
		+ '<option value="2000">2000</option>'
		+ '<option value="2001">2001</option>'
		+ '<option value="2002">2002</option> '
		+ '<option value="2003">2003</option>'
		+ '<option value="2004">2004</option>'
		+ '<option value="2005">2005</option>'
		+ '<option value="2006">2006</option>'
		+ '<option value="1110">Climatology</option></select>';

var depth_selection = '<select id="depth" name="depth" onchange="setDepth(this.options[this.selectedIndex].value)">'
		+ '<option value="0" >0m</option>'
		+ '<option value="10" >10m</option>'
		+ '<option value="20" >20m</option>'
		+ '<option value="30" >30m</option>'
		+ '<option value="50" >50m</option>'
		+ '<option value="75" >75m</option>'
		+ '<option value="100" >100m</option>'
		+ '<option value="125" >125m</option>'
		+ '<option value="150" >150m</option>'
		+ '<option value="200" >200m</option>'
		+ '<option value="250" >250m</option>'
		+ '<option value="300" >300m</option>'
		+ '<option value="400" >400m</option>'
		+ '<option value="500" >500m</option>'
		+ '<option value="600" >600m</option>'
		+ '<option value="700" >700m</option>'
		+ '<option value="800" >800m</option>'
		+ '<option value="900" >900m</option>'
		+ '<option value="1000" >1000m</option>'
		+ '<option value="1100" >1100m</option>'
		+ '<option value="1200" >1200m</option>'
		+ '<option value="1300" >1300m</option>'
		+ '<option value="1400" >1400m</option>'
		+ '<option value="1500" >1500m</option>'
		+ '<option value="1750" >1750m</option>'
		+ '<option value="2000" >2000m</option>'
		+ '<option value="2500" >2500m</option>'
		+ '<option value="3000" >3000m</option>'
		+ '<option value="3500" >3500m</option>'
		+ '<option value="4000" >4000m</option>'
		+ '<option value="4500" >4500m</option>'
		+ '<option value="5000" >5000m</option>'
		+ '<option value="5500" >5500m</option></select>';

//var pfam_selection = '<select id="depth" name="depth" onchange="setPFAM(this.options[this.selectedIndex].value)">'
//	+ '<option value="7tm_1" >7tm_1</option>'
//	+ '<option value="HSP70" >HSP70</option></select>';

var stability_selection = '<select id="stability_layer_selection" name="stability" onchange="setCurLayerName(this.options[this.selectedIndex].value)">'
		+ '<option value="">Select Stability Layer</option>'
		+ '<option value="woa05_temperature_stability">Temperature</option>'
		+ '<option value="woa05_nitrate_stability">Nitrate</option>'
		+ '<option value="woa05_phosphate_stability">Phosphate</option>'
		+ '<option value="woa05_salinity_stability">Salinity</option>'
		+ '<option value="woa05_silicate_stability">Silicate</option>'
		+ '<option value="woa05_oxygen_dissolved_stability">Dissolved oxygen</option>'
		+ '<option value="woa05_oxygen_saturation_stability">Oxygen Saturation</option>'
		+ '<option value="woa05_oxygen_utilization_stability">Oxygen Utilization</option></select>';

// default client map options
var options = {
	controls : [],
	numZoomLevels : 16,
	projection : "EPSG:4326"

	// restrictedExtent : extent
};
// WMSGetFeatureInfo control for pop up creation to give sampling site details
var info;

// this is for genes mapserver
// TODO rename to gmsMap

function setLocationbarHash(event) {
	// OpenLayers.Console.log("TEST called");
	var h = document.getElementById('permalink');
	if (h != null) {
		window.location.hash = h.hash;
	}
}

/**
 * Main map creation function to be called on site load. This creates Genes
 * Mapserver layers only. See other functions for additional functionality.
 *
 * @return Boolean true on succesful creation
 */
function cMap() {

	map = new OpenLayers.Map('gmsmap', options);

	bathymetryLayer = new OpenLayers.Layer.WMS("bathymetry", gms_wms_url, {
				layers : 'bathymetry',
				format : 'image/png',
				transparent : "true"
			}, {
				isBaseLayer : false,
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	samplingsitesLayer = new OpenLayers.Layer.WMS("samplingsites", gms_wms_url,
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
			}, {
				isBaseLayer : false,
				attribution : 'Provided by <a style="color:black" href="http://www.megx.net/">megx.net</a>',
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});


	thematicLayer = new OpenLayers.Layer.WMS("thematiclayer", gms_wms_url, {
				layers : '',
				format : 'image/png',
				transparent : "false",
				time : cur_year + '-' + cur_season,
				isBaseLayer : false
			}, {
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	pfamLayer = new OpenLayers.Layer.WMS("geopfam", gms_wms_url, {
		layers : 'geopfam',
		format : 'image/png',
		transparent : "true",
		pfam_accession : cur_pfam
	}, {
		isBaseLayer : false,
		singleTile : true,
		maxExtent : extent,
		maxResolution : "auto"
	});
	//setVisible(pfamLayer.name, true);
	satelliteLayer = new OpenLayers.Layer.WMS("satellite", gms_wms_url, {
				layers : 'satellite',
				format : 'image/png',
				transparent : "true"
			}, {
				isBaseLayer : false,
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	satelliteLayerMod = new OpenLayers.Layer.WMS("satellite_mod", gms_wms_url,
			{
				layers : 'satellite_mod',
				format : 'image/png',
				transparent : "false"
			}, {
				isBaseLayer : true,
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	undersea_arc = new OpenLayers.Layer.WMS("undersea_arc", gms_wms_url, {
				layers : 'undersea_arc',
				format : 'image/png',
				transparent : "true"
			}, {
				isBaseLayer : false,
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	undersea_point = new OpenLayers.Layer.WMS("undersea_point", gms_wms_url, {
				layers : 'undersea_point',
				format : 'image/png',
				transparent : "true"
			}, {
				isBaseLayer : false,
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	lakesLayer = new OpenLayers.Layer.WMS("lakes", gms_wms_url, {
				layers : 'lakes',
				format : 'image/png',
				transparent : "true"
			}, {
				isBaseLayer : false,
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	boundariesLayer = new OpenLayers.Layer.WMS("boundaries", gms_wms_url, {
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
	limitsoceansLayer = new OpenLayers.Layer.WMS("limitsoceans", gms_wms_url, {
		layers : 'limitsoceans',
		format : 'image/png',
		transparent : "true"
			// BBOX : extent.toBBOX(),
			// WIDTH : extent.getWidth(),
			// HEIGHT : extent.getHeight()
		}, {
		isBaseLayer : false,
		singleTile : true,
		maxExtent : extent,
		maxResolution : "auto"
	});

	coordinatesLayer = new OpenLayers.Layer.WMS("coordinates", gms_wms_url, {
				layers : 'coordinates',
				format : 'image/png',
				transparent : "true"
			}, {
				isBaseLayer : false,
				singleTile : true,
				maxExtent : extent,
				maxResolution : "auto"
			});

	// add here all layers to the map, the order determines the layers z-index!!
	map.addLayers([satelliteLayerMod, bathymetryLayer, satelliteLayer,
			thematicLayer, undersea_arc, undersea_point, lakesLayer,
			boundariesLayer, limitsoceansLayer, coordinatesLayer,
			samplingsitesLayer,pfamLayer]);

	map.setBaseLayer(satelliteLayerMod);
	// set visibility of layer and check box
	setVisible(bathymetryLayer.name, false);
	setVisible(thematicLayer.name, false);
	setVisible(satelliteLayer.name, false);
	setVisible(satelliteLayerMod.name, false);
	setVisible(undersea_arc.name, false);
	setVisible(undersea_point.name, false);
	setVisible(lakesLayer.name, false);
	setVisible(boundariesLayer.name, true);
	setVisible(limitsoceansLayer.name, false);
	setVisible(coordinatesLayer.name, false);
	setVisible(samplingsitesLayer.name, false);
	setVisible(pfamLayer.name, true);
	
	// create the legend
	cLegend('geopfam');


	var url = pfamLayer.getFullRequestString();
	OpenLayers.Console.log('GetFeatureInfo URL=' + url);
	info = new OpenLayers.Control.WMSGetFeatureInfo({
				// url : url,
				// hover: true,
				vendorParams : {
					pfam_accession : '7tm_1'
				},
				layers : map.getLayersByName('geopfam'),
				title : 'Identify features by clicking',
				// queryVisible : true,
				eventListeners : {
					getfeatureinfo : function(event) {
						var lonlat = map.getLonLatFromPixel(event.xy);
						map
								.addPopup(new OpenLayers.Popup.FramedCloud(
										"chicken", lonlat, null, event.text
												? event.text
												: 'No result at ' + lonlat,
										null, true));
					}
				}
			});
	map.addControl(info);
	info.activate();
	OpenLayers.Console.log("Openlayers Version=" + OpenLayers.VERSION_NUMBER);
	// map controls
	var navContr = new OpenLayers.Control.Navigation();
	navContr.setMap(map);

	map.addControl(navContr);
	map.addControl(new OpenLayers.Control.ScaleLine());
	map.addControl(new OpenLayers.Control.MousePosition());
	//map.addControl(new OpenLayers.Control.KeyboardDefaults());
	var panZoomContr = new OpenLayers.Control.PanZoomBar();
	panZoomContr.setMap(map);
	map.addControl(panZoomContr);
	map.addControl(new OpenLayers.Control.Attribution());
	var permalinkContr = new OpenLayers.Control.Permalink('permalink');
	permalinkContr.setMap(map);
	map.addControl(permalinkContr);

	// map.setCenter(new OpenLayers.LonLat(lon, lat), 0);
	if (!map.getCenter()) {
		map.zoomToMaxExtent();
	}
	// map.zoomToMaxExtent();

	OpenLayers.Console.log("limitsofoceans request="
			+ limitsoceansLayer.getFullRequestString());
	OpenLayers.Console.log("Sampling Sites request="
			+ pfamLayer.getFullRequestString());
	OpenLayers.Console.log("bathyindex=" + map.getLayerIndex(bathymetryLayer));
	OpenLayers.Console.log("geopfam index="
			+ map.getLayerIndex(pfamLayer));


	// vents.register("resize", this, this.updateSize);
	map.events.register("moveend", map, setLocationbarHash);
	map.events.register("zoomend", map, setLocationbarHash);
	map.events.register("changelayer", map, setLocationbarHash);
	map.events.register("changebaselayer", map, setLocationbarHash);
	c_mode = '';
	getPFAM();
	return true;
}


function setVisible(name, flag) {
	var l = map.getLayersByName(name);
	OpenLayers.Console.log('setting visibility to ' + flag + ' for layer '
			+ name);
	// only get the first hit
	if (l != null) {
		var layer = l[0];
		layer.setVisibility(flag);
		var elem = document.getElementById(name + '_cb');
		if (elem != null) {
			elem.checked = flag;
		}

	} else {
		OpenLayers.Console.log('layer does not exist=' + name);
	}

}

function setSamplingSiteFilter(name, flag) {
	OpenLayers.Console.log('name=' + name + 'flag=' + flag);
	if (name == 'rRNA') {
		if (flag) {
			sam_rrna = name;
		} else {
			sam_rrna = '';
			document.getElementById('rrnas_cb').checked = false;
		}
		// OpenLayers.Console.log('rrna=' + sam_rrna);
	}
	if (name == 'genome') {
		if (flag) {
			sam_genomes = name;
		} else {
			sam_genomes = '';
			document.getElementById('genomes_cb').checked = false;
		}
		// OpenLayers.Console.log('genome=' + sam_genomes);
	}
	if (name == 'metagenome') {

		if (flag) {
			sam_metagenomes = name;
		} else {
			sam_metagenomes = '';
			document.getElementById('metagenomes_cb').checked = false;
		}
		// OpenLayers.Console.log('metagenome=' + sam_metagenomes);
	}

	if (name == 'phage') {

		if (flag) {
			sam_phages = name;
		} else {
			sam_phages = '';
			document.getElementById('phages_cb').checked = false;
		}
		// OpenLayers.Console.log('phage=' + sam_phages);
	}
	reloadSamplingSitesLayer();
}



function selectThematicLayer(layerName) {

	// OpenLayers.Console.log('layer to enable=' + layerName);

	// setting global variable for current layer name
	setCurLayerName(layerName);

	if (cur_lname == 'chlorophyll') {
		document.getElementById('thematic_second_opt').innerHTML = annual_selection;
		document.getElementById('thematic_third_opt').innerHTML = chloro_selection;
	} else if (cur_lname == 'stability') {
		document.getElementById('thematic_second_opt').innerHTML = stability_selection;
		document.getElementById('thematic_third_opt').innerHTML = depth_selection;
	} else {
		document.getElementById('thematic_second_opt').innerHTML = annual_selection;
		document.getElementById('thematic_third_opt').innerHTML = depth_selection;
	}
}

function setSeason(season) {
	cur_season = season;
	// OpenLayers.Console.log('current season=' + cur_season);
}

function setDepth(depth) {
	cur_depth = depth;
	// OpenLayers.Console.log('current depth=' + cur_depth);
}
function setPFAM(accession) {
	if (accession != 'Not found'){
	cur_pfam = accession;
	}else{
	cur_pfam = 'PF00001';	
	}
	// OpenLayers.Console.log('current depth=' + cur_depth);
}
function setYear(year) {
	cur_year = year;
	// OpenLayers.Console.log('current year=' + cur_year);
}

function setCurLayerName(layerName) {
	cur_lname = layerName
}

function cLegend(layerName) {
	var legendId = 'samplingSiteLegendImg';
	var legendId1 = 'samplingSiteLegendImgPFAM';
	if (layerName != 'samplingsites') {
		legendId = 'legendImg';
		legendId1 = 'legendImgPFAM';
	}

	var imgTag = 'hallo';

	var request = OpenLayers.Request.GET({
				url : gms_wms_url,
				async : false,
				params : {
					LAYER : layerName,
					MODE : 'LEGEND'
				}
			});
	var request1 = OpenLayers.Request.GET({
		url : gms_wms_url,
		async : false,
		params : {
			LAYER : 'geopfam',
			MODE : 'LEGEND'
		}
	});
	imgTag1 = request1.responseText;
	imgTag = request.responseText;

	if (layerName != 'geopfam') {
		document.getElementById(legendId1).innerHTML = imgTag1;
		document.getElementById(legendId).innerHTML = imgTag;
	} else {
		//
		document.getElementById(legendId1).innerHTML = imgTag1;
	}
}

function refreshPFAMLayer() {
	OpenLayers.Console.log('refreshing Genes Mapserver;pfam_accession=' + cur_season);
	
	var l = map.getLayersByName('geopfam');
	var cur_layer = l[0];
	map.setBaseLayer(cur_layer);
	// now that everything is prepared and loaded
	if (cur_layer != null) {

		// layer.redraw(true);
		cur_layer.mergeNewParams({
					layers : 'geopfam',
					pfam_accession : cur_pfam,
					isBaseLayer : false,
					bbox : map.getExtent().toBBOX()
				});

//		OpenLayers.Console.log('new request='
//				+ cur_layer.getFullRequestString());

		// map.setLayerIndex(cur_layer, 1);

//		OpenLayers.Console.log("layerindex=" + map.getLayerIndex(cur_layer));

		cur_layer.setVisibility(true);
		map.render('gmsmap');
//		OpenLayers.Console.log('loaded layer=' + lname + ';url='
//				+ cur_layer.getFullRequestString());
		// now creating legend
		//cLegend('geopfam');

	}

}
function refreshThematicLayers() {
	OpenLayers.Console.log('refreshing Genes Mapserver;depth=' + cur_depth
			+ ';l=' + cur_layer + ';yr=' + cur_year + ';season=' + cur_season);

	if (cur_lname == null || cur_lname == "") {
		alert("No layer selected. Please choose a layer and click \"Map it\" again.");
		return;
	}

	if (document.getElementById('stability_layer_selection') != null
			&& document.getElementById('stability_layer_selection').selectedIndex == 0) {
		// Select Stability Layer
		alert("Please chose a parameter for stability map and click \"Map it\" again.");
		return;
	}

	var lname = null;
	var time;
	if (cur_lname != 'chlorophyll') {
		OpenLayers.Console.log('non chloro selected layer=' + cur_lname);
		// TODO seem to be null bug here lname
		lname = cur_lname;
	}

	if (cur_lname == 'chlorophyll' && cur_year != null && cur_season != null) {

		lname = "chlorophyll";

		if (cur_year == '0') {
			cur_year = '1110';
		}

		if (cur_season != '00' && cur_season < '13') {
			lname = lname + '_year';
			time = cur_year + '-' + cur_season;
		}

		if (cur_season >= '13' || cur_season == '00') {
			lname = lname + '_' + cur_season;
			OpenLayers.Console.log('not default SEASON layer=' + lname);
		}

	}

	var l = map.getLayersByName('thematiclayer');
	var cur_layer = l[0];
	map.setBaseLayer(cur_layer);
	// now that everything is prepared and loaded
	if (cur_layer != null) {

		// layer.redraw(true);
		cur_layer.mergeNewParams({
					layers : lname,
					time : cur_year,
					depth : cur_depth,
					season : cur_season,
					isBaseLayer : true,
					bbox : map.getExtent().toBBOX()
				});

//		OpenLayers.Console.log('new request='
//				+ cur_layer.getFullRequestString());

		 map.setLayerIndex(cur_layer, 1);

//		OpenLayers.Console.log("layerindex=" + map.getLayerIndex(cur_layer));

		cur_layer.setVisibility(true);
		setVisible(pfamLayer.name, true);
		//map.render('gmsmap');
//		OpenLayers.Console.log('loaded layer=' + lname + ';url='
//				+ cur_layer.getFullRequestString());
		// now creating legend

		cLegend(lname);
		//cLegendPFAM('geopfam');
	}

}

function resetMap() {
	var h = document.getElementById('permalink');
	if (h != null) {
		//leaving out the hash part in order to really reload the original status
//		window.location.href = h.protocol + h.host + h.pathname + h.search;
		//works because permalink hash is reset not empty
		h.hash = '';
		window.location.hash = null;
//		OpenLayers.Console.log('load=' + window.location);
//		OpenLayers.Console.log('perma=' + h);
		window.location.replace(window.location);
	}
	return true;
}

function getURLParam(strParamName) {

	var strReturn = "";
	var strHref = window.location.href;
	if (strHref.indexOf("?") > -1) {
		var strQueryString = strHref.substr(strHref.indexOf("?")).toLowerCase();
		var aQueryString = strQueryString.split("&");

		for (var iParam = 0; iParam < aQueryString.length; iParam++) {
			if (aQueryString[iParam].indexOf(strParamName.toLowerCase() + "=") > -1) {
				var aParam = aQueryString[iParam].split("=");
				strReturn = aParam[1];
				break;
			}
		}
	}
	return unescape(strReturn);
}
function getPFAM() {
	
	var e = cur_pfam;
	var pfamBase = "http://pfam.sanger.ac.uk/family/";
	if (e != ''){
	var t = '';

        $.ajax({
            dataType: "xml",
            url: gms_host_url + "/ws/v1/PfamProxy/v1.0.0/" + e,
            success: function (i) {
                var n = $(i),
                    o = n.find("description").text(),
                    r = n.find("comment").text(),
                    s = $("<a></a>").attr("href", pfamBase + e).attr("target", "_blank").addClass("titleLink").text(e);
                
                o.length > 0 || r.length > 0 ? ($("#pfam-description").html($("<p></p>").html(s).append(o)).append($("<p></p>").html(r))) : ($("#pfam-description"))
            },
            error: function () {
                $("#pfam-description").text("Error querying web service for XML.")
            }
        })

	}  

}