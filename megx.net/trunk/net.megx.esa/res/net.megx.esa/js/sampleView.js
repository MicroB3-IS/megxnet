$(document).ready(function(){
	var SAMPLE_DETAILS_URL = ctx.siteUrl + '/ws/v1/esa/v1.0.0/sample?sampleId=';
	var SAMPLE_THUMBNAILS_URL = ctx.siteUrl + '/ws/v1/esa/v1.0.0/content/photo/thumbnail/';
	var SAMPLE_ORIGINAL_PHOTOS_URL = ctx.siteUrl + '/ws/v1/esa/v1.0.0/content/photo/original/';
	var NO_IMAGE_ICON_URL = ctx.siteUrl + '/net.megx.esa/img/no_photo.png';
	
	var map = new Biojs.MegxMapWidget({
		target : 'megxMapWidgetDet',
		layerSet : 'osd-app-smp-details',
		gmsBaseURL : ctx.siteUrl + '/wms',
		log : false
	});
	
//	map._removeControl(OpenLayers.Control.KeyboardDefaults.prototype.CLASS_NAME);
	
	var ajaxCall = function(httpVerb, url, data, successHandler, errorHandler){
		$.ajax({
			  type: httpVerb,
			  url: url,
			  data: data,
			  contentType : "application/json",
			  success: function(response){
				  if(successHandler){
					  successHandler.call(this, response.data);  
				  }
			  },
			  error: function(jqXHR, textStatus, errorThrown ){
				  if(errorHandler){
					  errorHandler.call(this, response, jqXHR, textStatus, errorThrown );  
				  }
			  }
		});
	};
	
	var populateSampleTable = function(data){
		data = data || {};
		$('td.observer').html(renderText(data.collectorId));
		$('td.date').html(renderText(data.taken));
		$('td.biome').html(renderText(data.biome));
		$('td.weatherCondition').html(renderText(data.weatherCondition));
		$('td.airTemperature').html(renderText(data.airTemperature, '&deg;C'));
		$('td.waterTemperature').html(renderText(data.waterTemperature, '&deg;C'));
		$('td.windSpeed').html(renderText(data.windSpeed, 'km/h'));
		$('td.longitude').html(renderText(data.lon));
		$('td.latitude').html(renderText(data.lat));
		$('td.accuracy').html(renderText(data.accuracy, 'm'));
		$('td.samplingDepth').html(renderText(data.samplingDepth, 'm'));
		$('td.sampleName').html(renderText(data.label));
		$('td.salinity').html(renderText(data.salinity));
		$('td.phosphate').html(renderText(data.phosphate, 'mg/l'));
		$('td.nitrate').html(renderText(data.nitrate, 'mg/l'));
		$('td.nitrite').html(renderText(data.nitrite, 'mg/l'));
		$('td.pH').html(renderText(data.ph));
		$('td.secchiDepth').html(renderText(data.secchiDepth));
		$('td.comment').html(renderText(data.comment));
		$('td.project').html(renderText(data.projectId));
		$('td.shipName').html(renderText(data.shipName));
		$('td.boatManufacturer').html(renderText(data.boatManufacturer));
		$('td.model').html(renderText(data.boatModel));
		$('td.length').html(renderText(data.boatLength, 'm'));
		$('td.homeport').html(renderText(data.homeport));
		$('td.nationality').html(renderText(data.nationality));
		$('input.sid').val(data.id);
	};
	
	var renderText = function(text, measurementSymbol){
		text = text || '';
		measurementSymbol = measurementSymbol || '';
		return text !== '' ? text + '&nbsp;' + measurementSymbol : '/';
	}
	
	handleError = function(img){
		console.log('error event called!!!');
		$(img).attr('src', NO_IMAGE_ICON_URL);
	};
	
	var populateSampleImages = function(data){
		data = data || {};
		if(data.photos.length > 0){
			$.each(data.photos, function(index, photo){
				var anchor = '<a class="gallery" data-lightbox="example-set" href="' + SAMPLE_ORIGINAL_PHOTOS_URL + photo.uuid + '">';
				anchor+= '<img src="' + SAMPLE_THUMBNAILS_URL + photo.uuid + '" class="sampleImage" onError="handleError(this);" /></a>';
				$('div.sampleImages').append(anchor);
			});
		} else{
			var noImageIcon = '<img src="' + NO_IMAGE_ICON_URL + '" class="noImageClass" />';
			$('div.sampleImages').html(noImageIcon);
		}
	};
	
	var populateMap = function(data){
		var markers = new OpenLayers.Layer.Markers( "Markers" );
	    map._addLayer(markers);

	    var size = new OpenLayers.Size(21,25);
	    var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
	    var icon = new OpenLayers.Icon('http://www.openlayers.org/dev/img/marker.png',size,offset);
	    var marker = new OpenLayers.Marker(new OpenLayers.LonLat(data.lon, data.lat),icon);
	    markers.addMarker(marker);
	};
	
	var populatePage = function(data){
		data = data || {};
		populateSampleImages(data);
		populateSampleTable(data);
		populateMap(data);
	};
	
	var loadSampleData = function(sampleId){
		sampleId = sampleId || '';
		ajaxCall('GET', SAMPLE_DETAILS_URL + sampleId , sampleId, populatePage);
	};
	
	loadSampleData(sampleId);
	
	$('a.downloadSample').click(function(){
		var sid = $('input.sid').val() || '';
		if(sid !== ''){
			$('input.sampleIdsHolder').val(sid);
			$('#sf').submit();
		}
		return false;
	});
});