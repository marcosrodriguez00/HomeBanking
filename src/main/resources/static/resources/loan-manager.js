const { createApp } = Vue;

createApp({

  data() {
    return {
        name: "",
        maxAmount: 0,
        interestRate: 0,
        newPayment: 0,
        payments: [],
        loans: [],
        selectedLoanId: []
    };
  },

  created() {
    axios
        .get('/api/loans')
        .then((response) => {
            this.loans = response.data;
        })
        .catch((error) => {
          console.log(error)
        });
  },

  methods: {

    addPayment() {
        this.payments.push(this.newPayment);
        this.newPayment = "";
    },

    resetPayments() {
        this.payments = [];
    },

    createNewLoan() {
        let loanData = {
            name: this.name,
            maxAmount: this.maxAmount,
            interestRate: this.interestRate,
            payments: this.payments
            };
      axios
      .post('/api/loans/create', loanData)
      .then(response => {

        Swal.fire({
          icon: "success",
          title: "Loan created",
          text: "Loan created",
          color: "#fff",
          background: "#1c2754",
          confirmButtonColor: "#17acc9",
      });
        location.pathname = '/web/admin/loan-manager.html'
      })
      .catch(error => { 
        console.error('Error:', error)
        this.errorMessage(error.response.data)
      });
    },

    deleteLoan() {
        axios
        .post('http://localhost:8080/api/loans/delete', `loanId=${this.selectedLoanId}`)
        .then(response => {

            Swal.fire({
              icon: "success",
              title: "Loan deleted",
              text: "Loan deleted",
              color: "#fff",
              background: "#1c2754",
              confirmButtonColor: "#17acc9",
          });
            location.pathname = '/web/admin/loan-manager.html'
          })
          .catch(error => { 
            console.error('Error:', error)
            this.errorMessage(error.response.data)
          });
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

