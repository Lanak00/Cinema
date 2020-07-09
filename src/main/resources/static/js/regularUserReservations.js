var loggedUser = null;

$(document).ready(function () {     
    $.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/getLoggedUser",                 
        dataType: "json",                                           
        success: function (data) {
            loggedUser = data;
        	getReservations();
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
    
    
    $('#zanr').formSelect();
    $('#sort').formSelect();
});

$(document).on('click', '.btnCheckReservation', function () {
	var toSend = JSON.stringify({
        "userId": loggedUser.id,
        "projectionId": this.id,
    });
    
    $.ajax({
        type: "POST",                                           
        url: "http://localhost:8080/api/regularUsers/checkReservation",                                                      
        contentType: "application/json",                           
        data: toSend,                                      
        success: function (data) {
            alert("Uspesno ste rezervisali projekciju.");
        },
        error: function (data) {
            alert("Doslo je do greske pri rezervaciji.");
        }
    });
});

$(document).on('click', '.btnUncheckReservation', function () {
	var toSend = JSON.stringify({
        "userId": loggedUser.id,
        "projectionId": this.id,
    });
    
    $.ajax({
        type: "POST",                                           
        url: "http://localhost:8080/api/regularUsers/uncheckReservation",                                                      
        contentType: "application/json",                           
        data: toSend,                                      
        success: function (data) {
        	getReservations();
            alert("Uspesno ste rezervisali projekciju.");
        },
        error: function (data) {
            alert("Doslo je do greske pri rezervaciji.");
        }
    });
});

function populateReservations(data) {
	$('#reservations').empty();
    var header = "<tr>" +
    "<th>Naslov filma</th>" +
    "<th>Trajanje</th>" +
    "<th>Zanr</th>" +
    "<th>Ocena</th>" +
    "<th>Vreme</th>" +
    "<th>Cena</th>" +
    "<th>Broj rezervisanih karata</th>" + 
    "<th>Bioskop</th>" +
    "</tr>";
    
    $('#reservations').append(header);
    
    for (i = 0; i < data.length; i++) {                     
        var row = "<tr>";                                   
        row += "<td>" + data[i]['movie']['title'] + "</td>";     
        row += "<td>" + data[i]['movie']['duration'] + "</td>";
       	row += "<td>" + data[i]['movie']['genre'] + "</td>";
       	row += "<td>" + data[i]['movie']['averageRating'] + "</td>";
       	row += "<td>" + data[i]['dateAndTime'] + "</td>";
       	row += "<td>" + data[i]['price'] + "</td>";
       	row += "<td>" + data[i]['ticketsReserved'] + "</td>";
       	row += "<td>" + data[i]['cinemaName'] + "</td>";
       	
        var btnPotvrdi = "<button class='btnCheckReservation btn waves-effect waves-light' id = " + data[i]['id'] + ">Potvrdi</button>";
        var btnOtkazi = "<button class='btnUncheckReservation btn waves-effect waves-light' id = " + data[i]['id'] + ">Otkazi</button>";
        row += "<td>" + btnPotvrdi + "</td>";
		row += "<td>" + btnOtkazi + "</td>";
		
        $('#reservations').append(row);                            
    }
}

function getReservations() {
	$.ajax({
	    type: "GET",                                                
	    url: "http://localhost:8080/api/regularUsers/getReservations/" + loggedUser.id,                 
	    dataType: "json",                                          
	    success: function (data) {
	        populateReservations(data);
	    },
	    error: function (data) {
	        console.log("ERROR : ", data);
	    }
   });
}

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