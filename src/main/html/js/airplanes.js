
  function loadTable() {
  var api = "http://localhost:8080/airplanes";
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
  var html = '<div class="tables-airplanes-content">';
  var queryString = location.search
  let params = new URLSearchParams(queryString)
  let newRecord = params.get("new")
  let modifyRecord = parseInt(params.get("mod"))
  let deleteRecord = parseInt(params.get("del"))

  html += '<table id="airplaneTable" class=" table table-hover table-striped">'
  + '<thead>'
  + '<tr>'
  + '<th  class=\'text-center\ bg-primary text-white\' align=\'center\' style=\'width:20%\'  scope="col">Name</th>'
  + '<th  class=\'text-center\ bg-primary text-white\'  align=\'center\' style=\'width:20%\'  scope="col">Number Of Seats</th>'
  + '<th  class=\'text-center\ bg-primary text-white\'  align=\'center\' style=\'width:20%\'  scope="col">Airplane Type</th>' + '<th  class=\'text-center\ bg-primary text-white\'  align=\'center\' style=\'width:20%\'  scope="col">Available for Flight</th>'
  + '<th  class=\'text-center\ bg-primary text-white\'  align=\'center\' style=\'width:20%\'  scope="col">Methods</th>'
  + '</tr>'
  + '</thead>'
  + '<tbody>';
  for (var i = 0; i < result.length; i++) {
  html += '<tr align=\'center\'  >'
  + '<td  > ' + result[i].name + '</td>'
  + '<td  >' + result[i].numberSeats + '</td>'
  + '<td   >' + result[i].airplaneType + '</td>'
  + '<td   >' + result[i].avaibleFlight + '</td>'
  + '<td   >' +
  '<div class="pointer">' +
  '<i class="fa fa-pencil" aria-hidden="true" onclick="modifyRow(' + result[i].id + ',' + (i + 1) + ')" title="Modify Airplane"></i>&nbsp;' +
  '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete Airplane"></i>'
  + '</div></td>'
  + '</tr>';


}
  html += '</tbody></table>';

  //      }
  html += '</div>';
  // Set all content
  $('.tables-airplanes').html(html);
}, 1000);
})
  .fail(function () {
  alert('Error : Failed to reach API Url or check your connection');
  $(button).prop('disabled', false);
})

});
}


  function addRow() {
// Get a reference to the table
  let name = document.getElementById('name');
  let tableRef = document.getElementById("airplaneTable");

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
  newCell.appendChild(inputValue);

  newCell = newRow.insertCell(1);
  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "numberOfSeats"
  inputValue.id = "numberOfSeats";
  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(2);
  let selectValue = document.createElement("select");
  selectValue.name = "airplaneType"
  selectValue.id = "airplaneType";

  var arraylist = ['AIRBUS', 'JETS', 'LIGHT_JETS', 'MIDSIZE_JETS', 'JUMBO_JETS', 'REGIONAL_JETS',
  'NARROW_BODY_AIRCRAFT',
  'WIDE_BODY_AIRLINERS',
  'REGIONAL_AIRCRAFT']

  for (var i = 0; i < arraylist.length; i++) {
  var option = document.createElement("option");
  option.value = arraylist[i];
  option.text = arraylist[i];
  selectValue.appendChild(option);
}
  newCell.appendChild(selectValue);

  newCell = newRow.insertCell(3);
  selectValue = document.createElement("select");
  selectValue.name = "avaibleFlight"
  selectValue.id = "avaibleFlight";
  var arraylist = ['true', 'false']

  for (var i = 0; i < arraylist.length; i++) {
  var option = document.createElement("option");
  option.value = arraylist[i];
  option.text = arraylist[i];
  selectValue.appendChild(option);
}
  selectValue.style = 'width:50%';
  newCell.appendChild(selectValue);

  newCell = newRow.insertCell(4);
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
  var numberOfSeatsJson = document.getElementById("numberOfSeats").value;
  var airplaneTypeJson = document.getElementById("airplaneType").value;
  var avaibleFlightJson = document.getElementById("avaibleFlight").value;

  var obj = {
  name: nameJson,
  numberSeats: numberOfSeatsJson,
  airplaneType: airplaneTypeJson,
  avaibleFlight: avaibleFlightJson
};
  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/airplanes";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
  var status = xhr.status;
  if (status === 201) {
  //  window.location.href = "airplanes.html"
  loadTable();
} else {
  alert("Error on creating new Airplane, Error: " + status)
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
  var numberOfSeatsJson = document.getElementById("numberOfSeats").value;
  var airplaneTypeJson = document.getElementById("airplaneType").value;
  var avaibleFlightJson = document.getElementById("avaibleFlight").value;

  var obj = {
  name: nameJson,
  numberSeats: numberOfSeatsJson,
  airplaneType: airplaneTypeJson,
  avaibleFlight: avaibleFlightJson
};
  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/airplanes/" + id;

  xhr.open("PUT", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
  var status = xhr.status;
  if (status === 200) {
  //   window.location.href = "airplanes.html"
  loadTable();
} else {
  alert("Error on creating new Airplane, Error: " + status)
}
}
  console.log(status);
};

///
  function checkInputValues() {

    var name = document.getElementById('name').value;

  if ((name == "") || (name == "undefined")) {
    alert("Please insert the name ");
    document.getElementById('name').focus();
    return false;
  }

  var numberOfSeats = document.getElementById('numberOfSeats').value;

  if ((numberOfSeats == "") || (numberOfSeats == "undefined")) {
    alert("Please insert number of seats");
    document.getElementById('numberOfSeats').focus();
    return false;
  }

  return true;
  }
  ///
  function modifyRow(id, row) {
// Get a reference to the table
  let name = document.getElementById('name');
  let tableRef = document.getElementById("airplaneTable");

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
  var rowCreate = tableRef.rows;
  var colCreate = rowCreate[row + 1].cells;
  inputValue.value = colCreate[0].innerHTML;

  newCell.appendChild(inputValue);

  newCell = newRow.insertCell(1);
  inputValue = document.createElement("input");
  inputValue.type = "text";
  inputValue.name = "numberOfSeats"
  inputValue.id = "numberOfSeats";

  rowCreate = tableRef.rows;
  colCreate = rowCreate[row + 1].cells;
  inputValue.value = colCreate[1].innerHTML;


  newCell.appendChild(inputValue);
  newCell = newRow.insertCell(2);
  let selectValue = document.createElement("select");
  selectValue.name = "airplaneType"
  selectValue.id = "airplaneType";

  var arraylist = ['AIRBUS', 'JETS', 'LIGHT_JETS', 'MIDSIZE_JETS', 'JUMBO_JETS', 'REGIONAL_JETS',
  'NARROW_BODY_AIRCRAFT',
  'WIDE_BODY_AIRLINERS',
  'REGIONAL_AIRCRAFT']

  for (var i = 0; i < arraylist.length; i++) {
  var option = document.createElement("option");
  option.value = arraylist[i];
  option.text = arraylist[i];
  selectValue.appendChild(option);
}
  selectValue.value = colCreate[2].innerHTML;
  newCell.appendChild(selectValue);

  newCell = newRow.insertCell(3);
  selectValue = document.createElement("select");
  selectValue.name = "avaibleFlight"
  selectValue.id = "avaibleFlight";
  var arraylist = ['true', 'false']

  for (var i = 0; i < arraylist.length; i++) {
  var option = document.createElement("option");
  option.value = arraylist[i];
  option.text = arraylist[i];
  selectValue.appendChild(option);
}

  selectValue.style = 'width:50%';
  selectValue.value = colCreate[3].innerHTML;

  newCell.appendChild(selectValue);

  newCell = newRow.insertCell(4);
  let buttonSave = document.createElement("button");
  buttonSave.textContent = "Save";
  buttonSave.id = "createPutJson";
  newCell.appendChild(buttonSave);
  buttonSave.setAttribute("onclick", 'generatePutJson(' + id + ')');
  tableRef.rows[row + 1].style.display = "none";

}


  function deleteRow(numRec) {
    if (!confirm("Are you sure?")) {
    return false;
    }

  //    var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/airplanes/";

  fetch(url + numRec, {
  method: 'DELETE'
}).then(response => {
  //  return console.log(response.status)

  if (response.status === 204) {
  //  window.location.href = "airplanes.html"
  loadTable();
} else {
  response.text().then(function (text) {
  alert(text.substr(text.search("message") + 9, (text.length - text.search("message")) - (text.length - text.search("time")) - 11));

  //    alert(text);

//          var myarr = text.sea(":");
  //    alert(text[0].message);
})


}

});
};
