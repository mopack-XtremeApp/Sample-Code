//var contextPath = "http://localhost:8080/assignment-security/";
var contextPath="http://moti-assignment.herokuapp.com/";


$(document).ready(function() {
	
	$.ajax({
		url : contextPath+"secure/user/findAll",
		success : function(data) {
			var currentIndex=0;
			 
			while(currentIndex<data.length){
				var tr=$('<tr></tr>');
				$('<td>'+data[currentIndex].firstName+'</td>').appendTo(tr);
				$('<td>'+data[currentIndex].lastName+'</td>').appendTo(tr);
				$('<td>'+data[currentIndex].username+'</td>').appendTo(tr);
				$('<td>'+data[currentIndex].emailAddress+'</td>').appendTo(tr);
				
				var editUserUrl = contextPath+"securehtml/add_update_user.html?username=" + data[currentIndex].username; 
				
				$('<td><a href=' + editUserUrl + '>Edit</a></td>').appendTo(tr);
				
				//If user is ADMIN, then delete link should not be displayed
				if(data[currentIndex].username!='ADMIN'){
					$('<td><a style="cursor:pointer;" onclick="javascript:deleteUser(\''+data[currentIndex].username+'\')">Delete</a></td>').appendTo(tr);
				}
				currentIndex=currentIndex+1;
				
				//This is not working. New TR does not appand to table
				tr.appendTo('#user-table  > tbody:last');
			}
			
		},
		error : function(error) {
			alert(JSON.stringify(error));
			console.log(error);
		}
	});
	
	
	$("#link-add-user").click(function() {
		window.location = contextPath + "securehtml/add_update_user.html";	 
	});
	
	deleteUser = function(username){
		$.ajax({
			url : contextPath+"secure/user/deleteUser/"+username,
			type : "DELETE",
			
			success : function(data) {
				alert("The user "+username+" successfully deleted");
				window.location = contextPath + "securehtml/list_user.html";
				
			},
			error:function(data){
				alert("We are not able to delete the user "+username+"");
			}
		});
	}
	
});