const { createApp } = Vue;

const url = "http://localhost:8080/api/clients/2"

createApp({
  data() {
    return {
        client: []
    };
  },
  created() {
    axios
        .get(url)
        .then((response) => {
            this.client = response.data;
            console.log(this.client);
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
    }
  },
}).mount('#app');