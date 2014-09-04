javascript: (function(c) {
    var d = document;
    var h = d.getElementsByTagName("head")[0];
    var ss = h.getElementsByTagName("script");
    var t = false;
    if (ss) {
        for (var i = 0; i < ss.length; i++) {
            if (ss[i].src && ss[i].src == c.src) {
                t = true;
                break;
            }
        }
    }
    if (t) {
        window.postMessage(JSON.stringify({
            type: "bookmarkletclicked"
        }), d.URL.substring(0, d.URL.indexOf(d.domain)) + d.domain);
    } else {
        var scr = d.createElement("script");
        scr.src = c.src;
        h.appendChild(scr);
    }
})({
    src: "https://mb3is.megx.net/net.megx.megxbar/js/bookmarklet.min.js"
});