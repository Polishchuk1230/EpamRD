function makeOrder() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     let response = JSON.parse(this.responseText);
     alert(response.message);
     if (Boolean(response.result)) {
       document.location.reload();
     }
    }
  };
  xhttp.open("POST", "order", true);
  xhttp.send();
}

function removeFromCart(id) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     let response = JSON.parse(this.responseText);
     alert(response.message);
     if (Boolean(response.result)) {
       document.location.reload();
     }
    }
  };
  xhttp.open("DELETE", "cart/" + id, true);
  xhttp.send();
}

function setNewNumber(jsonOrder) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     let response = JSON.parse(this.responseText);
     alert(response.message);
     if (Boolean(response.result)) {
       document.location.reload();
     }
    }
  };
  xhttp.open("PUT", "cart", true);
  xhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
  xhttp.send(JSON.stringify(jsonOrder));
}

function addToCart(jsonOrder) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     let response = JSON.parse(this.responseText);
     alert(response.message);
    }
  };
  xhttp.open("POST", "cart", true);
  xhttp.setRequestHeader('Content-type', 'application/json; charset=utf-8');
  xhttp.send(JSON.stringify(jsonOrder));
}