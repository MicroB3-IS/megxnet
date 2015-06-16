/**
 * Author: Matthias Stock
 */
var PfamView = Backbone.View.extend({

    el: '#pfam',

    initialize: function () {
        _.bindAll(this, 'onSelect', 'queryXml', 'queryHtml');
        this.pfamQueryXML = this.options.pfamQueryXML;
        this.pfamQueryHTML = this.options.pfamQueryHTML;
        this.eventAggregator.bind(ContentView.EVENT_SELECT, this.onSelect);
    },
    render: function () {
    },
    onSelect: function (node) {
        var escaped, message, logLang;
        if (this.$el.hasClass("ui-accordion-content-active")) {
            this.$("#pfam-description").removeClass("i18n-options-pfam-noresult");
            this.$("span").remove();
            this.$("#pfam-description").empty();
            this.$("#pfam-literature").empty();
            if (node.label && node.label.length > 0) {
                escaped = _.escape(node.label);
            } else {
                escaped = _.escape(node.id);
            }
            logLang = this.proxModel.translate().options.log;
            message = logLang.pfamQuery + " " + logLang.forNodes + " " + escaped;
            this.eventAggregator.trigger(LogView.EVENT_LOG, message);
            this.queryXml(escaped);
        }
    },
    queryXml: function(escaped) {
        var self = this;
        if (this.pfamQueryXML) {
            $.ajax({
                dataType: "xml",
                url: this.pfamQueryXML.replace("{id}", escaped),
                success: function (data) {
                    var xml = $(data),
                        description = xml.find("description").text(),
                        comment = xml.find("comment").text(),
                        link = $("<a></a>")
                            .attr("href", self.pfamBase + escaped)
                            .attr("target", "_blank")
                            .addClass("titleLink")
                            .text(escaped);
                    if (description.length > 0 || comment.length > 0) {
                        self.$("#pfam-description").html(
                            $("<p></p>").html(link).append(description)
                        ).append(
                            $("<p></p>").html(comment)
                        );
                        self.queryHtml(escaped);
                    } else {
                        self.$("#pfam-description").addClass("i18n-options-pfam-noresult");
                        self.updateLocale();
                    }
                },
                error: function () {
                    self.$("#pfam-description").text("Error querying web service for XML.");
                }
            });
        }
    },
    queryHtml: function(escaped) {
        var self = this;
        if (this.pfamQueryHTML) {
            $.ajax({
                dataType: "html",
                url: this.pfamQueryHTML.replace("{id}", escaped),
                success: function (data) {
                    // remove img tags to prevent preloading src attributes when passed to the dom
                    data.replace(/<\s*img.*?>/, "");
                    $(data).find("a[name^='ref']").parent("p").each(function(index, element) {
                        var $element = $(element);
                        $element.children("a").attr("target", "_blank");
                        $("#pfam-literature").append($element);
                    });
                },
                error: function () {
                    self.$("#pfam-literature").text("Error querying web service for HTML.");
                }
            });
        }
    }
});