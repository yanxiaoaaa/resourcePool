<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">数据库连接池: <b>{{number}}</b> 您正在使用：<b>{{used_now}}</b> </h3>
            <button class="btn btn-success pull-right" @click="create">新建数据库连接</button>            
          </div>
          <div class="box-body table-responsive">
            <table class="table table-bordered table-striped">
              <tbody>
                <tr>
                  <td>序号</td>
                  <td>连接名称</td>
                  <td>连接状态</td>
                  <td>使用</td>
                  <td>释放</td>
                  <td>操作</td>
                </tr>
                <tr v-for="(item,index) in items" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{item.name}}</td>
                  <td>{{item.type}}</td>
                  <td>
                    <button class="btn btn-success" @click="use(index)">使用</button>
                  </td>
                  <td>
                    <button class="btn btn-success" @click="unuse(index)">释放</button>
                  </td>
                  <td>
                    <button class="btn btn-danger" @click="del(index)">删除</button>
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
  name: "GraphTable",
  components: {
    CommonAlert
  },
  data() {
    return {
      items: [],
      info: "",
      RecordName: "",
      RecordType: "",
      number: "",
      show: false,
      error: false,
      info: ""
    };
  },
  mounted() {
    this.getConn();
  },
  computed: {
    used_now: function(){
      var result="";
      for(var i = 0; i <this.$store.state.used.length; i++) {
        if(i!=0){
          result=result+"、"+(String)(this.$store.state.used[i]+1);
        }
        else{
          result=result+(String)(this.$store.state.used[i]+1);
        }
      }
      return result;
    }
  },
  methods: {
    getConn: function(){
        this.$http.post(`http://localhost:8000/ResourcePool/GetConnection`).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            this.items=body;
            this.number=this.items.length;
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    create: function() {
      this.$http.post(`http://localhost:8000/ResourcePool/AddConnection`).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            this.getConn();
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    del: function(index) {
        var data = {
            index: index
        };
        this.$http.post(`http://localhost:8000/ResourcePool/DeleteConnection`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            if(body.information==1){
              alert("该连接正在使用，无法删除")
            }
            else{
              this.show = true;
              this.items.splice(index,1);
              setTimeout(() => {
                this.show = false;
              }, 1000);
              this.getConn();
            }
            
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    use: function(index){
      for(var i = 0; i <this.$store.state.used.length; i++) {
					if(this.$store.state.used[i] == index) {
            alert("您已拥有权限");
						return;
					}
				}
        var data = {
            index: index
        };
        this.$http.post(`http://localhost:8000/ResourcePool/use`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            if(body.information==1){
              this.$store.state.used.push(index);
              this.$store.state.canUse=this.$store.state.canUse+1;
            }
            else{
              alert("该连接已经被使用");
            }
            this.getConn();
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    unuse: function(index){
      var flag=0;
        for(var i = 0; i <this.$store.state.used.length; i++) {
					if(this.$store.state.used[i] == index) {
            flag=1;
					}
        }
        if(flag==0){
            alert("您还未获取权限");
						return;
        }
        var data = {
            index: index
        };
        this.$http.post(`http://localhost:8000/ResourcePool/unuse`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            if(body.information==1){
              for(var i = 0; i <this.$store.state.used.length; i++) {
					      if(this.$store.state.used[i] == index) {
                  this.$store.state.used.splice(i,1);
					      }
              }
              this.$store.state.canUse=this.$store.state.canUse-1;
            }
            else{
              alert("尚未获取连接权限");
            }
            this.getConn();
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
