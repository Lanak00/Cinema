$(document).on('click', '#logButton', function (event) {  
	event.preventDefault();
	var errorLabel = $('#errorLabel');
	var username = $('#username').val();
	var password = $('#password').val();
	
	if (username == null || username.trim() === ''){
		errorLabel.text("Morate popuniti sva polja.");
		return;
	}
	
	if (password == null || password.trim() === ''){
		errorLabel.text("Morate popuniti sva polja.");
		return;
	}
	
    var toSend = JSON.stringify({
    	"username": username,
        "password": password
    });
                            
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/login",
        dataType: 'text',
        contentType: "application/json",                          
        data: toSend,
        success: function (data) {
			if(data == "KORISNIK"){
				location.href = 'regularUser.html';
			}
			else if(data == "MENADZER"){
				location.href = 'ManagerPages/ManagerMovies.html';
			}
			else if(data == "ADMINISTRATOR"){
				location.href = 'AdministratorPages/AdministratorMovies.html';
			}
        },
        error: function (data) {
            errorLabel.text("Korisnicko ime ili lozinka nisu ispravni.");
        }
    });
});


$(document).on('input', '#username', function() { $('#errorLabel').text(" "); });

$(document).on('input', '#password', function() { $('#errorLabel').text(" "); });

