$(document).ready(function() {
	
	$('#div-form-sample').hide();
	
	$('#select-treatment-id').change(function(){
		var baseURL = "treatment/sample/list/" 
		var optionSelected = $('#select-treatment-id option:selected').val();
		
		$.getJSON(baseURL+optionSelected, function( data ) {
			var items = [];
			  $.each( data, function( key, val ) {
				  items.push( "<option value='" + data[key].id + "'>" + data[key].name + "</option>" );
			  });
			  $('#select-sample').empty();
			  $('#select-sample').append(items);
			  $('#div-form-sample').show();
		});
	});
});