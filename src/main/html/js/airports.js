function loadTable() {
  var api = "http://localhost:8080/airports";
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
          var html = '<div class="tables-airports-content">';
          var queryString = location.search
          let params = new URLSearchParams(queryString)


          html += '<table id="airportsTable" class="table table-hover table-striped">'
            + '<thead>'
            + '<tr>'
            + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Name</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Short Name</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:5%\"  scope="col">Country</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">City</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:20%\"  scope="col">Street Address</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Postal Code</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">State Province</th>'
            + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Methods</th>'
            + '</tr>'
            + '</thead>'
            + '<tbody>';
          for (var i = 0; i < result.length; i++) {
            html += '<tr align=\'center\'  >'
              + '<td  > ' + result[i].name + '</td>'
              + '<td  >' + result[i].shortName + '</td>'
              + '<td   >' + result[i].country + '</td>'
              + '<td   >' + result[i].city + '</td>'
              + '<td   >' + result[i].streetAddress + '</td>'
              + '<td   >' + result[i].postalCode + '</td>'
              + '<td   >' + result[i].stateProvince + '</td>'
              + '<td   >' +
              '<div class="pointer">' +
              '<i class="fa fa-pencil" aria-hidden="true" onclick="modifyRow(' + result[i].id + ',' + (i + 1) + ')" title="Modify Airport"></i>&nbsp;' +
              '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete Airport"></i>'
              + '</div></td>'
              + '</tr>';


          }
          html += '</tbody></table>';

          //      }
          html += '</div>';
          // Set all content
          $('.tables-airports').html(html);
        }, 1000);
      })
      .fail(function () {
        alert('Error : Failed to reach API Url or check your connection');
      })

  });
}


function addRow() {
// Get a reference to the table
  let name = document.getElementById('name');
  let tableRef = document.getElementById("airportsTable");

  if (typeof (name) == 'undefined' || name == null) {

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
  inputValue.name = "name"
  inputValue.id = "name";
  inputValue.style.width = "100%";

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(1);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "shortName"
  inputValue.id = "shortName";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);

  newCell = newRow.insertCell(2);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "country"
  inputValue.id = "country";
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
  inputValue.name = "streetAddress"
  inputValue.id = "streetAddress";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(5);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "postalCode"
  inputValue.id = "postalCode";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(6);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "stateProvince"
  inputValue.id = "stateProvince";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(7);

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
  var nameJson = document.getElementById("name").value;
  var shortNameJson = document.getElementById("shortName").value;
  var countryJson = document.getElementById("country").value;
  var cityJson = document.getElementById("city").value;
  var streetAddressJson = document.getElementById("streetAddress").value;
  var postalCodeJson = document.getElementById("postalCode").value;
  var stateProvinceJson = document.getElementById("stateProvince").value;

  var obj = {
    name: nameJson,
    shortName: shortNameJson,
    country: countryJson,
    city: cityJson,
    streetAddress: streetAddressJson,
    postalCode: postalCodeJson,
    stateProvince: stateProvinceJson


  };
  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/airports";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 201) {
      loadTable();
    } else {
      alert("Error on creating new Airport, Error: " + status)
    }
  }
  console.log(status);
};


function generatePutJson(id) {



  let checkValues = checkInputValues();
  if (!checkValues) {
    return false;
  }

  var nameJson = document.getElementById("name").value;
  var shortNameJson = document.getElementById("shortName").value;
  var countryJson = document.getElementById("country").value;
  var cityJson = document.getElementById("city").value;
  var streetAddressJson = document.getElementById("streetAddress").value;
  var postalCodeJson = document.getElementById("postalCode").value;
  var stateProvinceJson = document.getElementById("stateProvince").value;

  var obj = {
    name: nameJson,
    shortName: shortNameJson,
    country: countryJson,
    city: cityJson,
    streetAddress: streetAddressJson,
    postalCode: postalCodeJson,
    stateProvince: stateProvinceJson
  };
  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/airports/" + id;

  xhr.open("PUT", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 200) {
      loadTable();
    } else {
      alert("Error on creating new Airport, Error: " + status)
    }
  }
  console.log(status);
};


function modifyRow(id, row) {
// Get a reference to the table
  let name = document.getElementById('name');
  let tableRef = document.getElementById("airportsTable");

  if (typeof (name) == 'undefined' || name == null) {

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
  inputValue.name = "name"
  inputValue.id = "name";
  inputValue.style.width = "100%";
  var rowCreate = tableRef.rows;
  var colCreate = rowCreate[row + 1].cells;
  inputValue.value = colCreate[0].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(1);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "shortName"
  inputValue.id = "shortName";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[1].innerHTML;
  newCell.appendChild(inputValue);

  newCell = newRow.insertCell(2);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "country"
  inputValue.id = "country";
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
  inputValue.name = "streetAddress"
  inputValue.id = "streetAddress";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[4].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(5);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "postalCode"
  inputValue.id = "postalCode";
  inputValue.style.width = "100%";
  inputValue.value = colCreate[5].innerHTML;

  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(6);

  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "stateProvince"
  inputValue.id = "stateProvince";
  inputValue.style.width = "100%";
  newCell.appendChild(inputValue);
  inputValue.value = colCreate[6].innerHTML;

  newCell = newRow.insertCell(7);

  let buttonSave = document.createElement("button");
  buttonSave.textContent = "Save";
  buttonSave.id = "createJson";
  newCell.appendChild(buttonSave);

  //
  buttonSave.setAttribute("onclick", 'generatePutJson(' + id + ')');
  tableRef.rows[row + 1].style.display = "none";

}

function checkInputValues() {

  let name = document.getElementById('name').value;

  if ((name == "") || (name == "undefined")) {
    alert("Please insert the name! ");
    document.getElementById('name').focus();
    return false;
  }

  var shortName = document.getElementById('shortName').value;

  if ((shortName == "") || (shortName == "undefined")) {
    alert("Please insert the SHT code !");
    document.getElementById('shortName').focus();
    return false;
  }


  var country = document.getElementById('country').value;

  if ((country == "") || (country == "undefined")) {
    alert("Please insert the country");
    document.getElementById('country').focus();
    return false;
  }
  var city = document.getElementById('city').value;

  if ((city == "") || (city == "undefined")) {
    alert("Please insert the city !");
    document.getElementById('city').focus();
    return false;
  }

  var streetAddress = document.getElementById('streetAddress').value;

  if ((streetAddress == "") || (streetAddress == "undefined")) {
    alert("Please insert the Street Address");
    document.getElementById('streetAddress').focus();
    return false;
  }


  var postalCode = document.getElementById('postalCode').value;

  if ((postalCode == "") || (postalCode == "undefined")) {
    alert("Please insert the Postal Code");
    document.getElementById('postalCode').focus();
    return false;
  }

  var stateProvince = document.getElementById('stateProvince').value;

  if ((stateProvince == "") || (stateProvince == "undefined")) {
    alert("Please insert the province");
    document.getElementById('stateProvince').focus();
    return false;
  }
  return true;
}


function deleteRow(numRec) {
  if (!confirm("Are you sure?")) {
    return false;
  }
  var url = "http://localhost:8080/airports/";

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
