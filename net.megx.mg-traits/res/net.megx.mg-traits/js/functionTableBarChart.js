$(document).ready(function () {
	if(currentMetagenomeId){
	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/mg" + currentMetagenomeId + "-j/function-table", function( functionTableResponse ) {
		var data = [];
		if(functionTableResponse){
		 data = functionTableResponse.data.pfam.sort(function(a,b){
				return a - b;
			}).reverse().splice(0,10);
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
                data: data		        
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