<template>
  <header class="main-header">
    <router-link to="/" class="logo" :class="!loginStatus ? 'trans' : ''">
      <span class="logo-mini"><b>=</b>></span>
      <span class="logo-lg"><b>资源池</b>展示</span>
    </router-link>

    <nav class="navbar navbar-static-top" :class="!loginStatus ? 'trans' : ''">
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li class="user user-menu">
            <a href="#">
              <img src="../../assets/images/avatar.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs">{{user}}</span>
            </a>
          </li>
          <li class="user user-menu" @click="delete_database">
            <router-link to="/" style="color:white">
              <span class="hidden-xs" >删除</span>
            </router-link>
          </li> 
        </ul>
      </div>
    </nav>
  </header>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "CommonHeader",
  computed:{
    user: function(){
      return this.$store.state.username;
    }
  },
  methods: { 
    delete_database: function() {
      
      const data = {
        title: this.$store.state.pagename[this.$store.state.page]
      };
      this.$http.post(`http://localhost:8000/ResourcePool/delete_database`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            alert(body.information);
          }
          this.$store.state.page=-1;
        },
        response => {
          console.log("error");
        }
      );
    }
  }
};
</script>

<style scoped>
.navbar.navbar-static-top.trans,
.skin-blue .main-header .logo.trans {
  background-color: transparent;
}
.navbar.navbar-static-top {
  background-color: #222d32;
}
.skin-blue .main-header .logo {
  background-color: #1e282c;
}
</style>


