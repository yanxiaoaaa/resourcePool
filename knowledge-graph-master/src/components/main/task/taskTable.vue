<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">任务列表</h3>
          </div>
          <div class="box-body table-responsive">
            <table class="table table-bordered table-striped">
              <tbody>
                <tr>
                  <td>序号</td>
                  <td>任务名称</td>
                  <td>创建时间</td>
                  <td>数据量</td>
                  <td>执行状态</td>
                  <td>操作</td>
                </tr>
                <tr v-for="(item,index) in items" :key="index" data-id="item._id">
                  <td>{{index+1}}</td>
                  <td>{{item.name}}</td>
                  <td>{{formatDate(item.create)}}</td>
                  <td>{{item.dataNum}}</td>
                  <td>{{Boolean(item.status) === false ? "运行中" : "已完成"}}</td>
                  <td>
                    <router-link v-bind:to="'/task/'+item._id" style="color:white">
                      <button class="btn btn-primary">查看</button>
                    </router-link>
                    <button class="btn btn-danger" @click="del(item._id)">删除</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <common-alert :show="show" :error="error" :info="info"></common-alert>
        </div>
      </div>
    </div>
  </section>
</div>
</template>
<script>
import { format } from "../../../util/index";
import CommonAlert from "@/components/common/CommonAlert";

export default {
  name: "TaskTable",
  components: {
    CommonAlert
  },
  data() {
    return {
      items: [],

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
      const data = {
        userId: sessionStorage.getItem("userId")
      };
      this.$http.post(`${this.$store.state.basicUrl}/task`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              console.log(data);
              this.items = data;
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
    del: function(taskId) {
      const data = {
        taskId
      };
      this.$http.post(`${this.$store.state.basicUrl}/task/del`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            this.show = true;
            this.info = data.info;
            if (result === "success") {
              this.fetch();
            } else if (result === "fail") {
              this.error = true;
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
    formatDate: function(time) {
      return format(time);
    }
  }
};
</script>
<style scoped>
</style>
