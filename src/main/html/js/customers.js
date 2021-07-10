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
      result = window.jsonValues;
      result = result.sort(function(a,b) {
        return b.id - a.id;
      });
      for (var i = 0; i < window.jsonValues[id].purchases.length; i++) {
        html += '<tr align=\'center\'  >'
          + '<td  > ' + (i+1)+ '</td>'
          + '<td  > ' + window.jsonValues[id].purchases[i].datePurchase + '</td>'
          if(window.jsonValues[id].purchases[i].purchaseStatus == "COMPLETE"){
            html += '<td  ><i  class="fa fa-check" aria-hidden="true" title="Complete"></i></td>'}else{
            html +=   '<td  ><i  class="fa fa-times" aria-hidden="true" title="Not Complete"></i></td>'
          }
        html += '<td   >' + window.jsonValues[id].purchases[i].trip.startDateFlight + '</td>'
          + '<td   >' + window.jsonValues[id].purchases[i].trip.departure.name + '</td>'
          + '<td   >' + window.jsonValues[id].purchases[i].trip.destination.name + '</td>'
          + '<td   >' + window.jsonValues[id].purchases[i].trip.tripStatus + '</td>';
        let totalAmount =0;
        for (var j = 0; j < window.jsonValues[id].purchases[i].products.length; j++) {
                totalAmount+=window.jsonValues[id].purchases[i].products[j].priceAmount;
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

function listCreditCard(id) {
  var api = "http://localhost:8080/creditcards/customers/"+id;
  let  html
  $(document).ready(function () {
    $.ajax({
      url: api,
      method: 'GET',
      cache: false,
      type: "text/json"
    })
      .done(function (evt) {

        setTimeout(function () {
          var resultCreditCard = evt;
          var queryString = location.search
          let params = new URLSearchParams(queryString)

          html= '<br><p><h2 align="center">List Credit Card</h2></p>' +
            '<h4 align="center">Customer:<b>'+resultCreditCard.customer.firstName+' '+resultCreditCard.customer.lastName +'</b></h4>' +
            '<p align=\'center\'><button class=\'btnServgreen\' type=\'button\' onclick="addCreditCardRow()"><i class=\'fa fa-pencil\'></i> New(+)</button>\n' +
            '</p>' +
            '<input type="hidden" id="custId" name="custId" value='+resultCreditCard.customer.id +'><table id="creditCardTable" class="table table-hover table-striped">'
            + '<thead>'
            + '<tr>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Num.</th>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Number</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Issuer-Network</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">First Name</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Last Name</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Date Expiration</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Methods</th>'
            + '</tr>'
            + '</thead>'
            + '<tbody>';
          resultCreditCard.creditCardDtoSingles = resultCreditCard.creditCardDtoSingles.sort(function(a,b) {
            return b.id - a.id;
          });
          for (var i = 0; i < resultCreditCard.creditCardDtoSingles.length; i++) {
            html += '<tr align=\'center\'  >'
              + '<td  > ' + (i+1) + '</td>'
              + '<td  > ' + resultCreditCard.creditCardDtoSingles[i].numberCard + '</td>'
              + '<td  >' + resultCreditCard.creditCardDtoSingles[i].issuingNetwork + '</td>'
              + '<td  > ' + resultCreditCard.creditCardDtoSingles[i].firstName + '</td>'
              + '<td  >' + resultCreditCard.creditCardDtoSingles[i].lastName + '</td>'
              + '<td  >' + resultCreditCard.creditCardDtoSingles[i].dateExpiration + '</td>'
              + '<td   >' +
            '<div class="pointer">'+
            '<i class="fa fa-pencil" aria-hidden="true" onclick="modifyCreditCardRow(' +resultCreditCard.creditCardDtoSingles[i].id+ ',' + (i + 1) + ')" title="Modify CreditCard"></i>&nbsp;' +
            '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteCreditCardRow('+resultCreditCard.customer.id+',' + resultCreditCard.creditCardDtoSingles[i].id + ')" title="Delete CreditCard"></i>&nbsp;'+

          '</div></td>'

              + '</tr>';

            // Set all content

          }
          $('.modal-content').html(html);
          $('#myModal').modal('show');
          }, 1000);
      })
      .fail(function () {
        alert('Error : Failed to reach API Url or check your connection');
      })

  });

}

//

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



function deleteCreditCardRow(custId,numRec) {
  if (!confirm("Are you sure?")) {
    return false;
  }
  var url = "http://localhost:8080/creditcards/"+ numRec;

  fetch(url, {
    method: 'DELETE'
  }).then(response => {

    if (response.status === 204) {
      listCreditCard(custId);
    } else {
      response.text().then(function (text) {
        alert(text.substr(text.search("message") + 9, (text.length - text.search("message")) - (text.length - text.search("time")) - 11));
      })


    }

  });



};



function addCreditCardRow() {
// Get a reference to the table
  let numberCard = document.getElementById('numberCard');
  let tableRef = document.getElementById("creditCardTable");

  if (typeof (numberCard) == 'undefined' || numberCard == null) {

  } else {
    var rowShow = document.getElementById("modifyCreditCardRow");

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
  newRow.id = "addCreditCardRow";
  let newCell = newRow.insertCell(0);
  let innerHtmlText = document.createElement("innerHTML");
  innerHtmlText.textContent="NEW";
  newCell.appendChild(innerHtmlText);


  newCell = newRow.insertCell(1);
  let inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "numberCard"
  inputValue.id = "numberCard";
  inputValue.style.width = "100%";

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(2);
  let selectValue = document.createElement("select");
  selectValue.name = "issuingNetwork"
  selectValue.id = "issuingNetwork";

  var arraylist = ['AMERICAN_EXPRESS', 'VISA', 'DISCOVER', 'MASTER_CARD', 'DINERS_CLUB'];

  for (var i = 0; i < arraylist.length; i++) {
    var option = document.createElement("option");
    option.value = arraylist[i];
    option.text = arraylist[i];
    selectValue.appendChild(option);
  }
  newCell.appendChild(selectValue);

  newCell = newRow.insertCell(3);



  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "firstNameCreditCard"
  inputValue.id = "firstNameCreditCard";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(4);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "lastNameCreditCard"
  inputValue.id = "lastNameCreditCard";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(5);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "dateExpiration"
  inputValue.id = "dateExpiration";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(6);



  let buttonSave = document.createElement("button");
  buttonSave.textContent = "Save";
  buttonSave.id = "generateCreditCardJson";
  newCell.appendChild(buttonSave);
  buttonSave.setAttribute("onclick", 'generateCreditCardJson()');

}
function modifyCreditCardRow(id, row) {
  let numberCard = document.getElementById('numberCard');
  let tableRef = document.getElementById("creditCardTable");

  if (typeof (numberCard) == 'undefined' || numberCard == null) {

  } else {
    var addCreditCardRow = document.getElementById("addCreditCardRow");

    if (typeof (addCreditCardRow) == 'undefined' || addCreditCardRow == null) {
      for (i = 0; i < tableRef.rows.length; i++) {
        tableRef.rows[i].style.display = "";
      }
      var rowShow = document.getElementById("modifyCreditCardRow");
      rowShow.parentNode.removeChild(rowShow);
    } else {
      addCreditCardRow.parentNode.removeChild(addCreditCardRow);

    }
  }

  let newRow = tableRef.insertRow(row);

  newRow.id = "modifyCreditCardRow";
  let newCell = newRow.insertCell(0);
  let innerHtmlText = document.createElement("innerHTML");
  innerHtmlText.textContent="Modify";
  newCell.appendChild(innerHtmlText);


  newCell = newRow.insertCell(1);
  let inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "numberCard"
  inputValue.id = "numberCard";
  var rowCreate = tableRef.rows;
  var colCreate = rowCreate[row + 1].cells;

  inputValue.value = colCreate[1].innerHTML;

  inputValue.style.width = "100%";

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(2);
  let selectValue = document.createElement("select");
  selectValue.name = "issuingNetwork"
  selectValue.id = "issuingNetwork";

  var arraylist = ['AMERICAN_EXPRESS', 'VISA', 'DISCOVER', 'MASTER_CARD', 'DINERS_CLUB'];

  for (var i = 0; i < arraylist.length; i++) {
    var option = document.createElement("option");
    option.value = arraylist[i];
    option.text = arraylist[i];
    selectValue.appendChild(option);
  }
  selectValue.value = colCreate[2].innerHTML;

  newCell.appendChild(selectValue);

  newCell = newRow.insertCell(3);



  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "firstNameCreditCard"
  inputValue.id = "firstNameCreditCard";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[3].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(4);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "lastNameCreditCard"
  inputValue.id = "lastNameCreditCard";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[4].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(5);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "dateExpiration"
  inputValue.id = "dateExpiration";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[5].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(6);


  let buttonSave = document.createElement("button");
  buttonSave.textContent = "Save";
  buttonSave.id = "createJson";
  newCell.appendChild(buttonSave);


  //
  buttonSave.setAttribute("onclick", 'generateCreditCardPutJson(' + id + ')');
  tableRef.rows[row + 1].style.display = "none";

}





function generateCreditCardJson() {


  let checkValues = checkCreditCardInputValues();
  if (!checkValues) {
    return false;
  }



  let obj=createCreditCardJson();

  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/creditcards";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");
  let custId=document.getElementById("custId").value;

  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 201) {
    listCreditCard(custId);
     // $('.modal-content').html(null);
     // $('.modal-content').html(html);

    } else {
      alert("Error on creating new Credit Card, Error: " + status)
    }
  }
  console.log(status);
};


function generateCreditCardPutJson(id) {



  let checkValues = checkCreditCardInputValues();
  if (!checkValues) {
    return false;
  }

  let obj=createCreditCardJson();

  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/creditcards/" + id;



  xhr.open("PUT", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");
  let custId=document.getElementById("custId").value;


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    var text = xhr.responseText;
    responseText =text.substr(text.search("message") + 9, (text.length - text.search("message")) - (text.length - text.search("time")) - 11)

    if (status === 200) {
      listCreditCard(custId);
      // $('.modal-content').html(null);
      // $('.modal-content').html(html);

    } else {
      if(responseText!=""){
        alert(responseText);

      }

      //   .text().then(function (text) {
      //   alert(text.substr(text.search("message") + 9, (text.length - text.search("message")) - (text.length - text.search("time")) - 11));
      // });
    }
  }
  console.log(status);
};

function createCreditCardJson() {
  var firstNameJson = document.getElementById("firstNameCreditCard").value;
  var lastNameJson = document.getElementById("lastNameCreditCard").value;
  var numberCardJson = document.getElementById("numberCard").value;
  var issuingNetworkJson = document.getElementById("issuingNetwork").value;
  var dateExpirationJson = document.getElementById("dateExpiration").value;
  var idJson = document.getElementById("custId").value;

  var custObj={
    id:idJson
  }
  var obj = {
    firstName: firstNameJson,
    lastName: lastNameJson,
    numberCard: numberCardJson,
    issuingNetwork: issuingNetworkJson,
    dateExpiration: dateExpirationJson,
    customer: custObj
  };
  return obj;

}


function checkCreditCardInputValues() {

  let numberCard = document.getElementById('numberCard').value;

  if ((numberCard == "") || (numberCard == "undefined")) {
    alert("Please insert the Credit Card Number! ");
    document.getElementById('number').focus();
    return false;
  }




  var firstNameCreditCard = document.getElementById('firstNameCreditCard').value;

  if ((firstNameCreditCard == "") || (firstNameCreditCard == "undefined")) {
    alert("Please insert the First Name");
    document.getElementById('firstNameCreditCard').focus();
    return false;
  }
  var lastNameCreditCard = document.getElementById('lastNameCreditCard').value;

  if ((lastNameCreditCard == "") || (lastNameCreditCard == "undefined")) {
    alert("Please insert the Last Name !");
    document.getElementById('lastNameCreditCard').focus();
    return false;
  }

  var dateExpiration = document.getElementById('dateExpiration').value;

  if ((dateExpiration == "") || (dateExpiration == "undefined")) {
    alert("Please insert the Date of Expiration");
    document.getElementById('dateExpiration').focus();
    return false;
  }

  return true;
}


