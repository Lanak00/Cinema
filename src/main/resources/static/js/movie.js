
$(document).ready(function () { 
    $.ajax({
        type: "GET",                                               
        url: "http://localhost:8080/api/movies",        
        dataType: "json",                                          
        success: function (data) {
           populateMovies(data);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
    
    
    $('#zanr').formSelect(); //materialize css
    $('#sort').formSelect(); //materialize css
});


$(document).on("submit", "#searchForm", function (event) {    
    event.preventDefault();
    
    var naziv = $("#naziv").val();
    var zanr = $("#zanr").val();
    var opis = $("#opis").val();
    var ocenaMin = $( "#ocenaMin" ).val();
    var ocenaMax = $( "#ocenaMax" ).val();
    var cenaMin = $( "#cenaMin" ).val();
    var cenaMax = $( "#cenaMax" ).val();
    var datum = $("#datum").val();
    var termin = $("#termin").val();
     
    var toSend = JSON.stringify({
        "title": naziv,
        "description": opis,
        "genre": zanr,
        "averageRatingMin": ocenaMin,
        "averageRatingMax": ocenaMax,
        "priceMin": cenaMin,
        "priceMax": cenaMax,
        "date": datum,
        "time": termin
    });
    
    $.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/movies",                 
        dataType: "json",                                           
        contentType: "application/json",                            
        data: toSend,                                      
        success: function (data) {
            populateMovies(data);
        },
        error: function (data) {
            alert("Greška!");
        }
    });
});


$(document).on("change", '#sort', function (event) {
	  var sortUrl = "http://localhost:8080/api/movies/sort?sortBy=" + this.value;
	  $.ajax({
	        type: "GET",                                               
	        url: sortUrl,                 
	        dataType: "json",                 
	        success: function (data) {
	            populateMovies(data);
	        },
	        error: function (data) {
	            alert("Greška!");
	        }
	   });
});

$(document).on('click', '.btnSeeMore', function () {
	$('#moviesStuff').hide();
	$('#movieProjectionsStuff').show();
	
	$.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/projections/getProjectionsOfMovie/" + this.id,                
        dataType: "json",                                           
        success: function (data) {
        	populateProjections(data);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#back', function () {
	$('#moviesStuff').show();
	$('#movieProjectionsStuff').hide();
});

function populateProjections(data) {
	$('#movieProjections').empty();
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
    
    $('#movieProjections').append(header);
    
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
       	
        $('#movieProjections').append(row);                            
    }
}

function populateMovies(data) {
	$( "#movies" ).empty();
    var header = "<tr>" + 
    "<th></th>" + 
    "<th>Naslov</th>" + 
    "<th>Opis</th>" +
    "<th>Zanr</th>" + 
    "<th>Trajanje</th>" + 
    "<th>Prosecna ocena</th>" +
    "</tr>"
    $('#movies').append(header);
    
    for (i = 0; i < data.length; i++) {                     
        var row = "<tr>";
        var image = "<img alt='Image' height='100' width='100' src='/images/" + data[i]['imageName'] + "' >";
        row += "<td>" + image + "</td>";                                  
        row += "<td>" + data[i]['title'] + "</td>";     
        row += "<td>" + data[i]['description'] + "</td>";
        row += "<td>" + data[i]['genre'] + "</td>";
        row += "<td>" + data[i]['duration'] + "</td>";
        row += "<td>" + data[i]['averageRating'] + "</td>";
		
        var btn = "<button class='btnSeeMore btn waves-effect waves-light' id = " + data[i]['id'] + ">Projekcije</button>";
        row += "<td>" + btn + "</td>";                      

        $('#movies').append(row);
    }
}

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

$(document).on('click', '#manMov', function () {
	location.href = '/ManagerPages/ManagerMovies.html';
});

$(document).on('click', '#manCin', function () {
	location.href = '/ManagerPages/Manager.html';
});