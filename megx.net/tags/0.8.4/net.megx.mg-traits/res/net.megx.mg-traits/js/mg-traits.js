jQuery(document).ready(
    function() {
        (function() {

            var columns = [{
                data: 'id',
                className: "text-center",
                render: function(id) {
                    return '<input class=\'downloadTrait\' type=\'checkbox\' value=\'' + id + '\'>';
                },
                width: '80px',
                sortable: false
            }, {
                data: 'sampleLabel',
                className: 'left data-pad'
            }, {
                data: 'sampleName',
                className: 'left data-pad'
            }, {
                data: 'sampleEnvironment',
                className: 'left data-pad',
                width: '120px'
            }, {
                data: 'size',
                className: 'left data-pad',
                width: '120px'
            }, {
                data: 'numberOfSequences',
                className: 'left data-pad',
                width: '120px'
            }, {
                data: 'id',
                className: 'center',
                width: '120px',
                render: function(id) {
                    var viewUrl = ctx.siteUrl + '/mg-traits/sampleDetails?id=' + id;
                    return '<a href=' + viewUrl + ' class=\'viewSampleClass\'>View more</a>';
                }
            }];

            var buttons = {
                toolbarButtons: {
                    downloadSelected: {
                        tooltip: "Download Selected",
                    },
                    downloadAll: {
                        tooltip: "Download All",
                        link : {
                            url : ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/all",
                          }
                    }
                }
            };

            var datatablesConfigOverride = {};
            var url = "";

            $.getJSON(ctx.siteUrl + "/ws/v1/mg-traits/v1.0.0/all",
                function(allTraits, textStatus, jqXHR) {

                    if (allTraits) {

                        var nbRorwsToAdd = allTraits.data.length;
                        var rowsToAdd = [];

                        for (var i = 0; i < allTraits.data.length; i++) {
                            rowsToAdd.push({
                                'id': allTraits.data[i].id,
                                'sampleLabel': allTraits.data[i].sampleLabel,
                                'sampleName': allTraits.data[i].sampleName,
                                'sampleEnvironment': allTraits.data[i].sampleEnvironment,
                                'size': allTraits.data[i].totalMB,
                                'numberOfSequences': allTraits.data[i].numReads
                            });
                        }
                        if (rowsToAdd.length === nbRorwsToAdd) {
                            createDataTable(rowsToAdd);
                            traitsDownload();
                        }

                    } else {
                        $("#errorMessage").show();
                    }

                }).fail(function() {
	                $("#errorMessage").show();
	                $("#simple-traits-table").hide();
            });


            function createDataTable(data) {
                var tableData = data || {};
                var table = new Megx.MegxTable("simple-traits-table", columns, buttons,
                    url, tableData, datatablesConfigOverride);
            };

            function traitsDownload() {

                var traitsTable = $(".megx_dataTable");

                $(document).on('click', '.downloadTrait', function() {
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

                $("[name*='simple-traits-table_length']").after('<a class="selectAllTraitsLnk" href="#">Select all</a>');
                $("[name*='simple-traits-table_length']").after('<a class="deselectAllTraitsLnk" href="#">Deselect all</a>');

                $('a.selectAllTraitsLnk').click(function() {
                    var allRows = traitsTable.dataTable().fnGetNodes();
                    $.each(allRows, function(i, row) {
                        var selectChb = $(row).find('input.downloadTrait');
                        if (!$(selectChb).prop('checked')) {
                            $(row).addClass('row_selected');
                            $(selectChb).prop('checked', true);
                        }
                    });
                    $(this).hide();
                    $('a.deselectAllTraitsLnk').show();
                    return false;
                });

                $('a.deselectAllTraitsLnk').click(function() {
                    var allRows = traitsTable.dataTable().fnGetNodes();
                    $.each(allRows, function(i, row) {
                        var selectChb = $(row).find('input.downloadTrait');
                        if ($(selectChb).prop('checked')) {
                            $(row).removeClass('row_selected');
                            $(selectChb).prop('checked', false);
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
                    width: 400,
                    modal: true,
                    title: "Download traits",
                    buttons: {
                        Ok: function() {
                            $(this).dialog("close");
                        }
                    }
                });

                $('.download-selected').parent().click(function() {
                    var allRows = traitsTable.dataTable().fnGetNodes();
                    var selectedRows = $(allRows).filter('tr.row_selected');

                    if (selectedRows.length === 0) {
                        $("#noTraitsSelected").dialog('open');
                    } else {
                        var traitIds = [];
                        $.each(selectedRows, function(i, row) {
                            var traitId = $(row).find('input.downloadTrait').val();
                            traitIds.push(traitId);
                        });
                        $('input.traitIdsHolder').val(traitIds.join(','));
                        $('#sf').submit();
                    }
                    return false;
                });
            }

        })();
    });