$(document).ready(function () {
	if(currentMetagenomeId){
	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/mg" + currentMetagenomeId + "-j/function-table", function( functionTableResponse ) {
		var data = [];
		if(functionTableResponse){
			$.each(functionTableResponse.data, function (i, currFunction) {
				currFunction.value = parseInt(currFunction.value);
				data.push(currFunction);
			});
			data = data.sort(function (t1, t2) {
					return t1.value - t2.value;
				}).reverse().slice(0, 10);
		}
        $('#functionalContainer').highcharts({
            chart: {
                type: 'column'
            },
            plotOptions: {
    	        series: {
    	            pointWidth: 20
    	        }
    	    },
            title: {
                text: 'Functional Content'
            },
            xAxis : {
				categories : data.length === 10 ?
				[data[0].key, data[1].key, data[2].key,
					data[3].key, data[4].key, data[5].key,
					data[6].key, data[7].key, data[8].key,
					data[9].key] : []
			},
            yAxis: {
                labels: {
                    overflow: 'justify'
                }
            },
            credits: {
                enabled: false
            },
            series: [{
                name: 'Bar plot of the functional content',
                data : data.length === 10 ?
						[data[0].value, data[1].value, data[2].value,
							data[3].value, data[4].value, data[5].value,
							data[6].value, data[7].value, data[8].value,
							data[9].value] : []		        
            }],
            lang: {
            	noData : 'No data aviable for Functional Content'
            },
            noData: {
                style: {    
                    fontWeight: 'bold',     
                    fontSize: '15px',
                    color: '#303030'        
                }
            }
        });
	});
	}
 });