<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:attribute name="headImport">
		<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/bootstrap-table.min.css">
		<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/bootstrap-table.min.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.11.0/locale/bootstrap-table-zh-CN.min.js"></script>
		
		
	</jsp:attribute>
    <jsp:body>
    <div class="container">
	    <div class="page-header">
			<h1><span class="label label-default">Contig</span> <small>information - ${contig.reference}</small></h1> 
		</div>
		<div class="panel panel-default">
	  			<div class="panel-body text-center">
	  				<div class="row">
					  <div class="col-md-3"><strong>Sample:</strong> ${sample.name}</div>
					  <div class="col-md-2"><strong>Contig size</strong>:  ${contig.size}</div>
					  <div class="col-md-2"><strong>Reads:</strong> ${contig.numberOfreads}</div>
					  <div class="col-md-3"><strong>Reads classifieds</strong> ${contig.numberOfReadsClassified}</div>
					  <div class="col-md-2"><strong>Features:</strong> ${contig.numberOfFeatures}</div>
					</div>
	  			</div>
			</div> 
   		<div class="btn-group" role="group" aria-label="">
 					<a class="btn btn-default" role="button" href="<c:url value="/contig/view/${contig.id}/species/${viewingMode}"/>">Species</a>
 					<a class="btn btn-default" role="button" href="<c:url value="/contig/view/${contig.id}/genus/${viewingMode}"/>">Genus</a>
				<a class="btn btn-default" role="button" href="<c:url value="/contig/view/${contig.id}/family/${viewingMode}"/>">Family</a>
				<a class="btn btn-default" role="button" href="<c:url value="/contig/view/${contig.id}/order/${viewingMode}"/>">Order</a>
				<a class="btn btn-default" role="button" href="<c:url value="/contig/view/${contig.id}/class/${viewingMode}"/>">Class</a>
				<a class="btn btn-default" role="button" href="<c:url value="/contig/view/${contig.id}/phylum/${viewingMode}"/>">phylum</a>
 					<div class="btn-group" role="group">
	    				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	    					Contig Viewing Mode
	      					<span class="caret"></span>
	    				</button>
	    				<ul class="dropdown-menu">
		      				<li><a href="<c:url value="/contig/view/${contig.id}/${rank}/readsOnContig"/>">reads</a></li>
		      				<li><a href="<c:url value="/contig/view/${contig.id}/${rank}/consensusReadsOnContig"/>">consensus reads</a></li>
	    				</ul>
  				</div>
  					
		</div>
		<h3><span class="label label-default pull-right"> ${rank} </span></h3>
		<div id="contigViewer">
		</div>
		
		<!-- FEATURE DETAIL MODAL -->
		<div class="modal fade" id="FeatureModal" tabindex="-1" role="dialog" aria-labelledby="Feature detail">
		  <div class="modal-dialog modal-lg" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h2 class="modal-title" id="myModalLabel"><span class="label label-success">Feature</span> <small>detail</small> </h2>
		      </div>
		      <div class="modal-body">
		      <div class="panel panel-default">
	  				<div class="panel-body"> 
						<div id="feature-detail"></div>	  				
	  				</div>		
				</div>
				<div class="panel panel-default">
					<div class="panel-heading"><strong>Annotations</strong></div>
					<table id="annotations" data-toggle="annotations" class="table table-hover">
						<thead>
							<tr> 
								<th data-field="type">Type</th> <th data-field="name">Name</th> <th data-field="identity">Identity</th> <th data-field="alignLength">Align Length</th> <th data-field="queryStart">Query Start</th> <th data-field="queryEnd">Query End</th> <th data-field="evalue">E-value</th> <th data-field="bitScore">Bit Score</th> 
							</tr>
						</thead>	
						<tbody>
						</tbody>
					</table>
				</div>			        
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- READ ON CONTIG DETAIL MODAL -->
		<div class="modal fade" id="readOnContigModal" tabindex="-1" role="dialog" aria-labelledby="Read On Contig Detail">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h2 class="modal-title" id="myModalLabel"><span class="label label-success">Read On Contig</span> <small>detail</small> </h2>
		      </div>
		      <div class="modal-body">
		      <div class="panel panel-default">
		      		<div class="panel-heading"><strong>Alignment Read</strong></div>
	  				<div class="panel-body"> 
						<div id="alignment-read-detail"></div>	  				
	  				</div>		
				</div>
				<div class="panel panel-default">
					<div class="panel-heading"><strong>Taxon Read</strong></div>
					<div class="panel-body">
						<div id="taxon-read-detail"></div>
					</div>
				</div>			        
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
	</div>	
   
 		
 		

<script type="text/javascript">

$(document).ready(function(){
	
	$("#modalButton").click(function(){
        $("#myModal").modal();
    });
	
	
	var ft = new FeatureViewer('${contig.sequence}',
	    "#contigViewer", {
	    showAxis: true,
	    showSequence: true,
	    brushActive: true,
	    toolbar:true,
	    bubbleHelp:true,
	    zoomMax:50
	});
	

	ft.onFeatureSelected(function (d) {
    	if (d.detail.description.toLowerCase().indexOf("feature") >= 0){
	    	showFeatureDetail(d.detail.id);
    	} else if (d.detail.description.toLowerCase().indexOf("read") >= 0){
	    	showReadDetail(d.detail.id);
    	}
        
    });

	
	var urlFeaturesOnContig = '<c:url value="/contig/featureOnContig/${contig.id}"/>';
	var urlContigViewer = '<c:url value="/contig/viewer/${contig.id}"/>';
	var urlReadsOnCOntig = '<c:url value="/contig/${viewingMode}/${contig.id}/${rank}"/>';
	var urlOverlapTaxonOnCOntig = '<c:url value="/contig/overlapTaxaOnContig/${contig.id}/${rank}"/>';
	var urlUndefinedRegionsOnCOntig = '<c:url value="/contig/undefinedRegionsOnContig/${contig.id}/${rank}"/>';
	var urlUnknowRegionsOnContig = '<c:url value="/contig/unknowRegionsOnContig/${contig.id}/${rank}"/>';
	var urlBoundariesRegionsOnContig = '<c:url value="/contig/boundariesRegionsOnContig/${contig.id}/${rank}"/>';
	
	
	var queueName = 'featureQueue';
	
	
	addCallFeatureViewerToQueue(ft, queueName, "#fd8d3c", 'rect', urlFeaturesOnContig);
	addCallFeatureViewerToQueue(ft, queueName, "#b0133b", 'rect', urlContigViewer);
	
	addCallFeatureViewerToQueue(ft, queueName, randColor(), 'multipleRect', urlReadsOnCOntig);
	addCallFeatureViewerToQueue(ft, queueName, "#FF0000", 'path', urlOverlapTaxonOnCOntig);
	addCallFeatureViewerToQueue(ft, queueName, "#FF0000", 'multipleRect', urlUndefinedRegionsOnCOntig);
	addCallFeatureViewerToQueue(ft, queueName, "#A0A0A0", 'multipleRect', urlUnknowRegionsOnContig);
	addCallFeatureViewerToQueue(ft, queueName, "#000000", 'multipleRect', urlBoundariesRegionsOnContig);
	
	
	$(document).dequeue(queueName);
	
	$('#annotations').bootstrapTable({});
	
});

function randColor(){
	return '#'+Math.floor(Math.random()*16777215).toString(16);
}

function addCallFeatureViewerToQueue(ft, qName, color, fvType, apiURL) {
	
    $(document).queue(qName, function() {
    	$.ajax({url:apiURL, success: function(result){
    		$.each(result, function(k, v) {
    			
    			//console.log(k + ":" + v.length);
    			
    			if(v.length > 0){
    				ft.addFeature({
        		        data: v,
        		        name: k,
        		        className: "reads_"+k.substring(0,3),
        		        color: color,
        		        type: fvType
        		    });	
    			}
    			
    	   	});
    		 // activate the next ajax call when this one finishes
    		$(document).dequeue(qName);
    	}});
        
    });
}

function showReadDetail(readId){
	
	
	console.log("read id: "+ readId);
	
	
	
	$.ajax({url: '<c:url value="/read/"/>' + readId, success: function(readOnContigResult){
		var taxonReadDetail = $("#taxon-read-detail"),
		alignmentReadDetail = $("#alignment-read-detail");
		
		console.log(readOnContigResult)
		if(readOnContigResult){
			
			alignmentReadDetail.empty().append("<p> <strong> Start: </strong>" + readOnContigResult.start + "<strong> End: </strong>"  + readOnContigResult.end + "<strong> CIGAR: </strong>"  + readOnContigResult.cigar + "<strong> FLAG: </strong> </p>");
			
	        $.each(explainFlags(readOnContigResult.flag), function(key, value){
	        	alignmentReadDetail.append("<p>" + value + "</p>");
	        });
	        
	        if(readOnContigResult.taxon){
	        	taxonReadDetail.empty().append("<p> <strong> Scientific Name: </strong>" + readOnContigResult.taxon.scientificName + "<strong> Hank: </strong>"  + readOnContigResult.taxon.hank + "</p>");
	        } else {
	        	taxonReadDetail.empty().append("<p> <strong> No Taxon</strong> </p>");
	        }
	        $("#readOnContigModal").modal();
		}
    }});
	
}
function showFeatureDetail(featureId){
	console.log("annotation id: "+ featureId);
	
	$("#feature-detail").empty();
	
	$.ajax({url: '<c:url value="/feature/"/>' + featureId, success: function(featureResult){
        $("#feature-detail").append("<p> <strong> Type: </strong>" + featureResult.type + " <strong>Start: </strong>"  
        		+ featureResult.start + "<strong> End: </strong>" + featureResult.end + "<strong> Strand: </strong>" + featureResult.strand + "</p>");
    }});
	
	$.ajax({url: '<c:url value="/feature/geneproduct/"/>' + featureId, success: function(geneProductResult){
		if(geneProductResult){
	        $("#feature-detail").append("<p> <strong> Gene Product assigned: </strong>" + geneProductResult.product + "<strong> Source: </strong>"  + geneProductResult.source + "</p>");
		}
    }});
	
	$.ajax({url: '<c:url value="/feature/philodist/"/>' + featureId, success: function(philodistResult){
		if(philodistResult){
	        $("#feature-detail").append("<p> <strong> IMG Philodist - Lineage: </strong>" + philodistResult.lineage + "<strong> Identity: </strong>"  + philodistResult.identity + "</p>");
		}
    }});

	$.ajax({url: '<c:url value="/feature/annotations/"/>' + featureId, success: function(annotationsResult){
		if(annotationsResult){
			$('#annotations').bootstrapTable('load', annotationsResult);
			$("#FeatureModal").modal();
			
		}
   	}});
}

// SUMARY FLAG
function explainFlags(flagValue) {
	var lstFlags = [["read paired", 0x1],
	                ["read mapped in proper pair", 0x2],
	                ["read unmapped", 0x4],
	                ["mate unmapped", 0x8],
	                ["read reverse strand", 0x10],
	                ["mate reverse strand", 0x20],
	                ["first in pair", 0x40],
	                ["second in pair", 0x80],
	                ["not primary alignment", 0x100],
	                ["read fails platform/vendor quality checks", 0x200],
	                ["read is PCR or optical duplicate", 0x400],
	                ["supplementary alignment", 0x800]];
	
    var summary = [];
    for(var i = 0; i < lstFlags.length; i++) {
        if(lstFlags[i][1] & flagValue) {
            summary.push(lstFlags[i][0]);
        } 
    }
    return summary;
} 
            

</script>


    </jsp:body>
</t:default>
