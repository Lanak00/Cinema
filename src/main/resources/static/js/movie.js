var movies;

$(document).ready(function () { 
    $.ajax({
        type: "GET",                                                // HTTP metoda
        url: "http://localhost:8080/api/movies",                 // URL koji se gađa
        dataType: "json",                                           // tip povratne vrednosti
        success: function (data) {
            var header = "<tr>" + 
            "<th>Title</th>" + 
            "<th>Description</th>" +
            "<th>Genre</th>" + 
            "<th>Duration</th>" + 
            "<th>Average Rating</th>" +
            "</tr>"
            $('#movies').append(header);
            
            for (i = 0; i < data.length; i++) {                     // prolazimo kroz listu svih zaposlenih
	            var row = "<tr>";                                   // kreiramo red za tabelu
	            row += "<td>" + data[i]['title'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
	            row += "<td>" + data[i]['description'] + "</td>";
	            row += "<td>" + data[i]['genre'] + "</td>";
	            row += "<td>" + data[i]['duration'] + "</td>";
	            row += "<td>" + data[i]['averageRating'] + "</td>";
	
	            var btn = "<button class='btnSeeMore' id = " + data[i]['id'] + ">See more</button>";
	            row += "<td>" + btn + "</td>";                      // ubacujemo button u poslednje polje reda
	
	            $('#movies').append(row);                            // ubacujemo kreirani red u tabelu čiji je id = employees
	        }
            
            movies = data;
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
    
    
    $('#zanr').formSelect();
    $('#sort').formSelect();
  
});


$(document).on("submit", "#searchForm", function (event) {           // kada je submitovana forma za kreiranje novog zaposlenog
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
        type: "POST",                                               // HTTP metoda je POST
        url: "http://localhost:8080/api/movies",                 // URL na koji se šalju podaci
        dataType: "json",                                           // tip povratne vrednosti
        contentType: "application/json",                            // tip podataka koje šaljemo
        data: toSend,                                      // Šaljemo novog zaposlenog
        success: function (data) {
            $( "#movies" ).empty();
            var header = "<tr>" + 
            "<th>Title</th>" + 
            "<th>Description</th>" +
            "<th>Genre</th>" + 
            "<th>Duration</th>" + 
            "<th>Average Rating</th>" +
            "</tr>"
            $('#movies').append(header);
            
            for (i = 0; i < data.length; i++) {                     // prolazimo kroz listu svih zaposlenih
	            var row = "<tr>";                                   // kreiramo red za tabelu
	            row += "<td>" + data[i]['title'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
	            row += "<td>" + data[i]['description'] + "</td>";
	            row += "<td>" + data[i]['genre'] + "</td>";
	            row += "<td>" + data[i]['duration'] + "</td>";
	            row += "<td>" + data[i]['averageRating'] + "</td>";
	
	            var btn = "<button class='btnSeeMore' id = " + data[i]['id'] + ">See more</button>";
	            row += "<td>" + btn + "</td>";                      // ubacujemo button u poslednje polje reda
	
	            $('#movies').append(row);                            // ubacujemo kreirani red u tabelu čiji je id = employees
	        }
            
            movies = data;
        },
        error: function (data) {
            alert("Greška!");
        }
    });
});


$(document).on("submit", "#sortForm", function (event) {           // kada je submitovana forma za kreiranje novog zaposlenog
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
        type: "POST",                                               // HTTP metoda je POST
        url: "http://localhost:8080/api/movies",                 // URL na koji se šalju podaci
        dataType: "json",                                           // tip povratne vrednosti
        contentType: "application/json",                            // tip podataka koje šaljemo
        data: toSend,                                      // Šaljemo novog zaposlenog
        success: function (data) {
            $( "#movies" ).empty();
            var header = "<tr>" + 
            "<th>Title</th>" + 
            "<th>Description</th>" +
            "<th>Genre</th>" + 
            "<th>Duration</th>" + 
            "<th>Average Rating</th>" +
            "</tr>"
            $('#movies').append(header);
            
            for (i = 0; i < data.length; i++) {                     // prolazimo kroz listu svih zaposlenih
	            var row = "<tr>";                                   // kreiramo red za tabelu
	            row += "<td>" + data[i]['title'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
	            row += "<td>" + data[i]['description'] + "</td>";
	            row += "<td>" + data[i]['genre'] + "</td>";
	            row += "<td>" + data[i]['duration'] + "</td>";
	            row += "<td>" + data[i]['averageRating'] + "</td>";
	
	            var btn = "<button class='btnSeeMore' id = " + data[i]['id'] + ">See more</button>";
	            row += "<td>" + btn + "</td>";                      // ubacujemo button u poslednje polje reda
	
	            $('#movies').append(row);                            // ubacujemo kreirani red u tabelu čiji je id = employees
	        }
            
            movies = data;
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
		
		            var btn = "<button class='btnSeeMore' id = " + data[i]['id'] + ">See more</button>";
		            row += "<td>" + btn + "</td>";                      
		
		            $('#movies').append(row);
		        }
	            
	            movies = data;
	        },
	        error: function (data) {
	            alert("Greška!");
	        }
	    });
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
           location.href = "/templates/login.html";
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