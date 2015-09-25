<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:default>
    <jsp:body>
    	<div class="mdl-grid">
		  <div class="mdl-cell--2-col-desktop"></div>
		  <div class="mdl-cell--6-col-desktop">
			  <form action="<c:url value='/login/login'/>" method="post">
			  		<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
						<select class="mdl-textfield__input" name="treatment" id="treatment">
						  <option value="volvo">ZC3b</option>
						  <option value="saab">zc4</option>
						</select>
						<label class="mdl-textfield__label" for="treatment">Treatment Name</label>
					</div>
		  			<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					  <input class="mdl-textfield__input" name="sample.name" type="text" id="sampleName" />
					  <label class="mdl-textfield__label" for="sampleName">Sample Name</label>
					</div>
					<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">  
					  <input class="mdl-textfield__input" name="sample.inputFileCaravela" type="file" id="inputFileCaravela" />
					  <label class="mdl-textfield__label" for="inputFileCaravela">Input File</label>
					</div>
					<div class="mdl-textfield">
						<input class="mdl-button" type="submit" name="ok">
					</div>
	  			</form>
	  		</div>
		</div>
        
	    	
  		
  		
    </jsp:body>
</t:default>


