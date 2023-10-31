const { createApp } = Vue;

createApp({
  data() {
    return {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
      confirm_password: "",
      passwordMatch: true,
    };
  },
  created() {
  },
  methods: {
    register() {
        if(this.password === this.confirm_password){
            this.passwordMatch = true;
            axios
                .post("/api/clients", `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`)
                .then(response => {
                    console.log('registered!!!');
                    Swal.fire({
                      icon: 'success',
                      title: 'Register Successful!',
                      showConfirmButton: true,
                    })
                    axios
                        .post('/api/login', "email=" + this.email + "&pwd=" + this.password)
                        .then(response => {
                            console.log('signed in!!!');
                            location.pathname = '/web/accounts.html';
                        })
                        .catch(error => console.error('Error:', error));
                })
                
                .catch(error => { 
                    console.error('Error:', error)
                    this.errorMessage(error.response.data)
                });
        }
        else{
            this.passwordMatch = false;
        }
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
    }
}).mount('#app');