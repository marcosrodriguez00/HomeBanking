const urlParams = new URLSearchParams(window.location.search);
const idAccount = urlParams.get('id');

const { createApp } = Vue;

const url = "http://localhost:8080/api/accounts/" + idAccount


createApp({
  data() {
    return {
        account: [],
        transactions: [],
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
            console.log(this.transactions);
        })
        .catch((error) => console.log(error))
  },
  methods: {
    
  },
  computed: {
  }
}).mount('#app');