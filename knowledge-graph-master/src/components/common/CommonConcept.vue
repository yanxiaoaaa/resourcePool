<template>
  <div class="col-xs-9">
    <div class="box box-primary" style="min-height: 500px">
      <div class="box-header">
        <h3 class="box-title">{{name}}<span class="badge bg-light-blue" style="margin-left:5px;vertical-align:top">概念</span></h3>
      </div>
      <div class="box-body table-responsive no-padding">
        <!-- form start -->
        <form role="form">
          <div class="box-body">
            <div class="form-group">
              <label for="exampleInputEmail1">名称</label>
              <input type="text" class="form-control" id="exampleInputEmail1" value="concept.name">
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">唯一标识</label>
              <input type="text" class="form-control" id="exampleInputPassword1" value="concept._id">
            </div>
            <div class="form-group">
              <label for="exampleInputFile">描述</label>
              <textarea class="form-control" style="text-align:left" value="concept.info"></textarea>
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">拥有的属性信息</label>
              <table class="table table-bordered table-striped">
                <tbody>
                  <tr>
                    <td>序号</td>
                    <td>属性id</td>
                    <td>属性名称</td>
                    <td>操作</td>
                  </tr>
                  <tr v-for="(item,index) in attrbute" :key="index">
                    <td>{{index+1}}</td>
                    <td>{{item._id}}</td>
                    <td>{{item.name}}</td>
                    <td>
                      <button class="btn btn-primary">查看</button>
                    </td>
                  </tr>
                </tbody>
            </table>
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">拥有的关系信息</label>
              <p class="relation-wrap">
                <span>父概念</span>
                <button class="btn btn-default btn-xs">需求与用例分析</button>
                <button class="btn btn-default btn-xs">+</button>                                                    
              </p>
              <p class="relation-wrap">
                <span>子概念</span>
                <button class="btn btn-default btn-xs">+</button>                          
              </p>
              <p class="relation-wrap">
                <span>关联概念</span>
                <button class="btn btn-default btn-xs">+</button>                                                    
              </p>
            </div>
          </div>
          <!-- /.box-body -->

          <div class="box-footer" style="text-align:center">
            <button class="btn btn-primary">保存</button>
            <button class="btn btn-default">关闭</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "CommonConcept",
  props: ["name"]
};
</script>
<style scoped>
</style>