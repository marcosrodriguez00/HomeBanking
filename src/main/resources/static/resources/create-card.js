const { createApp } = Vue;

const url = "/api/clients/currents"

createApp({
  data() {
    return {
        client: [],
        loading: true,
        cardColor: "",
        cardType: ""
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
    logout() {
        axios
        .post('api/logout')
        .then((response) => {
            console.log('logged out');
            location.pathname = '/index.html';
        })
    },
    createCard() {
      axios
      .post('/api/clients/current/cards', `type=${cardType}&color=${cardColor}`)
      .then((response) => {
        alert("Cuenta Creada");
      })
      .catch(error => console.log(error))
    }
  },
  computed: {

  }
}).mount('#app');