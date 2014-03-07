$(document).ready(function () {
	if(currentMetagenomeId){
	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/mg"+ currentMetagenomeId +"-j/di-nucleotide-odds-ratio", function( diNucleotideResponse ) {
		var data = [];
		if(diNucleotideResponse){
			 data = [ 
						diNucleotideResponse.data.paa_ptt,
						diNucleotideResponse.data.pac_pgt,
						diNucleotideResponse.data.pcc_pgg,
						diNucleotideResponse.data.pca_ptg,
						diNucleotideResponse.data.pga_ptc,
						diNucleotideResponse.data.pag_pct,
						diNucleotideResponse.data.pat,
						diNucleotideResponse.data.pcg,
						diNucleotideResponse.data.pgc,
						diNucleotideResponse.data.pta
			 		];
		}
        $('#diNucleotideContainer').highcharts({
            chart: {
                type: 'bar',
                inverted : true
            },
            title: {
                text: 'Dinuc Trait'
            },
            xAxis: {
                categories: ['paa_ptt', 'pac_pgt', 'pcc_pgg', 'pca_ptg', 'pga_ptc','pag_pct', 'pat', 'pcg', 'pgc', 'pta'],
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
                name: 'Bar plot of the dinuc traits',
                data: data
            }],
            lang: {
            	noData : 'No data aviable for Dinuc Trait'
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