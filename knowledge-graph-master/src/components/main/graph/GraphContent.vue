<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">{{item.name}}<small style="margin-left: 10px">{{item.info}}</small></h3>
            <button class="btn btn-warning pull-right">查看图谱</button>
          </div>
          <div class="box-body">
            <div class="col-xs-3" >
              <div class="graph-title">
                <p>{{item.name}}</p>
                <i class="fa fa-plus pull-right" data-toggle="modal" data-target="#createConcept"></i>
              </div>
              <div class="graph-detail" v-for="(concept,index) in item.concept" :key="index" @click="showConceptDetail(index)" :class="{active: index === currentIndex}">
                <p>{{concept.name}}</p>
                <i class="fa fa-plus pull-right" data-toggle="modal" data-target="#createAttr"></i>
              </div>
            </div>

            <!-- <template v-if="this.currentType === 'concept'"> -->
            <div class="col-xs-9">
              <div class="box box-primary" style="min-height: 500px">
                <div class="box-header">
                  <h3 class="box-title">{{currentConcept.name}}<span class="badge bg-light-blue" style="margin-left:5px;vertical-align:top">概念</span></h3>
                  <div class="tab pull-right">
                    <span :class="{active : currentType === 'concept'}" @click="toggleShow('concept')">概念详情</span>
                    <span :class="{active : currentType === 'entity'}" @click="toggleShow('entity')">实例详情</span>
                  </div>
                </div>

                <div class="box-body no-padding" v-show="currentType === 'concept'">
                  <!-- form start -->
                  <form role="form">
                    <div class="box-body">
                      <div class="row">
                        <div class="form-group col-xs-6">
                          <label for="conceptName">名称</label>
                          <input type="text" class="form-control" id="conceptName" :value="currentConcept.name">
                        </div>
                        <div class="form-group col-xs-6">
                          <label for="exampleInputPassword1">唯一标识</label>
                          <input type="text" class="form-control" id="exampleInputPassword1" :value="currentConcept._id" disabled>
                        </div>
                      </div>
                      <div class="form-group">
                        <label for="conceptInfo">描述</label>
                        <textarea class="form-control" style="text-align:left" id="conceptInfo" :value="currentConcept.info"></textarea>
                      </div>
                      <div class="form-group">
                        <label for="exampleInputPassword1">拥有的属性信息</label>
                        <button class="btn btn-success btn-xs" data-toggle="modal" data-target="#createAttr">添加</button>
                        <table class="table table-bordered table-striped">
                          <tbody>
                            <tr>
                              <td>序号</td>
                              <td>属性名称</td>
                              <td>操作</td>
                            </tr>
                            <tr v-for="(item,index) in currentConcept.attr" :key="index">
                              <td>{{index+1}}</td>
                              <td>{{item.name}}</td>
                              <td>
                                <button class="btn btn-primary btn-xs" data-toggle="modal" data-target="#attributeDetail" @click="currentAttr = currentConcept.attr[index]">查看</button>
                                <button class="btn btn-danger btn-xs" @click="delAttribute(item._id)">删除</button>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                      <div class="form-group">
                        <label for="exampleInputPassword1">拥有的关系信息</label>
                        <button class="btn btn-success btn-xs" data-toggle="modal" data-target="#createRelation">添加</button>                        
                        
                        <p class="relation-wrap" v-for="(item,index) in currentConcept.relation" :key="index">
                          <span>{{item.name}}</span>
                          <button class="btn btn-default btn-xs">{{item.from_name}}</button>
                          <button class="btn btn-default btn-xs">{{item.from_attr}}</button>  
                          <span>to</span>
                          <button class="btn btn-default btn-xs">{{item.to_name}}</button>
                          <button class="btn btn-default btn-xs">{{item.to_attr}}</button>                                          
                        </p>
                      </div>
                    </div>
                    <!-- /.box-body -->

                    <div class="box-footer" style="text-align:center">
                      <button class="btn btn-primary" @click="editConcept">保存</button>
                      <button class="btn btn-danger" @click="delConcept">删除</button>
                    </div>
                  </form>
                </div>

                <div class="box-body no-padding table-responsive" v-show="currentType === 'entity'">
                  <div class="row">
                    <div class="col-md-12 col-sm-12">
                      <div class="pad" style="overflow-x: scroll">
                        <table class="table table-bordered table-striped" style="margin:0" id="dataTables">
                          <tbody>
                            <tr>
                              <td>序号</td>                                    
                              <td v-for="(item, index) in currentConcept.attr" :key="index">{{item.name}}</td>
                            </tr>
                            <template v-if="currentConcept.name === '企业'">
                              <tr v-for="(item, index) in currentEntity" :key="index">
                                <td>{{index}}</td>
                                <td>{{item.com_name}}</td>
                                <td>{{item.uni_code}}</td>
                                <td>{{item.persons}}</td>
                                <td>{{item.est_date}}</td>
                                <td>{{item.reg_capital}}</td>
                                <td>{{item.address}}</td>
                                <td>{{item.keywords}}</td>
                              </tr>
                            </template>

                            <template v-if="currentConcept.name === '技术链'">
                              <tr v-for="(item, index) in currentEntity" :key="index">
                                <td>{{index}}</td>
                                <td>{{item.name}}</td>
                              </tr>
                            </template>

                            <template v-if="currentConcept.name === '新闻'">
                              <tr>
                                <tr v-for="(item, index) in currentEntity" :key="index">
                                <td>{{index}}</td>
                                <td>{{item.title}}</td>
                                <td>{{item.time}}</td>
                                <td>{{item.abstractt}}</td>
                                <td>{{item.keywords}}</td>
                                <td>{{item.ner_names}}</td>
                              </tr>
                            </template>

                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
              <!-- <common-concept :name="currentConcept.name"></common-concept> -->
            <!-- </template>
            <template v-else>
              <p>当前没有任何内容可展示，请添加新概念</p>
            </template> -->
                
          </div>
          <common-alert :show="show" :error="error" :info="info"></common-alert>
        </div>
      </div>
    </div>
  </section>

  <!-- 新增概念 -->
  <div class="modal fade" id="createConcept" tabindex="-1" role="dialog" aria-labelledby="createLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content" style="width: 400px;margin:0 auto">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="createLabel">新增概念</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>概念名称</label>
            <input class="form-control" type="text" v-model="conceptName"/>
          </div>
          <div class="form-group">
            <label>概念描述</label>
            <input class="form-control" type="text" v-model="conceptInfo"/>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="createConcept">保存</button>
        </div>
      </div>
    </div>
  </div>

  <!-- 新增属性 -->
  <div class="modal fade" id="createAttr" tabindex="-1" role="dialog" aria-labelledby="createLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content" style="width: 400px;margin:0 auto">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="createLabel">新增属性</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>属性名称</label>
            <input class="form-control" type="text" v-model="attrName"/>
          </div>
          <div class="form-group">
            <label>属性定义</label>
            <input class="form-control" type="text" v-model="attrInfo"/>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="createAttr">保存</button>
        </div>
      </div>
    </div>
  </div>

  <!-- 新增关系 -->
  <div class="modal fade" id="createRelation" tabindex="-1" role="dialog" aria-labelledby="createLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content" style="width: 400px;margin:0 auto">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="createLabel">新增关系</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>关系名称</label>
            <input class="form-control" type="text" v-model="relationName"/>
          </div>
          <div class="form-group">
            <label>概念1</label>
            <input class="form-control" type="text" :value="currentConcept.name" disabled/>
          </div>
          <div class="form-group">
            <label>概念1关联属性</label>
            <select class="form-control" v-model="fromAttr">
              <option v-for="(item,index) in currentConcept.attr" :key="index" :value="item._id">{{item.name}}</option>
            </select>
          </div>
          <div class="form-group">
            <label>概念2</label>
            <select class="form-control" v-model="to">
              <option v-for="(concept,index) in item.concept" :key="index" :value="concept._id">{{concept.name}}</option>              
            </select>
          </div>
          <div class="form-group">
            <label>概念2关联属性</label>
            <select class="form-control" v-model="toAttr">
              <option v-for="(item,index) in selectAttr" :key="index" :value="item._id">{{item.name}}</option>              
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="createRelation">保存</button>
        </div>
      </div>
    </div>
  </div>

  <!-- 属性详情 -->
  <div class="modal fade" id="attributeDetail" tabindex="-1" role="dialog" aria-labelledby="createLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content" style="width: 400px;margin:0 auto">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title" id="createLabel">属性详情</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>属性名称</label>
            <input class="form-control" type="text" :value="currentAttr.name"/>
          </div>
          <div class="form-group">
            <label>属性定义</label>
            <input class="form-control" type="text" :value="currentAttr.info"/>
          </div>
          <!-- <div class="form-group">
            <label>同义词</label>
            <button class="btn btn-default btn-xs" v-for="(item, index) in currentAttr.synonym" :key="index">{{item}}</button>
            <button class="btn btn-default btn-xs">+</button>
            <input class="form-control" type="text" v-model="synonymWord" />
          </div> -->
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="editAttribute">保存</button>
        </div>
      </div>
    </div>
  </div>

   

</div>
</template>
<script>
import { getPath } from "../../../util/index";
// import { CommonConcept } from "@/components/common/CommonConcept";
import CommonAlert from "@/components/common/CommonAlert";
export default {
  name: "GraphContent",
  components: {
    CommonAlert
  },
  data() {
    return {
      item: {},

      conceptName: "",
      conceptInfo: "",

      attrName: "",
      attrInfo: "",

      currentIndex: 0,
      currentType: "",
      currentConcept: {},
      currentAttr: {},
      currentEntity: [],

      relationName: "",
      fromAttr: "",
      to: "",
      toAttr: "",

      // synonymWord: "",

      show: false,
      error: false,
      info: ""
    };
  },
  mounted() {
    this.fetch(this.currentIndex);
  },
  methods: {
    fetch: function(index) {
      const graphId = getPath(window.location.href);
      this.$http.get(`${this.$store.state.basicUrl}/graph/${graphId}`).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              this.item = data;
              if (data.concept.length > 0) {
                this.currentType = "concept";
                this.currentConcept = data.concept[index];
              }
              console.log(data);
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
    createConcept: function() {
      const data = {
        graphId: this.item._id,
        name: this.conceptName,
        info: this.conceptInfo
      };
      this.$http
        .post(`${this.$store.state.basicUrl}/graph/createConcept`, data)
        .then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              const { result, data } = body;
              this.show = true;
              this.info = data.info;
              if (result === "success") {
                this.fetch(this.currentIndex);
                this.conceptName = "";
                this.conceptInfo = "";
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
    createAttr: function() {
      const data = {
        conceptId: this.currentConcept._id,
        name: this.attrName,
        info: this.attrInfo
      };
      console.log(data);
      this.$http
        .post(`${this.$store.state.basicUrl}/graph/createAttr`, data)
        .then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              const { result, data } = body;
              this.show = true;
              this.info = data.info;
              if (result === "success") {
                this.fetch(this.currentIndex);
                this.attrName = "";
                this.attrInfo = "";
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
    createRelation: function() {
      const data = {
        name: this.relationName,
        from: this.currentConcept._id,
        fromAttr: this.fromAttr,
        to: this.to,
        toAttr: this.toAttr
      };
      this.$http
        .post(`${this.$store.state.basicUrl}/graph/createRelation`, data)
        .then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              const { result, data } = body;
              this.show = true;
              this.info = data.info;
              if (result === "success") {
                this.fetch(this.currentIndex);
                this.relationName = "";
                this.fromAttr = "";
                this.to = "";
                this.toAttr = "";
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
    showConceptDetail(index) {
      this.currentType = "concept";
      this.currentIndex = index;
      this.currentConcept = this.item.concept[index];
    },
    findConcept(id) {
      if (this.item.concept) {
        for (let i = 0; i < this.item.concept.length; i++) {
          if (this.item.concept[i]._id === id) {
            return this.item.concept[i];
          }
        }
      }
    },
    editConcept() {
      const data = {
        id: this.currentConcept._id,
        name: document.getElementById("conceptName").value,
        info: document.getElementById("conceptInfo").value
      };
      this.$http
        .post(`${this.$store.state.basicUrl}/graph/editConcept`, data)
        .then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              const { result, data } = body;
              this.show = true;
              this.info = data.info;
              if (result === "success") {
                this.fetch(this.currentIndex);
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
    editAttribute() {
      console.log("save attribute edit!");
    },
    delConcept() {
      const data = {
        id: this.currentConcept._id,
        graphId: getPath(window.location.href)
      };
      this.$http
        .post(`${this.$store.state.basicUrl}/graph/delConcept`, data)
        .then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              const { result, data } = body;
              this.show = true;
              this.info = data.info;
              if (result === "success") {
                this.fetch(0);
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
    delAttribute(id) {
      const data = {
        id,
        conceptId: this.currentConcept._id
      };
      this.$http.post(`${this.$store.state.basicUrl}/graph/delAttr`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            this.show = true;
            this.info = data.info;
            if (result === "success") {
              this.fetch(0);
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
    toggleShow(type) {
      this.currentType = type;
      if (type === "entity") {
        let url = "";
        if (this.currentConcept.name === "企业") {
          url = this.$store.state.getAllCompanyUrl;
        } else if (this.currentConcept.name === "技术链") {
          url = this.$store.state.getAllTechUrl;
        } else if (this.currentConcept.name === "新闻") {
          url = this.$store.state.getAllNewsUrl;
        }
        this.$http.get(url).then(
          response => {
            const { status, ok, body } = response;
            if (status === 200 && ok) {
              this.currentEntity = body;
            } else {
              this.show = true;
              this.error = true;
              this.info = "获取实体信息失败，请重试！";
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
    }
  },
  computed: {
    selectAttr: function() {
      if (this.findConcept(this.to)) {
        return this.findConcept(this.to).attr;
      }
      return [];
    }
  }
};
</script>
<style scoped>
.skin-blue .sidebar-menu > li > .treeview-menu {
  background-color: #ecf0f5;
  transition: all ease 0.3s;
}
.skin-blue .sidebar-menu > li > .treeview-menu > li > a:hover {
  color: #3c8dbc;
}
.skin-blue .sidebar-menu > li:hover > a,
.skin-blue .sidebar-menu > li.active > a,
.skin-blue .sidebar-menu > li.menu-open > a {
  background-color: #d2d6de;
  color: #3c8dbc;
}
.relation-wrap {
  font-size: 12px;
  margin: 10px 0;
}
.relation-wrap button {
  margin-left: 10px;
  font-size: 10px;
}
.graph-title,
.graph-detail {
  height: 40px;
  line-height: 40px;
  color: #fff;
  padding: 0 10px;
}
.graph-title {
  background-color: #3c8dbc;
}
.graph-detail {
  position: relative;
  color: #000;
  background-color: #ecf0f5;
  transition: all ease 0.3s;
  cursor: pointer;
}
.graph-detail:hover,
.graph-detail.active {
  color: #fff;
  background-color: rgba(60, 141, 188, 0.5);
}
.graph-detail p {
  margin-left: 20px;
  cursor: pointer;
}
.graph-title i,
.graph-detail i {
  position: absolute;
  right: 10px;
  top: 0;
  line-height: 40px;
  cursor: pointer;
}
.graph-title i {
  right: 25px;
}
.tab span {
  padding: 5px;
  background-color: #e7e7e7;
  color: #000;
  cursor: pointer;
  transition: all ease 0.3s;
}
.tab span:nth-child(1) {
  margin-right: -5px;
}
.tab .active {
  background-color: #3c8dbc;
  color: #fff;
}
</style>
