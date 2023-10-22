const { createApp } = Vue;

const url = "/api/clients/currents"

createApp({
  data() {
    return {
        client: [],
        loading: true
    };
  },
  created() {
    axios
        .get(url)
        .then((response) => {
            this.client = response.data;
            setTimeout(() => this.loading = false, 300);
        })
        .catch((error) => console.log(error))
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
  },
}).mount('#app');