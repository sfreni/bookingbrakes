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


          html += '<h2 align="center">Purchases:  '+result.length+'</h2><table id="purchasesTable" class="table table-hover table-striped">'
            + '<thead>'
            + '<tr>'
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
              + '<td  >' + result[i].id + '</b><td  >' + result[i].trip.departure.city + '</b></td><td align=\'center\'  >' + result[i].trip.destination.city + '</b></td>' +
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

            totalAmount = Math.round(totalAmount).toFixed(2)

            html += '<td   >€ ' + totalAmount.replace(".", ",") + '</td>'


            // html += '<td   >€ ' + Number(totalAmount).toLocaleString("it-IT") + '</td>'
              + '<td   >'
              + '<div class="pointer">'
              + '<i class="fa fa-search-plus" aria-hidden="true" onclick="detailsPurchase(' + (i) + ')" title="Details" ></i>&nbsp;'
              + '<i class="fa fa-pencil" aria-hidden="true" onclick="modifyPurchase(' + (i) + ')" title="Modify"></i>&nbsp;'
              + '<i  class="fa fa-trash" aria-hidden="true" onclick="deleteRow(' + result[i].id + ')" title="Delete"></i>&nbsp;'
              + '<i  class="fas fa-hand-holding-usd" aria-hidden="true" onclick="refundPurchase(' +  (i) + ')" title="Refund"></i>&nbsp;'
              + '</div></td>'
              + '</tr>';


          }
          html += '</tbody></table>';

          html += '</div>';
          $('.tables-purchases').html(html);
        }, 1000);
      })
      .fail(function () {
        alert('Error : Failed to reach API Url or check your connection');
      })

  });
}


function detailsPurchase(row) {
  let html;
  if (window.jsonValues[row].products[0] != undefined) {
    html = '<br><p><h2 align="center">Purchase Details</h2></p>' +
      '<h4 align="center">Departure:<b>' + window.jsonValues[row].trip.departure.name + '</b>, Destination:<b>' + window.jsonValues[row].trip.destination.name + '</b></h4>'
    let dateDeparture = window.jsonValues[row].trip.startDateFlight.substr(8, 2) + "/" + window.jsonValues[row].trip.startDateFlight.substr(5, 2) + "/" + window.jsonValues[row].trip.startDateFlight.substr(0, 4) + " " + window.jsonValues[row].trip.startDateFlight.substr(11, 5);
    let dateArrivale = window.jsonValues[row].trip.endDateFlight.substr(8, 2) + "/" + window.jsonValues[row].trip.endDateFlight.substr(5, 2) + "/" + window.jsonValues[row].trip.endDateFlight.substr(0, 4) + " " + window.jsonValues[row].trip.endDateFlight.substr(11, 5);
    html += '<h4 align="center">Departure Time:<b>' + dateDeparture + '</b>, Arrival Time:<b>' + dateArrivale + '</b></h4>'
      +'<div class="container">'
      + '<br><div class="row d-flex justify-content-center ">'
      + '<div class="col"></div><div class="col align-self-center"">'
      + '<p align="center"><button type="bt" class="btn btn-primary" onclick="modifyPurchase('+row+')" id="buttonSubmitValues" >Modify</button>'
    + '&nbsp;<button type="bt" class="btn btn-primary" onclick="closeThisModal()" id="buttonCloseModal" >Close</button></p></div>'
      + '<div class="col"></div></div></div>'
   + '<table id="detailsTable" class="table table-hover table-striped">'
      + '<thead>'
      + '<tr>'
      + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Type</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">NR. Seat</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">First Name</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Last Name</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Birthday</th>'
      + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:10%\"  scope="col">Amount</th>'
      + '</tr>'
      + '</thead>'
      + '<tbody>';
    result = window.jsonValues[row];
   let totalAmount=0;
    for (var i = 0; i < result.products.length; i++) {
      html += '<tr align=\'center\'  >'

      if (result.products[i].type == "Seat") {
        html += '<td > Seat </td>'
        + '<td  > ' + result.products[i].nrSeat + '</td>'
        + '<td  > ' + result.products[i].firstNamePassenger + '</td>'
        + '<td  > ' + result.products[i].lastNamePassenger + '</td>'
        let datePurchase = result.products[i].dateOfBirth.substr(8, 2) + "/" + result.products[i].dateOfBirth.substr(5, 2) + "/" + result.products[i].dateOfBirth.substr(0, 4);
        html += '<td  > ' + datePurchase + '</td>'
      } else {
        html += '<td  >'+convertUnreadableName(result.products[i].additionalServiceType)+' </td>'
           + '<td  > </td>'
          + '<td  > </td>'+ '<td  > </td>'+ '<td  > </td>'

      }
      totalAmount +=result.products[i].priceAmount;
      amountValue = Math.round(result.products[i].priceAmount).toFixed(2)

      html += '<td   >€ ' + amountValue.replace(".", ",") + '</td>'

        + '</tr>';


    }
    totalAmount = Math.round(totalAmount ).toFixed(2)


    html += '<tr align=\'center\'  >'
      + '<td  > </td>'
      + '<td  > </td>'+ '<td  > </td>'+ '<td  > </td>'
      + '<td  ></td>'
      + '<td  ><b>Tot. € ' + totalAmount.replace(".", ",") +'</b></td>'
      + '</tr></table></div>'
///

  } else {
    html = '<br><h2 align="center">Purchases</h2>' +
      '<h4 align="center"><br>Departure:<b>' + window.jsonValues[row].trip.departure.name + '</b></h4>' +
      '<h4 align="center"><br>Destination:<b>' + window.jsonValues[row].trip.destination.name + '</b></h4>' +
      '<h4 align="center"><br><b>No Valid Purchases</b></h4><br>';
  }

  if (window.jsonValues[row].products[0] != undefined && window.jsonValues[row].creditCardTransactions.length>0) {
    html += '<h2 align="center">Transactions</h2><br><div class="row d-flex justify-content-center ">'
    + '<div class="col"></div><div class="col align-self-center"">'
    + '<div class="col"></div></div></div>'
    + '<table><tbody><div id="detailsTable" class="table table-hover table-striped">'
    + '<thead>'
    + '<tr>'
    + '<th  class=\"text-center\ bg-primary text-white\" align="center" style="width:10%\"  scope="col">Nr.</th>'
    + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:30%\"  scope="col">Credit Card</th>'
    + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:30%\"  scope="col">Status</th>'
    + '<th  class=\'text-center\ bg-primary text-white\'  align="center" style="width:30%\"  scope="col">Total</th>'
    + '</tr>'
    + '</thead>'
    + '<tbody>'

    creditCardTransaction = window.jsonValues[row].creditCardTransactions;
    let totalTransactionsPaid=0;
    let totalTransactionsRefund=0;
    for (var i = 0; i < creditCardTransaction.length; i++) {
      html += '<tr align=\'center\'  >'

        + '<td >' + creditCardTransaction[i].id + ' </td>'
        + '<td >' + creditCardTransaction[i].creditcard.numberCard + ' '

      if (creditCardTransaction[i].creditcard.issuingNetwork=="VISA") {
        html += " (Visa)" ;
      } else if(creditCardTransaction[i].creditcard.issuingNetwork=="MASTER_CARD") {
        html += " (Master Card)" ;
      } else if(creditCardTransaction[i].creditcard.issuingNetwork=="AMERICAN_EXPRESS") {
        html += " (American Express)" ;
      } else if(creditCardTransaction[i].creditcard.issuingNetwork=="DISCOVER") {
        html += " (Discover)" ;
      } else if(creditCardTransaction[i].creditcard.issuingNetwork=="DINERS_CLUB") {
        html += " (Diners Club)" ;
      }

      html +=  ' </td>'
        + '<td >' + creditCardTransaction[i].transactionStatus + ' </td>'

      let amountValue = Math.round(creditCardTransaction[i].totalePriceAmount).toFixed(2)

       if(creditCardTransaction[i].transactionStatus==="PAID") {
         html += '<td   >€ ' + amountValue.replace(".", ",") + '</td>'
       }else{
         html += '<td   >- € ' + amountValue.replace(".", ",") + '</td>'

       }

        + '</tr>';
      if(creditCardTransaction[i].transactionStatus==="PAID"){
        totalTransactionsPaid+=creditCardTransaction[i].totalePriceAmount
      }
      if(creditCardTransaction[i].transactionStatus==="REFUND"){
        totalTransactionsRefund+=creditCardTransaction[i].totalePriceAmount
      }
    }
    totalTransactionsPaid = Math.round(totalTransactionsPaid).toFixed(2)
    totalTransactionsRefund=Math.round(totalTransactionsRefund).toFixed(2)

    html += '</div></tbody><thead><b><h4 align="center">Total Paid: € ' + totalTransactionsPaid.replace(".", ",") +'. ' +
      'Total Refund: € '+totalTransactionsRefund.replace(".", ",") + '</b></h4></thead></table></div><br>'
  }else{
    html += '<h4 align="center"><br><b>No Valid Transactions</b></h4><br>';
  }

    document.getElementById("modalSizeValue").className = "modal-dialog modal-xl";
  $('.modal-content').html(html);
  $('#myModalNew').modal('show');

}

function refundPurchase(row) {
    let totalAmount=0;
    let products=window.jsonValues[row].products
    for(let i=0;i<products.length;i++){
          totalAmount+=products[i].priceAmount;
      }
  let totalPaid=0;
  let creditCardTransactions=window.jsonValues[row].creditCardTransactions
  for(let i=0;i<creditCardTransactions.length;i++){
    if(creditCardTransactions[i].transactionStatus==="PAID")
      totalPaid+=creditCardTransactions[i].totalePriceAmount;

  }
  let totalRefund=0;
  for(let i=0;i<creditCardTransactions.length;i++){
    if(creditCardTransactions[i].transactionStatus==="REFUND")
      totalRefund+=creditCardTransactions[i].totalePriceAmount;

  }

  console.log(totalAmount+ " "+ totalPaid+ " "+ totalRefund)
  let html = '<br>'
    + '<div class="container"><h2 align="center"><p>The Purchase N.' + window.jsonValues[row].id + ' has a total amount € '+(totalAmount)+'.</p>'
    if((totalPaid-totalRefund) > 0) {
      html +='<p> You can refund  € ' + (totalPaid - totalRefund) + '</p></h2>'
    }else {
      html +='<p> Ther&apos;s no payment to refund </p></h2>'
    }
      html += '<br><div class="form-row d-flex justify-content-center">'
  if((totalPaid-totalRefund) > 0) {
    html += '<button type="bt" class="btn btn-primary" onclick="refundJson(' + row + ',' + (totalPaid - totalRefund) + ')" id="buttonCloseModal" >Refund</button></form>'
  }
    html +=  '&nbsp;<button type="bt" class="btn btn-primary" onclick="closeThisModal()" id="buttonCloseModal" >Close</button></form>'
      + '</div><br>'

  $('.modal-content').html(html);
  $('#myModalNew').modal('show');
}
function refundJson(row,totalRefund){

  let object = {
    creditcard: window.jsonValues[row].creditCardTransactions[0].creditcard,
    totalePriceAmount: totalRefund,
    purchase: window.jsonValues[row],
    transactionStatus: "REFUND",
    customer: window.jsonValues[row].customer
  };


  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/creditcardtransactions";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(object));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 201) {
      let html = '<div class="container"><br><div class="form-row d-flex justify-content-center">'
        + '<h2 align="center">Your refund is now complete!</h2>'
        + '</div>'
        + '<br><div class="form-row d-flex justify-content-center">'
        + '<button type="bt" class="btn btn-primary" onclick="closeThisModal()" id="buttonSubmitValues" >Ok</button></form>'
        + '</div>'
        + '</div><br>'
      loadTable();
      $('.modal-content').html(html);
    } else {
      alert("Error on creating new Trips, Error: " + status)
    }
  }


}

function modifyPurchase(row){
  let html =  '<div class="container">'
  let totalPaid=0;
  let creditCardTransactions=window.jsonValues[row].creditCardTransactions
  for(let i=0;i<creditCardTransactions.length;i++){
    if(creditCardTransactions[i].transactionStatus==="PAID")
      totalPaid+=creditCardTransactions[i].totalePriceAmount;

  }
  let totalRefund=0;
  for(let i=0;i<creditCardTransactions.length;i++){
    if(creditCardTransactions[i].transactionStatus==="REFUND")
      totalRefund+=creditCardTransactions[i].totalePriceAmount;

  }
    if(totalRefund==0 || totalPaid>totalRefund){
      html+= '<br><div class="form-row d-flex justify-content-center" id="rowForPassangers">'
    + '<div class="col">'
    + ' <label for="exampleInputPassword1">Passengers</label>'
    + '<div class="input-group">'
    + '<input type="text" class="form-control" placeholder="Passengers" id="passengerValue">'
    + '<div class="input-group-append">'
    + '<button class="btn btn-outline-secondary" onclick="more(' + "passengerValue" + ')" type="button">+</button>'
    + '<button class="btn btn-outline-secondary" id="lessButton" onclick="less(' + "passengerValue" + ',1)" type="button">-</button>'
    + '<input type="hidden" id="rowTrip" value="'+row+'">'
    + '<input type="hidden" id="isModify" value="1">'
    + '</div>'
    + '</div>'
    + '</div>'
    + '<div class="col">'
    + '</div>'
    + '<div class="col">'
    + '</div>'
    + '</div>'


  document.getElementById("modalSizeValue").className = "modal-dialog modal-lg";
  $('.modal-content').html("");
  $('.modal-content').html(html);
  $('#myModalNew').modal('show');

  createSeatsFlight(window.jsonValues[row].trip.airplane.numberSeats,window.jsonValues[row].trip.id,true)
    }else{
      document.getElementById("modalSizeValue").className = "modal-dialog modal-lg";
      html+= '<div class="container"><h2 align="center">Your order N.' + window.jsonValues[row].id + ' has been refunded, so you can&apos;t modify it again</h2>'

      $('.modal-content').html("");
      $('.modal-content').html(html);
      $('#myModalNew').modal('show');
    }

    }

function convertUnreadableName(unreadableName){


  switch(unreadableName) {
    case "PRIORITY_BOARDING":
      return "Priority Boarding";
      break;
    case "TEN_KG_CHECKIN_BAG":
      return "10kg Checkin Bag";
      break;
    case "TWENTY_KG_CHECKIN_BAG":
      return "20kg Checkin Bag";
      break;
    case "EXCESS_BAGGAGE_FEE":
      return "Excess Beggage Fee";
      break;
    case "SPORT_EQUIPMENT":
      return "Sport Equipment";
      break;
    case "MUSICAL_EQUIPMENT":
      return "Musical Equipment";
      break;
    default:
      return null
      break;
  }


}

function getObjectDB(ending) {
  var api = "http://localhost:8080/" + ending;

  return fetch(api)
    .then(response => response.json())

}

function createEditWindow() {
  let html =  '<div class="container">'


    + '<br><div class="form-row d-flex justify-content-center">'
    + '<div class="col">'
    + '<label for="customerLabel">Customer</label>'
    + '<input class="form-control" list="Customer" id="CustomerList" placeholder="Type to search...">'
    + '<datalist id="Customer">'
    + '</datalist>'

    + '</div>'
    + '<div class="col">'

    + '</div>'
    + '</div>'



    + '<div class="form-row d-flex justify-content-center">'
    + '<div class="col">'
    + '<label for="depatureLabel">Departure</label>'
    + '<input class="form-control" list="Departure" id="DepartureList" placeholder="Type to search...">'
    + '<datalist id="Departure">'
    + '</datalist>'

     + '</div>'
    + '<div class="col">'
    + ' <label for="destinationLabel">Destination</label>'
    + '<input class="form-control" list="Departure" id="DestinationList" placeholder="Type to search...">'
    + '<datalist id="Destination">'
    + '</datalist>'
    + '</div>'
    + '</div>'


    + '<div class="form-row d-flex justify-content-center">'
    + '<div class="col" >'

    + ' <label for="exampleInputPassword1">Departure Time</label>'
    + '<input class="form-control " type="text"  data-provide="datepicker" data-date-format="dd/mm/yyyy" data-date-autoclose="true" id="dateDeparture" >'

    + '</div>'

    + '<div class="col">'
    + ' <label for="exampleInputPassword1">Passengers</label>'
    + '<div class="input-group">'


    + '<input type="text" class="form-control" placeholder="Passengers" value="1" id="passengerValue">'
    + '<div class="input-group-append">'
    + '<button class="btn btn-outline-secondary" onclick="more(' + "passengerValue" + ')" type="button">+</button>'
    + '<button class="btn btn-outline-secondary" onclick="less(' + "passengerValue" + ',1)" type="button">-</button>'
    + '</div>'
    + '</div>'
    + '</div>'
    + '</div>'
    + '<br><div class="form-row d-flex justify-content-center">'
    + '<button type="bt" class="btn btn-primary" onclick="chooseTrip()" id="buttonSubmitValues" >Search</button></form>'
    + '&nbsp;<button type="bt" class="btn btn-primary" onclick="closeThisModal()" id="buttonCloseModal" >Cancel</button></form>'
    + '</div><br>'

  document.getElementById("modalSizeValue").className = "modal-dialog modal-lg";

  $('.modal-content').html(html);
  $('#myModalNew').modal('show');
}

function closeThisModal() {
  $('#myModalNew').modal('hide');
}

function more(element) {
  let intValue = parseInt(element.value) + 1;
  element.value = intValue

}

function less(element, lowerBounder) {
  let intValue = parseInt(element.value) - 1;
  if (intValue <= lowerBounder) {
    intValue = lowerBounder;
  }

  element.value = intValue

}

function calculateTotalValue() {

  let amountValue = parseFloat(document.getElementById('feeCounter').value);
  let qtyValue = 0;
  qtyValue += parseInt(document.getElementById('inputTextBoarding').value)
  qtyValue += parseInt(document.getElementById('inputText10KgBag').value)
  qtyValue += parseInt(document.getElementById('inputText20KgBag').value)
  qtyValue += parseInt(document.getElementById('inputTextExcessBaggageFee').value)
  qtyValue += parseInt(document.getElementById('inputTextSportEquipment').value)
  qtyValue += parseInt(document.getElementById('inputTextMusicalEquipment').value)
  amountValue = Math.round(amountValue + (qtyValue * 30.00)).toFixed(2)
  document.getElementById('amountValue').innerHTML = "Amount: € " + amountValue.replace(".", ",");
  ;

}

function chooseTrip() {

  if (!checkFirstStepElements()) {
    return false
  }
  extractTrips();
}

function checkFirstStepElements() {
  var customer = document.getElementById('CustomerList').value;
  var departure = document.getElementById('DepartureList').value;
  var destination = document.getElementById('DestinationList').value;
  let dateDeparture = document.getElementById('dateDeparture').value;
  let date_regex = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00))))$/g;

  if ((customer == "") || (customer == "undefined")) {
    alert("Please insert a customer! ");
    document.getElementById('Customer').focus();
    return false;
  }

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
  let countResult = 0;
  return fetch(api)
    .then(response => response.json())
    .then(trips => {
      trips.sort(function (a, b) {
        return b.id - a.id;
      });

      html = '<div class="container border" id="travelFlight"><br><p>&nbsp;<i class="fas fa-plane-departure"></i>&nbsp;<h7><b>' + document.getElementById('DepartureList').value + '</b>' +
        ' To: <b>' + document.getElementById('DestinationList').value + '</b></p></h7><table id="tripsTable" class="table table-hover ">'

        + '<tbody>';

      for (var i = 0; i < trips.length; i++) {
        let startDateFlightValue = trips[i].startDateFlight;
        startDateFlightValue = startDateFlightValue.substr(8, 2) + "/" + startDateFlightValue.substr(5, 2) + "/" + startDateFlightValue.substr(0, 4) + " " + startDateFlightValue.substr(11, 5);

        let endDateFlightValue = trips[i].endDateFlight;
        endDateFlightValue = endDateFlightValue.substr(8, 2) + "/" + endDateFlightValue.substr(5, 2) + "/" + endDateFlightValue.substr(0, 4) + " " + endDateFlightValue.substr(11, 5);
        if (startDateFlightValue.substr(0, 10) === document.getElementById('dateDeparture').value
          && trips[i].departure.city === document.getElementById('DepartureList').value
          && trips[i].destination.city === document.getElementById('DestinationList').value) {

          html += '<tr align=\'center\' id="trip' + trips[i].id + '"  >'
            + '<td   >' + startDateFlightValue.substr(0, 10) + '</td>'
            + '<td  ><p> <b>' + trips[i].departure.city + "</b></p>" + startDateFlightValue.substr(11, 5) + '</td>'
          const date1 = new Date(trips[i].startDateFlight);
          const date2 = new Date(trips[i].endDateFlight);
          const diffTime = Math.abs(date2 - date1);
          const hours = Math.floor(diffTime / (1000 * 60 * 60));
          const minutes = Math.floor((diffTime / (1000 * 60)) - (hours * 60));


          html += '<td  class="align-middle" ><p style="margin : 0; padding-top:0;"><i class="fas fa-plane-departure"></i> ' + hours + 'h ' + minutes + 'm</p>Duration</td>'
            + '<td  ><p><b> ' + trips[i].destination.city + "</b></p>" + endDateFlightValue.substr(11, 5) + '</td>'

            + '<td   >Flight No. ' + trips[i].id
            + '<div class="pointer">'
            + '<i class="fas fa-chevron-circle-right" id="button' + trips[i].id + '" onclick="createSeatsFlight(' + trips[i].airplane.numberSeats + ',' + trips[i].id + ',false)" title="Book This"></i>'
            + '</div></td>'
            + '</tr>';
          countResult++;
        }
      }

      if (countResult == 0) {
        html += ' <h2 align="center">No Trip Avaible for this date</h2>'

      }
      html += '</tbody></table></div>';

      let elementToDelete = document.getElementById('travelFlight')
      if (elementToDelete != undefined) elementToDelete.remove();
      elementToDelete = document.getElementById('seatsFlight')
      if (elementToDelete != undefined) elementToDelete.remove();

      elementToDelete = document.getElementById('amountValue')
      if (elementToDelete != undefined) elementToDelete.remove();
      elementToDelete = document.getElementById('containerDataPassenger')
      if (elementToDelete != undefined) elementToDelete.remove();
      elementToDelete = document.getElementById('containerAdditionalServicePassenger')
      if (elementToDelete != undefined) elementToDelete.remove();
      elementToDelete = document.getElementById('purchaseElements')
      if (elementToDelete != undefined) elementToDelete.remove();
      elementToDelete = document.getElementById('panelValueAndCounter')
      if (elementToDelete != undefined) elementToDelete.remove();

      $('#myModalNew').find('.modal-content').append(html);
    })
    .catch(err => {
      console.error('An error occurred', err);
    });


}

function createSeatsFlight(nrSeats, idFlight,isModify) {

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



      let elementToDelete = document.getElementById('seatsFlight')
      if (elementToDelete != undefined) elementToDelete.remove();
      if(!isModify){
      document.getElementById("button" + idFlight).setAttribute("onclick", null);
      }

      let html = '<div class="form-row d-flex justify-content-center" id="panelValueAndCounter"><br><br><h2 id="amountValue">'
      let productPurchase=[];
      if(isModify) {
        let productIteration=window.jsonValues[document.getElementById('rowTrip').value].products
        let totalAmountIteration=0;
        for(let i=0;i<productIteration.length;i++){
          if(productIteration[i].type=="Seat") {
            productPurchase.push(productIteration[i].nrSeat);
          }
          totalAmountIteration+=productIteration[i].priceAmount;


        }
         totalAmountIteration = Math.round(totalAmountIteration).toFixed(2);
        html += 'Amount: € '+totalAmountIteration.replace(".", ",")
        document.getElementById('passengerValue').value=productPurchase.length;
        document.getElementById('lessButton').setAttribute('onclick', 'less(passengerValue,'+productPurchase.length+')');

      }else{
      html += 'Amount: € 0,00 '
      }
      html += '</h2>'
        + ' <input type="hidden" id="feeCounter" value="0">'
        + ' <input type="hidden" id="idTripValue" value="' + idFlight + '">'
        + '<br></div><div align="center" class="container border" id="seatsFlight">'
        + '     <div class="form-row d-flex justify-content-center">'
        + '<button type="bt" class="btn btn-primary" onclick="addPassengerData(' + nrSeats + ','+isModify+')" id="buttonSubmitAdditionalService" >&nbsp;Next&nbsp;</button>'
        + ' </div>'
        + '<br><table id="seatsTable">'
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
                html +=  '   <input type="checkbox" id="seat' + ((i * 6) + j) + '" onclick="checkPassengers(' + nrSeats + ',' + ((i * 6) + j) + ')" checked/>'
                  +    '   <label for="seat' + ((i * 6) + j) + '" id="label' + ((i * 6) + j) + '">' + character + '0' + j + '</label>'

              }else{
                html +=  '   <input type="checkbox" id="seat' + ((i * 6) + j) + '" onclick="checkPassengers(' + nrSeats + ',' + ((i * 6) + j) + ')" disabled/>'
                +              '   <label for="seat' + ((i * 6) + j) + '" id="label' + ((i * 6) + j) + '">' + 'X' + '</label>'
              }
            }else{
              html +=  '   <input type="checkbox" id="seat' + ((i * 6) + j) + '" onclick="checkPassengers(' + nrSeats + ',' + ((i * 6) + j) + ')"/>'
               +              '   <label for="seat' + ((i * 6) + j) + '" id="label' + ((i * 6) + j) + '">' + character + '0' + j + '</label>'
            }
            html +=  ' </td>'

          }

        }

        html += '</tr >'

      }
      html += '</table></div>';
      $('#myModalNew').find('.modal-content').append(html);

      let el = document.getElementById('amountValue');

      $('#myModalNew').scrollTop(el.offsetTop);
      $('#myModalNew').scrollLeft(el.offsetLeft);

    })
    .catch(err => {
      console.error('An error occurred', err);
    });

}

function addPassengerData(nrSeats,isModify) {
  let arrSeats = checkPassengers(nrSeats, 0);
  if (arrSeats == null) {
    alert("Please select seats equals passengers")
    return false;
  }
  let AdditionalServiceAmount=0;

  let productPurchase=[];
  if(isModify){
    let productIteration=window.jsonValues[document.getElementById('rowTrip').value].products
    for(let i=0;i<productIteration.length;i++){
      if(productIteration[i].type=="Seat") {
        productPurchase.push(    [productIteration[i].nrSeat,
            productIteration[i].firstNamePassenger,
            productIteration[i].lastNamePassenger,
            productIteration[i].dateOfBirth])
      }
      if(productIteration[i].type=="AdditionalService") {
        AdditionalServiceAmount+=productIteration[i].priceAmount;
      }
      }
  }
  let size = arrSeats.length;

  let html = '<div class="container" id="purchaseElements">'
    + ' <input type="hidden" id="totalSeatPassanger" value="' + size + '">'

    + '     <div class="form-row d-flex justify-content-center" id="containerDataPassenger">'
    + '<p><button type="bt" class="btn btn-primary" onclick="additionalServices('+isModify+')" id="buttonSubmitAdditionalService" >&nbsp;Next&nbsp;</button></p>'
    + ' </div>'

  for (let i = 0; i < size; i++) {
    html += '<div class="form-row d-flex justify-content-center">'
      + ' <h2>Seat: ' + arrSeats[i] + '</h2>'
      + ' <input type="hidden" id="seatPassangerValue' + i + '" value="' + arrSeats[i] + '">'
      + '</div>'
      + '<div class="form-row d-flex justify-content-center">'
      + '<div class="col">'
      + '<label for="firstName">First Name</label>'
      + '<input type="text" class="form-control" id="firstNameInput' + i + '" '
    if(isModify){
      for(let j=0;j<productPurchase.length;j++) {
          if(arrSeats[i]==productPurchase[j][0]){
            html +=' value="'+productPurchase[j][1]+'"'
          }
      }
    }
    html+= '>'
      + '</div>'
      + '<div class="col">'
      + '<label for="lastName">Last Name</label>'
      + '<input type="text" class="form-control" id="lastNameInput' + i + '"'
    if(isModify){
      for(let j=0;j<productPurchase.length;j++) {
        if(arrSeats[i]==productPurchase[j][0]){
          html +=' value="'+productPurchase[j][2]+'"'
        }
      }
    }
    html+= '>'
      + '</div>'
      + '<div class="col">'
      + '<label for="birthDayLabel">Birthday</label>'
      + '<input type="text" class="form-control"   data-provide="datepicker" data-date-format="dd/mm/yyyy" data-date-autoclose="true" id="birthDayInput' + i + '"'
    if(isModify){
    for(let j=0;j<productPurchase.length;j++) {
      if(arrSeats[i]==productPurchase[j][0]){
        let datePurchase = productPurchase[j][3].substr(8, 2) + "/" + productPurchase[j][3].substr(5, 2) + "/" + productPurchase[j][3].substr(0, 4);
        html +=' value="'+datePurchase+'"'

      }
    }
  }
  html+= '>'

    + '</div>'
      + '</div>'
  }
  html += '</br></div>'
  let elementToDelete = document.getElementById('seatsFlight')
  if (elementToDelete != undefined) elementToDelete.remove();
  $('#myModalNew').find('.modal-content').append(html);
  if(isModify) {

    let feeValue=parseFloat(document.getElementById('feeCounter').value) +AdditionalServiceAmount;
     // document.getElementById('feeCounter').value=feeValue
     document.getElementById('amountValue').innerText =" Amount: € "+feeValue+",00"
  }
  }

function  calculateAdditionalValue() {
  let AdditionalServiceAmount = 0;
  let productIteration = window.jsonValues[document.getElementById('rowTrip').value].products
  for (let i = 0; i < productIteration.length; i++) {
    if (productIteration[i].type == "AdditionalService") {
      AdditionalServiceAmount += productIteration[i].priceAmount;
    }
    let feeValue = parseFloat(document.getElementById('feeCounter').value) + AdditionalServiceAmount;
    // document.getElementById('feeCounter').value=feeValue
    document.getElementById('amountValue').innerText = " Amount: € " + feeValue + ",00"
  }
}

function additionalServices(isModify) {
  let bookedSeats = parseInt(document.getElementById('totalSeatPassanger').value)
  for (let i = 0; i < bookedSeats; i++) {
    if (document.getElementById('firstNameInput' + i).value == "" ||
      document.getElementById('firstNameInput' + i).value == undefined) {
      alert("Please insert the first name of passenger! ");
      document.getElementById('firstNameInput' + i).focus();
      return false;
    }

    if (document.getElementById('lastNameInput' + i).value == "" ||
      document.getElementById('lastNameInput' + i).value == undefined) {
      alert("Please insert the last name of passenger! ");
      document.getElementById('lastNameInput' + i).focus();
      return false;
    }

    if (document.getElementById('birthDayInput' + i).value == "" ||
      document.getElementById('birthDayInput' + i).value == undefined) {
      alert("Please insert the birthday of passenger! ");
      document.getElementById('birthDayInput' + i).focus();
      return false;
    }
  }

  let productPurchase=[];
  if(isModify){
    let productIteration=window.jsonValues[document.getElementById('rowTrip').value].products
    for(let i=0;i<productIteration.length;i++){
      if(productIteration[i].type=="AdditionalService") {
        productPurchase.push([productIteration[i].additionalServiceType,(productIteration[i].priceAmount/30)])
      }
    }
  }

  let html = '<div class="container" id="containerAdditionalServicePassenger">'

    + '<div class="form-row d-flex justify-content-center">'
    + ' <h2>Additional Service:</h2>'
    + '</div>'
    + '<div class="row">'
    + '<div class = "col-md-12">'
    + '  <div class="accordion" id="accordionExample">'
    + '<div class="card">'
    + '<div class="card-header" id="headingOne">'
    + '<h2 class="mb-0">'
    + ' <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">'
    + '  Priority Boarding'
    + ' </button>'
    + '</h2>'
    + ' </div>'
    + '    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">'
    + '<div class="card-body">'
    + '<div class="row align-items-center"><div class="col-8">'
    + '<label for="inputTextBoarding">Add Priority Boarding at price of € 30,00</label>'
    + ' </div>'
    + '<div class="col-4">'
    + '<div class="input-group">'
    + '<input type="text"  id="inputTextBoarding" class="form-control" value="0"  placeholder="Qty"><div class="input-group-append">'
      + '<button class="btn btn-outline-secondary" onclick="more(' + "inputTextBoarding" + ');calculateTotalValue()" type="button">+</button>'
      + '<button class="btn btn-outline-secondary" onclick="less(' + "inputTextBoarding" + ',0);calculateTotalValue()" type="button">-</button>'
      + '</div>'
      + '</div>'
      + '</div>'
      + ' </div>'
      + ' </div>'
      + '</div>'
      + '</div>'
      + '<div class="card">'
      + ' <div class="card-header" id="headingTwo">'
      + ' <h2 class="mb-0">'
      + '  <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">'
      + '  Checkin Bag(10 KG)'
      + ' </button>'
      + ' </h2>'
      + ' </div>'
      + '<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">'
      + '<div class="card-body">'
      + '<div class="row align-items-center"><div class="col-8">'
      + '<label for="inputText10KgBag">Add 10KG Checkin Bag at price of € 30,00</label>'
      + ' </div>'
      + '<div class="col-4">'
      + '<div class="input-group">'

      + '<input type="text" class="form-control" placeholder="Qty" value="0" id="inputText10KgBag">'
      + '<div class="input-group-append">'
      + '<button class="btn btn-outline-secondary" onclick="more(' + "inputText10KgBag" + ');calculateTotalValue()" type="button">+</button>'
      + '<button class="btn btn-outline-secondary" onclick="less(' + "inputText10KgBag" + ',0);calculateTotalValue()" type="button">-</button>'
      + '</div>'
      + '</div>'
      + ' </div>'
      + '</div>'
      + ' </div>'
      + ' </div>'
      + ' <div class="card">'
      + ' <div class="card-header" id="headingThree">'
      + ' <h2 class="mb-0">'
      + '<button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">'
      + '  Checkin Bag(20 KG)'
      + '  </button>'
      + '  </h2>'
      + '</div>'
      + '<div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionExample">'
      + '<div class="card-body">'
      + '<div class="row align-items-center"><div class="col-8">'
      + '<label for="inputText20KgBag">Add 20 Checkin Bag at price of € 30,00<label>'
      + ' </div>'
      + '<div class="col-4">'

      + '<div class="input-group">'


      + '<input type="text" class="form-control" placeholder="Qty" value="0" id="inputText20KgBag"">'
      + '<div class="input-group-append">'
      + '<button class="btn btn-outline-secondary" onclick="more(' + "inputText20KgBag" + ');calculateTotalValue()" type="button">+</button>'
      + '<button class="btn btn-outline-secondary" onclick="less(' + "inputText20KgBag" + ',0);calculateTotalValue()" type="button">-</button>'
      + '</div>'
      + '</div>'
      + '</div>'
      + ' </div>'
      + ' </div>'
      + '</div>'
      + '</div>'
      + '<div class="card">'
      + ' <div class="card-header" id="headingFour">'
      + ' <h2 class="mb-0">'
      + '  <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">'
      + '  Excess Baggage Fee'
      + ' </button>'
      + ' </h2>'
      + ' </div>'
      + '<div id="collapseFour" class="collapse" aria-labelledby="headingFour" data-parent="#accordionExample">'
      + '<div class="card-body">'
      + '<div class="row align-items-center"><div class="col-8">'
      + '<label for="inputTextExcessBaggageFee">Excess Baggage Fee per KG at price of € 30,00</label>'
      + ' </div>'
      + '<div class="col-4">'
      + '<div class="input-group">'

      + '<input type="text" class="form-control" placeholder="Qty" value="0" id="inputTextExcessBaggageFee"">'
      + '<div class="input-group-append">'
      + '<button class="btn btn-outline-secondary" onclick="more(' + "inputTextExcessBaggageFee" + ');calculateTotalValue()" type="button">+</button>'
      + '<button class="btn btn-outline-secondary" onclick="less(' + "inputTextExcessBaggageFee" + ',0);calculateTotalValue()" type="button">-</button>'
      + '</div>'
      + '</div>'
      + ' </div>'
      + '</div>'
      + ' </div>'
      + ' </div>'

      + '<div class="card">'
      + ' <div class="card-header" id="headingFive">'
      + ' <h2 class="mb-0">'
      + '  <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">'
      + '  Sport Equipment'
      + ' </button>'
      + ' </h2>'
      + ' </div>'
      + '<div id="collapseFive" class="collapse" aria-labelledby="headingFive" data-parent="#accordionExample">'
      + '<div class="card-body">'
      + '<div class="row align-items-center"><div class="col-8">'
      + '<label for="inputTextSportEquipment">Sport Equipment at price of € 30,00 per unit</label>'
      + ' </div>'
      + '<div class="col-4">'
      + '<div class="input-group">'

      + '<input type="text" class="form-control" placeholder="Qty" value="0" id="inputTextSportEquipment"">'
      + '<div class="input-group-append">'
      + '<button class="btn btn-outline-secondary" onclick="more(' + "inputTextSportEquipment" + ');calculateTotalValue()" type="button">+</button>'
      + '<button class="btn btn-outline-secondary" onclick="less(' + "inputTextSportEquipment" + ',0);calculateTotalValue()" type="button">-</button>'
      + '</div>'
      + '</div>'
      + ' </div>'
      + '</div>'
      + ' </div>'
      + ' </div>'
      + '<div class="card">'
      + ' <div class="card-header" id="headingSix">'
      + ' <h2 class="mb-0">'
      + '  <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">'
      + '  Musical Equipment'
      + ' </button>'
      + ' </h2>'
      + ' </div>'
      + '<div id="collapseSix" class="collapse" aria-labelledby="headingSix" data-parent="#accordionExample">'
      + '<div class="card-body">'
      + '<div class="row align-items-center"><div class="col-8">'
      + '<label for="inputTextMusicalEquipment">Musical Equipment at price of € 30,00 per unit</label>'
      + ' </div>'
      + '<div class="col-4">'
      + '<div class="input-group">'

      + '<input type="text" class="form-control" placeholder="Qty" value="0" id="inputTextMusicalEquipment"">'
      + '<div class="input-group-append">'
      + '<button class="btn btn-outline-secondary" onclick="more(' + "inputTextMusicalEquipment" + ');calculateTotalValue()" type="button">+</button>'
      + '<button class="btn btn-outline-secondary" onclick="less(' + "inputTextMusicalEquipment" + ',0);calculateTotalValue()" type="button">-</button>'
      + '</div>'
      + '</div>'
      + ' </div>'
      + '</div>'
      + ' </div>'
      + ' </div>'


      + '</div>'


  html += '</br></div>'

  $('#containerDataPassenger').after(html);
  document.getElementById('buttonSubmitAdditionalService').setAttribute('onclick', 'generateJson('+isModify+')')
  document.getElementById('buttonSubmitAdditionalService').innerHTML="Finish"


  let el = document.getElementById('amountValue');

  $('#myModalNew').scrollTop(el.offsetTop);
  $('#myModalNew').scrollLeft(el.offsetLeft);

  let option = [
    ['inputTextBoarding', 'PRIORITY_BOARDING'],
    ['inputText10KgBag', 'TEN_KG_CHECKIN_BAG'],
    ['inputText20KgBag', 'TWENTY_KG_CHECKIN_BAG'],
    ['inputTextExcessBaggageFee', 'EXCESS_BAGGAGE_FEE'],
    ['inputTextSportEquipment', 'SPORT_EQUIPMENT'],
    ['inputTextMusicalEquipment', 'MUSICAL_EQUIPMENT']
  ]

  if(isModify){
    for(let i=0;i<productPurchase.length;i++) {
      for(let j=0;j<option.length;j++) {
      if(option[j][1]==productPurchase[i][0]){
        console.log(option[j][0],productPurchase[i][1])
        document.getElementById(option[j][0]).value=productPurchase[i][1]
      }
    }
    }
    document.getElementById('rowForPassangers').style.visibility = 'hidden';

  }


}

function checkPassengers(nrSeats, idCheckInput) {
  let tableRef = document.getElementById("seatsTable");
  let countRows = $('#seatsTable tr').length
  let countChecked = 0;
  let arraySeats = [];
  for (let i = 1; i <= nrSeats; i++) {
    if (document.getElementById("seat" + i).checked) {
      arraySeats.push(document.getElementById("label" + i).innerText);
      countChecked++;
    }
  }
  if (countChecked > document.getElementById("passengerValue").value) {
    alert("You can't book more seats than passengers")
    document.getElementById("seat" + idCheckInput).checked = false;
    return false;
  }

  let amount = Math.round(countChecked * 150.00).toFixed(2);
  document.getElementById("amountValue").innerHTML = "Amount: € " + amount.replace(".", ",");
  document.getElementById('feeCounter').value = amount;

  if(document.getElementById('isModify')!==null && document.getElementById('isModify').value=="1"){
    console.log(document.getElementById('isModify'))
    calculateAdditionalValue();

  }
  if (countChecked == document.getElementById("passengerValue").value) {
    return arraySeats;
  }
  return (null);

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
        optDeparture.setAttribute("data-value", airports[i].id);

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

  getObjectDB("customers")
    .then(customers => {
      customers.sort(function (a, b) {
        return b.id - a.id;
      });
      let selectCustomer = document.getElementById('Customer');
      if (isModify == true) {
        let tableRef = document.getElementById("tripsTable");
        var rowGetData = tableRef.rows;
        var colGetData = rowGetData[row].cells;
        let innerHtmlDepartureValue = colGetData[0].innerHTML;
        let innerHtmlDestinationValue = colGetData[1].innerHTML;
      }

      for (var i = 0; i < customers.length; i++) {
        let optCustomer = document.createElement('option');
        optCustomer.value = customers[i].firstName + " " + customers[i].lastName;
        optCustomer.setAttribute("data-value", customers[i].id);

        selectCustomer.appendChild(optCustomer);
        if (isModify == true && customers[i].name.trim() == innerHtmlDepartureValue.trim()) {
          optDeparture.selected = true;

        }

        if (isModify == true && customers[i].name.trim() == innerHtmlDestinationValue.trim()) {
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

function generateJson(isModify) {
  let obj;
  if(!isModify){
    obj = createJson();



  }else{
    obj = createPutJson();
  }

  var xhr = new XMLHttpRequest();

  if(!isModify) {
    var url = "http://localhost:8080/purchases";
    xhr.open("POST", url, true);
  }else{
    var url = "http://localhost:8080/purchases/"+obj.id;
    console.log(obj);
    xhr.open("PUT", url, true);
  }
    xhr.setRequestHeader("Content-Type", "application/json");

  if(!isModify) {

    xhr.send(JSON.stringify(obj));
    xhr.onreadystatechange = function () {
      var status = xhr.status;
      if (xhr.readyState == 4 && status === 201) {
        loadTable()
        loadPayment(xhr.responseText);
      }
      if (xhr.readyState == 4 && status != 201) {
        alert("Error on creating new Trips, Error: " + status)
      }
    }
  }

  if(isModify) {

    xhr.send(JSON.stringify(obj));
    xhr.onreadystatechange = function () {
      var status = xhr.status;
      if (xhr.readyState == 4 && status === 200) {
        loadTable()
        loadPayment(xhr.responseText);
      }
      if (xhr.readyState == 4 && status != 200) {
        alert("Error on creating new Trips, Error: " + status)
      }
    }
  }





}

function loadPayment(purchase) {



  const purchaseJson = JSON.parse(purchase);
  let totalAmount = 0;
  for (let i = 0; i < purchaseJson.products.length; i++) {
    totalAmount += purchaseJson.products[i].priceAmount;
  }
  let totalPaid=0;
  if(document.getElementById('isModify')!==null && document.getElementById('isModify').value=="1"){
    let creditCardTransactionJson = window.jsonValues[document.getElementById('rowTrip').value].creditCardTransactions
    for(let i=0;i<creditCardTransactionJson.length;i++){
        if(creditCardTransactionJson[i].transactionStatus==="PAID"){
          totalPaid+=creditCardTransactionJson[i].totalePriceAmount
        }
    }

  }
  let html = '<br>'
  console.log(totalAmount+" "+totalPaid)
  if((totalAmount-totalPaid)>0){

  html += '<div class="container"><h2 align="center">Your order N.' + purchaseJson.id + ' is acquired, the amount to pay is € ' + (totalAmount-totalPaid) + '</h2>'

    + '<br><div class="form-row d-flex justify-content-center">'
    + '<div class="col">'
    + '<label for="customerLabel">Credit Card</label>'
      + '<input class="form-control " type="text" id="numberCard" placeholder="Credit Card Number ">'
    + '<input type="hidden" id="issuerNetwork" value="">'
    + '<input type="hidden" id="custId" value="'+purchaseJson.customer.id +'" >'
    + '<input type="hidden" id="purchaseId" value="'+purchaseJson.id +'" >'
    + '<input type="hidden" id="totalAmountValue" value="'+totalAmount +'" >'
    + '<input type="hidden" id="totalPaidValue" value="'+totalPaid +'" >'
    + '<input type="hidden" id="isModify" value="1" >'
    + '</div>'

    + '</div>'


    + '<div class="form-row d-flex justify-content-center">'
    + '<div class="col">'
    + '<label for="depatureLabel">Card expiration date(MM-YY)</label>'
    + '<input class="form-control" type="text"  id="dateExpiration" placeholder="MM-YY" maxlength="5">'
    + '</div>'
    + '<div class="col">'
    + ' <label for="destinationLabel">CVV</label>'
    + '<input class="form-control" type="text" id="cardCvv"  placeholder="CVV" maxlength="3">'
    + '</div>'
    + '</div>'

    + '<div class="form-row d-flex justify-content-center">'
    + '<div class="col" >'

    + ' <label for="exampleInputPassword1">First Name</label>'
   + '<input class="form-control " type="text"  id="firstNameCreditCard" >'

    + '</div>'

    + '<div class="col">'

    + ' <label for="exampleInputPassword1">Last Name</label>'
    + '<input class="form-control " type="text"  id="lastNameCreditCard" >'


    + '</div>'
    + '</div>'
    + '<br><div class="form-row d-flex justify-content-center">'
    + '<button type="bt" class="btn btn-primary" onclick="payOrder()" id="buttonSubmitValues" >Pay</button></form>'
    + '&nbsp;<button type="bt" class="btn btn-primary" onclick="closeThisModal()" id="buttonCloseModal" >Cancel</button></form>'
    + '</div><br>'

}else{
  html += '<br>'
    + '<div class="container"><h2 align="center">Your order N.' + purchaseJson.id + ' is acquired, there&apos;s no amount to pay.</h2>'
    + '<br><div class="form-row d-flex justify-content-center">'
  + '<button type="bt" class="btn btn-primary" onclick="closeThisModal()" id="buttonCloseModal" >Close</button></form>'
  + '</div><br>'
}




  document.getElementById("modalSizeValue").className = "modal-dialog modal-sx";
  $('.modal-content').html("");
  $('.modal-content').html(html);


}





function payOrder() {
    if(!checkCrediCard()) {
    alert("Please insert a valid Credit Card! ");
    return false;
  }
  if (document.getElementById('dateExpiration').value == "" ||
    document.getElementById('dateExpiration').value == undefined) {
    alert("Please insert the Expire Date! ");
    document.getElementById('dateExpiration').focus();
    return false;
  }

  var expire = document.getElementById("dateExpiration").value;
  if(!expire.match(/(0[1-9]|1[0-2])[-][0-9]{2}/)){
    alert("Please insert a valid Expire Date! ");
    return false;
  } else {
    var d = new Date();
    var currentYear = d.getFullYear();
    var currentMonth = d.getMonth() + 1;
    var parts = expire.split('-');
    var year = parseInt(parts[1], 10) + 2000;
    var month = parseInt(parts[0], 10);
    if (year < currentYear || (year == currentYear && month < currentMonth)) {
      alert("Please insert a valid Expire Date! ");
      return false;
    }

  }



  if (document.getElementById('cardCvv').value == "" ||
    document.getElementById('cardCvv').value == undefined) {
    alert("Please insert the CVV! ");
    document.getElementById('cardCvv').focus();
    return false;
  }


  if (document.getElementById('firstNameCreditCard').value == "" ||
    document.getElementById('firstNameCreditCard').value == undefined) {
    alert("Please insert the first name of passenger! ");
    document.getElementById('firstNameCreditCard').focus();
    return false;
  }

  if (document.getElementById('lastNameCreditCard').value == "" ||
    document.getElementById('lastNameCreditCard').value == undefined) {
    alert("Please insert the last name of passenger! ");
    document.getElementById('lastNameCreditCard').focus();
    return false;
  }

  let obj = createCreditCardJson();

  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/creditcards";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(obj));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (xhr.readyState == 4 && status === 201) {
      saveTransaction(xhr.responseText);
      if (xhr.readyState == 4 && status != 201) {
      alert("Error on creating new Trips, Error: " + status)
    }
  }


}
}
function saveTransaction(creditCardJson){


  creditCardJson= JSON.parse(creditCardJson);
  let purchaseJson ={
    id: document.getElementById('purchaseId').value
  }
  let customerJson ={
    id: document.getElementById('custId').value
  }
  let amountPaid=parseFloat(document.getElementById('totalAmountValue').value)-
                parseFloat(document.getElementById('totalPaidValue').value)

  console.log(amountPaid);
  let object = {
    creditcard: creditCardJson,
    totalePriceAmount: amountPaid,
    purchase: purchaseJson,
    transactionStatus: "PAID",
    customer: customerJson
  };


  var xhr = new XMLHttpRequest();
  var url = "http://localhost:8080/creditcardtransactions";
  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");


  xhr.send(JSON.stringify(object));
  xhr.onreadystatechange = function () {
    var status = xhr.status;
    if (status === 201) {
     let html = '<div class="container"><br><div class="form-row d-flex justify-content-center">'
      + '<h2 align="center">Your order is now complete!</h2>'
        + '</div>'
        + '<br><div class="form-row d-flex justify-content-center">'
        + '<button type="bt" class="btn btn-primary" onclick="closeThisModal()" id="buttonSubmitValues" >Ok</button></form>'
        + '</div>'
       + '</div><br>'
        loadTable();
      $('.modal-content').html(html);
    } else {
      alert("Error on creating new Trips, Error: " + status)
    }
  }


}





function createCreditCardJson() {
  var firstNameJson = document.getElementById("firstNameCreditCard").value;
  var lastNameJson = document.getElementById("lastNameCreditCard").value;
  var numberCardJson = document.getElementById("numberCard").value;
  var issuingNetworkJson = document.getElementById("issuerNetwork").value;

  var dateExpirationJson = document.getElementById("dateExpiration").value;
  dateExpirationJson="20"+dateExpirationJson.substr(3,2)+"-"+dateExpirationJson.substr(0,2)+"-"+"01"
    + " "+"00:00:00";
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



function checkCrediCard() {
   var card = document.getElementById("numberCard").value;
  var card = card.replace(/-/g, "");
   var visaRegEx = /^(?:4[0-9]{12}(?:[0-9]{3})?)$/;
  var mastercardRegEx = /^(?:5[1-5][0-9]{14})$/;
  var amexpRegEx = /^(?:3[47][0-9]{13})$/;
  var discovRegEx = /^(?:6(?:011|5[0-9][0-9])[0-9]{12})$/;
  var dinersRegEx = /^(?:9[0-9]{12}(?:[0-9]{3})?)$/;

  if (visaRegEx.test(card)) {
    document.getElementById('issuerNetwork').value="VISA";
  } else if(mastercardRegEx.test(card)) {
    document.getElementById('issuerNetwork').value="MASTER_CARD";
  } else if(amexpRegEx.test(card)) {
    document.getElementById('issuerNetwork').value="AMERICAN_EXPRESS";
  } else if(discovRegEx.test(card)) {
    document.getElementById('issuerNetwork').value="DISCOVER";
  } else if(dinersRegEx.test(card)) {
    document.getElementById('issuerNetwork').value="DINERS_CLUB";
  }else{
    return false;
  }
  return true;

}

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
  element = document.getElementById('Customer');
  let customerJson;
  for (var i = 0; i < element.options.length; i++) {

    if (element.options[i].value === document.getElementById('CustomerList').value) {
      customerJson = {

        id: element.options[i].getAttribute("data-value")

      }
      break;
    }
  }


  let tripJson = {
    id: document.getElementById("idTripValue").value
  }

  let today = new Date();
  let dd = today.getDate();
  let mm = today.getMonth() + 1;
  let yyyy = today.getFullYear();
  if (mm < 10) mm = "0" + mm;
  if (dd < 10) dd = "0" + dd;

  let dateJson = yyyy + "-" + mm + "-" + dd + " 00:00:00"

  let bookedSeats = parseInt(document.getElementById('totalSeatPassanger').value)
  let productsJson = {
    products: []
  }
  for (let i = 0; i < bookedSeats; i++) {
    let dateToTransform = document.getElementById('birthDayInput' + i).value
    dateToTransform = dateToTransform.substr(6, 4) + "-" + dateToTransform.substr(3, 2) + "-"
      + dateToTransform.substr(0, 2) + " " + "00:00:00";

    productsJson.products.push({
      "type": "Seat",
      "priceAmount": 150.00,
      "nrSeat": document.getElementById('seatPassangerValue' + i).value,
      "firstNamePassenger": document.getElementById('firstNameInput' + i).value,
      "lastNamePassenger": document.getElementById('lastNameInput' + i).value,
      "dateOfBirth": dateToTransform
    });
  }
  let option = [
    ['inputTextBoarding', 'PRIORITY_BOARDING'],
    ['inputText10KgBag', 'TEN_KG_CHECKIN_BAG'],
    ['inputText20KgBag', 'TWENTY_KG_CHECKIN_BAG'],
    ['inputTextExcessBaggageFee', 'EXCESS_BAGGAGE_FEE'],
    ['inputTextSportEquipment', 'SPORT_EQUIPMENT'],
    ['inputTextMusicalEquipment', 'MUSICAL_EQUIPMENT']
  ]

  for (let i = 0; i < option.length; i++) {
    if (parseInt(document.getElementById(option[i][0]).value) > 0) {
      let priceAmount = parseInt(document.getElementById(option[i][0]).value) * 30
      productsJson.products.push({
        "type": "AdditionalService",
        "additionalServiceType": option[i][1],
        "priceAmount": priceAmount,
      });


    }
  }


  var obj = {
    customer: customerJson,
    trip: tripJson,
    datePurchase: dateJson,
    products: productsJson.products,
  };
  return obj;
}


function createPutJson() {
  // const element=window.jsonValues[document.getElementById('rowTrip').value].customer.id
  let customerJson = window.jsonValues[document.getElementById('rowTrip').value].customer
  let tripJson = window.jsonValues[document.getElementById('rowTrip').value].trip
  let dateJson = window.jsonValues[document.getElementById('rowTrip').value].datePurchase

  let bookedSeats = parseInt(document.getElementById('passengerValue').value)
  let productsJson = {
    products: []
  }
  for (let i = 0; i < bookedSeats; i++) {
    let dateToTransform = document.getElementById('birthDayInput' + i).value
    dateToTransform = dateToTransform.substr(6, 4) + "-" + dateToTransform.substr(3, 2) + "-"
      + dateToTransform.substr(0, 2) + " " + "00:00:00";

    productsJson.products.push({
      "type": "Seat",
      "priceAmount": 150.00,
      "nrSeat": document.getElementById('seatPassangerValue' + i).value,
      "firstNamePassenger": document.getElementById('firstNameInput' + i).value,
      "lastNamePassenger": document.getElementById('lastNameInput' + i).value,
      "dateOfBirth": dateToTransform
    });
  }
  let option = [
    ['inputTextBoarding', 'PRIORITY_BOARDING'],
    ['inputText10KgBag', 'TEN_KG_CHECKIN_BAG'],
    ['inputText20KgBag', 'TWENTY_KG_CHECKIN_BAG'],
    ['inputTextExcessBaggageFee', 'EXCESS_BAGGAGE_FEE'],
    ['inputTextSportEquipment', 'SPORT_EQUIPMENT'],
    ['inputTextMusicalEquipment', 'MUSICAL_EQUIPMENT']
  ]

  for (let i = 0; i < option.length; i++) {
    if (parseInt(document.getElementById(option[i][0]).value) > 0) {
      let priceAmount = parseInt(document.getElementById(option[i][0]).value) * 30
      productsJson.products.push({
        "type": "AdditionalService",
        "additionalServiceType": option[i][1],
        "priceAmount": priceAmount,
      });


    }
  }


  var obj = {
    id: window.jsonValues[document.getElementById('rowTrip').value].id,
    customer: customerJson,
    trip: tripJson,
    datePurchase: dateJson,
    products: productsJson.products,
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
  if (!confirm("Delete a Purchase, Are you sure?")) {
    return false;
  }
  var url = "http://localhost:8080/purchases/";

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


