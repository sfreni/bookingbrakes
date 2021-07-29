function loadTable() {
  var api = "http://localhost:8080/trips";
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
          var html = '<div class="tables-trips-content">';
          var queryString = location.search
          let params = new URLSearchParams(queryString)
          result = result.sort(function (a, b) {
            return b.id - a.id;
          });


          html += '<h2 align="center">Flights:  '+result.length+'</h2><table id="tripsTable" class="table table-hover table-striped">'
            + '<thead>'
            + '<tr>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Departure</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Destination</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Departure Time</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Arrival Time</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Airplane</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Trip Status</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Reserved Book</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Methods</th>'
            + '</tr>'
            + '</thead>'
            + '<tbody>';
          for (var i = 0; i < result.length; i++) {
            html += '<tr align=\'center\'  >'
              + '<td  > ' + result[i].departure.city + '</td>'
              + '<td  >' + result[i].destination.city + '</td>';
            let startDateFlightValue= result[i].startDateFlight;
            startDateFlightValue= startDateFlightValue.substr(8,2)+"/"+startDateFlightValue.substr(5,2)+"/"+startDateFlightValue.substr(0,4)+" "+startDateFlightValue.substr(11,5);

            let endDateFlightValue= result[i].endDateFlight;
            endDateFlightValue= endDateFlightValue.substr(8,2)+"/"+endDateFlightValue.substr(5,2)+"/"+endDateFlightValue.substr(0,4)+" "+endDateFlightValue.substr(11,5);

            html +=  '<td   >' + startDateFlightValue + '</td>'
            html +=  '<td   >' + endDateFlightValue + '</td>'
              + '<td   >' + result[i].airplane.name + '</td>'
              + '<td   >' + result[i].tripStatus + '</td>'


            let productList=[];
            for (let j = 0; j < result[i].purchases.length; j++) {
              for (let k = 0; k < result[i].purchases[j].products.length; k++) {
                if(result[i].purchases[j].products[k].type=="Seat"){
                  productList.push(result[i].purchases[j].products[k].nrSeat);
                }
              }
            }

            let reservedPercentage=parseInt(productList.length / result[i].airplane.numberSeats *100);

            html +=  '<td   >'+productList.length+ '/'+result[i].airplane.numberSeats+ '<b> (' + reservedPercentage + '%)</b></td>'
              + '<td   >'
              + '<div class="pointer">'
              + '<i class="fa fa-pencil" aria-hidden="true" onclick="editTrip(' + (i + 1) + ',' + result[i].id + ',true)" title="Modify Trip"></i>&nbsp;'
              + '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete Trip"></i>&nbsp;'
              + '<i class="fa fa-shopping-bag" aria-hidden="true" onclick="listPurchase(' + i + ')" title="List Purchase" ></i>&nbsp;'
              + '<i class="fa fa-folder-open" aria-hidden="true" onclick="lookAvaibleSeats(' + result[i].airplane.numberSeats + ','+ result[i].id +','+"true"+')" title="List Seats" ></i>&nbsp;'
              + '</div></td>'
              + '</tr>';


          }
          html += '</tbody></table>';

          //      }
          html += '</div>';
          // Set all content
          $('.tables-trips').html(html);
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
      let dateDeparture= window.jsonValues[id].startDateFlight.substr(8,2)+"/"+window.jsonValues[id].startDateFlight.substr(5,2)+"/"+window.jsonValues[id].startDateFlight.substr(0,4)+" "+window.jsonValues[id].startDateFlight.substr(11,5);
     let dateArrivale= window.jsonValues[id].endDateFlight.substr(8,2)+"/"+window.jsonValues[id].endDateFlight.substr(5,2)+"/"+window.jsonValues[id].endDateFlight.substr(0,4)+" "+window.jsonValues[id].endDateFlight.substr(11,5);
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
    purchases = window.jsonValues[id].purchases;
    purchases = purchases.sort(function (a, b) {
      return b.id - a.id;
    });
    for (var i = 0; i < purchases.length; i++) {
      html += '<tr align=\'center\'  >'
        + '<td  > ' + (i + 1) + '</td>'
      let datePurchase= purchases[i].datePurchase.substr(8,2)+"/"+purchases[i].datePurchase.substr(5,2)+"/"+purchases[i].datePurchase.substr(0,4)+" "+purchases[i].datePurchase.substr(11,5);


      html += '<td  > ' + datePurchase + '</td>'
      if (purchases[i].purchaseStatus == "COMPLETE") {
        html += '<td  ><i  class="fa fa-check" aria-hidden="true" title="Complete"></i></td>'
      } else {
        html += '<td  ><i  class="fa fa-times" aria-hidden="true" title="Not Complete"></i></td>'
      }
      html += '<td>' + purchases[i].customer.firstName+ ' ' + purchases[i].customer.lastName+ '</td>'
      let totalAmount = 0;
      for (var j = 0; j < purchases[i].products.length; j++) {
        totalAmount += purchases[i].products[j].priceAmount;
      }


      html += '<td   >€ ' + Number(totalAmount).toLocaleString("it-IT") + '</td>'


        + '</tr>';
    }



  } else {
     html = '<br><h2 align="center">Purchases</h2>' +
      '<h4 align="center"><br>Departure:<b>' + window.jsonValues[id].departure.name + '</b></h4>' +
      '<h4 align="center"><br>Destination:<b>'+ window.jsonValues[id].destination.name + '</b></h4>' +
      '<h4 align="center"><br><b>No Purchases</b></h4><br>';
  }
  document.getElementById("modalSizeValue").className="modal-dialog modal-xl";
  $('.modal-content').html(html);
  $('#myModalNew').modal('show');

}

function getObjectDB(ending) {
  var api = "http://localhost:8080/"+ending;

  return fetch(api)
    .then(response => response.json())

}
function createEditWindow(){
  let html =  '<br>'
    + '<div class="container border">'
    + '<br><div class="form-row d-flex justify-content-center">'
    + '<div class="col-5">'
    + '<label for="depatureLabel">Departure</label>'
     + ' <select class="form-control form-control-sm" id="Departure">'

  + '</select>'
     // + '<small id="emailHelp" class="form-text text-muted">Well never share your email with anyone else.</small>'
     + '</div>'
     + '<div class="col-5">'
     + ' <label for="destinationLabel">Destination</label>'
    + ' <select class="form-control form-control-sm"  id="Destination">'

 + '</select>'
     + '</div>'
    + '</div>'
    + '<br><div class="form-row d-flex justify-content-center">'

    + '<div class="col-5">'
    + ' <label for="exampleInputPassword1">Departure Time</label>'
    + ' <input type="text" class="form-control form-control-sm" id="departureTime" placeholder="gg/mm/yyyy HH:MM">'
    + '</div>'

    + '<div class="col-5">'
    + ' <label for="exampleInputPassword1">Arrival Time</label>'
    + ' <input type="text" class="form-control form-control-sm" id="arrivalTime" placeholder="gg/mm/yyyy HH:MM">'
    + '</div>'
    + '</div>'
    + '<br><div class="form-row d-flex justify-content-center">'

    + ' <div class="col-5">'
    + ' <label for="airplaneLabel">Airplane</label>'
    + ' <select class="form-control form-control-sm"  id="Airplane">'

    + '</select>'
    + '</div>'
    + ' <div class="col-5">'
    + ' <label for="tripStatus" >Status</label>'
    + ' <select class="form-control form-control-sm"  id="tripStatus">'
    + ' <option value="AVAILABLE">AVAILABLE</option>'
    + ' <option value="NOT_AVAILABLE">NOT_AVAILABLE</option>'
    + ' <option value="CANCELLED">CANCELLED</option>'
    + '</select>'
    + '</div></div>'
    + '<br><div class="form-row d-flex justify-content-center">'
    + '<button type="bt" class="btn btn-primary" onclick="generateJson()" id="buttonSubmitValues" >Submit</button></form>'
    + '</div><br>'

  // $('.tables-trips').html(html);
  document.getElementById("modalSizeValue").className="modal-dialog modal-sx";

  $('.modal-content').html(html);
    $('#myModalNew').modal('show');
}

function editTrip(row, idModify, isModify) {

  createEditWindow();

  loadingDataOnEdit(row,idModify,isModify);


}

function loadingDataOnEdit(row,idModify,isModify){
  getObjectDB("airports")
    .then(airports => {
      airports.sort(function (a, b) {
        return b.id - a.id;
      });
      let selectDeparture = document.getElementById('Departure');
      let selectDestination = document.getElementById('Destination');

      let tableRef = document.getElementById("tripsTable");
      var rowGetData = tableRef.rows;
      var colGetData = rowGetData[row].cells;
      let innerHtmlDepartureValue = colGetData[0].innerHTML;
      let innerHtmlDestinationValue = colGetData[1].innerHTML;


      for (var i = 0; i < airports.length; i++) {
        let optDeparture = document.createElement('option');
        let optDestination = document.createElement('option');
        optDeparture.value=airports[i].id;
        optDeparture.innerHTML = airports[i].name;

        optDestination.value=airports[i].id;
        optDestination.innerHTML = airports[i].name;

        selectDeparture.appendChild(optDeparture);
        if(isModify==true &&  airports[i].name.trim()==innerHtmlDepartureValue.trim()){
          optDeparture.selected= true;

        }

        selectDestination.appendChild(optDestination);

        if(isModify==true &&  airports[i].name.trim()==innerHtmlDestinationValue.trim()){
          optDestination.selected= true;

        }
      }

    })
    .catch(err => {
      console.error('An error occurred', err);
    });

  getObjectDB("airplanes")
    .then(airplanes => {
      airplanes.sort(function (a, b) {
        return b.id - a.id;
      });
      let tableRef = document.getElementById("tripsTable");
      var rowGetData = tableRef.rows;
      var colGetData = rowGetData[row].cells;
      let innerHtmlAirplaneValue = colGetData[4].innerHTML;

      let selectAirplane = document.getElementById('Airplane');

      for (var i = 0; i < airplanes.length; i++) {
        let optAirplane = document.createElement('option');
        optAirplane.value=airplanes[i].id;
        optAirplane.innerHTML = airplanes[i].name;

        selectAirplane.appendChild(optAirplane);
         if(isModify==true &&  airplanes[i].name.trim()==innerHtmlAirplaneValue.trim()){
           optAirplane.selected= true;
         }

      }

    })
    .catch(err => {
      console.error('An error ocurred', err);
    });
  if(isModify==true) {

    let tableRef = document.getElementById("tripsTable");
    let rowGetData = tableRef.rows;
    let colGetData = rowGetData[row].cells;
    let innerHtmlTripStatusValue = colGetData[5].innerHTML;
    document.getElementById('departureTime').value = colGetData[2].innerHTML;
    document.getElementById('arrivalTime').value = colGetData[3].innerHTML;
    document.getElementById('tripStatus').value = innerHtmlTripStatusValue;
    document.getElementById('buttonSubmitValues').onclick = 'generatePutJson(idModify)';
    document.getElementById('buttonSubmitValues').setAttribute('onclick','generatePutJson('+idModify+')')

  }


}
function generateJson() {


  let checkValues = checkInputValues();
  if (!checkValues) {
    return false;
  }


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

  obj.id=id;

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
  let departureJson ={
    id: document.getElementById("Departure").value
  }
  let destinationJson ={
    id: document.getElementById("Destination").value
  }
  let airplaneJson ={
    id: document.getElementById("Airplane").value
  }
  var departureTimeJson = document.getElementById("departureTime").value;
  var arrivalTimeJson = document.getElementById("arrivalTime").value;
  var tripStatusJson = document.getElementById("tripStatus").value;

  departureTimeJson=departureTimeJson.substr(6,4)+"-"+departureTimeJson.substr(3,2)+"-"
    +departureTimeJson.substr(0,2) +" "+departureTimeJson.substr(11,5)+":00";
  arrivalTimeJson=arrivalTimeJson.substr(6,4)+"-"+arrivalTimeJson.substr(3,2)+"-"
    +arrivalTimeJson.substr(0,2) +" "+arrivalTimeJson.substr(11,5)+":00";



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

  if( document.getElementById("Departure").value == document.getElementById("Destination").value){
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



function lookAvaibleSeats(nrSeats, idFlight,isModify) {

  let api = "http://localhost:8080/trips/"+idFlight;


  return fetch(api)
    .then(response => response.json())
    .then(tripId => {


      let productList=[];
      for (let i = 0; i < tripId.purchases.length; i++) {
        for (let j = 0; j < tripId.purchases[i].products.length; j++) {
          if(tripId.purchases[i].products[j].type=="Seat"){
            productList.push(tripId.purchases[i].products[j].nrSeat);
          }
        }
      }



      let html = ''
      let productPurchase=[];
      let reservedPercentage=parseInt(productList.length / tripId.airplane.numberSeats *100);
      html += '<br><p><h2 align="center" id="amountValue">Reserved Seats N. '+productList.length+'/'+tripId.airplane.numberSeats+' ('+reservedPercentage+'%)</h2></p>' +
        '<h4 align="center">Departure: <b>' + tripId.departure.city + '</b> Destination: <b>' + tripId.destination.city + '</b></h4>'
      let dateDeparture= tripId.startDateFlight.substr(8,2)+"/"+tripId.startDateFlight.substr(5,2)+"/"+tripId.startDateFlight.substr(0,4)+" "+tripId.startDateFlight.substr(11,5);
      let dateArrivale= tripId.endDateFlight.substr(8,2)+"/"+tripId.endDateFlight.substr(5,2)+"/"+tripId.endDateFlight.substr(0,4)+" "+tripId.endDateFlight.substr(11,5);
      html += '<p><h4 align="center">Departure Time: <b>' + dateDeparture + '</b></p> <p>Arrival Time: <b>' + dateArrivale + '</b></p></h4>'
      +  '<table id="seatsTable">'
      let nrSeatsRow = Math.floor(nrSeats / 6);

      for (let i = 0; i <= nrSeatsRow; i++) {
        html += '<tr >';
        let character = convertToNumberingScheme(i + 1);
        for (let j = 1; j <= 6; j++) {
          if (((i * 6) + j) <= nrSeats) {

            if (j == 4) {
              html += '  <td width="10%">' + i

                + ' </td>'

            }
            html += '  <td width="10%">'
            if(productList.indexOf(character + '0' + j )>=0){

              if(isModify && productPurchase.indexOf(character + '0' + j )>=0) {
                html +=  '   <input type="checkbox" id="seat' + ((i * 6) + j) + '" onclick="checkPassengers(' + nrSeats + ',' + ((i * 6) + j) + ')" disabled />'
                  +    '   <label for="seat' + ((i * 6) + j) + '" id="label' + ((i * 6) + j) + '">' + character + '0' + j + '</label>'

              }else{
                html +=  '   <input type="checkbox" id="seat' + ((i * 6) + j) + '" onclick="checkPassengers(' + nrSeats + ',' + ((i * 6) + j) + ')" checked disabled/>'
                  +              '   <label for="seat' + ((i * 6) + j) + '" id="label' + ((i * 6) + j) + '">' + 'X' + '</label>'
              }
            }else{
              html +=  '   <input type="checkbox" id="seat' + ((i * 6) + j) + '" onclick="checkPassengers(' + nrSeats + ',' + ((i * 6) + j) + ')" disabled/>'
                +              '   <label for="seat' + ((i * 6) + j) + '" id="label' + ((i * 6) + j) + '">' + character + '0' + j + '</label>'
            }
            html +=  ' </td>'

          }

        }

        html += '</tr >'

      }
      html += '</table>';
      document.getElementById("modalSizeValue").className="modal-dialog   ";
      $('.modal-content').html(html);
      $('#myModalNew').modal('show');

    })
    .catch(err => {
      console.error('An error occurred', err);
    });

}

function convertToNumberingScheme(number) {
  var baseChar = ("A").charCodeAt(0),
    letters = "";

  do {
    number -= 1;
    letters = String.fromCharCode(baseChar + (number % 26)) + letters;
    number = (number / 26) >> 0;
  } while (number > 0);

  return letters;
}
