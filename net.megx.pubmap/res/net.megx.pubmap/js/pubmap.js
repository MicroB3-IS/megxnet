$(document).ready(function() {
	
	$.getJSON( ctx.siteUrl + "/ws/v1/pubmap/v1.0.0/all",
	        function(allArticles, textStatus, jqXHR ){
			
				if(allArticles != null){
					
					var nbRorwsToAdd = allArticles.data.length;
		        	var rowsToAdd = [];
		        	
		            for(var i = 0; i < allArticles.data.length; i++) {
		            	
		            	//parsing megxJson to get Title and Authors
		            	var megxBarJSON = JSON.parse(allArticles.data[i].megxBarJSON);
		            	var title = JSON.parse(megxBarJSON).title;
		            	var authors = JSON.parse(megxBarJSON).authors;
		            	
		            	var date = allArticles.data[i].created;
		            	var worldRegion = allArticles.data[i].worldRegion;
		            	var place = allArticles.data[i].place;
		            	var id = allArticles.data[i].pmid;
		            	
		            	//parsing xml to get the Journal Title
		            	var articleXML = allArticles.data[i].articleXML,
		            	xmlDoc = $.parseXML(articleXML),
		            	$xml = $(xmlDoc),
		            	$journal = $xml.find("Journal"),
		            	$title = $journal.find("Title");
		            	
		            	rowsToAdd.push({
		 	                'title': title || '',
		 	                'authors': authors || '',
		 	                'journal': $title.text() || '',
		 	                'date': date || '',
		 	                'worldRegion': worldRegion || '',
		 	                'place': place || '',
		 	                'id' : id
		 	            });
		            }
			    	if(rowsToAdd.length === nbRorwsToAdd){
			    		createDatatable(rowsToAdd)
			    	} 
					
				}else{
					$("#errorMessage").show();
				}
	        	   
	        }).error(function() {
	        	$("#errorMessage").show();
	        });
	
	function createDatatable(data){
		
		MegxDatatableWidget().createTable(
				{
					tableDivId:"articlesId",
					columns:["Title","Authors","Journal","Date","World Region","Place Name","Action"],
					aoColumns:[{
					            mData: 'title',
					            sClass: 'left data-pad',
					            sWidth: '165px'
					        }, {
					            mData: 'authors',
					            sClass: 'left data-pad',
					            sWidth: '145px'
					        }, {
					            mData: 'journal',
					            sClass: 'left data-pad',
					            sWidth: '145px'
					        }, {
					            mData: 'date',
					            sClass: 'left data-pad',
					            sWidth: '90px',
					           
					        }, {
					            mData: 'worldRegion',
					            sClass: 'left data-pad',
					            sWidth: '100px',
					           
					        }, {
					            mData: 'place',
					            sClass: 'left data-pad',
					            sWidth: '100px',
					           
					        }, { 
					    	 	mData: 'id',
					    	 	sClass: 'center data-pad',
					    	 	mRender: function(id){
						    		var viewUrl = 'http://www.ncbi.nlm.nih.gov/pubmed/' + id;
						 			return '<a target=\'_blank\' href=' + viewUrl + ' class=\'viewSampleClass\'>View Article</a>';
						    	},
					    	 	sWidth: '80px',
					    	 	bSortable: false
					    	}],
			    	data: data
					
				}
			);
		
	}
});