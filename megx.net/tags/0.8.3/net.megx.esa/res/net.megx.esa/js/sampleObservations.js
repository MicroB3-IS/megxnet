$(document).ready(function(){
	var NB_OBSERVATIONS = 10;
	var OBSERVATIONS_URL = ctx.siteUrl + '/ws/v1/esa/v1.0.0/observations/';
	var SAMPLE_THUMBNAILS_URL = ctx.siteUrl + '/ws/v1/esa/v1.0.0/content/photo/thumbnail/';
	var NO_IMAGE_ICON_URL = ctx.siteUrl + '/net.megx.esa/img/no_photo.png';
	
	var ajaxCall = function (httpVerb, url, data, successHandler, errorHandler) {
        $.ajax({
            type: httpVerb,
            url: url,
            data: data,
            success: function (response) {
                if (successHandler) {
                    successHandler.call(this, response.data);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                if (errorHandler) {
                    errorHandler.call(this, response, jqXHR, textStatus, errorThrown);
                }
            }
        });
    };
	
    handleError = function(img){
		console.log('error event called!!!');
		$(img).attr('src', NO_IMAGE_ICON_URL);
	};
    
    var renderObservation = function(observation){
    	observation = observation || {};
    	var imgSrc = observation.thumbnailId ? SAMPLE_THUMBNAILS_URL + observation.thumbnailId : NO_IMAGE_ICON_URL;
    	var viewUrl = ctx.siteUrl + '/osd-app/sampleDetails?sampleId=' + observation.id;
    	var htmlToRender = ['<div id="recentObservation">',
    	                    '<div id="recentObservationImage">',
    	                    '<img src="', imgSrc ,'" + style="width: 80px; padding-left: 3px; padding-top: 7px; border-radius: 4px;" onError="handleError(this);" />',
    	                    '</div>',
    	                    '<div id="recentObservationDescription">',
    	                    '<div id="recentObservationDetails"><strong>Observer:</strong> ', observation.observer, '</div>',
    	                    '<div id="recentObservationDetails"><strong>Sample Label:</strong> ', observation.sampleName ,'</div>',
    	                    '<div id="recentObservationDetails"><strong>Ocean:</strong> ' , observation.geoRegion ,'</div>',
    	                    '<div id="recentObservationDetails"><strong>Date:</strong> ', observation.taken, '</div>',
    	                    '<div id="recentObservationDetails"><a href=', viewUrl ,' class=\'viewSampleClass\'>View more</a></div>',
    	                    
    	                    '</div>',
    	                    '</div>'
    	                    ].join('');
    	
    	$('#recentObservations').append(htmlToRender);
    	
    };
    
	var getLatestObservations = function () {
	    ajaxCall('GET', OBSERVATIONS_URL + NB_OBSERVATIONS, null, function (response) {
	        $.each(response, function(i, observation){
	        	renderObservation(observation);
	        });
	    }, function (jqXHR, textStatus, errorThrown) {
	        console.log('Error occured while retrieving latest observations: ', errorThrown);
	    });
	}();
});