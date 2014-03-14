$(document).ready(function () {
	if (currentMetagenomeId) {
		$.getJSON(ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/mg" + currentMetagenomeId + "-j/taxonomic-content", function (taxonomyResponse) {
			var data = [];
			if (taxonomyResponse) {
				$.each(taxonomyResponse.data, function (i, currTaxonomy) {
					currTaxonomy.value = parseInt(currTaxonomy.value);
					data.push(currTaxonomy);
				});
				data = data.sort(function (t1, t2) {
						return t1.value - t2.value;
					}).reverse().slice(0, 10);
			}
			$('#taxonomyContainer').highcharts({
				chart : {
					type : 'column'
				},
				plotOptions : {
					series : {
						pointWidth : 20
					}
				},
				title : {
					text : 'Taxonomic Content'
				},
				xAxis : {
					categories : data.length === 10 ?
					[data[0].key, data[1].key, data[2].key,
						data[3].key, data[4].key, data[5].key,
						data[6].key, data[7].key, data[8].key,
						data[9].key] : []
				},
				yAxis : {
					labels : {
						overflow : 'justify'
					}
				},
				credits : {
					enabled : false
				},
				series : [{
						name : 'Bar plot of the taxonomic content',
						data : data.length === 10 ?
						[data[0].value, data[1].value, data[2].value,
							data[3].value, data[4].value, data[5].value,
							data[6].value, data[7].value, data[8].value,
							data[9].value] : []
				}],
				lang : {
					noData : 'No data aviable for Taxonomic Content'
				},
				noData : {
					style : {
						fontWeight : 'bold',
						fontSize : '15px',
						color : '#303030'
					}
				}
			});
		});
	}
});
