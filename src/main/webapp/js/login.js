
var contextPath = "http://rsplws150:8080/spring-hibernate-template/";

$(document).ready(function() {

	// init validation engine
	$("#form-login").validationEngine();

	$("#btn-signin").click(function() {
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
						window.location = contextPath + "list_user.html";
					}else if(data.message == "USER"){

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
	});

});
