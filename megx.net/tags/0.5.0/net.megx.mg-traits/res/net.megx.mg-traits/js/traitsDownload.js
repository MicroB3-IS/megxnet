$(document).ready(function () {

    var traitsTable = $("#simpleTraitsTableId");
    
    $('input.downloadTrait').live('change', function () {
        var parentRow = $(this).parent().parent();
        var nbRows = traitsTable.dataTable().fnGetNodes();
        $(parentRow).toggleClass('row_selected');
        var nbSelectedRows = $(nbRows).filter('tr.row_selected').length;

        if (nbSelectedRows === $(nbRows).length) {
            $('a.deselectAllTraitsLnk').show();
            $('a.selectAllTraitsLnk').hide();
        } else {
            $('a.deselectAllTraitsLnk').hide();
            $('a.selectAllTraitsLnk').show();
        }
    });

    $('div.dataTables_length').append('<a class="selectAllTraitsLnk" href="#">Select all</a>');
    $('div.dataTables_length').append('<a class="deselectAllTraitsLnk" href="#">Deselect all</a>');
    $('div.dataTables_paginate').before('<a class="downloadSelected" href="#">Download selected data</a>');

    $('a.selectAllTraitsLnk').click(function () {
        var allRows = traitsTable.dataTable().fnGetNodes();
        $.each(allRows, function (i, row) {
            var selectChb = $(row).find('input.downloadTrait');
            if (!$(selectChb).attr('checked')) {
                $(row).toggleClass('row_selected');
                $(selectChb).attr('checked', 'checked');
            }
        });
        $(this).hide();
        $('a.deselectAllTraitsLnk').show();
        return false;
    });

    $('a.deselectAllTraitsLnk').click(function () {
        var allRows = traitsTable.dataTable().fnGetNodes();
        $.each(allRows, function (i, row) {
            var selectChb = $(row).find('input.downloadTrait');
            if ($(selectChb).attr('checked')) {
                $(row).toggleClass('row_selected');
                $(selectChb).removeAttr('checked');
            }
        });
        $(this).hide();
        $('a.selectAllTraitsLnk').show();
        return false;
    });

    $("#noTraitsSelected").dialog({
        resizable: false,
        autoOpen: false,
        height: 150,
        width: 376,
        modal: true,
        title: "Download traits",
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    });

    $('a.downloadSelected').click(function () {
        var allRows = traitsTable.dataTable().fnGetNodes();
        var selectedRows = $(allRows).filter('tr.row_selected');

        if (selectedRows.length === 0) {
            $("#noTraitsSelected").dialog('open');
        } else {
            var traitIds = [];
            $.each(selectedRows, function (i, row) {
                var traitId = $(row).find('input.downloadTrait').val();
                traitIds.push(traitId);
            });
            $('input.traitIdsHolder').val(traitIds.join(','));
            $('#sf').submit();
        }
        return false;
    });
});