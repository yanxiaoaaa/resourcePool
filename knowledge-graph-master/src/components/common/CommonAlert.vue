<template>
  <div>
    <transition name="slide-fade">
      <div class="my-info-box my-info-box-success" v-show="show && !error">
        <i class="fa fa-check-circle-o"></i>
        <span>{{info}}</span>
      </div>
    </transition>
    <transition name="slide-fade">
      <div class="my-info-box my-info-box-danger" v-show="show && error">
        <i class="fa fa-times-circle-o"></i>
        <span>{{info}}</span>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  name: "CommonAlert",
  props: ["show", "info", "error"]
};
</script>
<style scoped>
.my-info-box {
  padding: 10px 20px;
  color: #fff;
  position: absolute;
  border-radius: 5px;
  left: 50%;
  transform: translateX(-50%);
  top: 80px;
}
.my-info-box-success {
  background-color: rgba(0, 166, 90, 0.5);
}
.my-info-box-danger {
  background-color: rgba(215, 57, 37, 0.5);
}
.slide-fade-enter-active {
  transition: all 0.3s ease;
}
.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>

