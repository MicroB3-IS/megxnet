/**
 * Author: Matthias Stock
 */
var EgoView = Backbone.View.extend({

    el: '#ego',

    initialize: function () {
        _.bindAll(this, 'reset', 'onSelect', 'updateLocale');
        this.eventAggregator.bind(ContentView.EVENT_SELECT, this.onSelect);
    },
    render: function () {
        var self = this;
        this.$("button#ego-add")
            .button({
                icons: {
                    primary: "ui-icon-plus"
                },
                text: false
            })
            .click(function (event) {
                self.$("button#ego-add").button("option", "disabled", true);
                self.$("#ego-info").removeClass().addClass("i18n-options-ego-select");
                self.updateLocale();
            });
        this.$("button#ego-submit")
            .button({
                disabled: true
            }).click(function (event) {
                var identifiers = [], labels = [], message, logLang, depth, ignoreDirection;
                self.$("select#ego-list option").each(function (index, option) {
                    identifiers.push($(option).val());
                    labels.push($(option).text());
                });
                depth = self.$("input#ego-depth-spinner").spinner("value");
                ignoreDirection = self.$("input#ego-direction-ignore").is(":checked");
                grax.ego(
                    depth,
                    ignoreDirection,
                    self.$("input#ego-layout").is(":checked"),
                    identifiers
                );
                self.reset();
                logLang = self.proxModel.translate().options.log;
                message = logLang.egoNetwork + " " + logLang.ofDepth + " " + depth + " " + logLang.forNodes + " ";
                message += labels.join(", ");
                if (ignoreDirection) {
                    message += "; " + logLang.directionIgnored;
                }
                self.eventAggregator.trigger(LogView.EVENT_LOG, message);
                self.eventAggregator.trigger(ProxView.EVENT_LOAD);
            });
        this.$("button#ego-clear")
            .button({
                disabled: true
            }).click(this.reset);
        this.$("button#ego-relayout")
            .button().click(function() {
                grax.layout(4000);
            });
        this.$("input#ego-depth-spinner").spinner({
            min: 0,
            incremental: false
        });
    },
    reset: function() {
        this.$("button#ego-submit").button("option", "disabled", true);
        this.$("button#ego-clear").button("option", "disabled", true);
        this.$("#ego-info").empty();
        this.$("select#ego-list").empty();
        this.$("button#ego-add").button("option", "disabled", false);
    },
    onSelect: function(node) {
        var escaped;
        if (node.label && node.label.length > 0) {
            escaped = _.escape(node.label);
        } else {
            escaped = _.escape(node.id);
        }
        if(this.$("button#ego-add").button("option", "disabled") === true) {
            this.$("#ego-info").empty();
            this.$("button#ego-add").button("option", "disabled", false);
            if (this.$("select#ego-list option[value=" + node.id + "]").length === 0) {
                this.$("select#ego-list").append(
                    $("<option></option>").val(node.id).text(escaped)
                );
            }
            this.$("button#ego-submit").button("option", "disabled", false);
            this.$("button#ego-clear").button("option", "disabled", false);
        }
    }
});