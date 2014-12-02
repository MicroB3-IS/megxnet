/**
 * Author: Matthias Stock
 */
var SearchView = Backbone.View.extend({

    el: '#search',

    initialize: function () {
        _.bindAll(this, 'reset');
        this.eventAggregator.bind(ProxView.EVENT_LOAD, this.reset);
        this.parent = this.options.parent;
        this.$queryInput = this.$("input#search-term");
        this.$resultList = this.$("select#search-results").hide();
        this.$noResult = this.$("#search-noresult").hide();
    },
    render: function () {
        var self = this;
        this.$("button#search-submit")
            .button({
                icons: {
                    primary: "ui-icon-search"
                },
                text: false
            })
            .click(function (event) {
                event.preventDefault();
                self.findNodes();
            });
        this.$queryInput
            .keyup(function (event) {
                if (event.which === 13) {
                    event.preventDefault();
                    self.findNodes();
                } else {
                    self.reset();
                }
            })
            .focus(this.reset);
        this.$resultList.change(function (event) {
            var target = self.$resultList.children("option:selected:first").val();
            self.proxModel.addFocus(target);
            grax.focus(target, true);
        });
    },
    reset: function() {
        this.$resultList.empty().hide();
        this.$noResult.hide();
        this.parent.updateHeight();
    },
    findNodes: function () {
        var self = this,
            result = grax.find(this.$queryInput.val());
        if (result) {
            if (result.data.length > 0) {
                this.$resultList.empty();
                $.each(result.data, function (index, value) {
                    var i, il, caption, label;
                    if (value.label && value.label.length > 0) {
                        label = value.label;
                    } else {
                        label = "No label";
                    }
                    if (value.hits.length > 1) {
                        caption = label + " (" + value.hits.length + ") (";
                    } else {
                        caption = label + " (";
                    }
                    for (i = 0, il = value.hits.length; i < il; i++) {
                        caption += value.hits[i];
                        if (i < il - 1) {
                            caption += ", ";
                        } else {
                            caption += ")";
                        }
                    }
                    self.$resultList.append(
                        $("<option></option>").val(value.id).text(caption)
                    );
                });
                if (result.limit > 0) {
                    this.$resultList.append(
                        $("<option></option>").text(this.proxModel.translate().options.search.more)
                    );
                }
                this.$resultList.show();
            } else {
                this.$noResult.show();
            }
            this.parent.updateHeight();
        }
    }
});