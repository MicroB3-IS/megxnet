$(document).ready(function(){
	var socket = $.atmosphere;
	var request = {
		url : 'http://localhost:8080/megx.net/topic/notifications',
		contentType : "application/json",
		logLevel : 'debug',
		transport : 'websocket',
		fallbackTransport : 'long-polling',
		enableXDR: true,
        timeout : 60000
	};

	request.onOpen = function(response) {
		console.log('Atmosphere connected using ' + response.transport);
	};

	request.onMessage = function(response) {
		var message = response.responseBody;
		//console.log('response.responseBody = ', JSON.stringify(response.responseBody));
		try {
			var json = JSON.parse(message);
		} catch (e) {
			console.log('This does not look like a valid JSON: ', message.data);
			return;
		}
		
		console.log('Server pushed down this data:', json);
		
	};

	request.onError = function(response) {
		console.log('Sorry, but there is some problem with your socket or the server is down');
	};

	var subSocket = socket.subscribe(request);
});