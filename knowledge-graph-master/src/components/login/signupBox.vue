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
          <input type="text" class="form-control" placeholder="请输入用户名" v-model="username" @blur="checkUsername">
          <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-feedback">
          <input type="password" class="form-control" placeholder="请输入密码" v-model="password" @blur="checkPassword">
          <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
         <div class="form-group has-feedback">
          <input type="password" class="form-control" placeholder="请再次输入密码" v-model="password_again" @blur="checkPasswordAgain">
          <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        
        <div class="row">
          <div class="col-xs-4 pull-right" >
            <router-link to="/login">
              <span class="text-center">已有账号？</span>
            </router-link>
          </div>
          <div class="col-xs-10 col-xs-offset-1">
            <button class="btn btn-primary btn-block col-xs-8" style="margin-top:10px" @click="signup" >注册</button>
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
      password_again: "",
      info: "欢迎加入KBuilder",
      error: false
    };
  },
  methods: {
    checkUsername: function() {
      if (this.username.length === 0) {
        this.error = true;
        this.info = "用户名不能为空，请重新输入";
      } else {
        this.error = false;
      }
    },
    checkPassword: function() {
      if (this.password.length === 0) {
        this.error = true;
        this.info = "密码不能为空，请重新输入";
      } else {
        this.error = false;
      }
    },
    checkPasswordAgain: function() {
      if (this.password !== this.password_again) {
        this.error = true;
        this.info = "两次输入的密码不一致，请重新输入";
      } else {
        this.error = false;
      }
    },
    signup: function() {
      if (!this.error && this.password === this.password_again) {
        const data = {
          username: this.username,
          password: this.password
        };
        this.$http.post(`${this.$store.state.basicUrl}/signup`, data).then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              const { result, data } = body;
              if (result === "success") {
                this.info = data.info;
                setTimeout(function() {
                  window.location.href = "#/";
                }, 2000);
              } else if (result === "fail") {
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
  }
};
</script>
<style scoped>
.login-title {
  color: #fff;
}
</style>

