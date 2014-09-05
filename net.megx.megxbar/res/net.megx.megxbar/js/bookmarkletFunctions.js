$(document).ready(function() {

    var latitude;
    var longitude;

    $('#logout').click(function() {
        logOut();
    });

    $('input:radio[name=rb]').click(function(event) {

        if ($('#decimal').is(':checked')) {
            $('#bookmarkFormDegrees').hide();
            $('#bookmarkFormDecimal').fadeIn(250);
        }
        if ($('#degrees').is(':checked')) {
            $('#bookmarkFormDecimal').hide();
            $('#bookmarkFormDegrees').fadeIn(250);
        }
    });

    $.validator.addMethod("latDE", function(value, element, param) {
        if (this.optional(element)) { //This is not a 'required' element and the input is empty
            return true;
        }
        if (validateLat(value)) {
            return true;
        }
        return false;
    });

    $.validator.addMethod("lonDE", function(value, element, param) {
        if (this.optional(element)) { //This is not a 'required' element and the input is empty
            return true;
        }
        if (validateLon(value)) {
            return true;
        }
        return false;
    });

    $('#latitudeDeg, #latitudeMin, #latitudeSec').keyup(function() {

        var latitudeData = {
            degrees: $('#latitudeDeg').val(),
            minutes: $('#latitudeMin').val(),
            seconds: $('#latitudeSec').val(),
            from: 0,
            to: 90,
            msgElement: "latitudeDegMsg"
        };

        if (latitudeData.degrees >= 90) {
            $('#latitudeMin').val("");
            $('#latitudeMin').prop('disabled', true);
            $('#latitudeSec').val("");
            $('#latitudeSec').prop('disabled', true);
        } else {
            $('#latitudeMin').prop('disabled', false);
            $('#latitudeSec').prop('disabled', false);
        }

        latitude = dmsToDegrees(latitudeData);

    });

    $('#longitudeDeg, #longitudeMin, #longitudeSec').keyup(function() {

        var longitudeData = {
            degrees: $('#longitudeDeg').val(),
            minutes: $('#longitudeMin').val(),
            seconds: $('#longitudeSec').val(),
            from: 0,
            to: 180,
            msgElement: "longitudeDegMsg"
        };

        if (longitudeData.degrees >= 180) {
            $('#longitudeMin').val("");
            $('#longitudeMin').prop('disabled', true);
            $('#longitudeSec').val("");
            $('#longitudeSec').prop('disabled', true);
        } else {
            $('#longitudeMin').prop('disabled', false);
            $('#longitudeSec').prop('disabled', false);
        }

        longitude = dmsToDegrees(longitudeData);

    });

    $("#submitBookmark").click(function() {

        if ($('#bookmarkFormDecimal').is(':visible')) {

            $("#bookmarkFormDecimal").validate({
                errorClass: "my-error-class",
                validClass: "my-valid-class",
                rules: {
                    latitudeDec: {
                        required: true,
                        latDE: true
                    },
                    longitudeDec: {
                        required: true,
                        lonDE: true
                    }
                },
                messages: {
                    latitudeDec: "Latitude value must be in range of 0 to 90.",
                    longitudeDec: "Longitude value must be in range of 0 to 180."
                }
            });

            if ($("#bookmarkFormDecimal").valid()) {

                var lat = $('#latitudeDec').val();
                var lon = $('#longitudeDec').val();

                if ($('#south').is(':checked')) {
                    lat = '-' + $('#latitudeDec').val();
                }
                if ($('#west').is(':checked')) {
                    lon = '-' + $('#longitudeDec').val();
                }

                console.log('Latitude: ' + parseFloat(lat).toFixed(6) + ' Longitude: ' + parseFloat(lon).toFixed(6));
                var data = collectData(parseFloat(lon), parseFloat(lat));

                insertBookmark(data);
            }
        } else {

            if (latitude != null && longitude != null) {

                if ($('#degSouth').is(':checked')) {
                    latitude *= -1;
                }
                if ($('#degWest').is(':checked')) {
                    longitude *= -1;
                }

                console.log("lat: " + latitude.toFixed(6) + " - lon: " + longitude.toFixed(6));

                var data = collectData(latitude.toFixed(6), longitude.toFixed(6));

                insertBookmark(data);
                latitude = null;
                longitude = null;
            } else {

                if (latitude == null) {
                    $('#latitudeDegMsg').text("Please first enter a valid latitude value.").show();
                }
                if (longitude == null) {
                    $('#longitudeDegMsg').text("Please first enter a valid longitude value.").show();
                }
            }
        }
    });

});

function validateLat(value) {

    if (value >= 0 && value <= 90.0 && value.indexOf('-') < 0) {
        return true;
    }
    return false;
}

function validateLon(value) {

    if (value >= 0 && value <= 180.0 && value.indexOf('-') < 0) {
        return true;
    }
    return false;
}

function dmsToDegrees(data) {

    var res = null;

    var degreesPatt = /^\d+$/g;
    var minutesPatt = /^\d+$/g;
    var secondsPatt = /^\d{1,2}(\.\d{1,4})?$/g;

    var degreesRes = degreesPatt.test(data.degrees);
    var minutesRes = minutesPatt.test(data.minutes);
    var secondsRes = secondsPatt.test(data.seconds);

    if (degreesRes && (parseInt(data.degrees) >= data.from && parseInt(data.degrees) <= data.to)) {

        var deg = 0;
        var min = 0;
        var sec = 0;

        $('#' + data.msgElement).hide();
        deg = parseInt(data.degrees);

        if ((minutesRes && (parseInt(data.minutes) >= 0 && parseInt(data.minutes) < 60)) || data.minutes == "") {

            $('#' + data.msgElement).hide();

            if (data.minutes == "") {
                min = 0;
            } else {
                min = parseInt(data.minutes);
            }

            if ((secondsRes && (parseFloat(data.seconds) >= 0 && parseFloat(data.seconds) < 60)) || data.seconds == "") {

                $('#' + data.msgElement).hide();

                if (data.seconds == "") {
                    sec = 0;
                } else {
                    sec = parseFloat(data.seconds);
                }

                res = deg + min / 60 + sec / 3600;

            } else {
                $('#' + data.msgElement).text("Seconds value must be 0 or greater and less than 60.").show();
            }
        } else {
            $('#' + data.msgElement).text("Minutes value must be 0 or greater and less than 60.").show();
        }
    } else {
        $('#' + data.msgElement).text("Degrees value must be in range of " + data.from + " to " + data.to + ".").show();
    }

    return res;
}

function logOut() {
    window.open("/megx.net/admin/logout.do", '_blank');
    refreshBookmarklet();
}

function refreshBookmarklet() {
    document.location.href = document.location.href;

}

function emptyMessageDiv() {
    $("#message").html("");
    $("#message").removeAttr("style")
}

function insertBookmark(data) {

    $.ajax({
        contentType: 'application/json',
        data: {
            "article": JSON.stringify(data)
        },
        dataType: 'json',
        success: function(message) {
            $("#message")
                .html(
                    "<button class='close' type='button' onclick='emptyMessageDiv()'>×</button><p>" + message + "</p>");
            $("#message").css("background-color", "#DFF0D8");
            $("#message").css("border", "1px solid #D6E9C6");
            $("#message").css("color", "#468847");
            $("#message").css("border-radius", "15px");
            $("#message").css("padding-left", "10px");
            $("input[type=text]").val("");

        },
        error: function(a, b, c) {
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
        type: 'POST',
        url: ctx.siteUrl + '/ws/v1/pubmap/v1.0.0/article'
    });
}

function collectData(lon, lat) {

    var megxbar = {
        title: msg.article.title,
        authors: msg.article.authors,
        lon: lon,
        lat: lat
    };

    var article = {
        pmid: msg.article.pmid,
        url: msg.article.url,
        lon: lon,
        lat: lat,
        megxBarJSON: JSON.stringify(megxbar),
        articleXML: msg.article.xml
    };

    return article;

};