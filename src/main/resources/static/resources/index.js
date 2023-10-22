const { createApp } = Vue;

createApp({
  data() {
    return {
        loading: true
    };
  },
  created() {
    setTimeout(() => this.loading = false, 300);
  },
  methods: {
    login() {
      axios
      .post('/api/login', "email=" + this.email + "&pwd=" + this.password)
      .then(response => {
        console.log('signed in!!!');
        location.pathname = '/web/accounts.html';
      })
      .catch(error => console.error('Error:', error));
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