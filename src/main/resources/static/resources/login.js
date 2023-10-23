const { createApp } = Vue;

createApp({
  data() {
    return {
      email: "",
      password: "",
    };
  },
  created() {
  },
  methods: {
    login() {
      axios
      .post('/api/login', "email=" + this.email + "&pwd=" + this.password)
      .then(response => {
        console.log('signed in!!!');
        location.pathname = '/web/accounts.html';
      })
      .catch(error => { 
        console.error('Error:', error)
        if (this.inputEmail = "" || this.inputPassword === "") {
          Swal.fire({
              icon: "error",
              title: "Error...",
              text: "Fill in all fields",
              color: "#fff",
          });
        } 
        else if(error.response.status === 401) {
          Swal.fire({
            icon: "error",
            title: "Incorrect password",
            text: "The provided password is incorrect",
            color: "#fff",
        });
        }
        else {
          Swal.fire({
              icon: "error",
              title: "Invalid user",
              text: "This user is not registered",
              color: "#fff",
          });
        }
    });
    },
    logout() {
        axios
        .post('api/logout')
        .then((response) => {
            console.log('logged out');
            location.pathname = '/index.html';
        })
    }
}}).mount('#app');