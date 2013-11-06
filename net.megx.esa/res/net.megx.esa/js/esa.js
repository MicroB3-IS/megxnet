$(document).ready(function () {
    //Atmosphere framework specific variables
    var socket = atmosphere;
    var request = {
        url: ctx.siteUrl + '/topic/notifications/esa',
        contentType: "application/json",
        logLevel: 'debug',
        transport: 'websocket',
        fallbackTransport: 'long-polling',
        enableXDR: true,
        timeout: 60000
    };

    //OpenLayers specific variables
    var map = new CMap({
        el: "samplesMap",
        gmsURL: ctx.siteUrl + "/wms",
        layers: ["satellite", "esa"]
    });

    //Define event handlers
    request.onOpen = function (response) {
        console.log('Atmosphere connected using ' + response.transport);
    };

    request.onMessage = function (response) {
        var message = response.responseBody.split('|')[1];
        var rowsToAdd = [];
        try {
            var json = atmosphere.util.parseJSON(message);
            var table = $(".megx_dataTable");
            for (i = 0; i < json.length; i++) {
                rowsToAdd.push({
                    'id': json[i].id,
                    'label': json[i].label,
                    'taken': json[i].taken,
                    'biome': json[i].biome,
                    'weatherCondition': json[i].weatherCondition,
                    'feature': json[i].feature,
                    'airTemperature': json[i].airTemperature,
                    'lat': json[i].lat,
                    'lon': json[i].lon,
                    'barcode': json[i].barcode,
                    'elevation': json[i].elevation,
                    'collection': json[i].collection,
                    'permit': json[i].permit,
                    'samplingDepth': json[i].samplingDepth,
                    'waterDepth': json[i].waterDepth,
                    'sampleSize': json[i].sampleSize,
                    'waterTemerature': json[i].waterTemerature,
                    'conductivity': json[i].conductivity,
                    'windSpeed': json[i].windSpeed,
                    'salinity': json[i].salinity,
                    'comment': json[i].comment,
                    'accuracy': json[i].accuracy,
                    'phosphate': json[i].phosphate,
                    'nitrate': json[i].nitrate,
                    'nitrite': json[i].nitrite,
                    'ph': json[i].ph,
                    'id': json[i].id
                });
            }

            //Add the newly created rows to the table
            table.dataTable().fnAddData(rowsToAdd);

            //Redraw the map
            if (map.redraw('esa')) {
                console.log('Map was redrawn');
            } else {
                console.log('Map was not redrawn');
            }
        } catch (e) {
            console.log('This does not look like a valid JSON: ', message.data);
            return;
        }

        console.log('Server pushed down this data:', json);

    };

    request.onError = function (response) {
        console.log('Sorry, but there is some problem with your socket or the server is down');
    };

    //Subscribe to AtmosphereServlet
    var subSocket = socket.subscribe(request);

    //Draw map of samples
    map.hideAll();

    map.showLayer("satellite");
    map.showLayer("esa");

    //TODO: This is a hack. 
    //A layer ordering functionality has to be implemented in CMapLayers so that the esa layer is always on top of the satellite layer 
    var esa = map.map.getLayersByName('esa')[0];
    map.map.setLayerIndex(esa, 100);
});