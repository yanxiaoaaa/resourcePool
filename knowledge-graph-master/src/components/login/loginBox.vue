<template>
  <div class="login-box">
    <div class="login-logo">
      <p class="login-title"><b>KB</b>uilder</p>
    </div>
    
    <div class="login-box-body">
      <p class="login-box-msg">
        <template v-if="error">
          <div class="form-group has-error" style="margin-bottom: 0">
            <label class="control-label" for='inputError'>
              <i class="fa fa-bell-o">
                <span style="margin-left: 10px">{{info}}</span>
              </i>
            </label>
          </div>
        </template> 
        <template v-else>
          <span>{{info}}</span>
        </template>       
      </p>

      <form>
        <div class="form-group has-feedback">
          <input type="text" class="form-control" placeholder="请输入用户名" v-model="username">
          <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
          <input type="password" class="form-control" placeholder="请输入密码" v-model="password">
          <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        
        <div class="row">
          <div class="col-xs-4" >
            <label><input type="checkbox"> 记住账号</label>
          </div>
          <div class="col-xs-4 pull-right" >
            <router-link to="/signup">
              <span class="text-center">没有账号？</span>
            </router-link>
          </div>
          <div class="col-xs-10 col-xs-offset-1">
            <button class="btn btn-primary btn-block col-xs-8" style="margin-top:10px" @click="login" >登录</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>
<script>
export default {
  name: "LoginBox",
  data() {
    return {
      username: "",
      password: "",
      info: "请输入用户名和密码",
      error: false
    };
  },
  methods: {
    login: function() {
      const data = {
        username: this.username,
        password: this.password
      };
      this.$http.post(`${this.$store.state.basicUrl}/login`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              alert("success");
              this.$store.commit("login", data);
              window.location.href = "/project";
            } else if (result === "fail") {
              alert("fail");
              this.error = true;
              this.info = data.info;
            }
          }
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
.login-title {
  color: #fff;
}
</style>

