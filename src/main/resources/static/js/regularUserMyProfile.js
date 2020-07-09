
$(document).ready(function () {     
    $.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/getLoggedUser",                 
        dataType: "json",                                           
        success: function (data) {
            $('#firstName').text(data.firstName);
            $('#lastName').text(data.lastName);
            $('#username').text(data.username);
            $('#email').text(data.email);
            $('#phone').text(data.phone);
            $('#birthday').text(data.birthDate);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });   
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