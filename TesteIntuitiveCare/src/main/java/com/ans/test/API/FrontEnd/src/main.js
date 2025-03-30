import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'

Vue.config.productionTip = false

// Configure axios
axios.defaults.baseURL = 'http://localhost:8080/api'

new Vue({
    render: h => h(App),
}).$mount('#app')