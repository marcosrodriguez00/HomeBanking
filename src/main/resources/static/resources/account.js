const urlParams = new URLSearchParams(window.location.search);
const idAccount = urlParams.get('id');

const { createApp } = Vue;

const url = "http://localhost:8080/api/accounts/" + idAccount


createApp({
  data() {
    return {
        account: [],
        transactions: [],
        loading: true
    };
  },
  created() {
    console.log("url")
    console.log(url)
    axios
        .get(url)
        .then((response) => {
            this.account = response.data;
            this.transactions = this.account.transactions;
            this.transactions.sort((a,b) => b.id - a.id);
            setTimeout(() => this.loading = false, 300);
        })
        .catch((error) => console.log(error))
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
  },
  computed: {
  }
}).mount('#app');