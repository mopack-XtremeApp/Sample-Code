//var contextPath = "http://localhost:8080/assignment-security/";
var contextPath="http://moti-assignment.herokuapp.com/";


$(document).ready(function() {


$("#link-logout-user").click(function() {
		$.ajax({
			url : contextPath+"logout",
			success : function(data) {
				alert("You have been successfully logged out.");
				
				window.location = contextPath + "login.html";
			},
			error : function(error) {
				alert(JSON.stringify(error));
				console.log(error);
			}
		});
	});

});
