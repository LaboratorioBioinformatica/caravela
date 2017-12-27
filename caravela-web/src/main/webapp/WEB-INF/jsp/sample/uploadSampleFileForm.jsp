<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:default>
	<jsp:attribute name="headImport">
		<script src=<c:url value="/js/jquery-file-upload/vendor/jquery.ui.widget.js"/>></script>
		<script src=<c:url value="/js/jquery-file-upload/jquery.iframe-transport.js"/>></script>
		<script src=<c:url value="/js/jquery-file-upload/jquery.fileupload.js"/>></script>
	</jsp:attribute>
	<jsp:body>
    <div class="container">
	    
	    <div class="page-header">
	    	<div class="row">
  				<div class="col-md-6">
  					<h1><span class="label label-success">Upload</span> Sample file</h1>	
  				</div>
  				<div class="col-md-6">
  					<h1><span class="label label-default pull-right">${sample.name}</span></h1>	
  				</div>
			</div>
		</div>
		
		<form role="form" name="uploadSampleFileForm" id="uploadSampleFileForm" action="${linkTo[UploadController].uploadSampleFile}" method="post" enctype="multipart/form-data">
			<input type="hidden" name="sampleId" value="${sample.id}" id="sampleId" >
		  
		  <div class="form-group">
		    <!-- label class="control-label" for="sampleFile">Sample File</label-->
		    <input type="file" name="file" id="fileupload" >
		  </div>
		</form>
		<div class="progress">
  			<div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: ;"> </div>
		</div>
				
	</div>
<script type="text/javascript">
$('#uploadSampleFileForm').fileupload({
	dataType: 'json',
	start: function (e) {
		console.log("upload startd")
	},
	progressall : function(e, data) {
	    var progress = parseInt(data.loaded / data.total * 100, 10);
	    $('.progress-bar').css('width', progress +'%').attr('aria-valuenow', progress).text(progress +'%'); 
	},
	done: function (e, data) {
		console.log('upload finished!');
		$(location).attr('href', '${linkTo[SampleController].listByStudy}${sample.study.id}');
	}
	});
</script>	
   </jsp:body>
</t:default>


