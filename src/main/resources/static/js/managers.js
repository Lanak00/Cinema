var loggedUser = null;
var currentCinema = null;

$(document).ready(function () {
	$.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/getLoggedUser",                 
        dataType: "json",                                           
        success: function (data) {
            loggedUser = data;
            
             $.ajax({
		        type: "GET",                                                
		        url: "http://localhost:8080/api/managers/getAssociatedCinemas/" + loggedUser.id,                 
		        dataType: "json",                                           
		        success: function (data) {
		            populateCinemasTable(data);
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
    
    $('#addHallForm').hide();
    $('#modifyHallForm').hide();
});

$(document).on('click', '.btnSale', function () {            
	$('#cinemaStuff').hide();
	$('#hallStuff').show();
	$('#projectionStuff').hide();
	
	currentCinema = this.id;
	
	getCinemaHalls();
});

$(document).on('click', '.btnRaspored', function () {            
    $('#cinemaStuff').hide();
	$('#hallStuff').hide();
	$('#projectionStuff').show();
	
	currentCinema = this.id;
	getProjections();
});

$(document).on('click', '.btnModifyHall', function () {            
     $('#hallsView').hide();
	 $('#modifyHallForm').show();
	  
	 $.ajax({
        type: "GET",                                       
        url: "http://localhost:8080/api/cinemaHalls/getCinemaHall/" + this.id,                 
        dataType: "json",                                           
        success: function (data) {
        	$("#modifyHallForm #id").val(data.id);
			$("#modifyHallForm #hallMark").val(data.hallMark);
			$("#modifyHallForm #capacity").val(data.capacity);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '.btnDeleteHall', function () {   
		var id = this.id;         
      $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/cinemaHalls/delete/" + id,  
        success: function () {
            getCinemaHalls();
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
      });   
});

$(document).on('click', '#addHallButton', function () {
	$('#hallsView').hide();
	$('#addHallForm').show();
});

$(document).on('click', '#addHallForm button', function () {
	event.preventDefault();
    
	var hallMark = $("#addHallForm #hallMark").val();
	var capacity = $("#addHallForm #capacity").val();
     
    var toSend = JSON.stringify({
    	"id": "-1",
        "hallMark": hallMark,
        "capacity": capacity
    });
    
    $.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/cinemaHalls/add/" + currentCinema,                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () {
        	getCinemaHalls();
        	$('#addHallForm').hide();
			$('#hallsView').show();
        	alert("Sala je uspesno dodata!");
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
});

$(document).on('click', '#modifyHallForm button', function () {
	event.preventDefault();
    
    var id = $("#modifyHallForm #id").val();
	var hallMark = $("#modifyHallForm #hallMark").val();
	var capacity = $("#modifyHallForm #capacity").val();
     
    var toSend = JSON.stringify({
    	"id": id,
        "hallMark": hallMark,
        "capacity": capacity
    });
    
    $.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/cinemaHalls/modify",                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () { 
        	getCinemaHalls();
        	$('#modifyHallForm').hide();
			$('#hallsView').show();
        	alert("Sala je uspesno izmenjena!");
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
});


$(document).on('click', '#addProjectionButton', function () {
	$('#projectionView').hide();
	$('#addProjectionForm').show();
	
	
		$.ajax({
	        type: "GET",                                                
	        url: "http://localhost:8080/api/movies",                 
	        dataType: "json",                                           
	        success: function (data) {
	        	var select = $('#movieSelect');
	        	select.empty();
	        
	            for (i = 0; i < data.length; i++) {
	            	var display = "Naziv: " + data[i]['title'] + ", Zanr: " + data[i]['genre'] + ", Trajanje: " + data[i]['duration'];
	            	var option = "<option value=" + data[i]['id'] + ">" + display + "</option>";
	            	select.append(option);
	            }
	            
	            
	        },
	        error: function (data) {
	            console.log("ERROR : ", data);
	        }
	    });
	    
	    $.ajax({
	        type: "GET",                                                
	        url: "http://localhost:8080/api/cinemaHalls/" + currentCinema,                 
	        dataType: "json",                                           
	        success: function (data) {
	        	var select = $('#hallSelect');
	        	select.empty();
	        
	            for (i = 0; i < data.length; i++) {
	            	var display = data[i]['hallMark'];
	            	var option = "<option value=" + data[i]['id'] + ">" + display + "</option>";
	            	select.append(option);
	            }
	            
	            
	        },
	        error: function (data) {
	            console.log("ERROR : ", data);
	        }
	    });
});

$(document).on('click', '#addProjectionForm button', function () {
	var selectedMovie = $("#movieSelect option:selected").val();
	var selectedHalls = $("#hallSelect").val();
	var date = $("#date").val();
    var time = $("#time").val();
	var price = $("#price").val();
	
	var dateTime = date + " " + time;
	
	var toSend = JSON.stringify({
		"id": "-1",
    	"movieId": selectedMovie,
        "halls": selectedHalls,
        "dateAndTime": dateTime,
        "price": price
    });
	
	$.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/projections/add",                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () {
        	getProjections();
        	$('#addProjectionForm').hide();
			$('#projectionView').show();
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
});


$(document).on('click', '.btnModifyProjection', function () {
	 $('#projectionView').hide();
	 $('#modifyProjectionForm').show();
	 
	 $.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/projections/getProjection/" + this.id,                
        dataType: "json",                                           
        success: function (data) {
        	var parts = data.dateAndTime.split(" ");
        	$('#datee').val(formatDate(parts[0]));
        	$('#timee').val(formatTime(parts[1]));
        	$('#pricee').val(data.price);
        	$('#id').val(data.id);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#modifyProjectionForm button', function () {
	var id = $("#id").val();
	var date = $("#datee").val();
    var time = $("#timee").val();
	var price = $("#pricee").val();
	
	var dateTime = date + " " + time;
	
	var toSend = JSON.stringify({
		"id": id,
    	"movieId": undefined,
        "halls": undefined,
        "dateAndTime": dateTime,
        "price": price
    });
	
	$.ajax({
        type: "POST",                                              
        url: "http://localhost:8080/api/projections/modify",                                                         
        contentType: "application/json",                          
        data: toSend,                              
        success: function () {
        	getProjections();
        	$('#addProjectionForm').hide();
			$('#projectionView').show();
	    },
        error: function (data) {
            alert("Greška!");
        }
    });
});
function formatDate(date) {
    var parts = date.split("-");
    var year = parts[0];
    var month = parts[1];
    var day = parts[2];
  
    if(month.length <= 1){
    	month = "0" + month;
    }
    
    if(day.length <= 1){
    	day = "0" + day;
    }
    
    return year + "-" + month + "-" + day;  
}

function formatTime(time) {
	var parts = time.split(":");
	var hours = parts[0];
	var minutes = parts[1];
	
	if(hours.length <= 1){
    	hours = "0" + hours;
    }
    
	if(minutes.length <= 1){
    	minutes = "0" + minutes;
    }
    
    return hours + ":" + minutes;
}

function populateCinemasTable(data) {
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
       	
        var btnSale = "<button class='btnSale btn waves-effect waves-light' id = " + data[i]['id'] + ">Sale</button>";
        var btnRaspored = "<button class='btnRaspored btn waves-effect waves-light' id = " + data[i]['id'] + ">Raspored</button>";
        row += "<td>" + btnSale + "</td>";
		row += "<td>" + btnRaspored + "</td>";
		
        $('#cinemas').append(row);                            
    }
}

function populateHallsTable(data) {
    $('#halls').empty();
    var header = "<tr>" +
    "<th>Oznaka sale</th>" + 
    "<th>Kapacitet</th>" +
    "</tr>"
    
    $('#halls').append(header);
    
    for (i = 0; i < data.length; i++) {                     
        var row = "<tr>";                                   
        row += "<td>" + data[i]['hallMark'] + "</td>";     
        row += "<td>" + data[i]['capacity'] + "</td>";
       	
        var btnIzmeni = "<button class='btnModifyHall btn waves-effect waves-light' id = " + data[i]['id'] + ">Izmeni</button>";
        var btnObrisi = "<button class='btnDeleteHall btn waves-effect waves-light' id = " + data[i]['id'] + ">Obrisi</button>";
        row += "<td>" + btnIzmeni + "</td>";
        row += "<td>" + btnObrisi + "</td>";

        $('#halls').append(row);                            
    }
}

function populateProjections(data) {
	$('#projections').empty();
    var header = "<tr>" +
    "<th>Naslov filma</th>" + 
    "<th>Trajanje</th>" +
    "<th>Zanr</th>" +
    "<th>Ocena</th>" + 
    "<th>Vreme</th>" +
    "<th>Cena</th>" +
    "<th>Broj rezervisanih karata</th>" +  
    "</tr>";
    
    $('#projections').append(header);
    
    for (i = 0; i < data.length; i++) {                     
        var row = "<tr>";                                   
        row += "<td>" + data[i]['movie']['title'] + "</td>";     
        row += "<td>" + data[i]['movie']['duration'] + "</td>";
       	row += "<td>" + data[i]['movie']['genre'] + "</td>";
       	row += "<td>" + data[i]['movie']['averageRating'] + "</td>";
       	row += "<td>" + data[i]['dateAndTime'] + "</td>";
       	row += "<td>" + data[i]['price'] + "</td>";
       	row += "<td>" + data[i]['ticketsReserved'] + "</td>";
       	
        var btnIzmeni = "<button class='btnModifyProjection btn waves-effect waves-light' id = " + data[i]['id'] + ">Izmeni</button>";
        row += "<td>" + btnIzmeni + "</td>";

        $('#projections').append(row);                            
    }
}

function getCinemaHalls() {
	$.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/cinemaHalls/" + currentCinema,                
        dataType: "json",                                           
        success: function (data) {
        	populateHallsTable(data);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
}

function getProjections() {
	$.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/projections/getAllProjectionsOfCinema/" + currentCinema,                
        dataType: "json",                                           
        success: function (data) {
        	populateProjections(data);
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
}

$(document).on('click', '#mov', function () {
	location.href = '/ManagerPages/ManagerMovies.html';
});

$(document).on('click', '#cin', function () {
	location.href = '/ManagerPages/Manager.html';
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