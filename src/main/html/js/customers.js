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
          result = result.sort(function(a,b) {
            return b.id - a.id;
          });


          html += '<h2 align="center">Customers:  '+result.length+'</h2><table id="customersTable" class="table table-hover table-striped">'
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
              + '<td   >' + result[i].city + '</td>';
            let birthDayHtml=result[i].birthDay.substr(8,2)+"/"+result[i].birthDay.substr(5,2)+"/"+result[i].birthDay.substr(0,4);

            html +=  '<td   >' + birthDayHtml + '</td>'

 //           html +=  '<td   >' + result[i].birthDay + '</td>'
                + '<td   >'
              + '<div class="pointer">'
               + '<i class="fa fa-pencil" aria-hidden="true" onclick="modifyRow(' + result[i].id + ',' + (i + 1) + ')" title="Modify Customer"></i>&nbsp;'
             + '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete Customer"></i>&nbsp;'
               +'<i class="fa fa-shopping-bag" aria-hidden="true" onclick="listPurchase(' +i+ ')" title="List Purchase" ></i>&nbsp;'
               +'<i class="fa fa-credit-card" aria-hidden="true" onclick="listCreditCard(' + result[i].id + ')" title="List CreditCard" ></i>&nbsp;'

            +  '</div></td>'
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
      let  html= '<br><p><h2 align="center">Purchases</h2></p>' +
        '<h4 align="center">Customer:<b>'+window.jsonValues[id].firstName+' '+window.jsonValues[id].lastName +'</b></h4>' +
        '<br><table id="purchasesTable" class="table table-hover table-striped">'+
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
      let purchases  = window.jsonValues[id].purchases;
      purchases = purchases.sort(function(a,b) {
         return b.id - a.id;
       });
      for (var i = 0; i < purchases.length; i++) {
        html += '<tr align=\'center\'  >'
          + '<td  > ' + (i+1)+ '</td>'
          + '<td  > ' + purchases[i].datePurchase + '</td>'
          if(purchases[i].purchaseStatus == "COMPLETE"){
            html += '<td  ><i  class="fa fa-check" aria-hidden="true" title="Complete"></i></td>'}else{
            html +=   '<td  ><i  class="fa fa-times" aria-hidden="true" title="Not Complete"></i></td>'
          }
        html += '<td   >' + purchases[i].trip.startDateFlight + '</td>'
          + '<td   >' + purchases[i].trip.departure.city + '</td>'
          + '<td   >' + purchases[i].trip.destination.city + '</td>'
          + '<td   >' + purchases[i].trip.tripStatus + '</td>';
        let totalAmount =0;
        for (var j = 0; j < purchases[i].products.length; j++) {
                totalAmount+=purchases[i].products[j].priceAmount;
        }


        html += '<td   >€ ' + Number(totalAmount).toLocaleString("it-IT") + '</td>'


          + '</tr>';
      }
      $('.modal-content').html(html);
      $('#myModal').modal('show');


    }else{
      let  html= '<br><h2 align="center">Purchases</h2>' +
        '<h4 align="center"><br>Customer:<b>'+window.jsonValues[id].firstName+' '+window.jsonValues[id].lastName +'</b></h4>' +
        '<h4 align="center"><br><b>No Purchases</b></h4><br>'
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
  inputValue.placeholder = "gg/mm/aaaa";
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

  birthDayJson=birthDayJson.substr(6,4)+"-"+birthDayJson.substr(3,2)+"-"
    +birthDayJson.substr(0,2) + " "+"00:00:00";

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
  let date_regex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00))))$/g;

  if (!(date_regex.test(birthDay))) {
    alert("Insert a valid Birthday");
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


