<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:attribute name="headImport">
		<script src=<c:url value="/js/feature-viewer@0.1.35.js"/>></script>
	</jsp:attribute>
    <jsp:body>
    <div class="container-fluid">
    <div class="panel panel-default">
				<div class="panel-heading">
					<strong>Contig Information</strong>
				</div>
				<div class="panel-body">
					<p><strong>Sample:</strong> ${sample.name}</p>
					<p><strong>Contig:</strong> ${contig.reference}</p>
					<p><strong>Contig size</strong>:  ${contig.size} | Reads: ${contig.numberOfreads} | Reads classifieds ${contig.numberOfReadsClassified} | Features: ${contig.numberOfFeatures}</p>
					
					<div id="feature-detail"></div>
					
				</div>	
	</div>
 		<div class="col-md-10 col-md-offset-1" style="height:1000px;vertical-align:top;margin-top:10px;" id="contigViewer">
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
 		</div>
	 </div>


	<script type="text/javascript">
	var FeatureViewer = require("feature-viewer");
	var ft = new FeatureViewer('${contig.sequence}',
	    "#contigViewer", {
	    showAxis: true,
	    showSequence: true,
	    brushActive: true,
	    toolbar:true,
	    bubbleHelp:true,
	    zoomMax:10
	});
	
    ft.onFeatureSelected(function (d) {
    	
    	console.log(d);
    	
    	 $.ajax({url: '<c:url value="/feature/philodist/"/>' + d.detail.id, success: function(result){
             $("#feature-detail").empty().append("<p>" + result.lineage + "</p>");
         }});
    	
    	
        console.log(d.detail);
        console.log(d.detail.id);
        
    });

$(document).ready(function(){

	var urlFeaturesOnContig = '<c:url value="/contig/featureOnContig/${contig.id}"/>';
	var urlContigViewer = '<c:url value="/contig/viewer/${contig.id}"/>';
	var urlReadsOnCOntig = '<c:url value="/contig/${viewingMode}/${contig.id}/${rank}"/>';
	var urlOverlapTaxonOnCOntig = '<c:url value="/contig/overlapTaxaOnContig/${contig.id}/${rank}"/>';
	var urlUndefinedRegionsOnCOntig = '<c:url value="/contig/undefinedRegionsOnContig/${contig.id}/${rank}"/>';
	var urlUnknowRegionsOnContig = '<c:url value="/contig/unknowRegionsOnContig/${contig.id}/${rank}"/>';
	var urlBoundariesRegionsOnContig = '<c:url value="/contig/boundariesRegionsOnContig/${contig.id}/${rank}"/>';
	
	
	var queueName = 'featureQueue';
	
	
	addCallFeatureViewerToQueue(queueName, "#fd8d3c", 'rect', urlFeaturesOnContig);
	addCallFeatureViewerToQueue(queueName, "#b0133b", 'rect', urlContigViewer);
	
	addCallFeatureViewerToQueue(queueName, randColor(), 'multipleRect', urlReadsOnCOntig);
	addCallFeatureViewerToQueue(queueName, "#FF0000", 'path', urlOverlapTaxonOnCOntig);
	addCallFeatureViewerToQueue(queueName, "#FF0000", 'multipleRect', urlUndefinedRegionsOnCOntig);
	addCallFeatureViewerToQueue(queueName, "#A0A0A0", 'multipleRect', urlUnknowRegionsOnContig);
	addCallFeatureViewerToQueue(queueName, "#000000", 'multipleRect', urlBoundariesRegionsOnContig);
	
	
	$(document).dequeue(queueName);
	
});

function randColor(){
	return '#'+Math.floor(Math.random()*16777215).toString(16);
}

function addCallFeatureViewerToQueue(qName, color, fvType, apiURL) {
	
    $(document).queue(qName, function() {
    	$.ajax({url:apiURL, success: function(result){
    		$.each(result, function(k, v) {
    			
    			console.log(k + ":" + v.length);
    			
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
</script>


    </jsp:body>
</t:default>
