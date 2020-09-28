// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    components: { App },
    template: '<App/>',
    beforeCreate: function() {
        document.getElementsByTagName("body")[0].className = "hold-transition skin-blue sidebar-mini";
        document.getElementsByTagName("body")[0].style.cssText = "height: 100%; min-height: 100%;";
    },
})