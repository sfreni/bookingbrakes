function loadTable() {
  var api = "http://localhost:8080/purchases";
  $(document).ready(function () {
    $.ajax({
      url: api,
      method: 'GET',
      cache: false,
      type: "text/json"
    })
      .done(function (evt) {

        setTimeout(function () {
          var result = evt;
          window.jsonValues = result;
          var html = '<div class="tables-purchases-content">';
          var queryString = location.search
          let params = new URLSearchParams(queryString)
          result = result.sort(function (a, b) {
            return b.id - a.id;
          });


          html += '<table id="purchasesTable" class="table table-hover table-striped">'
            + '<thead>'
            + '<tr>'
            // + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">N.</th>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:25%\"  colspan="4" scope="col">Trip</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Date</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:5%\"  scope="col">Status</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Customer</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Amount</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Methods</th>'
            + '</tr>'

            + '<tr>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center"   scope="col">Id</th>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center"   scope="col">Departure</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center"   scope="col">Destination</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center"   scope="col">Departure Time</th>'
            + '<th class=\'text-center\ bg-primary text-white\'></th>'
            + '<th class=\'text-center\ bg-primary text-white\'></th>'
            + '<th class=\'text-center\ bg-primary text-white\'></th>'
            + '<th class=\'text-center\ bg-primary text-white\'></th>'
            + '<th class=\'text-center\ bg-primary text-white\'></th>'
            + '</tr>'

            + '</thead>'
            + '<tbody>';
          result = window.jsonValues;
          result = result.sort(function (a, b) {
            return b.id - a.id;
          });
          for (var i = 0; i < result.length; i++) {
            html += '<tr align=\'center\'  >'
              + '<td  >' + result[i].id + '</b><td  >' + result[i].trip.departure.name + '</b></td><td align=\'center\'  >' + result[i].trip.destination.name + '</b></td>' +
              '<td>' + result[i].trip.startDateFlight + '</b></p></td>'
            let datePurchase = result[i].datePurchase.substr(8, 2) + "/" + result[i].datePurchase.substr(5, 2) + "/" + result[i].datePurchase.substr(0, 4) + " " + result[i].datePurchase.substr(11, 5);


            html += '<td  > ' + datePurchase + '</td>'
            if (result[i].purchaseStatus == "COMPLETE") {
              html += '<td  ><i  class="fa fa-check" aria-hidden="true" title="Complete"></i></td>'
            } else {
              html += '<td  ><i  class="fa fa-times" aria-hidden="true" title="Not Complete"></i></td>'
            }
            html += '<td  > ' + result[i].customer.firstName + ' ' + result[i].customer.lastName + '</td>'
            let totalAmount = 0;
            for (var j = 0; j < result[i].products.length; j++) {
              totalAmount += result[i].products[j].priceAmount;
            }


            html += '<td   >€ ' + Number(totalAmount).toLocaleString("it-IT") + '</td>'
              + '<td   >'
              + '<div class="pointer">'
              + '<i class="fa fa-pencil" aria-hidden="true" onclick="editTrip(' + (i + 1) + ',' + result[i].id + ',true)" title="Modify Trip"></i>&nbsp;'
              + '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete Trip"></i>&nbsp;'
              + '<i class="fa fa-shopping-bag" aria-hidden="true" onclick="listPurchase(' + i + ')" title="List Purchase" ></i>&nbsp;'

              + '</div></td>'
              + '</tr>';


          }
          html += '</tbody></table>';

          //      }
          html += '</div>';
          // Set all content
          $('.tables-purchases').html(html);
        }, 1000);
      })
      .fail(function () {
        alert('Error : Failed to reach API Url or check your connection');
      })

  });
}


function listPurchase(id) {
  let html;
  if (window.jsonValues[id].purchases[0] != undefined) {
    html = '<br><p><h2 align="center">Purchases</h2></p>' +
      '<h4 align="center">Departure:<b>' + window.jsonValues[id].departure.name + '</b>, Destination:<b>' + window.jsonValues[id].destination.name + '</b></h4>'
    let dateDeparture = window.jsonValues[id].startDateFlight.substr(8, 2) + "/" + window.jsonValues[id].startDateFlight.substr(5, 2) + "/" + window.jsonValues[id].startDateFlight.substr(0, 4) + " " + window.jsonValues[id].startDateFlight.substr(11, 5);
    let dateArrivale = window.jsonValues[id].endDateFlight.substr(8, 2) + "/" + window.jsonValues[id].endDateFlight.substr(5, 2) + "/" + window.jsonValues[id].endDateFlight.substr(0, 4) + " " + window.jsonValues[id].endDateFlight.substr(11, 5);
    html += '<h4 align="center">Departure Time:<b>' + dateDeparture + '</b>, Arrival Time:<b>' + dateArrivale + '</b></h4>' +
      '<br><table id="purchasesTable" class="table table-hover table-striped">' +
      '<thead>'
      + '<tr>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">N.</th>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Date</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Status</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Customer</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Amount</th>'
      + '</tr>'
      + '</thead>'
      + '<tbody>';
    result = window.jsonValues;
    result = result.sort(function (a, b) {
      return b.id - a.id;
    });
    for (var i = 0; i < window.jsonValues[id].purchases.length; i++) {
      html += '<tr align=\'center\'  >'
        + '<td  > ' + (i + 1) + '</td>'
      let datePurchase = window.jsonValues[id].purchases[i].datePurchase.substr(8, 2) + "/" + window.jsonValues[id].purchases[i].datePurchase.substr(5, 2) + "/" + window.jsonValues[id].purchases[i].datePurchase.substr(0, 4) + " " + window.jsonValues[id].purchases[i].datePurchase.substr(11, 5);


      html += '<td  > ' + datePurchase + '</td>'
      if (window.jsonValues[id].purchases[i].purchaseStatus == "COMPLETE") {
        html += '<td  ><i  class="fa fa-check" aria-hidden="true" title="Complete"></i></td>'
      } else {
        html += '<td  ><i  class="fa fa-times" aria-hidden="true" title="Not Complete"></i></td>'
      }
      html += '<td>' + window.jsonValues[id].purchases[i].customer.firstName + ' ' + window.jsonValues[id].purchases[i].customer.lastName + '</td>'
      let totalAmount = 0;
      for (var j = 0; j < window.jsonValues[id].purchases[i].products.length; j++) {
        totalAmount += window.jsonValues[id].purchases[i].products[j].priceAmount;
      }


      html += '<td   >€ ' + Number(totalAmount).toLocaleString("it-IT") + '</td>'


        + '</tr>';
    }


  } else {
    html = '<br><h2 align="center">Purchases</h2>' +
      '<h4 align="center"><br>Departure:<b>' + window.jsonValues[id].departure.name + '</b></h4>' +
      '<h4 align="center"><br>Destination:<b>' + window.jsonValues[id].destination.name + '</b></h4>' +
      '<h4 align="center"><br><b>No Purchases</b></h4><br>';
  }
  document.getElementById("modalSizeValue").className = "modal-dialog modal-xl";
  $('.modal-content').html(html);
  $('#myModalNew').modal('show');

}

function getObjectDB(ending) {
  var api = "http://localhost:8080/" + ending;

  return fetch(api)
    .then(response => response.json())

}

function createEditWindow() {
  let html = '<br>'
    + '<div class="container border">'
    + '<br><div class="form-row d-flex justify-content-center">'
    + '<div class="col">'
    + '<label for="depatureLabel">Departure</label>'
    + '<input class="form-control" list="Departure" id="DepartureList" placeholder="Type to search...">'
    + '<datalist id="Departure">'
    + '</datalist>'

    //    + ' <select class="form-control form-control-sm" id="Departure">'
    //
    // + '</select>'
    // + '<small id="emailHelp" class="form-text text-muted">Well never share your email with anyone else.</small>'
    + '</div>'
    + '<div class="col">'
    + ' <label for="destinationLabel">Destination</label>'
    + '<input class="form-control" list="Departure" id="DestinationList" placeholder="Type to search...">'
    + '<datalist id="Destination">'
    + '</datalist>'
    + '</div>'
    + '</div>'
    // + '</div>'
    // + '<br><div class="form-row d-flex justify-content-center">'
    // + '<div class="input-group date" >'
    // + '    <input type="text" class="form-control">'
    // + ' <div class="input-group-addon">'
    // + '    <span class="glyphicon glyphicon-th"></span>'
    // + '</div>'
    // + '</div>'


    +'<div class="form-row d-flex justify-content-center">'
    + '<div class="col" >'

    + ' <label for="exampleInputPassword1">Departure Time</label>'
    // + ' <input type="date" data-date="" data-date-format="DD/MM/YYYY" value="2020-08-29">'
    //  + '<input type="text" class="form-control" >'
    + '<input class="form-control " type="text"  data-provide="datepicker" data-date-format="dd/mm/yyyy" data-date-autoclose="true" id="dateDeparture" >'
    // + ' <input type="text" class="form-control form-control-sm" id="datepicker" placeholder="gg/mm/yyyy">'
    // + '<p>Date: <input type="text" id="date-from" class="hasDatepicker"></p>'

  + '</div>'
    //start
    // + '<div class="col-5" >'
    //
    // + ' <label for="passengerLabel">Departure Time</label>'
    // // + ' <input type="date" data-date="" data-date-format="DD/MM/YYYY" value="2020-08-29">'
    // //  + '<input type="text" class="form-control" >'
    // + '<input class="form-control" type="text"  data-provide="datepicker" data-date-format="dd/mm/yyyy" data-date-autoclose="true" >'
    // // + ' <input type="text" class="form-control form-control-sm" id="datepicker" placeholder="gg/mm/yyyy">'
    // // + '<p>Date: <input type="text" id="date-from" class="hasDatepicker"></p>'
    //
    // + '</div>'
    // + '</div>'

    //end
 // html   += '<div class="col-5">'
 //    + ' <label for="exampleInputPassword1">Passengers</label>'
 //    + ' <input type="text" class="form-control form-control-sm" id="arrivalTime" placeholder="Passengers">'
 //    + '</div>'
 //    + '</div>'
 //    + '<br><div class="form-row d-flex justify-content-center">'

  // html   += '<div class="input-group;col-5">'
  //   +'<input id="address" type="email" placeholder="Add your email address" class="form-control form-control-sm" /> <span class="input-group-btn">'
  //   +'  <button class="btn btn-warning" type="button" id="addressSearch">Subscribe</button>'
  //   +' </span>'
  //   +' </div>'
  //   + '</div>'

     + '<div class="col">'
    + ' <label for="exampleInputPassword1">Passengers</label>'
    + '<div class="input-group">'


    + '<input type="text" class="form-control" placeholder="Passengers" value="1" id="passengerValue"">'
   + '<div class="input-group-append">'
   + '<button class="btn btn-outline-secondary" onclick="more()" type="button">+</button>'
   + '<button class="btn btn-outline-secondary" onclick="less()" type="button">-</button>'
    + '</div>'
   + '</div>'
    + '</div>'
    + '</div>'
     + '<br><div class="form-row d-flex justify-content-center">'
     + '<button type="bt" class="btn btn-primary" onclick="chooseTrip()" id="buttonSubmitValues" >Search</button></form>'
    + '</div><br>'

  // $('.tables-trips').html(html);
  document.getElementById("modalSizeValue").className = "modal-dialog modal-lg";

  $('.modal-content').html(html);
  $('#myModalNew').modal('show');
}

function more(){
  let intValue=parseInt(document.getElementById("passengerValue").value) + 1;
    document.getElementById("passengerValue").value=intValue
}

function less(){
  let intValue=parseInt(document.getElementById("passengerValue").value) - 1 ;
    if(intValue<= 1){
      intValue=1;
  }
  document.getElementById("passengerValue").value=intValue

}


function chooseTrip(){
  if(!checkFirstStepElements()){
    return false
  };
  extractTrips();
}

function   checkFirstStepElements() {

  var departure = document.getElementById('DepartureList').value;
  var destination = document.getElementById('DestinationList').value;
  let dateDeparture= document.getElementById('dateDeparture').value;
  let date_regex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00))))$/g;




  if ((departure == "") || (departure == "undefined")) {
    alert("Please insert a departure city! ");
    document.getElementById('Departure').focus();
    return false;
  }
  if ((destination == "") || (destination == "undefined")) {
    alert("Please insert a destination city! ");
    document.getElementById('Destination').focus();
    return false;
  }

  if (!(date_regex.test(dateDeparture))) {
    alert("Insert a valid Departure Time: Example 01/18/2021 21:45");
    document.getElementById('dateDeparture').focus();
    return false;
  }


  if (departure == destination) {
    alert("Please don't set same values for Departure and Destination city")
    return false;
  }
  return true;
}

function extractTrips() {
  var api = "http://localhost:8080/trips";

  return fetch(api)
    .then(response => response.json())
    .then(trips => {
      trips.sort(function (a, b) {
        return b.id - a.id;
      });

      html = '<table id="tripsTable" class="table table-hover table-striped">'
        + '<thead>'
        + '<tr>'
        + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:15%\"  scope="col">Departure</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Departure Time</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Duration</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Destination</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Arrival Time</th>'
          + '</tr>'
        + '</thead>'
        + '<tbody>';

      for (var i = 0; i < trips.length; i++) {
        html += '<tr align=\'center\'  >'
          + '<td  > ' + trips[i].departure.city + '</td>'
          + '<td  >' + trips[i].destination.city + '</td>';
        const date1 = new Date(trips[i].startDateFlight);
        const date2 = new Date(trips[i].endDateFlight);
        const diffTime = Math.abs(date2 - date1);
        const hours = Math.floor(diffTime / (1000 * 60*60));
        const minutes = Math.floor((diffTime / (1000 * 60)) -(hours*60)) ;
        let startDateFlightValue= trips[i].startDateFlight;
        startDateFlightValue= startDateFlightValue.substr(8,2)+"/"+startDateFlightValue.substr(5,2)+"/"+startDateFlightValue.substr(0,4)+" "+startDateFlightValue.substr(11,5);

        let endDateFlightValue= trips[i].endDateFlight;
        endDateFlightValue= endDateFlightValue.substr(8,2)+"/"+endDateFlightValue.substr(5,2)+"/"+endDateFlightValue.substr(0,4)+" "+endDateFlightValue.substr(11,5);



        html +=  '<td   >' + hours +' : '+minutes+'</td>'
          +  '<td   >' + startDateFlightValue + '</td>'
          + '<td   >' + endDateFlightValue + '</td>'
          + '<td   >'
          + '<div class="pointer">'

          + '</div></td>'
          + '</tr>';


      }
      html += '</tbody></table>';
      // console.log(html)
      $('#myModalNew').find('.modal-content').append(html);
    })
    .catch(err => {
      console.error('An error occurred', err);
    });


}

function editPurchase(row, idModify, isModify) {

  createEditWindow();

  loadingDataOnEdit(row, idModify, isModify);


}

function loadingDataOnEdit(row, idModify, isModify) {
  getObjectDB("airports")
    .then(airports => {
      airports.sort(function (a, b) {
        return b.id - a.id;
      });
      let selectDeparture = document.getElementById('Departure');
      let selectDestination = document.getElementById('Destination');
      if (isModify == true) {
        let tableRef = document.getElementById("tripsTable");
        var rowGetData = tableRef.rows;
        var colGetData = rowGetData[row].cells;
        let innerHtmlDepartureValue = colGetData[0].innerHTML;
        let innerHtmlDestinationValue = colGetData[1].innerHTML;
      }

      for (var i = 0; i < airports.length; i++) {
        let optDeparture = document.createElement('option');
        let optDestination = document.createElement('option');
        optDeparture.value = airports[i].city;
        optDeparture.setAttribute("data-value",  airports[i].id);

        optDestination.value = airports[i].id;
        optDestination.label = airports[i].name;

        selectDeparture.appendChild(optDeparture);
        if (isModify == true && airports[i].name.trim() == innerHtmlDepartureValue.trim()) {
          optDeparture.selected = true;

        }

        selectDestination.appendChild(optDestination);

        if (isModify == true && airports[i].name.trim() == innerHtmlDestinationValue.trim()) {
          optDestination.selected = true;

        }
      }

    })
    .catch(err => {
      console.error('An error occurred', err);
    });


  if (isModify == true) {

    let tableRef = document.getElementById("tripsTable");
    let rowGetData = tableRef.rows;
    let colGetData = rowGetData[row].cells;
    let innerHtmlTripStatusValue = colGetData[5].innerHTML;
    document.getElementById('departureTime').value = colGetData[2].innerHTML;
    document.getElementById('arrivalTime').value = colGetData[3].innerHTML;
    document.getElementById('tripStatus').value = innerHtmlTripStatusValue;
    document.getElementById('buttonSubmitValues').onclick = 'generatePutJson(idModify)';
    document.getElementById('buttonSubmitValues').setAttribute('onclick', 'generatePutJson(' + idModify + ')')

  }


}

function generateJson() {


  let checkValues = checkInputValues();
  if (!checkValues) {
    return false;
  }

  let departureValue= $("#Departure option[value='" + $('#dataList').val() + "']").attr('data-value');

 alert(departureValue);
  let obj = createJson();

  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/trips";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 201) {
      $('#myModalNew').modal('hide');
      loadTable();
    } else {
      alert("Error on creating new Trips, Error: " + status)
    }
  }
};


function generatePutJson(id) {

  let checkValues = checkInputValues();
  if (!checkValues) {
    return false;
  }

  let obj = createJson();

  obj.id = id;

  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/trips/" + id;

  xhr.open("PUT", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 200) {
      $('#myModalNew').modal('hide');
      loadTable();
    } else {
      alert("Error on creating new Trip, Error: " + status)
    }
  }
};

function createJson() {
  let departureJson = {
    id: document.getElementById("Departure").value
  }
  let destinationJson = {
    id: document.getElementById("Destination").value
  }
  let airplaneJson = {
    id: document.getElementById("Airplane").value
  }
  var departureTimeJson = document.getElementById("departureTime").value;
  var arrivalTimeJson = document.getElementById("arrivalTime").value;
  var tripStatusJson = document.getElementById("tripStatus").value;

  departureTimeJson = departureTimeJson.substr(6, 4) + "-" + departureTimeJson.substr(3, 2) + "-"
    + departureTimeJson.substr(0, 2) + " " + departureTimeJson.substr(11, 5) + ":00";
  arrivalTimeJson = arrivalTimeJson.substr(6, 4) + "-" + arrivalTimeJson.substr(3, 2) + "-"
    + arrivalTimeJson.substr(0, 2) + " " + arrivalTimeJson.substr(11, 5) + ":00";


  var obj = {
    departure: departureJson,
    destination: destinationJson,
    airplane: airplaneJson,
    startDateFlight: departureTimeJson,
    endDateFlight: arrivalTimeJson,
    tripStatus: tripStatusJson,
  };
  return obj;

}


function checkInputValues() {


  var departureTime = document.getElementById('departureTime').value;
  var arrivalTime = document.getElementById('arrivalTime').value;

  let date_regex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00)))) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/g;


  if (!(date_regex.test(departureTime))) {
    alert("Insert a valid Departure Time: Example 01/18/2021 21:45");
    document.getElementById('departureTime').focus();
    return false;
  }

  date_regex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00)))) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/g;

  if (!(date_regex.test(arrivalTime))) {
    alert("Insert a valid Arrival Time: Example 01/18/2021 21:45");
    document.getElementById('arrivalTime').focus();
    return false;
  }

  if (document.getElementById("Departure").value == document.getElementById("Destination").value) {
    alert("Please don't set same value for Departure and Destination Airport")
    return false;
  }


  return true;
}


function deleteRow(numRec) {
  if (!confirm("Are you sure?")) {
    return false;
  }
  var url = "http://localhost:8080/trips/";

  fetch(url + numRec, {
    method: 'DELETE'
  }).then(response => {

    if (response.status === 204) {
      loadTable();
    } else {
      response.text().then(function (text) {
        alert(text.substr(text.search("message") + 9, (text.length - text.search("message")) - (text.length - text.search("time")) - 11));
      })


    }

  });


};


