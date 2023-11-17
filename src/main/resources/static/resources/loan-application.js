const { createApp } = Vue;

const url = "/api/clients/currents"

createApp({
  data() {
    return {
        client: [],
        loading: false,
        loans: [],
        loanId: 0,
        currentLoan: [],
        amount: 0,
        payments: 0,
        destinyAccountNumber: "",
        clientIsAdmin: false
    };
  },
  created() {
    axios
        .get(url)
        .then((response) => {
            this.client = response.data;
            this.clientIsAdmin = this.client.admin;
            setTimeout(() => this.loading = false, 300);
        })
        .catch((error) => console.log(error)),
    axios    
        .get('/api/loans')
        .then((response) => {
            this.loans = response.data
        })
        .catch((error) => console.log(error))
  },
  methods: {
    crearLink(account) {
        return 'http://localhost:8080/web/account.html?id=' + account.id
    },
    logout() {
        axios
        .post('http://localhost:8080/api/logout')
        .then((response) => {
            console.log('logged out');
            location.pathname = '/index.html';
        })
    },
    numberFormat(num) {
        let numString = num.toString().split('.')
  
        //La expresión regular /\B(?=(\d{3})+(?!\d))/g encuentra grupos de tres dígitos
        //seguidos por cualquier cantidad de grupos de tres dígitos adicionales y los
        //reemplaza por una coma. Esto crea el efecto de separación de miles.
        numString[0] = numString[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',')
  
        return numString.join('.')
    },
    requestLoan() {
        const loanBody = {
            loanId: this.currentLoan.id,
            amount: this.amount,
            payments: this.payments,
            destinyAccountNumber: this.destinyAccountNumber,
            interestRate: this.currentLoan.interestRate
        }
      axios.post('/api/loans', loanBody)
        .then((response) => {
          console.log("Prestamo pedido " + response)
          Swal.fire({
              icon: "success",
              title: "Loan requested",
              color: "#fff",
              background: "#1c2754",
              confirmButtonColor: "#17acc9",
          });
          location.href = 'http://localhost:8080/web/accounts.html'
        })
        .catch(error => {
          console.log(error)
          this.errorMessage(error.response.data)
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
    }
  },
  computed: {
    paymentAmount() {
      return (this.amount * this.currentLoan.interestRate) / this.payments
    }
  }
}).mount('#app');