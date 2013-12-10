var current = 1;
var allSamples = 1;
var currentAuthor = 1;
var allAuthors = 1;

$(document).ready(function() {
	$("#next_button").hide();
	$("#back_button").hide();
	$("#next_button_author").hide();
	$("#back_button_author").hide();
	setDateTime("");
});

function setDateTime(id) {
	var currentdate = new Date();
	$("#time" + id).val(
			currentdate.getHours() + ":" + currentdate.getMinutes() + ":"
					+ currentdate.getSeconds());
	$("#date" + id).val(
			(currentdate.getMonth() + 1) + "/" + currentdate.getDate() + "/"
					+ currentdate.getFullYear());
	$("#date" + id).datepicker();
}

function getSampleHtml(allSamples) {
	var source = $("#sample-template").html();
	var template = Handlebars.compile(source);

	var context = {
		allSamples : allSamples
	}
	var html = template(context);

	return html;
}

function getAuthorHtml(allAuthors) {
	var source = $("#author-template").html();
	var template = Handlebars.compile(source);

	var context = {
		allAuthors : allAuthors
	}
	var html = template(context);

	return html;
}

function getAllAuthors() {
	var forename = $('[id^="forename"]');
	var initials = $('[id^="initials"]');
	var surename = $('[id^="surename"]');
	var position = $('[id^="position"]');
	var objArr = [];

	for (i = 0; i < forename.length; i++) {
		var obj = {
			forename : forename[i].value,
			initials : initials[i].value,
			surename : surename[i].value,
			position : position[i].value
		}
		objArr.push(obj);
	}
	return objArr;
}

function getAllSamples() {
	var label = $('[id^="label"]');
	var region = $('[id^="region"]');
	var longitude = $('[id^="longitude"]');
	var latitude = $('[id^="latitude"]');
	var material = $('[id^="material"]');
	var depth = $('[id^="depth"]');
	var date = $('[id^="date"]');
	var time = $('[id^="time"]');

	var objArr = [];

	for (i = 0; i < region.length; i++) {
		var temp = date[i].value.split("-");
		var tempTime = time[i].value.split(":");
		var obj = {
			label : label[i].value,
			region : region[i].value,
			longitude : longitude[i].value,
			latitude : latitude[i].value,
			material : material[i].value,
			depth : depth[i].value,
			// depthunit : $('#doi').val(),
			samyear : temp[0],
			sammonth : temp[1],
			samday : temp[2],
			samhour : tempTime[0],
			sammin : tempTime[1],
			samsec : tempTime[2]
		}
		objArr.push(obj);
	}
	return objArr;
}

function add(id) {
	if (id == "sample") {
		$("#sample" + current).hide();
		allSamples++;
		current = allSamples;
		$("#samples").append(getSampleHtml(allSamples));
		$("#next_button").hide();
		if (!($("#back_button").is(":visible"))) {
			$("#back_button").show();
		}
		$("#currentSample").html("current sample :" + current);
		setDateTime(current);
	} else {
		$("#author" + currentAuthor).hide();
		allAuthors++;
		currentAuthor = allAuthors;
		$("#authors").append(getAuthorHtml(allAuthors));
		$("#next_button_author").hide();
		if (!($("#back_button_author").is(":visible"))) {
			$("#back_button_author").show();
		}
		$("#currentAuthor").html("Current author :" + currentAuthor);
		$("#position" + currentAuthor).val(currentAuthor);
	}
}

function previous(id) {
	if (id == "sample") {
		$("#sample" + current).hide();
		current--;
		$("#sample" + (current)).show();

		if (current < allSamples) {
			$("#next_button").show();
		}
		if (current == 1) {
			$("#back_button").hide();
		}
		$("#currentSample").html("Current sample :" + current);
	} else {
		$("#author" + currentAuthor).hide();
		currentAuthor--;
		$("#author" + (currentAuthor)).show();

		if (currentAuthor < allAuthors) {
			$("#next_button_author").show();
		}
		if (currentAuthor == 1) {
			$("#back_button_author").hide();
		}
		$("#curtentAuthor").html("Current author :" + currentAuthor);
	}
}

function next(id) {
	if (id == "sample") {
		$("#sample" + current).hide();
		current++;
		$("#sample" + current).show();

		if (current == allSamples) {
			$("#next_button").hide();
		}

		if (!($("#back_button").is(":visible"))) {
			$("#back_button").show();
		}
		$("#currentSample").html("current sample :" + current);
	} else {
		$("#author" + currentAuthor).hide();
		currentAuthor++;
		$("#author" + currentAuthor).show();

		if (currentAuthor == allAuthors) {
			$("#next_button_author").hide();
		}

		if (!($("#back_button_author").is(":visible"))) {
			$("#back_button_author").show();
		}
		$("#currentAuthor").html("current author :" + currentAuthor);
	}
}

function emptyMessageDiv() {
	$("#message").html("");
	$("#message").removeAttr("style")
}

function insertBookmark() {

		$.ajax({
				contentType : 'application/json',
				data : JSON.stringify(collectData()),
				dataType : 'json',
				success : function(data) {
					$("#message")
							.html(
									"<button class='close' type='button' onclick='emptyMessageDiv()'>×</button><p>Bookmark successfully stored to server.</p>");
					$("#message").css("background-color", "#DFF0D8");
					$("#message").css("border", "1px solid #D6E9C6");
					$("#message").css("color", "#468847");
					$("#message").css("border-radius", "15px");
					$("#message").css("padding-left", "10px");
					$("input[type=text], textarea").val("");
				},
				error : function(a, b, c) {
					$("#message")
							.html(
									"<button class='close' onclick='emptyMessageDiv()' type='button'>×</button><p>Server error bookmark not stored to server.</p>");
					$("#message").css("background-color", "#F2DEDE");
					$("#message").css("border", "1px solid #EED3D7");
					$("#message").css("color", "#B94A48");
					$("#message").css("border-radius", "15px");
					$("#message").css("padding-left", "10px");
				},
				// processData : false,
				type : 'POST',
				url : ctx.siteUrl + '/ws/pubmap/article/add'
			});
}

function collectData() {
	var json = {
		authors : getAllAuthors(),
		title : $('#title').val(),
		website : $('#url').val(),
		abstractUrl : $('#abstractUrl').val(),
		year : $('#publicationDate').val(),
		journalArticle : {
			issue : $('#issue').val(),
			// isoab : $('#publicationDate').val(),
			pages : $('#pages').val(),
			publication : $('#journalName').val(),
			// volume : $('#publicationDate').val(),
			month : $('#publicationDate').val(),
			day : $('#publicationDate').val(),
			abstractText : $('#abstractText').val(),
		// eissn : $('#publicationDate').val(),
		// pissn : $('#publicationDate').val()
		},
		identifiers : {
			doi : $('#doi').val(),
			pmid : $('#pubmedId').val()
		},
		samples : getAllSamples(),
		abstractUrl : $('#abstractUrl').val(),
		institute : $('#institute').val()
	};

	return json;
};