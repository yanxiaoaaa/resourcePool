<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">项目列表</h3>
            <button class="btn btn-success pull-right" data-toggle="modal" data-target="#create">新建项目</button>            
          </div>
          <div class="box-body table-responsive">
            <table class="table table-bordered table-striped">
              <tbody>
                <tr>
                  <td>序号</td>
                  <td>项目名称</td>
                  <td>创建时间</td>
                  <td>最后更改时间</td>
                  <td>操作</td>
                </tr>
                <tr v-for="(item,index) in items" :key="index" data-id="item._id">
                  <td>{{index+1}}</td>
                  <td>{{item.name}}</td>
                  <td>{{formatDate(item.create)}}</td>
                  <td>{{formatDate(item.modify)}}</td>
                  <td>
                    <router-link v-bind:to="'/project/'+item._id" style="color:white">
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
  
  <!-- Modal -->
  <div class="modal fade" id="create" tabindex="-1" role="dialog" aria-labelledby="createLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content" style="width: 400px;margin:0 auto">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="createLabel">新建项目</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>项目名称</label>
            <input class="form-control" type="text" v-model="projectName"/>
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
import { format } from "../../../util/index";
import CommonAlert from "@/components/common/CommonAlert";

export default {
  name: "ProjectTable",
  components: { CommonAlert },
  data() {
    return {
      items: [],
      error: false,
      info: "",
      projectName: "",

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
      this.$http.post(`${this.$store.state.basicUrl}/project`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
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
    create: function() {
      const data = {
        userId: sessionStorage.getItem("userId"),
        projectName: this.projectName
      };
      this.$http
        .post(`${this.$store.state.basicUrl}/project/create`, data)
        .then(
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
    del: function(projectId) {
      const data = {
        projectId
      };
      this.$http.post(`${this.$store.state.basicUrl}/project/del`, data).then(
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
