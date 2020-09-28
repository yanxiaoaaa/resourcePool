<template>
    <router-view/>
</template>

<script>
import Vue from "vue";
import Vuex from "vuex";
import VueResource from "vue-resource";

Vue.use(Vuex);
Vue.use(VueResource);

export default {
  name: "App",
  mounted(){
    this.$store.state.username=Math.random().toString(36).substr(2);;
  }
};
</script>
