<template>
  <div class="col-xs-6">
    <div class="box">
      <div class="box-header">
        <h3 class="box-title">接口演示</h3>
          <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
          <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
        </div>
      </div>
      <div class="box-body" style="padding: 0 10px">
        <label>输入样例</label>
        <div class="row" style="margin:0">
          <button class="col-xs-12 btn btn-default btn-xs example-list" v-for="(item,index) in example" :key="index" style="text-overflow: ellipsis;overflow: hidden; margin: 5px 0" :title="item" @click="handleInput(item)" >
            {{item}}
            <i class="fa fa-close" aria-hidden="true" @click.stop="deleteExample(index)"></i>
          </button>
          <button class="btn btn-xs btn-warning" data-toggle="modal" data-target="#create" style="margin: 5px 0">+</button>
        </div>
        <label>模型输入</label>
        <div class="row" style="margin:0">
          <textarea class="form-control" style="text-align:left" v-model="inputContent" rows="6"></textarea>              
        </div>
         <label>模型输出</label>
        <div class="row" style="margin:0">
          <textarea class="form-control" style="text-align:left" v-model="outputContent" rows="6" disabled></textarea>              
        </div>
        <div class="box-footer" style="text-align:center">
          <button class="btn btn-primary" @click="run">运行</button>
          <router-link to="/model" style="color:white">
            <button class="btn btn-default">关闭</button>
          </router-link>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
export default {
  name: "CommonRest",
  props: ["id", "url", "method", "example"],
  data() {
    return {
      inputExample: "",
      inputContent: "",
      outputContent: ""
    };
  },
  methods: {
    create() {
      if (this.inputExample.trim().length > 0) {
        this.example.push(this.inputExample);
        this.sendRequest();
      }
    },
    deleteExample(index) {
      this.example.splice(index, 1);
      this.sendRequest();
    },
    sendRequest() {
      const data = {
        _id: this.id,
        example: this.example
      };
      console.log(data);
      this.$http.post(`${this.$store.state.basicUrl}/model/edit`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              this.inputExample = "";
            }
          }
        },
        response => {
          console.log("error");
        }
      );
    },
    handleInput(content) {
      this.inputContent = content;
      this.outputContent = "";
    },
    run() {
      const data = {
        input: this.inputContent
      };
      this.$http.post(`${this.url}`, JSON.stringify(data)).then(response => {
        const { status, ok, body } = response;
        this.outputContent = JSON.stringify(body);
      }),
        response => {
          console.log("error");
        };
    }
  }
};
</script>
<style scoped>
.example-list:hover > .fa-close {
  visibility: visible;
}
.fa-close {
  visibility: hidden;
  position: absolute;
  right: 5px;
  top: 5px;
  cursor: pointer;
}
</style>

