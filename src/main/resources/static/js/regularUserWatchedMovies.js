var loggedUser = null;

$(document).ready(function () { 
    $.ajax({
        type: "GET",                                                
        url: "http://localhost:8080/api/getLoggedUser",                 
        dataType: "json",                                           
        success: function (data) {
            loggedUser = data;
            
            getWatchedMovies();
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '.btnRate', function () {
	var selector1 = "#" + this.id;
	$(selector1).hide();
	var selector2 = "#select" + this.id;
	$(selector2).show();
});

$(document).on('change', '.browser-default', function () {
	var selector = "#" + this.id;
	$(selector).hide();
	
	var parts = selector.split("t");
	var movieId = parts[1];
	
	var selectedRating = $(selector + " option:selected").val();
	
	var toSend = JSON.stringify({
        "userId": loggedUser.id,
        "movieId": movieId,
        "rating": selectedRating
    });
    
    $.ajax({
        type: "POST",                                           
        url: "http://localhost:8080/api/regularUsers/rate",                                                      
        contentType: "application/json",                           
        data: toSend,                                      
        success: function (data) {
        	getWatchedMovies();
            alert("Uspesno ste ocenili film.");
        },
        error: function (data) {
            alert("Doslo je do greske pri ocenjivanju.");
        }
    });
});

function populateWatchedMovies(data) {
    $( "#watchedMovies" ).empty();
    var header = "<tr>" + 
    "<th>Naslov</th>" + 
    "<th>Opis</th>" +
    "<th>Zanr</th>" + 
    "<th>Trajanje</th>" + 
    "<th>Prosecna ocena</th>" +
    "<th>Moja ocena</th>" +
    "</tr>"
    
    $('#watchedMovies').append(header);
    
    for (i = 0; i < data.length; i++) {                     
        var row = "<tr>";                                  
        row += "<td>" + data[i]['title'] + "</td>";     
        row += "<td>" + data[i]['description'] + "</td>";
        row += "<td>" + data[i]['genre'] + "</td>";
        row += "<td>" + data[i]['duration'] + "</td>";
        row += "<td>" + data[i]['averageRating'] + "</td>";
        
        var personalRating = data[i]['personalRating'];
        if(personalRating != -1) {
        	row += "<td>" + personalRating + "</td>";
        }
        else {
        	var btnOceni = "<button class='btnRate btn waves-effect waves-light' id = " + data[i]['id'] + " >Oceni</button>";
        	row += "<td>" + btnOceni + "</td>";
       
       		var selectRating = "<select class='browser-default' id = 'select" + data[i]['id'] + "' >" + 
        				   "<option value='1'>1<option>" + 
        				   "<option value='2'>2<option>" + 
        				   "<option value='3'>3<option>" + 
        				   "<option value='4'>4<option>" + 
        				   "<option value='5'>5<option>" + 
        				   "<option value='6'>6<option>" + 
        				   "<option value='7'>7<option>" + 
        				   "<option value='8'>8<option>" + 
        				   "<option value='9'>9<option>" +
        				   "<option value='10'>10<option>" +  
        				   "</select>";                   
		
			row += "<td>" + selectRating + "</td>";
        }
        
        $('#watchedMovies').append(row);
        $('.browser-default').hide();
    }
}

function getWatchedMovies() {
	$.ajax({
    type: "GET",                                                
    url: "http://localhost:8080/api/regularUsers/getWatchedMovies/" + loggedUser.id,                 
    dataType: "json",                                          
    success: function (data) {
        populateWatchedMovies(data);
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