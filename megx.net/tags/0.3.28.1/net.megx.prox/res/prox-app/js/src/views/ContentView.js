/**
 * Author: Matthias Stock
 */
var ContentView = Backbone.View.extend({

    el: '#content',

    initialize: function () {
        _.bindAll(this, 'updateLocale');
        _.bindAll(this, 'updateHeight');
        this.eventAggregator.bind(LanguageRouter.EVENT_LOCALE, this.updateLocale);
        this.eventAggregator.bind(ProxView.EVENT_RESIZE, this.updateHeight);
        this.$("div.loading").show();
    },
    events: {
        "dragover canvas:first": "dragOver",
        "dragenter canvas:first": "dragEnter",
        "dragleave canvas:first": "dragLeave",
        "drop canvas:first": "drop"
    },
    render: function () {
        var self;
        self = this;
        this.updateHeight();
        grax.init({
            container: this.$("#graph"),
            onSelect: function (node, nodesIn, nodesOut) {
                self.eventAggregator.trigger(ContentView.EVENT_SELECT, node, nodesIn, nodesOut);
            }
        });
        grax.load(this.options.filename, function() {
            self.$("div.loading").hide();
        });
    },
    dragOver: function(event) {
        if (event.preventDefault) {
            event.preventDefault();
        }
        return false;
    },
    dragEnter: function(event) {
        return false;
    },
    dragLeave: function(event) {
    },
    drop: function(event) {
        if (event.stopPropagation) {
            event.stopPropagation();
        }
        var file = event.originalEvent.dataTransfer.files[0];
        var extension = file.name.split(".").pop();
        if (extension === "graphml") {
            grax.load(file);
        } else {
            console.log("Only GRAPHML files are allowed, yet.");
        }
        return false;
    },
    updateHeight: function () {
        var $graph = this.$("#graph");
        var windowHeight = $(window).height();
        $graph.css('height', Math.max(1, windowHeight) + 'px');
        grax.fit();
    }
}, {
    EVENT_SELECT: "ContentView.EVENT_SELECT"
});