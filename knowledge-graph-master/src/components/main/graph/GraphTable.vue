<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">新建数据库 <b>{{Title}}</b></h3>
            <button class="btn btn-success pull-right" data-toggle="modal" data-target="#create">新建字段</button>            
          </div>
          <div class="box-body table-responsive">
            <table class="table table-bordered table-striped">
              <tbody>
                <tr>
                  <td>序号</td>
                  <td>字段名称</td>
                  <td>数据类型</td>
                  <td>操作</td>
                </tr>
                <tr v-for="(item,index) in items" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{item.name}}</td>
                  <td>{{item.type}}</td>
                  <td>
                    <button class="btn btn-danger" @click="del(index)">删除</button>
                  </td>
                </tr>
              </tbody>
            </table>
            <button class="btn btn-success pull-right" style="margin-top:10px" @click="submits">创建数据库</button>
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
          <h4 class="modal-title" id="createLabel">增加字段</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>更改数据库名</label>
            <input class="form-control" type="text" v-model="Title"/>
          </div>
           <div class="form-group">
            <label>字段名</label>
            <input class="form-control" type="text" v-model="RecordName"/>
          </div>
          <div class="form-group">
            <label>数据类型</label>
            <select class="form-control" v-model="RecordType">
              <option value ="varchar(64)">varchar</option>
              <option value ="int">int</option>
              <option value="char(64)">char</option>
              <option value="bit">bit</option>
              <option value="image">image</option>
              <option value="date">date</option>
            </select>
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
      Title: "",
      show: false,
      error: false,
      info: ""
    };
  },
  methods: {
    submits: function() {
      const data = {
        title: this.Title,
        content:this.items
      };
      this.$http.post(`http://localhost:8000/ResourcePool/create_database`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            this.show = true;
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
    create: function() {
      var data = {
        name: this.RecordName,
        type: this.RecordType
      };
      this.show = true;
      this.items.push(data);
      setTimeout(() => {
              this.show = false;
            }, 1000);
      this.RecordName="";
      this.RecordType="";
    },
    del: function(index) {
            this.show = true;
            this.items.splice(index,1);
            setTimeout(() => {
              this.show = false;
            }, 1000);
    },
    formatDate: function(time) {
      return format(time);
    }
  }
};
</script>
<style scoped>
</style>
