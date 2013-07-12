BlastResultPage = function() {
	
	// div that is created when seq alignment is loaded
	// keep track to remove current when new one is loaded
	this.activeSeqAlignmentDiv = null;
	
	// keep references of all checked row ids
	// of matching sequences in the table
	this.exportcheckboxes = [];
};

BlastResultPage.prototype = {
	loadSeqAlignment: function(el, id) {
		var loadingDivHTML = $('#seqAlignmentLoading').html();
		el.html(loadingDivHTML);
		
		var reqData = {
			jobId: '$job.jobId', 
			seqId: id	
		}
		
		var responseHandler = function(obj) { 
			var tpl = $('#sa_tpl').html();
			var html = Mustache.render(tpl, obj);
			el.html(html);
		}
		
		$.getJSON("$ctx.siteUrl/ws/blast/getSequenceAlignment", reqData, responseHandler);
	},
	
	onTableRowClick: function(nRow, aData, iDisplayIndex) {
		var tbl = $(nRow).closest('table')
		$('tr', tbl).removeClass('selected');
		$(nRow).addClass('selected');
		
		if(this.activeSeqAlignmentDiv) {
			this.activeSeqAlignmentDiv.remove();
		}
		
		this.activeSeqAlignmentDiv = $('<div class="panel_ct"/>');
		this.activeSeqAlignmentDiv.appendTo('#blast_ct');
		this.loadSeqAlignment(this.activeSeqAlignmentDiv, aData.id);
	},
	
	onRowCreated: function(nRow, aData, iDisplayIndex) {
		var me = this;
		//listen for click event on a row
		$(nRow).click(function() {
			me.onTableRowClick(nRow, aData, iDisplayIndex);
		});
		
		//tooltip on evalue hover
		$('a.eval_tt', nRow).hover(function() {
			if(!this.tt) {
				var pos = $(this).offset();
				this.tt = $('<div class="eval_tooltip" />')
					.html('<div class="title">Sequence alignment data:</div><pre>'+$(this).attr('tooltip')+'</pre>')
					.appendTo(document.body);
				
				this.tt.css('top', pos.top-$(this.tt).height()/2+$(this).height()/2);
				this.tt.css('left', pos.left+$(this).width()+5);
			}
			this.tt.show();
		}, function() {
			this.tt.hide();
		});
		
		// checkbox change listener
		$('.export_chk', nRow).change(function() {
			if(this.checked) {
				me.exportcheckboxes.push(aData.id);
			} else {
				var index = me.exportcheckboxes.indexOf(aData.id);
				if(index>=0) {
					me.exportcheckboxes.splice(index, 1);
				}
			}
		});
	},
	
	createExportButton: function() {
		var me = this;		
		// export menu
		var timerId = 0;
		var popup = $('<div class="menu_ct_div"></div>');
		var showPopup = function() {
			var numSelected = me.exportcheckboxes.length;
			var html = Mustache.render($('#export_menu_tpl').html(), {
				numSelected: numSelected,
				itLabel: numSelected==1 ? 'item' : 'items'
			});
			popup.html(html);
			$('a.exportLink',popup).click(function() {
				me.exportSelected($(this).html());
			});
			popup.fadeIn();
		};
		var hover_fnk = function() { 
			clearTimeout(timerId); 
		}; 
		var out_fnk = function() {
			clearTimeout(timerId);
			timerId = setTimeout(function() { popup.fadeOut() }, 500);
		};
		
		// add export button
		$('#bexp').css("float", "left")
			.css("padding", 2).css('margin-right', 50)
			.html('<button style="padding: 2px;"> Export ▼ </button>')
			.append(popup)
			.click(function(e) {
				showPopup();
				e.stopPropagation();
			})
			.hover(hover_fnk, out_fnk);
		popup.hover(hover_fnk, out_fnk);
		
		$(document).click(function() {
			popup.fadeOut();
		});
	},
	
	onTableRendered: function() {
		this.createExportButton();
	},
	
	exportSelected: function(txt) {
		alert("TODO export: " + txt + '\nRows:' + this.exportcheckboxes);
	},
	
	// cell renderers
	renderEvalueWithAlignemnt: function(data) {
		return '<a class="eval_tt" href="#" onclick="return false;" tooltip="'+data.alignment+'">'+data.eval+'</a>';
	},

	subjectTblLink: function(data){
		return '<a href="#" onclick="return false;">'+data+'</a>';
	},

	renderExportCheckBox: function() {
		return '<input class="export_chk" type="checkbox" onclick="event.stopPropagation();"/>';
	}
};

//global blast page
gbPage = new BlastResultPage();