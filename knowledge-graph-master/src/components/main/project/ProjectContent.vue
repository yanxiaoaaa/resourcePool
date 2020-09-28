<template>
  <div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">项目流程</h3>
          </div>

          <div class="box-body">
            <!-- 拖拽组件侧边栏 -->
            <div class="col-md-3 component-wrap">

              <!-- 数据组件 -->
              <li class="list-group-item data" :class="treeControl.isDataActive ? 'active' : ''" @click="treeControl.isDataActive = !treeControl.isDataActive">
                <i class="fa fa-database"></i> 
                <span>我的数据</span>
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right" :class="treeControl.isDataActive ? 'turn-down' : ''"></i>
                </span>
              </li>
              <draggable class="list-group" element="ul" v-model="items.data" :options="{group:{name:'people', pull:'clone', put:false }}" @start="isDragging=true" @end="isDragging=false"> 
                <transition-group type="transition" name="fade">
                  <li class="list-group-item" v-for="item in items.data" :key="item._id" v-show="treeControl.isDataActive">
                    <i class="fa fa-database"></i>
                    <span>{{item.name}}</span>
                  </li>
                </transition-group>
              </draggable>

              <!-- 模型组件 -->
              <li class="list-group-item model" :class="treeControl.isModelActive ? 'active' : ''" @click="treeControl.isModelActive = !treeControl.isModelActive">
                <i class="fa fa-cubes"></i> 
                <span>我的模型</span>
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right" :class="treeControl.isModelActive ? 'turn-down' : ''"></i>
                </span>
              </li>
              <draggable class="list-group" element="ul" v-model="items.model" :options="{group:{name:'people', pull:'clone', put:false }}" @start="isDragging=true" @end="isDragging=false">
                <transition-group type="transition" name="fade">                
                  <li class="list-group-item" v-for="item in items.model" :key="item._id" v-show="treeControl.isModelActive">
                    <i class="fa fa-cubes"></i>
                    <span>{{item.name}}</span>
                  </li>
                </transition-group>
              </draggable>

              <!-- 图谱组件 -->
              <li class="list-group-item graph" :class="treeControl.isGraphActive ? 'active' : ''" @click="treeControl.isGraphActive = !treeControl.isGraphActive">
                <i class="fa fa-arrows-alt"></i> 
                <span>我的图谱</span>
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right" :class="treeControl.isGraphActive ? 'turn-down' : ''"></i>
                </span>
              </li>
              <draggable class="list-group" element="ul" v-model="items.graph" :options="{group:{name:'people', pull:'clone', put:false }}"  @start="isDragging=true" @end="isDragging=false">
                <transition-group type="transition" name="fade">                
                  <li class="list-group-item" v-for="item in items.graph" :key="item._id" v-show="treeControl.isGraphActive">
                    <i class="fa fa-arrows-alt"></i>
                    <span>{{item.name}}</span>
                  </li>
                </transition-group>
              </draggable>
            </div>

            <!-- 拖拽组件放置区间 -->
            <div class="col-md-6 drop-wrap">
              <!-- 组件区块 -->
              <draggable element="ul" v-model="selectItems" :options="{group:'people'}" style="padding:0"> 
                  <transition-group name="no" class="drop-container" tag="ul">
                    <common-process v-for="(element, index) in selectItems" :key="element._id" :item="element"  v-on:showSetting="showSetting(element)" v-on:deleteItems="delSeletedItems(index)" > 
                      <i class="glyphicon glyphicon-pushpin" aria-hidden="true"></i>
                      {{element.name}}
                    </common-process> 
                  </transition-group>
              </draggable>
              <!-- 流程控制 -->
              <div class="toolBar">
                <div data-toggle="modal" data-target="#create">
                  <i class="glyphicon glyphicon-play-circle" aria-hidden="true"></i>
                  <p>运行</p>
                </div>
                <div @click="clear">
                  <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                  <p>清空</p>
                </div>
                <div>
                  <i class="glyphicon glyphicon-repeat" aria-hidden="true"></i>
                  <p>历史</p>
                </div>
              </div>
            </div>
            
            <!-- 组件区块配置区域 -->
            <div class="col-md-3">
              <common-setting :setting="setting" :item="currentItem" v-on:changeSetting="changeSetting($event)"></common-setting>
            </div>

          </div>
          <common-alert :show="show" :error="error" :info="info"></common-alert>          
        </div>
      </div>
    </div>
  </section>

  <!-- Modal -->
  <div class="modal fade" id="create" tabindex="-1" role="dialog" aria-labelledby="createLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content" style="width: 400px;margin:0 auto">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="createLabel">新增任务</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>任务名称</label>
            <input class="form-control" type="text" v-model="taskName"/>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="create">保存</button>
        </div>
      </div>
    </div>
  </div>

</div>
</template>
<script>
import draggable from "vuedraggable";
import CommonProcess from "@/components/common/CommonProcess";
import CommonSetting from "@/components/common/CommonSetting";
import CommonAlert from "@/components/common/CommonAlert";
import { getPath } from "../../../util/index";

export default {
  name: "ProjectContent",
  components: {
    draggable,
    CommonProcess,
    CommonSetting,
    CommonAlert
  },
  data() {
    const projectId = getPath(window.location.href);
    return {
      projectId: projectId,

      items: {
        // 所有的数据、模型、图谱组件
        data: [],
        model: [],
        graph: []
      },

      treeControl: {
        // 控制当前组件页面是否展开
        isDataActive: true,
        isModelActive: false,
        isGraphActive: false
      },

      selectItems: [], // 所有在拖拽区内的数据、模型、图谱组件

      currentItem: {}, // 根据当前选中内容，控制右侧setting展示内容
      setting: {
        dataAttr: [],
        modelAttr: {}
      },

      isDragging: false, // 关于拖拽的一些配置
      delayedDragging: false,

      taskName: "", // 新建任务

      show: false,
      error: false,
      info: ""
    };
  },
  mounted() {
    this.fetch();
  },
  methods: {
    fetch: function() {
      const sendData = {
        userId: sessionStorage.getItem("userId")
      };
      // 获取数据源
      this.$http.post(`${this.$store.state.basicUrl}/data`, sendData).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              for (let i = 0; i < data.length; i++) {
                data[i]["type"] = "data";
                this.items.data.push(data[i]);
              }
            } else if (result === "fail") {
              this.show = true;
              this.error = true;
              this.info = data.info;
              setTimeout(() => {
                this.show = false;
              }, 2000);
            }
          }
        },
        response => {
          console.log("error");
        }
      );
      // 获取模型
      this.$http.post(`${this.$store.state.basicUrl}/model`, sendData).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              console.log(data);
              for (let i = 0; i < data.length; i++) {
                if (data[i].type === "multi") {
                  data[i]["type"] = "model";
                  this.items.model.push(data[i]);
                }
              }
            } else if (result === "fail") {
              this.show = true;
              this.error = true;
              this.info = data.info;
              setTimeout(() => {
                this.show = false;
              }, 2000);
            }
          }
        },
        response => {
          console.log("error");
        }
      );
      // 获取图谱
      this.$http.post(`${this.$store.state.basicUrl}/graph`, sendData).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              for (let i = 0; i < data.length; i++) {
                data[i]["type"] = "graph";
                this.items.graph.push(data[i]);
              }
            } else if (result === "fail") {
              this.show = true;
              this.error = true;
              this.info = data.info;
              setTimeout(() => {
                this.show = false;
              }, 2000);
            }
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    create: function() {
      const dataId = "5b02860957a67a72e9ddbe7f";

      this.$http.get(`${this.$store.state.basicUrl}/data/${dataId}`).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              const { num, list } = data;

              const sendData = {
                userId: sessionStorage.getItem("userId"),
                taskName: this.taskName,
                dataNum: num
              };

              this.$http
                .post(`${this.$store.state.basicUrl}/task/create`, sendData)
                .then(
                  response => {
                    const { status, ok, body } = response;
                    if (status === 200 && ok) {
                      const { result, data } = body;
                      this.show = true;
                      if (result === "success") {
                        console.log(data);
                        this.info =
                          "您的任务正在大数据平台中执行，稍后请移步【我的任务】查看执行结果～";
                        setTimeout(() => {
                          this.run({
                            taskId: data.taskId,
                            list: list
                          });
                        }, 2000);
                      } else if (result === "fail") {
                        this.error = true;
                        this.info = data.info;
                      }
                      setTimeout(() => {
                        this.show = false;
                      }, 2000);
                    }
                  },
                  response => {
                    console.log("error");
                  }
                );
            } else if (result === "fail") {
              this.error = true;
              this.info = data.info;
            }
            setTimeout(() => {
              this.show = false;
            }, 2000);
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    clear: function() {
      this.selectItems = [];
    },
    delSeletedItems: function(index) {
      this.selectItems.splice(index, 1);
    },
    showSetting: function(element) {
      this.currentItem = element;
    },
    changeSetting(setting) {
      this.setting = setting;
    },
    run({ taskId, list }) {
      const url = "http://10.108.211.136:8006/news2domainkw";
      let result = [];
      for (let i = 0; i < list.length; i++) {
        const sendData = {
          input: list[i].title + "#" + list[i].content
        };
        this.$http.post(url, JSON.stringify(sendData)).then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              console.log(body);
              list[i]["result"] = body.finalkeys;
              result.push(list[i]);
              if (result.length === list.length) {
                console.log("finish");
                this.taskFinish({
                  taskId,
                  result
                });
              }
            }
          },
          response => {
            console.log("error");
          }
        );
      }
    },
    taskFinish({ taskId, result }) {
      const sendData = {
        taskId,
        doc: { status: true, result: result }
      };
      this.$http.post(`${this.$store.state.basicUrl}/task/edit`, sendData).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            this.show = true;
            if (result === "success") {
              console.log(data);
              this.info =
                "任务已执行完毕，客官可移步【我的任务】查看执行结果～";
            } else if (result === "fail") {
              this.error = true;
              this.info = data.info;
            }
            setTimeout(() => {
              this.show = false;
            }, 2000);
          }
        },
        response => {
          console.log("error");
        }
      );
    }
  },
  watch: {
    isDragging(newValue) {
      if (newValue) {
        this.delayedDragging = true;
        return;
      }
      this.$nextTick(() => {
        this.delayedDragging = false;
      });
    }
  }
};
</script>
<style lang="scss" scoped>
/* 拖拽组件侧边栏样式 */
.component-wrap {
  height: 500px;
  overflow-y: scroll;
  /* 组件类目以颜色区分 */
  .active {
    transition: all ease 0.3s;
    &.data {
      background-color: #00a65a;
      border: 1px solid #008d4c;
    }
    &.model {
      background-color: #f39c12;
      border: 1px solid #e08e0b;
    }
    &.graph {
      background-color: #dd4b39;
      border: 1px solid #d73925;
    }
  }

  .list-group-item {
    margin-bottom: -2px;
  }

  /* 组件在拖拽时鼠标样式 */
  .list-group {
    margin-bottom: 0;
    .list-group-item {
      cursor: move;
      font-size: 12px;
      text-overflow: ellipsis;
    }
  }
  /* 组件在拖拽时鼠标样式 */
  .pull-right {
    transition: transform ease-in 0.3s;
    &.turn-down {
      transform: rotate(-90deg);
    }
  }
}

.drop-wrap {
  background-image: url("../../../assets/images/boardbg.png");
  min-height: 500px;
  padding: 20px;
  border: 1px solid #ddd;
  .drop-container {
    min-height: 500px;
  }
  .toolBar {
    position: absolute;
    bottom: 0;
    width: 100%;
    i {
      font-size: 25px;
      cursor: pointer;
    }
    p {
      font-size: 12px;
    }
    div {
      width: 32%;
      display: inline-block;
      text-align: center;
    }
  }
}

.ghost {
  opacity: 0.5;
  background: #c8ebfb;
}

.fade-enter-active,
.fade-leave-active {
  transition: all ease 0.3s;
}
.fade-enter, .fade-leave-to
/* .list-leave-active for below version 2.1.8 */ {
  opacity: 0;
  transform: translateY(-42px);
}
</style>

