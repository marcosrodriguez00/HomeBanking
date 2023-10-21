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
      .post('/api/login', "email=" + this.email + "&pwd=" + this.password, {
        headers: {
          'content-type': 'application/x-www-form-urlencoded'
        }
      })
      .then(response => console.log('signed in!!!'))
      .catch(error => console.error('Error:', error));
  },
}}).mount('#app');