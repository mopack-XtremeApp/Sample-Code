//var contextPath = "http://localhost:8080/assignment-security/";
var contextPath="http://moti-assignment.herokuapp.com/";

$(document).ready(function() {

	var username = jQuery.url().param("username");

	if(username){
		
		$("#btn-add-update-user").html('Update User');
		
		$.ajax({
			url : contextPath+"secure/user/" + username,
			type : "GET",
			dataType : "json",
			success : function(data) {
				
				$("#userid").val(data.id);
				
				$("#username").attr("disabled", "disabled");
				$("#username").val(data.username);
				$("#password").css('display','none');
				$("#firstName").val(data.firstName);
				$("#lastName").val(data.lastName);
				$("#emailAddress").val(data.emailAddress);
			},
			error : function(error) {
				console.log("Error:");
				console.log(error);
			}
		});
	}
	
	
	$('#btn-cancel-add-update-user').click(function() {
		window.location = contextPath + "securehtml/list_user.html";
	});
	
	$('#form-add-update-user').submit(function() {

		// init validation engine
		$("#form-add-update-user").validationEngine();

		if ($("#form-add-update-user").validationEngine('validate')) {
			
			var userid = $("#userid").val();

			var url = "secure/user/addUser";
			
			if(userid && userid != ''){
				url = "secure/user/updateUser";
			}
			
			
			$.ajax({
				url : contextPath + url,
				type : "POST",
				dataType : "json",
				data : {
					"username" : $("#username").val(),
					"password" : $("#password").val(),
					"firstName" : $("#firstName").val(),
					"lastName" : $("#lastName").val(),
					"emailAddress" : $("#emailAddress").val()
				},
				success : function(data) {
					if(data.message == "Success"){
						window.location = contextPath + "securehtml/list_user.html";
					}else if(data.message == "Already Exist"){
						alert("Username "+$("#username").val()+" is already exist. Please choose different username");
						
					}else{
						alert("Currently we are not able to create the user. Please try again later");
						window.location = contextPath + "securehtml/list_user.html";
					}
				},
				error : function(error) {
					console.log("Error:");
					console.log(error);
				}
			});
		}
		
		return false;
	});
});