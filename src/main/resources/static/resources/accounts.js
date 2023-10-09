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
    }
  },
}).mount('#app');