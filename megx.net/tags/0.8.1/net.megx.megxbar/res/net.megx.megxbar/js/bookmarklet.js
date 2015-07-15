(function Megxbookmarklet() {

    //production
//    var iframeSrc = "https://mb3is.megx.net/bookmark";
//    var targetOrigin = "https://mb3is.megx.net";
    //iwgate
    var iframeSrc = "http://iwgate.poweredbyclear.com:9080/megx.net/bookmark";
    var targetOrigin = "http://iwgate.poweredbyclear.com:9080"
    //development
//   var iframeSrc = "http://192.168.2.215:8080/megx.net/bookmark";
//   var targetOrigin = "http://192.168.2.215:8080"

    var iframeZIndex = 2147483640;
    var body, ifrm, closeButton, closeButtonDiv;
    var iframeHolder = (function() {
        var ifr = null;
        return {
            setIframe: function(i) {
                ifr = i
            },
            getIframe: function() {
                return ifr
            }
        };
    })();
    var iframeCss = {
        "position": "fixed",
        "z-index": iframeZIndex,
        "-moz-box-sizing": "border-box",
        "box-sizing": "border-box",
        "padding": "15px",
        "border": "0",
        "background": "transparent",
        "height": "100%",
        "width": "365px",
        "top": "0",
        "right": "0",
        "overflow": "hidden"

    };
    var closeButtonCss = {
        "position": "fixed",
        "top": "7px",
        "z-index": iframeZIndex + 1,
        "right": "334px",
        "cursor": "pointer"
    };

    function useCss(el, css) {
        var s = "";
        for (var p in css)
            s += p + ":" + css[p] + ";";
        el.style.cssText = el.style.cssText + s
    }

    function createIframe() {
        ifrm = document.createElement("IFRAME");
        ifrm.src = iframeSrc;
        ifrm.allowTransparency = true;
        ifrm.frameBorder = 0;
        useCss(ifrm, iframeCss);
        closeButtonDiv = createCloseButton();
        bookmarkletDiv = document.createElement("DIV");
        bookmarkletDiv.appendChild(ifrm);
        bookmarkletDiv.appendChild(closeButtonDiv);
        body.appendChild(bookmarkletDiv);

        iframeHolder.setIframe(bookmarkletDiv);
    }

    function createCloseButton() {
        closeButtonDiv = document.createElement("DIV");
        closeButton = document.createElement("IMG");
        closeButton.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoxNDMxM0JBQUFCRTUxMUUyODZCQ0IyQzQxNEVBNDQ0NyIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDozNEI0Q0E1NkFDMDExMUUyODZCQ0IyQzQxNEVBNDQ0NyI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjE0MzEzQkE4QUJFNTExRTI4NkJDQjJDNDE0RUE0NDQ3IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjE0MzEzQkE5QUJFNTExRTI4NkJDQjJDNDE0RUE0NDQ3Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+VICatgAAA6dJREFUeNqcVktLW1EQnjxMKqkhVdMKWogKUmsNVLCmixSJXVgXsauCIAguXBb8A1JcKd26VXBbUNBNu9AgFKJWpSRqQy2RgA+sBhMrVZOY3M53ODdc85DUgUnOnTPzfec5c3RUXHTQeDzeZTab3Uaj8aXBYHiq0+keKYryO51O/7i+vl5OJBJfbTbbIvsqUksSgBtisdhbBgooJQj84I84GZ8HmPttTKVSn3nkXTBsbm7S2toaBYNB2t3dpWg0StXV1dTQ0EBOp5Pa29uptbVVBPPMFsvKyt6gqZ2VrgDBKhM839/fp7m5OZqfn6fT09Oi066srCSv10u9vb1UV1cHou9M1KEl0mkJksnkF3bwbG9v09TUFC0tLZW6xNTZ2UmDg4PU0tJCPFCfyWTqVon00kd/eHjoBQFmMDk5ST6fjzKZTMkKf8QhHjjAAy7JHzELXucPMMzMzNDCwgLxZv63Ig7xEIlnBL4gCYfDr5n9WSAQoNnZ2bxRjo2NFRx9ITvigQM84KokBqvV+grsfr+fjo+Pb4xufHycenp6aGtr64Yd37CjX2tHPHAgEtcgSMrLy3EaaGNjI28JhoeHs5sbCoWEDf+qoD83BjgQiStI9Hyjn2hBcrWxsTELurOzk23DXshfHYTE1WPT7/OljYNRDSomkUgk23Y4HEX9OP0Q7weaaU5DNrHxfIGisFRVVRU9OVoClbCYL3BkBoiqG69cXFz8grGpqangKdrb28uC19bWZtuwF/IHDkTiisuY4bQhdqqtrS1vVBMTE1nQmpoaYcO/KujPjQEOROJmBAlPfRlGt9stcpE2YGhoSJx9u91+w45v2NGvtSMeOHJJgZtBu4zVfn5+/hNpe3R0VGHHOyviIcADLvAxExynq/X19Y9g7O/vp+7u7julFcQhHiLxriS+yF1m1oe8hn6Mgi+T0tfXp1RUVJSs8EccBDjAk7jZcoKKZmF9fHZ2FoIjFyhlZGREqa+vVywWS1FFP/zgD0E8cCSeIa+eIBOwPjg5OfnEWfQFOlZXV0UaX1lZIdSZo6MjcbpQN1wuF3k8HuroEFkJVfMbH4h33IyxXuZWSJUIh8CKkfDJec91O1xKjYcf/OUMrBJHd2uNZzXJ6Vqnp6e7mpubXXwJnfwqcXDSs11eXvIjJh45ODgIcp5aGRgYwGvlD+tf1uRtNV5r02vI7kk1a0YIgBRrQp6gKw14ptAS3frukptnkMR6DUlGalpq0XfXPwEGAC/2hSkSgXukAAAAAElFTkSuQmCC"
        useCss(closeButton, closeButtonCss);
        closeButtonDiv.appendChild(closeButton);
        closeButton.onclick = function() {
            closeOnClick();
            return false
        };
        return closeButtonDiv;
    }

    function closeOnClick() {
        jQuery(iframeHolder.getIframe()).remove();
        return false;
    }

    function startIframe() {
        body = document.getElementsByTagName("BODY")[0];
        createIframe();
    }
    try {
        startIframe();
    } catch (e) {}

    var handleInternalMessage = function(data) {
        try {
            var msg = JSON.parse(data);
            if (msg.type && msg.type == "bookmarkletclicked") {
                var ifr = iframeHolder.getIframe();
                if (jQuery(ifr).is(':visible')) {
                    jQuery(ifr).remove();
                } else {
                    startIframe();
                }
            }
        } catch (e) { /*alert(e);*/ }
    };

    var handleExternalMessage = function(source, data) {
        try {
            var msg = JSON.parse(data);
            if (msg.type && msg.type == "getarticle") {
            	
                if (document.domain == "www.ncbi.nlm.nih.gov") {

                    if (document.getElementById("absid") != null) {

                        jQuery.get(document.URL + "?report=xml&format=text", function(data) {

                            var article = {};

                            article.url = document.URL;
                            article.pmid = document.getElementById("absid").value;
                            article.title = document.getElementsByTagName("h1")[1].innerHTML;

                            var authorsElement = document.getElementsByClassName("auths")[0];
                            var authorsLinks = authorsElement.getElementsByTagName("a");
                            var authorsList = [];
                            for (var i = 0; i < authorsLinks.length; i++) {
                                authorsList[i] = authorsLinks[i].innerHTML;
                            }
                            article.authors = authorsList;

                            var tempXml = document.createElement("p");
                            tempXml.innerHTML = data;
                            var xml = tempXml.getElementsByTagName("pre")[0].innerHTML;
                            article.xml = xml;

                            source.postMessage(JSON.stringify({
                                type: "setarticle",
                                article: article
                            }), targetOrigin);

                        }, false);

                    } else {
                        source.postMessage(JSON.stringify({
                            type: "noarticlefound"
                        }), targetOrigin);
                    }

                } else {
                    source.postMessage(JSON.stringify({
                        type: "wrongdomain"
                    }), targetOrigin);
                }
            }

        } catch (e) { /*alert(e);*/ }
    };

    var listener = function(event) {
        if (event.source === window) {
            handleInternalMessage(event.data);
        } else if (event.origin == targetOrigin) {
            handleExternalMessage(event.source, event.data);
        }
    }

    if (!window.hasmegxlistener) {
        if (window.addEventListener) {
            window.addEventListener("message", listener, false)
        } else {
            window.attachEvent("onmessage", listener)
        }
        window.hasmegxlistener = true;
    }

})();