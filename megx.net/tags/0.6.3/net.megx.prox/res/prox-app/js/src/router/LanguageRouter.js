/**
 * Author: Matthias Stock
 */
var LanguageRouter = Backbone.Router.extend({
    routes: {
        "": "onHome",
        ":locale": "onLocale"
    },
    onHome: function() {
        this.navigate(this.proxModel.get('locale'));
    },
    onLocale: function(locale) {
        var localeFull = i18n.id[locale];
        if (localeFull) {
            this.proxModel.set({ "locale": localeFull });
            this.eventAggregator.trigger(LanguageRouter.EVENT_LOCALE, localeFull);
        } else {
            this.navigate(i18n.id.standard, { trigger: true });
        }
    }
}, {
    EVENT_LOCALE: "LanguageRouter.EVENT_LOCALE"
});