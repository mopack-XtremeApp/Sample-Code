var contextPath = "http://rsplws150:8080/spring-hibernate-template/";

$(document).ready(function() {

	$("#btn-create-user").click(function() {

		// init validation engine
		$("#form-add-update-user").validationEngine();

		if ($("#form-add-update-user").validationEngine('validate')) {
			$.ajax({
				url : contextPath+"secure/user/addUser",
				type : "POST",
				dataType : "json",
				data : {
					"username" : $("username").val(),
					"password" : $("password").val(),
					"firstName" : $("firstName").val(),
					"lastName" : $("lastName").val(),
					"emailAddress" : $("emailAddress").val()
				},
				success : function(data) {
					if(data.message == "Success"){
						window.location = contextPath + "list_user.html";
					}else{
						alert("Currently we are not able to create the user. Please try again later");
						window.location = contextPath + "list_user.html";
					}
				},
				error : function(error) {
					console.log("Error:");
					console.log(error);
				}
			});
		}
	});
});