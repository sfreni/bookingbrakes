
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

            let birthDayHtml=resultCreditCard.creditCardDtoSingles[i].dateExpiration;
            birthDayHtml=birthDayHtml.substr(8,2)+"/"+birthDayHtml.substr(5,2)+"/"+birthDayHtml.substr(0,4);

            html +=  '<td   >' + birthDayHtml + '</td>'


//              + '<td  >' + resultCreditCard.creditCardDtoSingles[i].dateExpiration + '</td>'
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
  inputValue.placeholder = "gg/mm/aaaa";

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
  dateExpirationJson=dateExpirationJson.substr(6,4)+"-"+dateExpirationJson.substr(3,2)+"-"
    +dateExpirationJson.substr(0,2) + " "+"00:00:00";
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
  let date_regex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00))))$/g;

  if (!(date_regex.test(dateExpiration))) {
    alert("Insert a valid date of Expiration");
    document.getElementById('dateExpiration').focus();
    return false;
  }



  return true;
}


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

