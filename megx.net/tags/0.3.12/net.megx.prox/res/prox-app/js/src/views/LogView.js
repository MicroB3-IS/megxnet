/**
 * Author: Matthias Stock
 */
var LogView = Backbone.View.extend({

    el: '#log',

    initialize: function () {
        _.bindAll(this, 'onLog');
        this.eventAggregator.bind(LogView.EVENT_LOG, this.onLog);
    },
    render: function () {
    },
    onLog: function (message) {
        var date = new Date(),
            $time, $message;
        $time = $("<p></p>").addClass("time").text(this.twoDigits(date.getHours()) + ":" +
            this.twoDigits(date.getMinutes()) + ":" +
            this.twoDigits(date.getSeconds()));
        $message = $("<p></p>").addClass("message").text(message);
        this.$el.prepend(
            $("<div></div>").addClass("entry").append($time).append($message)
        );
    },
    twoDigits: function (n) {
        return n > 9 ? "" + n : "0" + n;
    }
}, {
    EVENT_LOG: "LogView.EVENT_LOG"
});