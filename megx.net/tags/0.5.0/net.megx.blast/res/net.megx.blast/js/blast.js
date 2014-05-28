$(document).ready(function() {
	
	$('#blast').click(function(){
	
		$.ajax({
			  type: "POST",
			  url: ctx.siteUrl + '/ws/v1/megx-blast/v1.0.0/jobs',
			  headers: { 
		          "Accept" : 'text/csv',
		          "Content-Type": 'application/x-www-form-urlencoded; charset=UTF-8'
		        },
		      async: false,
			  data: { 
				  label : 'test_label',
			      numNeighbors : $( "#numNeighbors" ).val(),
			      toolLabel : 'blast+',
			      toolVer : '2.2.28',
			      programName : 'blastp',
			      biodbLabel : $('input:radio[name=blastDb]:checked').val(),
			      biodbVersion : '24-02-2014_aa',
			      rawFasta : $( '#raw_fasta' ).val(),
			      evalue : $( '#eValue' ).val()
			      },
		      success: function(data) {
		    	  var jsonobject = csvjson.csv2json(data);
		    	  document.location.href = ctx.siteUrl + '/gms/geographic-blast/job?id=' + jsonobject.rows[0].Id;
		    	
		      }
		});	  		
	});
	
	$('input:radio[name=blastDb]').click(function(event){
		
		console.log($('label[for=' + event.target.id + ']').text().trim());
		
		
		if(document.getElementById('unknowns').checked){
			$('#numberNeighbors').show();
			
		}else {
			$('#numberNeighbors').hide();
			$('#numNeighbors').val('1')
		}
		
	});
	
});