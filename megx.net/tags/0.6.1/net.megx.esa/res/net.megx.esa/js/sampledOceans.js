$(document).ready(function(){
	var SAMPLED_OCEANS_URL = ctx.siteUrl + '/ws/v1/esa/v1.0.0/sampledOceans';
	
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
	
    var renderSampledOcean = function(sampledOcean){
    	sampledOcean = sampledOcean || {};
    	var htmlToRender = ['<tr>',
    	                    	'<td class="sampledOcean">', sampledOcean.location, '&nbsp;&nbsp;', sampledOcean.nbSamples,'</td>',
	                    	'</tr>'
    	                    ].join('');
    	
    	$('#sampledOceans').append(htmlToRender);
    };
    
	var getLatestObservations = function () {
	    ajaxCall('GET', SAMPLED_OCEANS_URL, null, function (response) {
	        for(var i = 0; i < response.length; i++){
	        	renderSampledOcean(response[i]);
	        }
	    }, function (jqXHR, textStatus, errorThrown) {
	        console.log('Error occured while retrieving data about oceans sampled so far: ', errorThrown);
	    });
	}();
});