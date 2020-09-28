<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">{{item.name}}</h3>
          </div>
          <div class="box-body table-responsive">
            <table class="table table-bordered table-striped">
              <tbody>
                <tr>
                  <td>序号</td>
                  <td>新闻标题</td>
                  <td>新闻内容</td>
                  <td style="width: 20%">处理结果</td>
                  <td>操作</td>
                </tr>
                <tr v-for="(element,index) in item.result" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{element.title}}</td>
                  <td>{{element.content}}</td>
                  <td style="width: 20%">{{element.result}}</td>
                  <td>
                    <button class="btn btn-primary">标注</button>
                    <button class="btn btn-danger">删除</button>
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
import { format, getPath } from "../../../util/index";
import CommonAlert from "@/components/common/CommonAlert";

export default {
  name: "TaskDetail",
  components: {
    CommonAlert
  },
  data() {
    return {
      item: {},

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
        taskId: getPath(window.location.href)
      };
      this.$http.post(`${this.$store.state.basicUrl}/task/detail`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              this.item = data;
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
    formatDate: function(time) {
      return format(time);
    }
  }
};
</script>
<style scoped>
</style>
