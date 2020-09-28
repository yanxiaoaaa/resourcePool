<template>
<div class="all-wrap">
  <common-header></common-header>
  <login-box></login-box>
</div>
  
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonFooter from "@/components/common/CommonFooter";
import LoginBox from "@/components/login/loginBox";

export default {
  components: {
    CommonHeader,
    CommonFooter,
    LoginBox
  },
  mounted: function() {
    document.getElementsByClassName("all-wrap")[0].style.height =
      window.innerHeight + "px";
  }
};
</script>

<style scoped>
.all-wrap {
  background-image: url("../../assets/images/bg.jpeg");
  background-size: 100% 100%;
  width: 100%;
  height: 100%;
}
</style>

