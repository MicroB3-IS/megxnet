$(document).ready(function () {
 
	$('#btnNow').click(function(){
		$('#surveyInfo').hide();
		var surveyUrl = ctx.siteUrl + '/osd-app/survey';
		window.open(surveyUrl, '_newtab');
	});
	
	$('#btnLater').click(function(){
		$('#surveyInfo').hide();
	});
	
});