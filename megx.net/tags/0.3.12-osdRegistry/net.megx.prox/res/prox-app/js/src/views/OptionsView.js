/**
 * Author: Matthias Stock
 */
var OptionsView = Backbone.View.extend({

    el: '#options',

    initialize: function () {
        _.bindAll(this, 'updateHeight', 'updateLocale');
        this.eventAggregator.bind(ProxView.EVENT_RESIZE, this.updateHeight);
        this.eventAggregator.bind(LanguageRouter.EVENT_LOCALE, this.updateLocale);
        this.historyView = new HistoryView({ parent: this });
        this.searchView = new SearchView({ parent: this });
        this.dataView = new InfoView({ parent: this });
        this.pfamView = new PfamView({
            parent: this,
            pfamQueryXML: this.options.pfamQueryXML,
            pfamQueryHTML: this.options.pfamQueryHTML
        });
        this.egoView = new EgoView({ parent: this });
        this.logView = new LogView({ parent: this });
        this.languageView = new LanguageView({ parent: this });
    },
    render: function () {
        this.historyView.render();
        this.searchView.render();
        this.dataView.render();
        this.pfamView.render();
        this.egoView.render();
        this.logView.render();
        this.languageView.render();
        this.$(".menu").accordion({
            heightStyle: "fill",
            animate: 200
        });
    },
    updateHeight: function () {
        this.$(".menu").accordion("refresh");
    }
});