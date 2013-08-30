/**
 * Author: Matthias Stock
 */
var HistoryView = Backbone.View.extend({

    el: '#history',

    initialize: function () {
        _.bindAll(this, "updateLocale", "pointerChange", "reset");
        this.eventAggregator.bind(ProxView.EVENT_LOAD, this.reset);
        this.proxModel.bind('change:focusHistoryPointer', this.pointerChange);
    },
    render: function () {
        var self = this;
        this.$("button#focus-back")
            .button({
                icons: {
                    primary: "ui-icon-circle-arrow-w"
                },
                disabled: true
            })
            .click(function (event) {
                event.preventDefault();
                grax.focus(self.proxModel.focusBack());
            });
        this.$("button#focus-forward")
            .button({
                icons: {
                    secondary: "ui-icon-circle-arrow-e"
                },
                disabled: true
            })
            .click(function (event) {
                event.preventDefault();
                grax.focus(self.proxModel.focusForward());
            });
    },
    pointerChange: function () {
        var pointer = this.proxModel.get("focusHistoryPointer"),
            isMax = this.proxModel.get("focusHistorySize");
        if (this.proxModel.isFocusPointerMax()) {
            this.$("button#focus-forward").button("option", "disabled", true);
        } else {
            this.$("button#focus-forward").button("option", "disabled", false);
        }
        if (pointer <= 0) {
            this.$("button#focus-back").button("option", "disabled", true);
        } else {
            this.$("button#focus-back").button("option", "disabled", false);
        }
    },
    reset: function() {
        this.proxModel.resetFocus();
    }
});