$(document).ready(function () {
	if(currentMetagenomeId){
	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/mg"+ currentMetagenomeId + "-j/amino-acid-content", function( aminoAcidResponse ) {
		var data = [];
		if(aminoAcidResponse){
			 data = [ 
					 aminoAcidResponse.data.ala,aminoAcidResponse.data.cys,
					 aminoAcidResponse.data.asp,aminoAcidResponse.data.glu,
					 aminoAcidResponse.data.phe,aminoAcidResponse.data.gly,
					 aminoAcidResponse.data.his,aminoAcidResponse.data.ile,
					 aminoAcidResponse.data.lys,aminoAcidResponse.data.leu,
					 aminoAcidResponse.data.met,aminoAcidResponse.data.asn,
					 aminoAcidResponse.data.pro,aminoAcidResponse.data.gln,
					 aminoAcidResponse.data.arg,aminoAcidResponse.data.ser,
					 aminoAcidResponse.data.thr,aminoAcidResponse.data.val,
					 aminoAcidResponse.data.trp,aminoAcidResponse.data.tyr
			 		];
		}
        $('#aminoAcidContainer').highcharts({
            chart: {
                type: 'column'
            },
            plotOptions: {
    	        series: {
    	            pointWidth: 20
    	        }
    	    },
            title: {
                text: 'Aminoacid Trait'
            },
            xAxis: {
                categories: ['ala', 'cys', 'asp', 'glu', 'phe','gly', 'his', 'ile', 'lys', 'leu','met', 'asn', 'pro', 'gln', 'arg','ser', 'thr', 'val', 'trp', 'tyr']
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
                name: 'Bar plot of the aminoacid traits',
                data: data
            }],
            lang: {
            	noData : 'No data aviable for Aminoacid Trait'
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