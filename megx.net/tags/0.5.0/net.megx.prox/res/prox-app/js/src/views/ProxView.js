/**
 * Author: Matthias Stock
 */
var ProxView = Backbone.View.extend({

    el: "body",

    initialize: function() {
        _.bindAll(this, 'onResize');
        this.optionsView = new OptionsView({
            pfamQueryXML: this.options.config.pfamQueryXML,
            pfamQueryHTML: this.options.config.pfamQueryHTML
        });
        this.contentView = new ContentView({
            "filename": this.options.config.filename
        });
        $(window).on('resize.Prox', this.onResize);
    },
    render: function() {
        this.options.languageRouter.navigate(i18n.id.standard, { trigger: true });
        this.optionsView.render();
        this.contentView.render();
    },
    events: {
        "click": "onClick"
    },
    onResize: function () {
        this.eventAggregator.trigger(ProxView.EVENT_RESIZE);
    },
    onClick: function() {
        this.eventAggregator.trigger(ProxView.EVENT_CLICK);
    },
    remove: function() {
        $(window).off('resize.Prox');
        Backbone.View.prototype.remove.call(this);
    }
}, {
    EVENT_RESIZE: "ProxView.EVENT_RESIZE",
    EVENT_CLICK: "ProxView.EVENT_CLICK",
    EVENT_LOAD: "ProxView.EVENT_LOAD"
});