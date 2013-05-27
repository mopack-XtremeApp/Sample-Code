//var contextPath = "http://localhost:8080/assignment-security/";
var contextPath="http://moti-assignment.herokuapp.com/";

$(document).ready(function() {

	// init validation engine
	$("#form-login").validationEngine();

	$('#form-login').submit(function() {
		//alert('button clicked');
		if ($("#form-login").validationEngine('validate')) {
			$.ajax({
				url :  contextPath + "authenticate",
				type : "POST",
				dataType : "json",
				data : {
					"username" : $("#username").val(),
					"password" : $("#password").val()
				},
				success : function(data) {

					if(data.message == "SUPER USER"){
						window.location = contextPath + "securehtml/list_user.html";
					}else if(data.message == "USER"){
						window.location = contextPath + "normal_user.html";
					}else{
						alert("User not found");
					}
						
				},
				error : function(error) {
					alert('error' + JSON.stringify(error));
					console.log("Error:");
					console.log(error);
				}
			});

		}
		return false;
	});

});
