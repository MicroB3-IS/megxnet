/**
 * Author: Matthias Stock
 */
var ProxModel = Backbone.Model.extend({
    defaults: {
        locale: i18n.id[i18n.id.standard],
        focusHistorySize: 1000,
        focusHistoryPointer: -1,
        focusHistory: []
    },
    initialize: function () {
        _.bindAll(this, "translate", "addFocus", "focusBack", "focusForward");
    },
    translate: function () {
        return i18n[this.get("locale")];
    },
    addFocus: function (id) {
        var pointer = this.get("focusHistoryPointer"),
            history = this.get("focusHistory");
        if (history[pointer] !== id) {
            if (pointer !== history.length - 1) {
                history = history.slice(0, pointer + 1);
            }
            history.push(id);
            if (history.length > this.get("focusHistorySize")) {
                history.shift();
            }
            this.set({focusHistory: history});
            this.set({focusHistoryPointer: history.length - 1});
        }
    },
    focusBack: function () {
        var pointer = this.get("focusHistoryPointer"),
            history = this.get("focusHistory");
        if (pointer > 0) {
            this.set({focusHistoryPointer: --pointer});
        }
        return history[pointer];
    },
    focusForward: function () {
        var pointer = this.get("focusHistoryPointer"),
            history = this.get("focusHistory");
        if (pointer < history.length - 1) {
            this.set({focusHistoryPointer: ++pointer});
        }
        return history[pointer];
    },
    getCurrentFocus: function () {
        var pointer = this.get("focusHistoryPointer"),
            history = this.get("focusHistory");
        return history[pointer];
    },
    isFocusPointerMax: function () {
        var pointer = this.get("focusHistoryPointer"),
            history = this.get("focusHistory");
        return pointer >= history.length - 1;
    },
    resetFocus: function () {
        this.set({
            focusHistory: [],
            focusHistoryPointer: -1
        });
    }
});