$(document).ready(function () {
	if(currentMetagenomeId){
		$.getJSON( ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/mg"+ currentMetagenomeId + "-j/codon-usage", function(codonUsageResponse) {
			var BAR_WIDTH = 10;
			var data = [];
			if(codonUsageResponse){
				data= codonUsageResponse.data;
			}
			$('#codonUsageContainer').highcharts({
				chart: {
					type: 'column'
				},
				plotOptions: {
					series: {
						pointWidth: BAR_WIDTH,
						pointPadding: 5
					}
				},
				title: {
					text: 'Codon Trait'
				},
				xAxis: {
					categories: ['gca', 'gcc', 'gcg', 'gct', 'tgc', 'tgt', 'gac',
								 'gat', 'gaa', 'gag', 'ttc', 'ttt', 'gga', 'ggc',
								 'ggg', 'ggt', 'cac', 'cat', 'ata', 'atc', 'att',
								 'aaa', 'aag', 'cta', 'ctc', 'ctg', 'ctt', 'tta', 'ttg', 'atg', 'aac',
								 'aat', 'cca', 'ccc', 'ccg', 'cct', 'caa',
								 'cag', 'aga', 'agg', 'cga', 'cgc', 'cgg',
								 'cgt', 'agc', 'agt', 'tca', 'tcc', 'tcg',
								 'tct', 'aca', 'acc', 'acg', 'act', 'gta',
								 'gtc', 'gtg', 'gtt', 'tgg', 'tac', 'tat'
							 ]
				},
				yAxis: {
					labels: {
						overflow: 'justify'
					}
				},
				credits: {
					enabled: false
				},
				scrollbar: {
			        enabled: true
			    },
				series: [{
					name: 'Bar plot of the codon traits',
					data: [data.gca,data.gcc,data.gcg,data.gct,data.tgc,data.tgt,data.gac,
						   data.gat,data.gaa,data.gag,data.ttc,data.ttt,data.gga,
						   data.ggc,data.ggg,data.ggt,data.cac,data.cat,data.ata,
						   data.atc,data.att,data.aaa,data.aag,data.cta,data.ctc,
						   data.ctg,data.ctt,data.tta,data.ttg,data.atg,data.aac,
						   data.aat,data.cca,data.ccc,data.ccg,data.cct,data.caa,
						   data.cag,data.aga,data.agg,data.cga,data.cgc,data.cgg,
						   data.cgt,data.agc,data.agt,data.tca,data.tcc,data.tcg,
						   data.tct,data.aca,data.acc,data.acg,data.act,data.gta,
						   data.gtc,data.gtg,data.gtt,data.tgg,data.tac,data.tat
						  ]
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