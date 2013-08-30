/**
 * Author: Matthias Stock
 */
var InfoView = Backbone.View.extend({

    el: '#data-info',

    initialize: function () {
        _.bindAll(this, "onSelect", "reset", "updateLocale");
        this.eventAggregator.bind(ProxView.EVENT_LOAD, this.reset);
        this.eventAggregator.bind(ContentView.EVENT_SELECT, this.onSelect);
    },
    render: function () {
    },
    onSelect: function (node, nodesIn, nodesOut) {
        var i, il, key, blacklist, rgb,
            sortedKeys = [],
            info = $("<table></table>");
        blacklist = {
            "color": true,
            "label": true,
            "gxpartition": true,
            "x": true,
            "y": true,
            "z": true,
            "r": true,
            "g": true,
            "b": true,
            "edges": true
        };
        rgb = "rgb(" + node.r * 255 + "," + node.g * 255 + "," + node.b * 255 + ")";
        info.append(this.createRow(
            this.proxModel.translate().general.node,
            $("<div></div>")
                .append($("<div></div>")
                .addClass("colorBox")
                .css("background-color", rgb)
                .attr("alt", rgb)
                .attr("title", rgb))
                .append(this.createFocusLink(node.id, node.label)),
            true
        ));
        for (key in node.attributes) {
            if (node.attributes.hasOwnProperty(key) && blacklist[key] !== true) {
                sortedKeys.push(key);
            }
        }
        sortedKeys.sort(function (a, b) {
            return a.toUpperCase().localeCompare(b.toUpperCase());
        });
        for (i = 0, il = sortedKeys.length; i < il; i++) {
            key = sortedKeys[i];
            info.append(this.createRow(key, node.attributes[key]));
        }
        if (nodesIn.length > 0) {
            info.append(this.createNodeList("in", nodesIn));
        }
        if (nodesOut.length > 0) {
            info.append(this.createNodeList("out", nodesOut));
        }
        this.$el.html(info);
    },
    createRow: function (title, value, html) {
        var row = $("<tr></tr>");
        row.append($("<td></td>").addClass("title").text(title));
        if (html) {
            row.append($("<td></td>").html(value));
        } else {
            row.append($("<td></td>").text(value));
        }
        return row;
    },
    createNodeList: function (title, nodes) {
        var i, il, node,
            nodeLinks = $("<ul></ul>").addClass("linkList");
        for (i = 0, il = nodes.length; i < il; i++) {
            node = nodes[i];
            nodeLinks.append($("<li></li>").append(this.createFocusLink(node.id, node.label)));
        }
        return this.createRow(title, nodeLinks, true);
    },
    createFocusLink: function (id, label) {
        var link, handler,
            self = this;
        handler = function (event) {
            var target;
            event.preventDefault();
            if (event.target.hash.length > 1) {
                target = event.target.hash.substr(1);
                self.proxModel.addFocus(target);
                grax.focus(target, true);
            }
        };
        link = $("<a></a>")
            .attr("href", "#" + id)
            .text(label)
            .click(handler);
        return link;
    },
    reset: function () {
        this.$el.empty();
    }
});