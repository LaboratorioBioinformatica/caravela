<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:attribute name="headImport">
		<script src=<c:url value="/js/feature-viewer@0.1.35.js"/>></script>
	</jsp:attribute>
    <jsp:body>
    <div class="container-fluid">
		<div class="page-header">
			<h1>Contig analyze - ${sample.name} - ${contig.reference} (${rank}) </h1>
		</div>

			<div class="panel-heading">Contig</div>
		 	<table class="table">
			    <thead>
			    	<tr>
				    	<th>Size</th>
				    	<th>Total number of reads</th>
				    	<th>Total number of reads classified</th>
				    	<th>Total number of features</th>
				    </tr>
				</thead>
				<tbody>
					<tr>
						<td>${contig.size}</td>
						<td>${contig.numberOfreads}</td>
						<td>${contig.numberOfReadsClassified}</td>
						<td>${contig.numberOfFeatures}</td>
					</tr>
				</tbody>
		 	</table>

	 		<div>
	 			<div class="col-md-10 col-md-offset-1" style="height:800px;vertical-align:top;margin-top:15px;" id="contigViewer">
					<div class="btn-group" role="group" aria-label="...">
	  					<button type="button" class="btn btn-default"><a href="<c:url value="/contig/view/${contig.id}/species/${viewingMode}"/>">Species</a></button>
	  					<button type="button" class="btn btn-default"><a href="<c:url value="/contig/view/${contig.id}/genus/${viewingMode}"/>">Genus</a></button>
							<button type="button" class="btn btn-default"><a href="<c:url value="/contig/view/${contig.id}/family/${viewingMode}"/>">Family</a></button>
							<button type="button" class="btn btn-default"><a href="<c:url value="/contig/view/${contig.id}/order/${viewingMode}"/>">Order</a></button>

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
	 </div>

	<script type="text/javascript">
	var contigSize = ${contig.size};
	var features = ${features};
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

	//Add some features
    ft.addFeature({
        data: [{x:1,y:contigSize}],
        name: "contig",
        className: "contigClass",
        color: "#b0133b",
        type: "rect"
    });

	//Add some features
    ft.addFeature({
        data: features,
        name: "Features",
        className: "features",
        color: "#fd8d3c",
        type: "rect"
    });
	
    ft.onFeatureSelected(function (d) {
    	
        console.log(d.detail);
    });

$(document).ready(function(){

	var urlReadsOnCOntig = '<c:url value="/contig/${viewingMode}/${contig.id}/${rank}"/>';
	var urlOverlapTaxonOnCOntig = '<c:url value="/contig/overlapTaxaOnContig/${contig.id}/${rank}"/>';
	var urlUndefinedRegionsOnCOntig = '<c:url value="/contig/undefinedRegionsOnContig/${contig.id}/${rank}"/>';
	var urlUnknowRegionsOnContig = '<c:url value="/contig/unknowRegionsOnContig/${contig.id}/${rank}"/>';
	var urlBoundariesRegionsOnContig = '<c:url value="/contig/boundariesRegionsOnContig/${contig.id}/${rank}"/>';
	
	
	var queueName = 'featureQueue';
	
	addCallFeatureViewerToQueue(queueName, randColor(), 'multipleRect', urlReadsOnCOntig);
	addCallFeatureViewerToQueue(queueName, "#FF0000", 'path', urlOverlapTaxonOnCOntig);
	addCallFeatureViewerToQueue(queueName, "#FF0000", 'multipleRect', urlUndefinedRegionsOnCOntig);
	addCallFeatureViewerToQueue(queueName, "#CCCCCC", 'multipleRect', urlUnknowRegionsOnContig);
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
    			
    			ft.addFeature({
    		        data: v,
    		        name: k,
    		        className: "reads_"+k.substring(0,3),
    		        color: color,
    		        type: fvType
    		    });
    	   	});
    		 // activate the next ajax call when this one finishes
    		$(document).dequeue(qName);
    	}});
        
    });
}
</script>


    </jsp:body>
</t:default>
