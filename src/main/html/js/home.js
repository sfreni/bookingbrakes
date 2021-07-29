
function loadValues(){

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
          let trips = evt;
          let productList=[];
          let amountValue=0;
          let countTrips=0;
          console.log(trips)
          for (let i = 0; i < trips.length; i++) {
            countTrips++
            for (let j = 0; j < trips[i].purchases.length; j++) {
              productList.push(trips[i].purchases[j]);
              for (let k = 0; k < trips[i].purchases[j].products.length; k++) {

                amountValue += trips[i].purchases[j].products[k].priceAmount;
              }
            }

          }




          document.getElementById("totalTrips").innerText= countTrips

            document.getElementById("totalPurchases").innerText= productList.length
          // amountValue = Math.round(amountValue).toFixed(2)

          document.getElementById("totalRevenue").innerText= new Intl.NumberFormat('it-IT', { style: 'currency', currency: 'EUR' }).format(amountValue)

          calculateCustomers();


        }, 1000);
      })

      .fail(function () {
        alert('Error : Failed to reach API Url or check your connection');
      })

  });





}

function calculateCustomers() {
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
          let customers = evt;

          document.getElementById("totalCustomers").innerText =customers.length


        }, 1000);
      })
      .fail(function () {
        alert('Error : Failed to reach API Url or check your connection');
      })

  });
}
