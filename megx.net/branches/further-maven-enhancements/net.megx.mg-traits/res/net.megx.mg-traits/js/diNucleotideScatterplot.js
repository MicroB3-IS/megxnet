$(document).ready(function(){
	 	
	$.getJSON ( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/traits-summary", function(traitPCA){
		
		var allEnv = {};
		
		if(traitPCA){
			$.each(traitPCA.data, function(i, curr){	
				if(curr.trait === 'di-nucleotide-odds-ratio'){
					if(allEnv[curr.sampleEnvironment]){
						allEnv[curr.sampleEnvironment].data.push({
							'x' : parseFloat(curr.x).toFixed(2),
							'y' : curr.y,
							'id' : curr.id
						});
						
					} else{
						allEnv[curr.sampleEnvironment] = {
								'name': curr.sampleEnvironment,
								'color' : '#'+Math.floor(Math.random()*16777215).toString(16),
								'marker': {
				                    'symbol': 'circle'
				                },
								'data' : [{
									'x' : parseFloat(curr.x).toFixed(2),
									'y' : curr.y,
									'id' : curr.id
								}]
						};
					}
				}
			});	
			
		}
		
		var getEnvData = function(){
			var all = [];
			for(var env in allEnv){
				all.push(allEnv[env]);
			}
			
			return all;
		}
		
			$('#traitsScatterplotDiNucleotide').highcharts({
				chart: {
	                type: 'scatter',
	                zoomType: 'xy',
	                width: 950,
	                marginLeft: 50
	                
	            },
	            title: {
	                text: 'Di Nucleotide Odds Ratio'
	            },
	            legend: {
	            	title : {
	            		text : '<b>Environment</b>'
	            	},
	            	
	                layout: 'vertical',
	                align: 'right',
	                verticalAlign:'top',
	                width: 150,
	                itemWidth: 90,
	                y:30,
	                float: true,
	                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
	                borderWidth: 1
	            },
	            credits: {
	                enabled: false
	            },
	            xAxis: {
	                title: {
	                    enabled: false,
	                    text: ''
	                },
	                startOnTick: true,
	                endOnTick: true,
	                showLastLabel: true,
	                tickPositioner: function () {
	                    var maxDeviation = Math.max(Math.abs(this.dataMax), Math.abs(this.dataMin));
	                    var halfMaxDeviation = maxDeviation / 2;
	                    return [parseFloat(-maxDeviation).toFixed(2), parseFloat(-halfMaxDeviation).toFixed(2), 0, parseFloat(halfMaxDeviation).toFixed(2), parseFloat(maxDeviation).toFixed(2)];
	                },
	                gridLineWidth: 0,
	                lineWidth: 1,
	                crossing: 0,
	                opposite: true
	            },
	            yAxis: {
	                title: {
	                    enabled: false,
	                    text: ''
	                },
	                startOnTick: true,
	                endOnTick: true,
	                tickPositioner: function () {
	                    var maxDeviation = Math.max(Math.abs(this.dataMax), Math.abs(this.dataMin));
	                    var halfMaxDeviation = maxDeviation / 2;
	                    return [parseFloat(-maxDeviation).toFixed(2), parseFloat(-halfMaxDeviation).toFixed(2), 0, parseFloat(halfMaxDeviation).toFixed(2), parseFloat(maxDeviation).toFixed(2)];
	                },
	                gridLineWidth: 0,
	                lineWidth: 1,
	                crossing: 0
	            },
	            plotOptions: {
	                series: {
	                    cursor: 'pointer',
	                    turboThreshold: 0,
	                    point: {
	                        events: {
	                        	click: function(event) {
	                        		var viewUrl = ctx.siteUrl + '/mg-traits/sampleDetails?id=' + this.id;	                    			
	                        		window.open( viewUrl, '_parent' );
	                            }
	                        }
	                    }
	                }
	            },
	            tooltip: {
	            	  valueDecimals: 2
	            },
	            series: getEnvData(),	           
	            lang: {
	                noData: 'No data aviable'
	            },
	            noData :{
	            	style: {
	            		fontWeight: 'bold',
	            		fontSize: '15px',
		                color: '#303030'
	            	}
	            }
	        });
	});
});