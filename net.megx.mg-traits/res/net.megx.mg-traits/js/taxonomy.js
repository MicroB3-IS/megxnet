$(document).ready(function () {
	if(currentMetagenomeId){
	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/taxonomic-content", function( taxonomyResponse ) {
		var taxonomyData = taxonomyResponse.data;
		var taxonomyArr = [];
		$.each(taxonomyData, function(i, currTaxonomy){
			if(currTaxonomy.id === currentMetagenomeId){
				currTaxonomy.value = parseInt(currTaxonomy.value);
				taxonomyArr.push(currTaxonomy);
			}			
		});
		taxonomyArr = taxonomyArr.sort(function(t1,t2){
			return t1.value - t2.value;			
		}).reverse().slice(0,10);
		$('#taxonomyContainer').highcharts({
            chart: {
                type: 'bar',
                inverted : true	
            },
            title: {
                text: 'Taxonomic Content'
            },
            xAxis: {
                categories: taxonomyArr.length === 10 ? 
                			[taxonomyArr[0].key,taxonomyArr[1].key,taxonomyArr[2].key,
                             taxonomyArr[3].key,taxonomyArr[4].key,taxonomyArr[5].key,
                             taxonomyArr[6].key,taxonomyArr[7].key,taxonomyArr[8].key,
                             taxonomyArr[9].key
                            ] : [] ,
                title: {
                    text: null
                }
            },
            yAxis: {
                min: 0,
                labels: {
                    overflow: 'justify'
                }
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            credits: {
                enabled: false
            },
            series: [{
                name: 'Bar plot of the taxonomic content',
                data: taxonomyArr.length === 10 ?
                	[taxonomyArr[0].value,taxonomyArr[1].value,taxonomyArr[2].value,
                       taxonomyArr[3].value,taxonomyArr[4].value,taxonomyArr[5].value,
                       taxonomyArr[6].value,taxonomyArr[7].value,taxonomyArr[8].value,
                       taxonomyArr[9].value
                      ] : []
            }],
            lang: {
            	noData : 'No data aviable for Taxonomic Content'
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