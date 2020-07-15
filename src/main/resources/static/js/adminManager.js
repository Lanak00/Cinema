$(document).ready(function () { 
    $.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/managers",                 
        dataType: "json",                                           
        success: function (data) {
        	populateTable(data);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#addButton', function () {
	showAddFormAndCloseOther();
});


$(document).on('click', '.btnDelete', function () {            
    
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/managers/delete/" + this.id,  
        success: function () {
            $.ajax({
                type: "GET",                                                
                url: "http://localhost:8080/api/managers",                 
                dataType: "json",                                           
                success: function (data) {
                	populateTable(data);
                	alert("Menadzer je uspesno obrisan!"); 
                },
                error: function (data) {
                    console.log("ERROR : ", data);
                }
            });
            
            
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#registerForm button', function () {
	event.preventDefault();
	
    var firstName = $("#registerForm #firstName").val();
    var lastName = $("#registerForm #lastName").val();
    var username = $("#registerForm #username").val();
    var password = $("#registerForm #password").val();
    var email = $("#registerForm #email").val();
    var phone = $("#registerForm #phone").val();
    var birthYear = $("#registerForm #birthYear").val();
    var birthMonth = $("#registerForm #birthMonth").val();
    var birthDay = $("#registerForm #birthDay").val();
    var birthDate = birthDay + "." + birthMonth + "." + birthYear + "."; 
    
    var toSend = JSON.stringify({
    	"id": "-1",
        "username": username,
        "password": password,
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "phone": phone,
        "birthDate": birthDate,
        "role": "MENADZER"
    });
    
    $.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/managers",                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () {
        	$.ajax({
			    type: "GET",                                                
			    url: "http://localhost:8080/api/managers",                 
			    dataType: "json",                                           
			    success: function (data) {
			    	populateTable(data);
			    	alert("Menager" + username + "je uspesno registrovan!");
			    },
			    error: function (data) {
			        console.log("ERROR : ", data);
			    }
			});
    
    		showOtherAndCloseForm();
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
    
    
});


function populateTable(data) {
	$('#managers').empty();
    var header = "<tr>" + 
    "<th>Korisnicko ime</th>" + 
    "<th>Ime</th>" +
    "<th>Prezime</th>" + 
    "<th>Email</th>" + 
    "<th>Phone</th>" + 
    "<th>Birth date</th>" +
    "</tr>";
    
    $('#managers').append(header);
    
    for (i = 0; i < data.length; i++) {
    	if(data[i]['role'] != "MENADZER"){
    		continue;
        }
    	
        var row = "<tr>";                                   
        row += "<td>" + data[i]['username'] + "</td>";     
        row += "<td>" + data[i]['firstName'] + "</td>";
        row += "<td>" + data[i]['lastName'] + "</td>";
        row += "<td>" + data[i]['email'] + "</td>";
        row += "<td>" + data[i]['phone'] + "</td>";
        row += "<td>" + data[i]['birthDate'] + "</td>";
       	
        var btnObrisi = "<button class='btnDelete btn waves-effect waves-light' id = " + data[i]['id'] + ">Obrisi</button>";
        row += "<td>" + btnObrisi + "</td>"; 

        $('#managers').append(row);                           
    }
}

function showAddFormAndCloseOther() {
	var form = $(".formAdd");
	var otherStuff = $("#managersStuff");
	otherStuff.hide();
    form.show();
}

function showOtherAndCloseForm() {
	var form = $(".formAdd");
	var otherStuff = $("#managersStuff");
	otherStuff.show();
    form.hide();
}

$(document).on('click', '#btnBackFormAdd', function () {
	showOtherAndCloseForm();
});

$(document).on('click', '#mov', function () {
	location.href = '/AdministratorPages/AdministratorMovies.html';
});

$(document).on('click', '#cin', function () {
	location.href = '/AdministratorPages/AdministratorCinemas.html';
});

$(document).on('click', '#man', function () {
	location.href = '/AdministratorPages/AdministratorManagers.html';
});

$(document).on('click', '#logout', function () {
	$.ajax({
        type: "GET",                                               
        url: "http://localhost:8080/api/logout",                                                          
        success: function () {
           window.location.href = "/";
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});