const urlParams = new URLSearchParams(window.location.search);
const idAccount = urlParams.get('id');

const { createApp } = Vue;

const url = "http://localhost:8080/api/accounts/" + idAccount


createApp({
  data() {
    return {
        account: [],
        transactions: [],
        loading: true,
        clientIsAdmin: false,
        client: [],
    };
  },
  created() {
    axios
        .get(url)
        .then((response) => {
            this.account = response.data;
            this.transactions = this.account.transactions;
            this.transactions.sort((a,b) => b.id - a.id);
            setTimeout(() => this.loading = false, 300);
        })
        .catch((error) => console.log(error))
    axios
        .get("http://localhost:8080/api/clients/currents")
        .then((response) => {
            this.client = response.data;
            this.clientIsAdmin = this.client.admin
        })
        .catch((error) => {
          console.log(error)
        });
  },
  methods: {
    numberFormat(num) {
      let numString = num.toString().split('.')

      //La expresión regular /\B(?=(\d{3})+(?!\d))/g encuentra grupos de tres dígitos
      //seguidos por cualquier cantidad de grupos de tres dígitos adicionales y los
      //reemplaza por una coma. Esto crea el efecto de separación de miles.
      numString[0] = numString[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',')

      return numString.join('.')
    },
    logout() {
      axios
      .post('http://localhost:8080/api/logout')
      .then((response) => {
          console.log('logged out');
          location.pathname = '/index.html';
      })
    },
    deleteAccount(){
      axios
      .post('/api/clients/current/accounts/delete', `accountNumber=${this.account.number}`)
      .then((response) => {
        console.log("Account deleted: " + response)
        Swal.fire({
            icon: "success",
            title: "Account deleted",
            text: "Account deleted",
            color: "#fff",
            background: "#1c2754",
            confirmButtonColor: "#17acc9",
        });
        location.href = 'http://localhost:8080/web/accounts.html'
      })
      .catch((error) => {
          console.log(error);
          this.errorMessage(error.response.data);
      })
    },
    
    errorMessage(message) {
      Swal.fire({
        icon: "error",
        title: "An error has occurred",
        text: message,
        color: "#fff",
        background: "#1c2754",
        confirmButtonColor: "#17acc9",
    });
  },
  cutDecimals(numero, cantidadDecimales) {
    const factorMultiplicador = Math.pow(10, cantidadDecimales);
    const numeroCortado = Math.floor(numero * factorMultiplicador) / factorMultiplicador;
    return numeroCortado.toFixed(cantidadDecimales);
  }
  },
}).mount('#app');