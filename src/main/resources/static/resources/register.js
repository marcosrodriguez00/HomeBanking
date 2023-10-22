const { createApp } = Vue;

createApp({
  data() {
    return {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
      confirm_password: "",
    };
  },
  created() {
  },
  methods: {
    register() {
        if(this.password === this.confirm_password){
            axios
                .post("/api/clients", `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
                .then(response => {
                    console.log('registered!!!');
                    alert("REGISTERED");
                    axios
                        .post('/api/login', "email=" + this.email + "&pwd=" + this.password)
                        .then(response => {
                            console.log('signed in!!!');
                            location.pathname = '/web/accounts.html';
                        })
                        .catch(error => console.error('Error:', error));
                })
                .catch(error => console.error('Error:', error));
        }
        else{
            alert("Passwords do not match");
        }
    }
    }
}).mount('#app');