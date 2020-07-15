var cinemaId = "";

$(document).ready(function () { 
    $.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/cinemas",                 
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

$(document).on('click', '.btnModify', function () {            
	showModifyFormAndCloseOther();
	
	$.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/cinemas/" + this.id,                 
        dataType: "json",                                           
        success: function (data) {
        	populateForm(data);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
	
});

$(document).on('click', '.btnDelete', function () {            
                                            
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/cinemas/delete/" + this.id,  
        success: function () {
            alert("Bioskop je uspesno obrisan!"); 
            
            $.ajax({
                type: "GET",                                                
                url: "http://localhost:8080/api/cinemas",                 
                dataType: "json",                                           
                success: function (data) {
                	populateTable(data);
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

$(document).on('click', '.btnManagers', function () {            
    $("#cinemaManagersStuff").show();
    $("#cinemaStuff").hide();
    $("#otherManagers").formSelect();
    
    cinemaId = this.id;
	populateManagersTables();
});

$(document).on('click', '.btnDeleteManager', function (event) {  
	event.preventDefault();          
    var toSend = JSON.stringify({
    	"cinemaId": cinemaId,
        "managerId": this.id
    });
                                    
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/api/cinemas/managers/remove",
        contentType: "application/json",                          
        data: toSend,
        success: function () {
			populateManagersTables();
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});


$(document).on('click', '#goToCinemas', function () {            
    $("#cinemaManagersStuff").hide();
    $("#cinemaStuff").show();                                  
});

$(document).on('click', '#cinemaManagersStuff form button', function (event) {
	event.preventDefault();
	var selected = $("#otherManagers option:selected").val()
	if (isNaN(selected)) {
		return;
	}
	
	var toSend = JSON.stringify({
    	"cinemaId": cinemaId,
        "managerId": selected
    });
    
    $.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/cinemas/managers/add",                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () {
        	populateManagersTables();
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
});


$(document).on('click', '#cinemaFormModify button', function () {
	event.preventDefault();
    
	var id = $("#cinemaFormModify #id").val();
    var naziv = $("#cinemaFormModify #name").val();
    var adresa = $("#cinemaFormModify #address").val();
    var telefon = $("#cinemaFormModify #mobilePhone").val();
    var email = $("#cinemaFormModify #email").val();
   
     
    var toSend = JSON.stringify({
    	"id": id,
        "name": naziv,
        "adress": adresa,
        "phoneNumber": telefon,
        "eMail": email
    });
    
    $.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/cinemas/modify",                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () {
        	alert("Bioskop " + toSend.name + " je uspesno izmenjen.");
        	
        	$.ajax({
                type: "GET",                                                
                url: "http://localhost:8080/api/cinemas",                 
                dataType: "json",                                           
                success: function (data) {
                	populateTable(data);
                },
                error: function (data) {
                    console.log("ERROR : ", data);
                }
            });
            
            showOtherAndCloseForms();
            
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
    
    
});

$(document).on('click', '#cinemaFormAdd button', function () {
	event.preventDefault();
    
    var naziv = $("#cinemaFormAdd #name").val();
    var adresa = $("#cinemaFormAdd #address").val();
    var telefon = $("#cinemaFormAdd #mobilePhone").val();
    var email = $("#cinemaFormAdd #email").val();
   
     
    var toSend = JSON.stringify({
    	"id": "-1",
        "name": naziv,
        "adress": adresa,
        "phoneNumber": telefon,
        "eMail": email
    });
    
    $.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/cinemas",                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () {
        	alert("Bioskop je uspesno dodat!");
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
    
    $.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/cinemas",                 
        dataType: "json",                                           
        success: function (data) {
        	populateTable(data);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
    
    showOtherAndCloseForms();
});

function populateTable(data) {
    $('#cinemas').empty();
    var header = "<tr>" +
    "<th>Ime bioskopa</th>" + 
    "<th>Adresa</th>" +
    "<th>Telefon</th>" + 
    "<th>Email</th>" + 
    "</tr>"
    
    $('#cinemas').append(header);
    
    for (i = 0; i < data.length; i++) {                     
        var row = "<tr>";                                   
        row += "<td>" + data[i]['name'] + "</td>";     
        row += "<td>" + data[i]['adress'] + "</td>";
        row += "<td>" + data[i]['phoneNumber'] + "</td>";
        row += "<td>" + data[i]['eMail'] + "</td>";
       	
        var btnIzmeni = "<button class='btnModify btn waves-effect waves-light' id = " + data[i]['id'] + ">Izmeni</button>";
        var btnObrisi = "<button class='btnDelete btn waves-effect waves-light' id = " + data[i]['id'] + ">Obrisi</button>";
        var btnMenadzeri = "<button class='btnManagers btn waves-effect waves-light' id = " + data[i]['id'] + ">Menadzeri</button>";
        row += "<td>" + btnIzmeni + "</td>";
        row += "<td>" + btnObrisi + "</td>";
        row += "<td>" + btnMenadzeri + "</td>"; 

        $('#cinemas').append(row);                            
    }
}

function populateForm(data) {
	$("#cinemaFormModify #id").val(data.id);
	$("#cinemaFormModify #name").val(data.name);
	$("#cinemaFormModify #address").val(data.adress);
	$("#cinemaFormModify #mobilePhone").val(data.phoneNumber);
	$("#cinemaFormModify #email").val(data.eMail);
}

function showModifyFormAndCloseOther() {
	var form = $(".formModify");
	var otherStuff = $("#cinemaStuff");
	otherStuff.hide();
    form.show();
}

function showAddFormAndCloseOther() {
	var form = $(".formAdd");
	var otherStuff = $("#cinemaStuff");
	otherStuff.hide();
    form.show();
}

function showOtherAndCloseForms() {
	var form1 = $(".formAdd");
	var form2 = $(".formModify");
	var otherStuff = $("#cinemaStuff");
	otherStuff.show();
    form1.hide();
    form2.hide();
}


function populateManagersTables() {
	var cinemaManagers = $('#cinemaManagers');
	    
	    $.ajax({
	        type: "GET",                                                
	        url: "http://localhost:8080/api/cinemas/managers/contained/" + cinemaId,                 
	        dataType: "json",                                           
	        success: function (data) {
	            cinemaManagers.empty();
			    var header = "<tr>" + 
			    "<th>Korisnicko ime</th>" + 
			    "<th>Ime</th>" +
			    "<th>Prezime</th>" + 
			    "<th>Email</th>" + 
			    "<th>Telefon</th>" + 
			    "<th>Datum rodjenja</th>" +
			    "</tr>";
			    
			    cinemaManagers.append(header);
			    
			    for (i = 0; i < data.length; i++) {
			        var row = "<tr>";                                   
			        row += "<td>" + data[i]['username'] + "</td>";     
			        row += "<td>" + data[i]['firstName'] + "</td>";
			        row += "<td>" + data[i]['lastName'] + "</td>";
			        row += "<td>" + data[i]['email'] + "</td>";
			        row += "<td>" + data[i]['phone'] + "</td>";
			        row += "<td>" + data[i]['birthDate'] + "</td>";
			       	
			        var btnObrisi = "<button class='btnDeleteManager btn waves-effect waves-light' id = " + data[i]['id'] + ">Obrisi</button>";
			        row += "<td>" + btnObrisi + "</td>"; 
			
			        cinemaManagers.append(row);                           
			    }
	        },
	        error: function (data) {
	            console.log("ERROR : ", data);
	        }
	    }); 
	    
	    
	    $.ajax({
	        type: "GET",                                                
	        url: "http://localhost:8080/api/cinemas/managers/not-contained/" + cinemaId,                 
	        dataType: "json",                                           
	        success: function (data) {
	        	var select = $('#otherManagers');
	        	select.empty();
	        
	            for (i = 0; i < data.length; i++) {
	            	var display = data[i]['username'] + ' ( ' + data[i]['firstName'] + ' ' + data[i]['lastName'] + ' )';
	            	var option = "<option value=" + data[i]['id'] + ">" + display + "</option>"
	            	select.append(option);
	            }
	            
	            
	        },
	        error: function (data) {
	            console.log("ERROR : ", data);
	        }
	    }); 
}


$(document).on('click', '#btnBackFormModify', function () {
	showOtherAndCloseForms();
});

$(document).on('click', '#btnBackFormAdd', function () {
	showOtherAndCloseForms();
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
