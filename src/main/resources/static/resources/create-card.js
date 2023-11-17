const { createApp } = Vue;

const url = "/api/clients/currents"

createApp({
  data() {
    return {
        client: [],
        loading: true,
        cardColor: "",
        cardType: "",
        clientIsAdmin: false
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
        .post('http://localhost:8080/api/logout')
        .then((response) => {
            console.log('logged out');
            location.pathname = '/index.html';
        })
    },
    createCard() {
      axios
        .post('/api/clients/current/cards',
         `cardType=${this.cardType}&cardColor=${this.cardColor}`)
        .then((response) => {
          console.log("Carta creada: " + response)
          Swal.fire({
              icon: "success",
              title: "Card created",
              text: "Card created",
              color: "#fff",
              background: "#1c2754",
              confirmButtonColor: "#17acc9",
          });
          location.href = 'http://localhost:8080/web/cards.html'
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

  }
}).mount('#app');