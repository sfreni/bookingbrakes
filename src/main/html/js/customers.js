function loadTable() {
  var api = "http://localhost:8080/customers";
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
          window.jsonValues =result;
          var html = '<div class="tables-customers-content">';
          var queryString = location.search
          let params = new URLSearchParams(queryString)


          html += '<table id="customersTable" class="table table-hover table-striped">'
            + '<thead>'
            + '<tr>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">First Name</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Last Name</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Street Address</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">City</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Birthday</th>'
          + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Methods</th>'
            + '</tr>'
            + '</thead>'
            + '<tbody>';
          for (var i = 0; i < result.length; i++) {
            html += '<tr align=\'center\'  >'
              + '<td  > ' + result[i].firstName + '</td>'
              + '<td  >' + result[i].lastName + '</td>'
              + '<td   >' + result[i].streetAddress + '</td>'
              + '<td   >' + result[i].city + '</td>'
              + '<td   >' + result[i].birthDay + '</td>'
                + '<td   >' +
              '<div class="pointer">'+
            //  '<i class="fa fa-credit-card" aria-hidden="true" onclick="listCreditCard(' + result[i].id +  ')" title="List Credit Card"></i>&nbsp;' +
              '<i class="fa fa-pencil" aria-hidden="true" onclick="modifyRow(' + result[i].id + ',' + (i + 1) + ')" title="Modify Customer"></i>&nbsp;' +
              '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete Customer"></i>&nbsp;'
            if(result[i].purchases[0] != undefined){
              html +='<i class="fa fa-shopping-bag" aria-hidden="true" onclick="listPurchase(' +i+ ')" title="List Purchase" ></i>&nbsp;' }
            if(result[i].creditCard[0] != undefined){
              html +='<i class="fa fa-credit-card" aria-hidden="true" onclick="listCreditCard(' +i+ ')" title="List CreditCard" ></i>&nbsp;' }

            html +=  '</div></td>'
              + '</tr>';


          }
          html += '</tbody></table>';

          //      }
          html += '</div>';
          // Set all content
          $('.tables-customers').html(html);
        }, 1000);
      })
      .fail(function () {
        alert('Error : Failed to reach API Url or check your connection');
      })

  });
}
function listPurchase(id) {
    if(window.jsonValues[id].purchases[0]!= undefined){
    let  html = '<table id="purchasesTable" class="table table-hover table-striped">'
        + '<thead>'
        + '<tr>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Id</th>'
        + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Date</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Status</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Date Flight</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Departure</th>'
        + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Destination</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Status Flight</th>'
      + '</tr>'
        + '</thead>'
        + '<tbody>';
      for (var i = 0; i < window.jsonValues[id].purchases.length; i++) {
        html += '<tr align=\'center\'  >'
          + '<td  > ' + window.jsonValues[id].purchases[i].id + '</td>'
          + '<td  > ' + window.jsonValues[id].purchases[i].datePurchase + '</td>'
          + '<td  >' + window.jsonValues[id].purchases[i].purchaseStatus + '</td>'
          + '<td   >' + window.jsonValues[id].purchases[i].trip.startDateFlight + '</td>'
          + '<td   >' + window.jsonValues[id].purchases[i].trip.departure.name + '</td>'
          + '<td   >' + window.jsonValues[id].purchases[i].trip.destination.name + '</td>'
          + '<td   >' + window.jsonValues[id].purchases[i].trip.tripStatus + '</td>'

          + '</tr>';
      }
      $('.modal-content').html(html);
      $('#myModal').modal('show');


    }


}

function listCreditCard(id) {
  if(window.jsonValues[id].creditCard[0]!= undefined){
    let  html = '<table id="creditCardTable" class="table table-hover table-striped">'
      + '<thead>'
      + '<tr>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Id</th>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Number</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Issuer-Network</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">First Name</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Last Name</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Date Expiration</th>'
      + '</tr>'
      + '</thead>'
      + '<tbody>';
    for (var i = 0; i < window.jsonValues[id].creditCard.length; i++) {
      html += '<tr align=\'center\'  >'
        + '<td  > ' + window.jsonValues[id].creditCard[i].id + '</td>'
        + '<td  > ' + window.jsonValues[id].creditCard[i].numberCard + '</td>'
        + '<td  >' + window.jsonValues[id].creditCard[i].issuingNetwork + '</td>'
        + '<td  > ' + window.jsonValues[id].creditCard[i].firstName + '</td>'
        + '<td  >' + window.jsonValues[id].creditCard[i].lastName + '</td>'
        + '<td  >' + window.jsonValues[id].creditCard[i].dateExpiration + '</td>'


        + '</tr>';
    }
    $('.modal-content').html(html);
    $('#myModal').modal('show');


  }


}


function addRow() {
// Get a reference to the table
  let firstName = document.getElementById('firstName');
  let tableRef = document.getElementById("customersTable");

  if (typeof (firstName) == 'undefined' || firstName == null) {

  } else {
    var rowShow = document.getElementById("modifyRow");

    if (typeof (rowShow) == 'undefined' || rowShow == null) {
      return false;
    } else {
      for (i = 0; i < tableRef.rows.length; i++) {
        tableRef.rows[i].style.display = "";
      }
      rowShow.parentNode.removeChild(rowShow);
    }


  }


  let newRow = tableRef.insertRow(1);
  newRow.id = "addRow";

  let newCell = newRow.insertCell(0);
  let inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "firstName"
  inputValue.id = "firstName";
  inputValue.style.width = "100%";

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(1);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "lastName"
  inputValue.id = "lastName";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);

  newCell = newRow.insertCell(2);



  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "streetAddress"
  inputValue.id = "streetAddress";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(3);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "city"
  inputValue.id = "city";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(4);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "birthDay"
  inputValue.id = "birthDay";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(5);



  let buttonSave = document.createElement("button");
  buttonSave.textContent = "Save";
  buttonSave.id = "createJson";
  newCell.appendChild(buttonSave);
  buttonSave.setAttribute("onclick", 'generateJson()');

}


function generateJson() {


  let checkValues = checkInputValues();
  if (!checkValues) {
    return false;
  }



  let obj=createJson();

  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/customers";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 201) {
      loadTable();
    } else {
      alert("Error on creating new Customer, Error: " + status)
    }
  }
  console.log(status);
};


function generatePutJson(id) {



  let checkValues = checkInputValues();
  if (!checkValues) {
    return false;
  }

  let obj=createJson();

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
  var firstNameJson = document.getElementById("firstName").value;
  var lastNameJson = document.getElementById("lastName").value;
  var cityJson = document.getElementById("city").value;
  var streetAddressJson = document.getElementById("streetAddress").value;
  var birthDayJson = document.getElementById("birthDay").value;

  var obj = {
    firstName: firstNameJson,
    lastName: lastNameJson,
    city: cityJson,
    streetAddress: streetAddressJson,
    birthDay: birthDayJson,
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

  let firstName = document.getElementById('firstName').value;

  if ((firstName == "") || (firstName == "undefined")) {
    alert("Please insert the First Name! ");
    document.getElementById('firstName').focus();
    return false;
  }

  var lastName = document.getElementById('lastName').value;

  if ((lastName == "") || (lastName == "undefined")) {
    alert("Please insert the LastName  !");
    document.getElementById('lastName').focus();
    return false;
  }


  var streetAddress = document.getElementById('streetAddress').value;

  if ((streetAddress == "") || (streetAddress == "undefined")) {
    alert("Please insert the Street Address");
    document.getElementById('streetAddress').focus();
    return false;
  }
  var city = document.getElementById('city').value;

  if ((city == "") || (city == "undefined")) {
    alert("Please insert the city !");
    document.getElementById('city').focus();
    return false;
  }

  var birthDay = document.getElementById('birthDay').value;

  if ((birthDay == "") || (birthDay == "undefined")) {
    alert("Please insert the Birthday");
    document.getElementById('birthDay').focus();
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
