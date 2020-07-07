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
    
    if(!firstName || !lastName || !username || !password || !email || !phone || !birthYear || !birthMonth || !birthDay){
    	alert("Sva polja moraju biti popunjena.");
    	return;
    }
    
    if(!( /^[a-zA-Z]+$/.test(firstName) )){
    	alert("Ime mora sadrzati iskljucivo slova.");
    	return;
    }
    
    if(!( /^[a-zA-Z]+$/.test(firstName) )){
    	alert("Prezime mora sadrzati iskljucivo slova.");
    	return;
    }
    
    if(username.length <= 6){
    	alert("Korisnicko ime mora imati barem 6 karaktera.");
    	return;
    }
    
    if(password.length <= 6){
    	alert("Lozinka mora imati barem 6 karaktera.");
    	return;
    }
    
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(String(email).toLowerCase())){
    	alert("Emali nije unet u ispravnom formatu.");
    	return;
    }
    
    if( !(/^\d+$/.test(phone)) )	{
    	alert("Telefon mora sardzati iskljucivo cifre.");
    	return;
    }
    
    var year = parseInt(birthYear);
    var month = parseInt(birthMonth);
    var day = parseInt(birthDay);
    
    if(year <= 1900 || year >= 2010){
    	alert("Godina mora biti broj izmedju 1900 i 2010.");
    	return;
    }
    
    if(month < 1 || month > 12 ){
    	alert("Mesec mora biti broj izmedju 1 i 12.");
    	return;
    }
    
    if(day < 1 || day > 31) {
    	alert("Greska nad poljem DAN");
    	return;
    }
    
    var toSend = JSON.stringify({
    	"id": "-1",
        "username": username,
        "password": password,
        "firstName": firstName,
        "lastName": lastName,
        "email": email,
        "phone": phone,
        "birthDate": birthDate,
        "role": "KORISNIK"
    });
    
    
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/register",
        dataType: 'text',
        contentType: "application/json",                          
        data: toSend,
        success: function (data) {
        	alert(username + ", uspesno ste se registrovali.");
			location.href = 'login.html';
        },
        error: function (data) {
            if(data.responseText == "USERNAME"){
            	alert("Korisnicko ime vec postoji.");
            }
            else if(data.responseText == "PASSWORD"){
            	alert("Lozinka vec postoji.");
            }
            else {
            	alert("Greska pri registraciji.");
            }
        }
    });
    
    
});