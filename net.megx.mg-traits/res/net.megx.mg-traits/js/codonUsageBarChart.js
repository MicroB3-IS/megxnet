$(document).ready(function () {
	if(currentMetagenomeId){
	$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/codon-usage", function( codonUsageResponse ) {
		
		var codonData = codonUsageResponse.data;
		var hasMatch = false;
		var codonObjToPlot = {};
		$.each(codonData, function(i, currCodon){
			if(currCodon.id === currentMetagenomeId){
				hasMatch = true;
				codonObjToPlot = currCodon;
			}			
		});
        $('#codonUsageContainer').highcharts({
            chart: {
                type: 'bar',
                inverted : true	
            },
            title: {
                text: 'Codon Trait'
            },
            xAxis: {
                categories: ['gcc','gcg','gct','tgc','tgt','gac',
                             'gat','gaa','gag','ttc','ttt','gga',
                             'ggc','ggg','ggt','cac','cat','ata', 
                             'atc','att','aaa','aag','cta','ctc',
                             'ctg','ctt','tta','ttg','atg','aac',
                             'aat','cca','ccc','ccg','cct','caa',
                             'cag','aga','agg','cga','cgc','cgg',
                             'cgt','agc','agt','tca','tcc','tcg',
                             'tct','aca','acc','acg','act','gta',
                             'gtc','gtg','gtt','tgg','tac','tat'
                             ],
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
                name: 'Bar plot of the codon traits',
                data: hasMatch ? 
                	  [codonObjToPlot.gcc,codonObjToPlot.gcg,codonObjToPlot.gct,codonObjToPlot.tgc,codonObjToPlot.tgt,codonObjToPlot.gac,
                       codonObjToPlot.gat,codonObjToPlot.gaa,codonObjToPlot.gag,codonObjToPlot.ttc,codonObjToPlot.ttt,codonObjToPlot.gga,
                       codonObjToPlot.ggc,codonObjToPlot.ggg,codonObjToPlot.ggt,codonObjToPlot.cac,codonObjToPlot.cat,codonObjToPlot.ata,
                       codonObjToPlot.atc,codonObjToPlot.att,codonObjToPlot.aaa,codonObjToPlot.aag,codonObjToPlot.cta,codonObjToPlot.ctc,
                       codonObjToPlot.ctg,codonObjToPlot.ctt,codonObjToPlot.tta,codonObjToPlot.ttg,codonObjToPlot.atg,codonObjToPlot.aac,
                       codonObjToPlot.aat,codonObjToPlot.cca,codonObjToPlot.ccc,codonObjToPlot.ccg,codonObjToPlot.cct,codonObjToPlot.caa,
                       codonObjToPlot.cag,codonObjToPlot.aga,codonObjToPlot.agg,codonObjToPlot.cga,codonObjToPlot.cgc,codonObjToPlot.cgg,
                       codonObjToPlot.cgt,codonObjToPlot.agc,codonObjToPlot.agt,codonObjToPlot.tca,codonObjToPlot.tcc,codonObjToPlot.tcg,
                       codonObjToPlot.tct,codonObjToPlot.aca,codonObjToPlot.acc,codonObjToPlot.acg,codonObjToPlot.act,codonObjToPlot.gta,
                       codonObjToPlot.gtc,codonObjToPlot.gtg,codonObjToPlot.gtt,codonObjToPlot.tgg,codonObjToPlot.tac,codonObjToPlot.tat
                      ] : []
            }],
            lang: {
            	noData : 'No data aviable for Codon Trait'
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