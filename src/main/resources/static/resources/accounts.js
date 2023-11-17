const { createApp } = Vue;

const url = "/api/clients/currents"

createApp({

  data() {
    return {
        client: [],
        loading: true,
        newAccountType: "",
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
        })
        .catch((error) => {
          console.log(error)
        });
  },

  methods: {
    crearLink(account) {
      return 'http://localhost:8080/web/account.html?id=' + account.id
    },
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
    createAccount() {
      axios
      .post('/api/clients/current/accounts', `accountType=${this.newAccountType}`)
      .then(response => {

        Swal.fire({
          icon: "success",
          title: "Account created",
          text: "Account created",
          color: "#fff",
          background: "#1c2754",
          confirmButtonColor: "#17acc9",
      });
        location.pathname = '/web/accounts.html'
      })
      .catch(error => { 
        console.error('Error:', error)
        if (error.response.status === 403) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'You already have the maximum amount of accounts (3)',
                color: '#fff'
            })
        }
      });
    },
    cutDecimals(numero, cantidadDecimales) {
      const factorMultiplicador = Math.pow(10, cantidadDecimales);
      const numeroCortado = Math.floor(numero * factorMultiplicador) / factorMultiplicador;
      return numeroCortado.toFixed(cantidadDecimales);
  }
  },
}).mount('#app');

