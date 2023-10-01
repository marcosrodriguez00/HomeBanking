const { createApp } = Vue;

const url = "http://localhost:8080/clients";

createApp({
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      id: 0,
      clientsInfo: [],
      newClient: [],
    };
  },
  created() {
    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        this.clientsInfo = data._embedded.clients;
        console.log(clientsInfo)
      })
      .catch((error) => console.log(error));
  },
  methods: {
    addClient() {
      const clientData = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
      };

      axios
        .post(url, clientData)
        .then(function (response) {
          console.log("Respuesta exitosa: ", response.data); // Utiliza response.data para acceder a los datos de la respuesta.
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
}).mount('#app');