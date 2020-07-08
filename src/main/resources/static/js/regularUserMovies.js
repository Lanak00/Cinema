var movies;

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
    
    
    $('#zanr').formSelect();
    $('#sort').formSelect();
  
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


$(document).on("submit", "#sortForm", function (event) {         
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

$(document).on('click', '#logout', function () {
	$.ajax({
        type: "GET",                                               
        url: "http://localhost:8080/api/logout",                                                          
        success: function () {
           location.href = "/templates/login.html";
        },
        error: function (data) {
            console.log("ERROR : ", data);
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
       	
        var btnRezervisi = "<button class='btnReserveProjection' id = " + data[i]['id'] + ">Rezervisi</button>";
        row += "<td>" + btnRezervisi + "</td>";

        $('#movieProjections').append(row);                            
    }
}

function populateMovies(data) {
	$( "#movies" ).empty();
    var header = "<tr>" + 
    "<th>Title</th>" + 
    "<th>Description</th>" +
    "<th>Genre</th>" + 
    "<th>Duration</th>" + 
    "<th>Average Rating</th>" +
    "</tr>"
    $('#movies').append(header);
    
    for (i = 0; i < data.length; i++) {                     
        var row = "<tr>";                                  
        row += "<td>" + data[i]['title'] + "</td>";     
        row += "<td>" + data[i]['description'] + "</td>";
        row += "<td>" + data[i]['genre'] + "</td>";
        row += "<td>" + data[i]['duration'] + "</td>";
        row += "<td>" + data[i]['averageRating'] + "</td>";

        var btn = "<button class='btnSeeMore' id = " + data[i]['id'] + ">Projekcije</button>";
        row += "<td>" + btn + "</td>";                      

        $('#movies').append(row);
    }
    
    movies = data;
}