$(document).ready(function(){
	 	
	$.getJSON ( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/traits-summary", function(traitPCA){
		
		var allEnv = {};
		
		if(traitPCA){
			$.each(traitPCA.data, function(i, curr){	
				if(curr.trait === 'di-nucleotide-odds-ratio'){
					if(allEnv[curr.sampleEnvironment]){
						allEnv[curr.sampleEnvironment].data.push({
							'x' : curr.x,
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
									'x' : curr.x,
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
	                resetZoomButton: {
	                    position: {
	                        x: 0,
	                        y: -30
	                    }
	                }
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
	                width: 200,
	                itemWidth: 90,
	                y: 100,
	                x: 0
	            },
	            credits: {
	                enabled: false
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