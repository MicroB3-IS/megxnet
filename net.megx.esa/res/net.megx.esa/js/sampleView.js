$(document).ready(function(){
	var SAMPLE_DETAILS_URL = ctx.siteUrl + '/ws/esa/sample?sampleId=';
	var SAMPLE_THUMBNAILS_URL = ctx.siteUrl + '/ws/esa/content/photo/thumbnail/';
	var SAMPLE_ORIGINAL_PHOTOS_URL = ctx.siteUrl + '/ws/esa/content/photo/original/';
	var NO_IMAGE_ICON_URL = ctx.siteUrl + '/net.megx.esa/img/no_photo.png';
	
	var map = new CMap({
		el: "sampleMap",
		gmsURL: ctx.siteUrl + "/wms",
		layers: ["satellite", "esa"]
	});
	
	//Draw map of samples
	map.hideAll();
	
	map.showLayer("satellite");
	
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
		$('td.observer').text(renderText(data.collectorId));
		$('td.date').text(renderText(data.taken));
		$('td.biome').text(renderText(data.biome));
		$('td.weatherCondition').text(renderText(data.weatherCondition));
		$('td.airTemperature').text(renderText(data.airTemperature));
		$('td.waterTemperature').text(renderText(data.waterTemperature));
		$('td.windSpeed').text(renderText(data.windSpeed));
		$('td.longitude').text(renderText(data.lon));
		$('td.latitude').text(renderText(data.lat));
		$('td.accuracy').text(renderText(data.accuracy));
		$('td.samplingDepth').text(renderText(data.samplingDepth));
		$('td.sampleName').text(renderText(data.label));
		$('td.salinity').text(renderText(data.salinity));
		$('td.phosphate').text(renderText(data.phosphate));
		$('td.nitrate').text(renderText(data.nitrate));
		$('td.nitrite').text(renderText(data.nitrite));
		$('td.pH').text(renderText(data.ph));
		$('td.secchiDepth').text(renderText(data.secchiDepth));
		$('td.comment').text(renderText(data.comment));
		$('input.sid').val(data.id);
	};
	
	var renderText = function(text){
		text = text || '';
		return text !== '' ? text : '/';
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
				$('td.sampleImages').append(anchor);
			});
		} else{
			var noImageIcon = '<img src="' + NO_IMAGE_ICON_URL + '" class="noImageClass" />';
			$('td.sampleImages').html(noImageIcon);
		}
	};
	
	var populateMap = function(data){
		var markers = new OpenLayers.Layer.Markers( "Markers" );
	    map.addLayer(markers);

	    var size = new OpenLayers.Size(21,25);
	    var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
	    var icon = new OpenLayers.Icon('http://www.openlayers.org/dev/img/marker.png',size,offset);
	    markers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(data.lon, data.lat),icon));
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