var tmp;

$(function () {
    $('.one-column').live('click', function () {
        //alert('layout');
        $('.dashboard').removeClass('layout-aa layout-aaa');
        $('.dashboard').addClass('layout-a');

        $('.dashboardCol').removeClass('nonsortable');
        $('.dashboardCol:gt(0)').addClass('nonsortable');

        // check if there are widgets in hidden columns, move them to the first column
        $('.nonsortable').each(function () {
            // move the widgets to the first column
            $(this).children().appendTo($('.dashboardCol:first'));
        });
        return false;
    });

    $('.two-column').live('click', function () {
        $('.dashboard').removeClass('layout-a layout-aaa');
        $('.dashboard').addClass('layout-aa');

        $('.dashboardCol').removeClass('nonsortable');
        $('.dashboardCol:gt(1)').addClass('nonsortable');

        // check if there are widgets in hidden columns, move them to the first column
        $('.nonsortable').each(function () {
            // move the widgets to the first column
            $(this).children().appendTo($('.dashboardCol:first'));
        });
        return false;
    });
    $('.three-column').live('click', function () {
        $('.dashboard').removeClass('layout-a layout-aa');
        $('.dashboard').addClass('layout-aaa');

        $('.dashboardCol').removeClass('nonsortable');

        return false;
    });

    $(function () {
        $(".dashboardCol").sortable({
            //placeholder: "ui-state-highlight",
            cursor: "move",
            handle: ".dashboardItemHeader",
            connectWith: ".dashboardCol",
            opacity: 0.6,
            helper: 'clone',
            dropOnEmpty: true
        });
    });

    var requestViewModel = function (data) {
        var self = this;

        ko.mapping.fromJS(data, {}, self);
        self.isSelected = ko.observable(false);
        self.isCompleted = ko.computed(function() {
            return self.State() == 10;
        });
        //self.isNew = ko.observable(false);
    };

    function mainViewModel(data) {
        var self = this;

        self.requests = ko.mapping.fromJS(data, {
            create: function (options) {
                //debugger;
                return new requestViewModel(options.data);
            }
        });

        self.selectedRequest = ko.observable(null);

        self.currentMessage = ko.observable(null);

        self.sendMessage = function (formElements) {
            //alert('submit');
            if (self.selectedRequest() == null) {
                
            }

            if (self.selectedRequest() != null && self.selectedRequest().State() != 1) {
                // try send it to server
                // if result from server ok, create a message and push it to current request's Messages array
            }
        };

        self.selectRequest = function (request) {
            debugger;

            ko.utils.arrayForEach(self.requests(), function (item) {
                if (item != request) {
                    item.isSelected(false);
                }
            });

            request.isSelected(true);
            self.selectedRequest(request);
        };
    };

    $.getJSON("/api/request", function (data) {
        debugger;

        var vm = new mainViewModel(data);
        tmp = vm;
        ko.applyBindings(vm);
    });
});