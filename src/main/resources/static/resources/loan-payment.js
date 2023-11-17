const { createApp } = Vue;

const url = "/api/clients/currents"

createApp({

  data() {
    return {
        client: [],
        loading: true,
        loans: [],
        amountOfPayments: 0,
        selectedAccount: [],
        selectedLoan: "",
        loanInDisplay: [],
        clientIsAdmin: false
    };
  },

  created() {
    axios
        .get(url)
        .then((response) => {
            this.client = response.data;
            this.clientIsAdmin = this.client.admin
            setTimeout(() => this.loading = false, 300);
            this.loans = this.client.loans;
            this.loanInDisplay = this.loans[0]
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
    cutDecimals(numero, cantidadDecimales) {
      const factorMultiplicador = Math.pow(10, cantidadDecimales);
      const numeroCortado = Math.floor(numero * factorMultiplicador) / factorMultiplicador;
      return numeroCortado.toFixed(cantidadDecimales);
    },
    changeLoanInDisplay(loanName) {
      this.loanInDisplay = this.loans.find(loan => loan.name == loanName)
    },
    payLoan(){
      axios
      .post('http://localhost:8080/api/loans/pay',
      `amountOfPayments=${this.amountOfPayments}&clientLoanId=${this.loanInDisplay.id}&accountNumber=${this.selectedAccount.number}`)
      .then((response) => {
        console.log("Payment accepted: " + response)
        Swal.fire({
            icon: "success",
            title: "Payment accepted",
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
  },
}).mount('#app');