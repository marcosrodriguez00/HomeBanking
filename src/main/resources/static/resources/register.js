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
                    alert("REGISTERED");
                    axios
                        .post('/api/login', "email=" + this.email + "&pwd=" + this.password)
                        .then(response => {
                            console.log('signed in!!!');
                            location.pathname = '/web/accounts.html';
                        })
                        .catch(error => console.error('Error:', error));
                })

                //error.response.data
                
                .catch(error => { 
                    console.error('Error:', error)
                    if(this.firstName === "" || this.lastName === ""
                       || this.email === "" || this.password === "") {
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: 'Make sure to fill in all the fields',
                                color: '#fff'
                            })
                    }
                    else if (error.response.status === 403) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'Email already registered!',
                            color: '#fff'
                        })
                    }
                });
        }
        else{
            this.passwordMatch = false;
        }
    }
    }
}).mount('#app');