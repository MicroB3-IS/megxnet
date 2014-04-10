$(document).ready(function () {

    var samplesTable = $(".megx_dataTable");
    var SAMPLE_DOWNLOAD_URL = ctx.siteUrl + '/ws/v1/esa/v1.0.0/downloadSamples';
    
    $('input.downloadSample').on('change', function () {
        var parentRow = $(this).parent().parent();
        var nbRows = samplesTable.dataTable().fnGetNodes();
        $(parentRow).toggleClass('row_selected');
        var nbSelectedRows = $(nbRows).filter('tr.row_selected').length;

        if (nbSelectedRows === $(nbRows).length) {
            $('a.deselectAllSamplesLnk').show();
            $('a.selectAllSamplesLnk').hide();
        } else {
            $('a.deselectAllSamplesLnk').hide();
            $('a.selectAllSamplesLnk').show();
        }
    });

    $('div.dataTables_length').append('<a class="selectAllSamplesLnk" href="#">Select all</a>');
    $('div.dataTables_length').append('<a class="deselectAllSamplesLnk" href="#">Deselect all</a>');
    $('div.dataTables_paginate').before('<a class="downloadSelected" href="#">Download selected data</a>');

    $('a.selectAllSamplesLnk').click(function () {
        var allRows = samplesTable.dataTable().fnGetNodes();
        $.each(allRows, function (i, row) {
            var selectChb = $(row).find('input.downloadSample');
            if (!$(selectChb).attr('checked')) {
                $(row).toggleClass('row_selected');
                $(selectChb).attr('checked', 'checked');
            }
        });
        $(this).hide();
        $('a.deselectAllSamplesLnk').show();
        return false;
    });

    $('a.deselectAllSamplesLnk').click(function () {
        var allRows = samplesTable.dataTable().fnGetNodes();
        $.each(allRows, function (i, row) {
            var selectChb = $(row).find('input.downloadSample');
            if ($(selectChb).attr('checked')) {
                $(row).toggleClass('row_selected');
                $(selectChb).removeAttr('checked');
            }
        });
        $(this).hide();
        $('a.selectAllSamplesLnk').show();
        return false;
    });

    $("#noSamplesSelected").dialog({
        resizable: false,
        autoOpen: false,
        height: 150,
        width: 376,
        modal: true,
        title: "Download samples",
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    });

    $('a.downloadSelected').click(function () {
        var allRows = samplesTable.dataTable().fnGetNodes();
        var selectedRows = $(allRows).filter('tr.row_selected');

        if (selectedRows.length === 0) {
            $("#noSamplesSelected").dialog('open');
        } else {
            var sampleIds = [];
            $.each(selectedRows, function (i, row) {
                var sampleId = $(row).find('input.downloadSample').val();
                sampleIds.push(sampleId);
            });
            
            $('input.sampleIdsHolder').val(sampleIds.join(','));
            $('#sf').submit();
        }

        return false;
    });

});