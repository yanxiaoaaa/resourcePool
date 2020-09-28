<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">数据列表</h3>
            <span class="pull-right last-update-time">上次更新时间：{{items.length > 0 ? formatDate(items[0].modify):'最近没有更新哦～'}}</span>
          </div>
          <div class="box-body table-responsive">
            <div class='alert alert-danger alert-dismissible' v-show="error">{{info}}</div>            
            <table class="table table-bordered table-striped">
              <tbody>
                <tr>
                  <td>序号</td>
                  <td>数据名称</td>
                  <td>数据来源</td>
                  <td>数据统计</td>
                  <td>创建时间</td>
                  <td>操作</td>
                </tr>
                <tr v-for="(item,index) in items" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{item.name}}</td>
                  <td>{{item.source}}</td>
                  <td>{{item.num}}</td>
                  <td>{{formatDate(item.create)}}</td>
                  <td>
                    <router-link v-bind:to="'/data/'+item._id" style="color:white">
                      <button class="btn btn-primary">查看</button>
                    </router-link>
                  </td>
                </tr>
              
                <!-- <tr v-for="(item,index) in newsData" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{item.name}}</td>
                  <td>{{item.source}}</td>
                  <td>{{item.num}}</td>
                  <td>{{item.create}}</td>
                  <td>
                    <router-link v-bind:to="'/data/'+item._id" style="color:white">
                      <button class="btn btn-primary">查看</button>
                    </router-link>
                  </td>
                </tr> -->

              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </section>

</div>
</template>
<script>
import { format } from "../../../util/index";

export default {
  name: "DataTable",
  data() {
    return {
      items: [],
      error: false,
      info: "",
      dataName: "",
      dataUri: "",
      dataSource: "",
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
      this.$http.post(`${this.$store.state.basicUrl}/data`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              this.items = data;
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
    },
    update: function() {
      console.log("update");
      const data = {
        userId: sessionStorage.getItem("userId")
      };
      //TODO:API change
      // this.$http.post(`${this.$store.state.basicUrl}/data/create`, data).then(
      //   response => {
      //     const { status, ok, body } = response;
      //     if (status === 200 && ok) {
      //       const { result, data } = body;
      //       if (result === "success") {
      //         window.location.reload();
      //       } else if (result === "fail") {
      //         this.error = true;
      //         this.info = data.info;
      //       }
      //     }
      //   },
      //   response => {
      //     console.log("error");
      //   }
      // );
    },
    // create: function() {
    //   const data = {
    //     userId: sessionStorage.getItem("userId"),
    //     dataName: this.dataName,
    //     dataUri: this.dataUri,
    //     dataSource: this.dataSource
    //   };
    //   this.$http.post(`${this.$store.state.basicUrl}/data/create`, data).then(
    //     response => {
    //       const { status, ok, body } = response;
    //       if (status === 200 && ok) {
    //         const { result, data } = body;
    //         if (result === "success") {
    //           window.location.reload();
    //         } else if (result === "fail") {
    //           this.error = true;
    //           this.info = data.info;
    //         }
    //       }
    //     },
    //     response => {
    //       console.log("error");
    //     }
    //   );
    // },
    // del: function(dataId) {
    //   const data = {
    //     dataId
    //   };
    //   this.$http.post(`${this.$store.state.basicUrl}/data/del`, data).then(
    //     response => {
    //       const { status, ok, body } = response;
    //       if (status === 200 && ok) {
    //         const { result, data } = body;
    //         if (result === "success") {
    //           window.location.reload();
    //         } else if (result === "fail") {
    //           this.error = true;
    //           this.info = data.info;
    //         }
    //       }
    //     },
    //     response => {
    //       console.log("error");
    //     }
    //   );
    // },
    formatDate: function(time) {
      return format(time);
    }
  }
};
</script>
<style scoped>
.last-update-time {
  line-height: 34px;
  margin-right: 20px;
  color: #00a65a;
}
</style>
