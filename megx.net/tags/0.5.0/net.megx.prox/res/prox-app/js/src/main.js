/**
 * Author: Matthias Stock
 */
prox.init = function(config) {
    config = $.extend({
        "file"          : "demo.graphml",
        "pfamQueryXML"  : "http://pfam.sanger.ac.uk/family/{id}?output=xml",
        "pfamQueryHTML" : "http://pfam.sanger.ac.uk/family/{id}"
    }, config);

    Backbone.View.prototype.updateLocale = function(locale) {
        locale = locale || this.proxModel.get("locale");
        var localeMap = $.extend(true, {}, i18n[i18n.id[i18n.id.standard]], i18n[locale]);
        this.$('[class|="i18n"]').each(function(index, element) {
            var $element = $(element);
            var cssClasses = $element.attr('class').split(' ');
            var cssClass = null;
            var localeTree = null;
            var itemString = null;
            var localeItem = localeMap;
            while(localeTree === null && cssClasses.length > 0) {
                cssClass = cssClasses.pop();
                if (cssClass.substr(0, 5) === 'i18n-') {
                    localeTree = cssClass.split('-');
                    localeTree.shift();
                }
            }
            do {
                itemString = localeTree.shift();
                localeItem = localeItem[itemString];
            } while(localeTree.length > 0 && typeof localeItem === 'object');
            if (typeof localeItem !== 'string') {
                localeItem = 'N/A';
            }
            $element.text(localeItem);
        });
    };

    var eventAggregator = _.extend({}, Backbone.Events);
    Backbone.View.prototype.eventAggregator = eventAggregator;
    Backbone.Router.prototype.eventAggregator = eventAggregator;

    this.proxModel = new ProxModel();
    Backbone.View.prototype.proxModel = this.proxModel;
    Backbone.Router.prototype.proxModel = this.proxModel;

    this.languageRouter = new LanguageRouter();
    this.proxView = new ProxView({
        "config": config,
        "languageRouter": this.languageRouter
    });
    Backbone.history.start();
    this.proxView.render();
};