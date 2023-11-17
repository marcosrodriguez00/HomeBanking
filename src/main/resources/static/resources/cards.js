const { createApp } = Vue;

const url = "/api/clients/currents"

createApp({
  data() {
    return {
        client: [],
        loading: true,
        isDebit: false,         // Estas indicaran si hay o 
        isCredit: false,        // no tarjetas de este tipo
        debitCards: [],
        creditCards: [],
        selectedCardNumberForDelete: "",
        clientIsAdmin: false,
    };
  },
  created() {
    axios
        .get(url)
        .then((response) => {
            this.client = response.data;
            this.clientIsAdmin = this.client.admin
            setTimeout(() => this.loading = false, 300);
            this.debitCards = this.client.cards.filter(card => card.type === "DEBIT")
            this.creditCards = this.client.cards.filter(card => card.type === "CREDIT")
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

    dateFormat(date){
        const fechaFormateada = date.slice(5, 7) + ' / ' + date.slice(2, 4);

        return fechaFormateada;
    },

    cardNumberFormat(number) {
        return number.split("-")
    },

    debitOrCredit(card) {
        if(card.type == "CREDIT") {
            return "CREDIT";
        } else {
            return "DEBIT";
        }
    },

    cardsCountIs0(cards){
      if(cards.length === 0){
        return true;
      }
      else{
        return false;   
      }
    },

    getCardGradient(color) {
        if (color === 'TITANIUM') {
            return { 'background-image': 'linear-gradient(45deg, #111111, #222222)' };
        } else if (color === 'GOLD') {
            return { 'background-image': 'linear-gradient(45deg, #FFD700, #FFA500)' };
        } else if (color === 'SILVER') {
            return { 'background-image': 'linear-gradient(45deg, #C0C0C0, #808080)' };
        }
    },

    logout() {
        axios
        .post('http://localhost:8080/api/logout')
        .then((response) => {
            console.log('logged out');
            location.pathname = '/index.html';
        })
    },

    deleteCard() {
      axios
      .post('/api/clients/current/cards/delete', `cardNumber=${this.selectedCardNumberForDelete}`)
      .then((response) => {
        console.log("Card deleted: " + response)
        Swal.fire({
            icon: "success",
            title: "Card deleted",
            text: "Card deleted",
            color: "#fff",
            background: "#1c2754",
            confirmButtonColor: "#17acc9",
        });
        location.href = 'http://localhost:8080/web/cards.html'
      })
      .catch((error) => {
          console.log(error);
          this.errorMessage(error.response.data);
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
    },

    isCardExpired(expirationDate){
      let todayDate = new Date();
      let cardExpiryDate = new Date(expirationDate);
      if (cardExpiryDate > todayDate) {
        return true;
      }
      else {
        return false;
      }
    }
  }
}).mount('#app');