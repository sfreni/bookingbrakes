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


          html += '<table id="tripsTable" class="table table-hover table-striped">'
            + '<thead>'
            + '<tr>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:15%\"  scope="col">Departure</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Destination</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Departure Time</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Arrival Time</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Airplane</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Trip Status</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Methods</th>'
            + '</tr>'
            + '</thead>'
            + '<tbody>';

          for (var i = 0; i < result.length; i++) {
            html += '<tr align=\'center\'  >'
              + '<td  > ' + result[i].departure.name + '</td>'
              + '<td  >' + result[i].destination.name + '</td>'
              + '<td   >' + result[i].startDateFlight.substr(0, 16) + '</td>'
              + '<td   >' + result[i].endDateFlight.substr(0, 16) + '</td>'
              + '<td   >' + result[i].airplane.name + '</td>'
              + '<td   >' + result[i].tripStatus + '</td>'
              + '<td   >'
              + '<div class="pointer">'
              + '<i class="fa fa-pencil" aria-hidden="true" onclick="modifyRow(' + result[i].id + ',' + (i + 1) + ')" title="Modify Customer"></i>&nbsp;'
              + '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete Customer"></i>&nbsp;'
              + '<i class="fa fa-shopping-bag" aria-hidden="true" onclick="listPurchase(' + i + ')" title="List Purchase" ></i>&nbsp;'

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
  if (window.jsonValues[id].purchases[0] != undefined) {
    let html = '<br><p><h2 align="center">Purchases</h2></p>' +
      '<h4 align="center">Customer:<b>' + window.jsonValues[id].firstName + ' ' + window.jsonValues[id].lastName + '</b></h4>' +
      '<br><table id="purchasesTable" class="table table-hover table-striped">' +
      '<thead>'
      + '<tr>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">N.</th>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Date</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Status</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Date Flight</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Departure</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:15%\"  scope="col">Destination</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Status Flight</th>'
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
        + '<td  > ' + window.jsonValues[id].purchases[i].datePurchase + '</td>'
      if (window.jsonValues[id].purchases[i].purchaseStatus == "COMPLETE") {
        html += '<td  ><i  class="fa fa-check" aria-hidden="true" title="Complete"></i></td>'
      } else {
        html += '<td  ><i  class="fa fa-times" aria-hidden="true" title="Not Complete"></i></td>'
      }
      html += '<td   >' + window.jsonValues[id].purchases[i].trip.startDateFlight + '</td>'
        + '<td   >' + window.jsonValues[id].purchases[i].trip.departure.name + '</td>'
        + '<td   >' + window.jsonValues[id].purchases[i].trip.destination.name + '</td>'
        + '<td   >' + window.jsonValues[id].purchases[i].trip.tripStatus + '</td>';
      let totalAmount = 0;
      for (var j = 0; j < window.jsonValues[id].purchases[i].products.length; j++) {
        totalAmount += window.jsonValues[id].purchases[i].products[j].priceAmount;
      }


      html += '<td   >€ ' + Number(totalAmount).toLocaleString("it-IT") + '</td>'


        + '</tr>';
    }
    $('.modal-content').html(html);
    $('#myModal').modal('show');


  } else {
    let html = '<br><h2 align="center">Purchases</h2>' +
      '<h4 align="center"><br>Customer:<b>' + window.jsonValues[id].firstName + ' ' + window.jsonValues[id].lastName + '</b></h4>' +
      '<h4 align="center"><br><b>No Purchases</b></h4><br>'
    $('.modal-content').html(html);
    $('#myModal').modal('show');
  }


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
    + '<button type="bt" class="btn btn-primary" onclick="generateJson()">Submit</button></form>'
    + '</div><br>'

  // $('.tables-trips').html(html);
    $('.modal-content').html(html);
    $('#myModalNew').modal('show');
}

function addTrip() {

  createEditWindow();

  getObjectDB("airports")
    .then(airports => {
      airports.sort(function (a, b) {
        return b.id - a.id;
      });
      let selectDeparture = document.getElementById('Departure');
      let selectDestination = document.getElementById('Destination');

      for (var i = 0; i < airports.length; i++) {
        let optDeparture = document.createElement('option');
        let optDestination = document.createElement('option');
        optDeparture.value=airports[i].id;
        optDeparture.innerHTML = airports[i].name;
        optDestination.value=airports[i].id;
        optDestination.innerHTML = airports[i].name;

        selectDeparture.appendChild(optDeparture);
        selectDestination.appendChild(optDestination);
      }

    })
    .catch(err => {
      console.error('An error ocurred', err);
    });

  getObjectDB("airplanes")
    .then(airplanes => {
      airplanes.sort(function (a, b) {
        return b.id - a.id;
      });
      let selectAirplane = document.getElementById('Airplane');

      for (var i = 0; i < airplanes.length; i++) {
        let optAirplane = document.createElement('option');
        optAirplane.value=airplanes[i].id;
        optAirplane.innerHTML = airplanes[i].name;

        selectAirplane.appendChild(optAirplane);
      }

    })
    .catch(err => {
      console.error('An error ocurred', err);
    });



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
      loadTable();
    } else {
      alert("Error on creating new Trips, Error: " + status)
    }
  }
  console.log(obj);
};


function generatePutJson(id) {


  let checkValues = checkInputValues();
  if (!checkValues) {
    return false;
  }

  let obj = createJson();

  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/customers/" + id;

  xhr.open("PUT", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 200) {
      loadTable();
    } else {
      alert("Error on creating new Customer, Error: " + status)
    }
  }
  console.log(status);
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

function modifyRow(id, row) {
// Get a reference to the table
  let firstName = document.getElementById('firstName');
  let tableRef = document.getElementById("customersTable");

  if (typeof (firstName) == 'undefined' || firstName == null) {

  } else {
    var addRow = document.getElementById("addRow");

    if (typeof (addRow) == 'undefined' || addRow == null) {
      for (i = 0; i < tableRef.rows.length; i++) {
        tableRef.rows[i].style.display = "";
      }
      var rowShow = document.getElementById("modifyRow");
      rowShow.parentNode.removeChild(rowShow);
    } else {
      addRow.parentNode.removeChild(addRow);

    }
  }

  let newRow = tableRef.insertRow(row);
  newRow.id = "modifyRow";

  let newCell = newRow.insertCell(0);
  let inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "firstName"
  inputValue.id = "firstName";
  inputValue.style.width = "100%";
  var rowCreate = tableRef.rows;
  var colCreate = rowCreate[row + 1].cells;
  inputValue.value = colCreate[0].innerHTML;
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(1);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "lastName"
  inputValue.id = "lastName";
  inputValue.style.width = "100%";

  inputValue.value = colCreate[1].innerHTML;
  newCell.appendChild(inputValue);

  newCell = newRow.insertCell(2);


  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "streetAddress"
  inputValue.id = "streetAddress";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[2].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(3);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "city"
  inputValue.id = "city";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[3].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(4);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "birthDay"
  inputValue.id = "birthDay";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[4].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(5);


  let buttonSave = document.createElement("button");
  buttonSave.textContent = "Save";
  buttonSave.id = "createJson";
  newCell.appendChild(buttonSave);


  //
  buttonSave.setAttribute("onclick", 'generatePutJson(' + id + ')');
  tableRef.rows[row + 1].style.display = "none";

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
  return true;
}


function deleteRow(numRec) {
  if (!confirm("Are you sure?")) {
    return false;
  }
  var url = "http://localhost:8080/customers/";

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


