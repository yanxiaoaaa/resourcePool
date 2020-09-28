<template>
  <li class="process" :class="className" @click="showSetting">
    <slot></slot>
     <i class="fa fa-close" aria-hidden="true" @click.stop="delSeletedItems"></i>
  </li>
</template>

<script>
export default {
  name: "CommonProcess",
  props: ["item"],
  computed: {
    // 控制拖拽拼图样式
    className: function() {
      if (this.item.type === "data") {
        return "process-success";
      } else if (this.item.type === "model") {
        return "process-warning";
      } else {
        return "process-danger";
      }
    }
  },
  methods: {
    showSetting() {
      this.$emit("showSetting");
    },
    delSeletedItems() {
      this.$emit("deleteItems");
    }
  }
};
</script>

<style>
.process {
  font-size: 12px;
  width: 15rem;
  height: 5rem;
  line-height: 5rem;
  margin: 3px auto;
  position: relative;
  border-radius: 6%;
  background-color: #f4f4f4;
  text-align: center;
  box-shadow: 1px 1px 1px #337ab7;
  list-style-type: none;
}
.process:hover > .fa-close {
  visibility: visible;
}
.fa-close {
  visibility: hidden;
  position: absolute;
  right: 5px;
  top: 5px;
  cursor: pointer;
}
.process:before {
  content: "";
  width: 1.8rem;
  height: 0.9rem;
  margin: 0;
  position: absolute;
  top: 0rem;
  left: 7rem;
  border-radius: 0 0 0.9rem 0.9rem;
  background-color: #ffffff;
  box-shadow: 1px 1px 1px #333;
  z-index: 0;
}
.process:after {
  content: "";
  width: 1.8rem;
  height: 0.9rem;
  border-radius: 0 0 0.9rem 0.9rem;
  position: absolute;
  top: 5rem;
  left: 7rem;
  /* border-radius: 50%; */
  background-color: #f4f4f4;
  box-shadow: 1px 1px 1px #333;
  z-index: 1;
}
.process.process-success:before {
  box-shadow: 1px 1px 1px #00a65a;
}
.process.process-success,
.process.process-success:after {
  box-shadow: 1px 1px 1px #00a65a;
  background-color: #5ac594;
  color: #fff;
}
.process.process-primary,
.process.process-primary:after {
  box-shadow: 1px 1px 1px #337ab7;
  background-color: #81b5d4;
  color: #fff;
}
.process.process-warning,
.process.process-warning:after {
  box-shadow: 1px 1px 1px #f39c12;
  background-color: #f7bf66;
  color: #fff;
}
.process.process-danger,
.process.process-danger:after {
  box-shadow: 1px 1px 1px #dd4b39;
  background-color: #e57f72;
  color: #fff;
}
.process.process-info,
.process.process-info:after {
  box-shadow: 1px 1px 1px #00c0ef;
  background-color: #5ad6f5;
  color: #fff;
}
</style>
