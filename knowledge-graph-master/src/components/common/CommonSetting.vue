<template>
<div>
  <transition name="list">
    <div class="box box-success" v-show="item.type === 'data'">
      <div class="box-header">
        <h3 class="box-title" style="font-size:14px;">{{item.name}}</h3>
        <h6>（请选择该数据源中需要处理的数据属性）</h6>
      </div>
      <div class="box-body table-responsive">
        <div class="form-group" >
          <div class="checkbox" v-for="(element,index) in item.title" :key="index">
            <label>
              <input type="checkbox" :value="element" v-model="setting.dataAttr" @change="changeSetting()"/>{{element}}
            </label>
          </div>
        </div>
      </div>
    </div>
  </transition>

  <transition name="list">
    <div class="box box-warning" v-show="item.type === 'model'">
      <div class="box-header">
        <h3 class="box-title" style="font-size:14px;">{{item.name}}</h3>
        <h6>（请选择该模型处理的数据属性）</h6>
      </div>
      <div class="box-body table-responsive">
        <div class="form-group">
          <div class="checkbox" v-for="(element,index) in setting.dataAttr" :key="item._id + index">
            <label>
              <input type="checkbox" :value="element" v-model="modelAttr"/>{{element}}
            </label>
          </div>
        </div>
      </div>
    </div>
  </transition>

  <transition name="list">
    <div class="box box-danger" v-show="item.type === 'graph'">
      <div class="box-header">
        <h3 class="box-title" style="font-size:14px;">{{item.name}}</h3>
        <h6>（请选择需要进行关联的图谱）</h6>      
      </div>
      <!-- <div class="box-body table-responsive">
      </div> -->
    </div>
  </transition>

  <transition name="list">
    <div class="box" v-if="item.type === undefined">
      <div class="box-header">
        <h3 class="box-title" style="font-size:14px;">设置栏</h3>
        <h6>（点击画布中的小组件，会有对应的设置项在这里出现～）</h6>      
      </div>
      <!-- <div class="box-body table-responsive">
      </div> -->
    </div>
  </transition>
</div>

</template>
<script>
export default {
  name: "CommonSetting",
  props: ["setting", "item"],
  methods: {
    changeSetting() {
      this.$emit("changeSetting", this.setting);
    }
  },
  computed: {
    modelAttr: {
      get: function() {
        return this.setting.modelAttr[this.item._id] || [];
      },
      set: function(newValue) {
        this.setting.modelAttr[this.item._id] = newValue;
        this.$emit("changeSetting", this.setting);
      }
    }
  }
};
</script>
<style scoped>
.box-body {
  padding: 0 10px;
  font-size: 12px;
}
.list-enter-active,
.list-leave-active {
  transition: all 1s;
}
.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translate(30px);
}
</style>

