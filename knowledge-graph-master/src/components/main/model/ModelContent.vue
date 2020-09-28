<template>
<div class="content-wrapper">
  <section class="content">
    <div class="row">
      <div class="col-xs-4">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">
              <span class="badge bg-green">资源池搜索</span>
            </h3>
          </div>
          <div class="box-body">

          <section class="content" style="min-height:450px;">
            <div class="row">
              <div class="col-xs-12">
                <div class="box">
                  <div class="box-header">
                    <button class="btn btn-success pull-right" data-toggle="modal" data-target="#create" @click="getcolumns">输入信息</button>            
                  </div>
                  <div class="box-body table-responsive">
                    <table class="table table-bordered table-striped">
                      <tbody>
                        <tr>
                          <td>字段名称</td>
                          <td>字段信息</td>
                          <td>操作</td>
                        </tr>
                        <tr v-for="(item,index) in items" :key="index">
                          <td>{{item.infor}}</td>
                          <td>{{item.name}}</td>
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
          

            <!-- form start -->
            <form role="form">
              <div class="box-footer" style="text-align:center">
                <button class="btn btn-primary" @click="submits">查询</button>
                <button class="btn btn-default" @click="add_infor">添加</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      
      <div class="modal fade" id="create" tabindex="-1" role="dialog" aria-labelledby="createLabel">
        <div class="modal-dialog" role="document">
          <div class="modal-content" style="width: 400px;margin:0 auto">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" id="createLabel">查询信息</h4>
            </div>
            <div class="modal-body">
              <div class="form-group">
                <label>可查询字段</label>
                <select class="form-control" v-model="RecordType">
                  <option v-for="(record,index) in records" :key="index">{{record}}</option>
                </select>
              </div>
              <div class="form-group">
                <label>查询值</label>
                <input class="form-control" type="text" v-model="RecordName"/>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
              <button type="button" class="btn btn-primary" data-dismiss="modal" @click="create">保存</button>
            </div>
          </div>
        </div>
      </div>

      
      <div class="col-xs-8">
      <div class="box" style="min-height:350px">
        <div class="box-header">
          <h3 class="box-title">
            <span class="badge bg-green">查询结果</span>
          </h3>
          <div class="box-tools pull-right">
            <a class="btn btn-box-tool" href="#/model">跳至图表展示</a>
          </div>
          <div class="box-body">

          <section class="content" style="min-height:500px;">
            <div class="row">
              <div class="col-xs-12">
                <div class="box">
                  <div class="box-body table-responsive">
                    <table class="table table-bordered table-striped" >
                      <tbody>
                        <tr style="overflow:hidden;word-break:keep-all">
                          <td v-for="(item,index) in this.$store.state.result_name" :key="index">{{item}}</td>
                        </tr>
                        <tr v-for="(item,index) in this.$store.state.result_infor" :key="index">
                          <td v-for="(i,index) in item" :key="index">{{i}}</td>
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

        </div>
      </div>
    </div>





    </div>
    <common-alert :show="show" :error="error" :info="info"></common-alert>
  </section>

   
  </div>

</template>
<script>
import { getPath } from "../../../util/index";
import CommonAlert from "@/components/common/CommonAlert";

export default {
  name: "ModelContent",
  components: {
    CommonAlert
  },
  data() {
    return {
      items: [],
      RecordName: "",
      RecordType: "",
      show: false,
      records: []
    };
  },
  methods: {
    add_infor: function(){
      var data = {
        type: this.$store.state.page,
        title: this.$store.state.pagename[this.$store.state.page],
        content: this.items
      };
      this.$http.post(`http://localhost:8000/ResourcePool/add_infor`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            alert(body.information);
          }
        },
        response => {
          console.log("error");
        }
      );
    },

    create: function() {
      var datas = {
        name: this.RecordName,
        infor: this.RecordType
      };
      this.items.push(datas);
      this.RecordName="";
      this.RecordType="";
      this.show = true;
      setTimeout(() => {
        this.show = false;
      }, 1000);
    },
    del: function(index) {
      this.items.splice(index,1);
      this.show = true;
      setTimeout(() => {
        this.show = false;
      }, 1000);
    },
    submits: function(){
      var data = {
        type: this.$store.state.page,
        title: this.$store.state.pagename[this.$store.state.page],
        content: this.items
      };
      this.$http.post(`http://localhost:8000/ResourcePool/findBySth`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            this.$store.state.result_infor=body;
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    getcolumns: function(){
      var data = {
        title: this.$store.state.pagename[this.$store.state.page]
      };
      this.$http.post(`http://localhost:8000/ResourcePool/findContent`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            this.records=body;
          }
        },
        response => {
          console.log("error");
        }
      );
    }
  }
};
</script>
<style scoped>
.compose-model {
  display: inline-block;
  margin-right: 10px;
}
.compose-model a {
  position: relative;
  transition: all ease 0.3s;
}
.compose-model:hover a {
  padding-right: 10px;
}
.compose-model:hover .fa-close {
  visibility: visible;
}
.fa-close {
  visibility: hidden;
  position: absolute;
  right: 0px;
  top: 0px;
  cursor: pointer;
}
.btn-label {
  margin-top: 5px;
  margin-right: 5px;
}
</style>
