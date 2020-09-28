<template>
<div class="content-wrapper" style="min-height:960px;">
  <section class="content">
    <div class="row">
      <div class="col-xs-12">
        <div class="box">
          <div class="box-header">
            <h3 class="box-title">{{data.name}}</h3>
          </div>
          <div class="box-body table-responsive">
            <!-- Info boxes -->
            <div class="row">
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                  <span class="info-box-icon bg-aqua"><i class="ion ion-ios-gear-outline"></i></span>

                  <div class="info-box-content">
                    <span class="info-box-text">数据来源</span>
                    <span class="info-box-number">{{data.source}}</span>
                  </div>
                  <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
              </div>
              <!-- /.col -->
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                  <span class="info-box-icon bg-red"><i class="fa fa-google-plus"></i></span>

                  <div class="info-box-content">
                    <span class="info-box-text">数据量</span>
                    <span class="info-box-number">{{data.num}}</span>
                  </div>
                  <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
              </div>
              <!-- /.col -->

              <!-- fix for small devices only -->
              <div class="clearfix visible-sm-block"></div>

              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                  <span class="info-box-icon bg-green"><i class="ion ion-ios-cart-outline"></i></span>

                  <div class="info-box-content">
                    <span class="info-box-text">创建时间</span>
                    <span class="info-box-number">{{data.create}}</span>
                  </div>
                  <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
              </div>
              <!-- /.col -->
              <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                  <span class="info-box-icon bg-yellow"><i class="ion ion-ios-people-outline"></i></span>

                  <div class="info-box-content">
                    <span class="info-box-text">历史操作</span>
                    <span class="info-box-number">{{data.history.length}}</span>
                  </div>
                  <!-- /.info-box-content -->
                </div>
                <!-- /.info-box -->
              </div>
              <!-- /.col -->
            </div>
            <!-- /.row -->
             <!-- Main row -->
            <div class="row">
              <!-- Left col -->
              <div class="col-md-12">
                <!-- MAP & BOX PANE -->
                <div class="box box-success">
                  <div class="box-header with-border">
                    <h3 class="box-title">数据样例（仅展示前10条数据）</h3>

                    <div class="box-tools pull-right">
                      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                      </button>
                      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                    </div>
                  </div>
                  <!-- /.box-header -->
                  <div class="box-body no-padding">
                    <div class="row">
                      <div class="col-md-12 col-sm-12">
                        <div class="pad" style="overflow-x: scroll">
                         <table class="table table-bordered table-striped" style="margin:0">
                            <tbody>
                              <tr>
                                <td>序号</td>                                    
                                <td v-for="(title,index) in data.title" :key="index">{{title}}</td>
                              </tr>
                              <tr v-for="(item,index) in data.list" :key="index">
                                <td>{{index+1}}</td>
                                <template v-for="(key,index) in item">
                                  <td v-bind:key="index">{{key}}</td>
                                </template>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </div>
                      
                    </div>
                    <!-- /.row -->
                  </div>
                  <!-- /.box-body -->
                </div>
                <!-- /.box -->
              </div>
            </div>
            
            <div class="row">
              <!-- Left col -->
              <div class="col-md-12">
                <!-- MAP & BOX PANE -->
                <div class="box box-primary">
                  <div class="box-header with-border">
                    <h3 class="box-title">历史操作记录</h3>
                    <div class="box-tools pull-right">
                      <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                      </button>
                      <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                    </div>
                  </div>
                  <!-- /.box-header -->
                  <div class="box-body">
                    <template v-if="data.history.length > 0">
                      <table class="table table-bordered table-striped" style="margin:0">
                        <tbody>
                          <tr>
                            <td>序号</td>                                    
                            <td>处理维度</td>
                            <td>操作模型</td>
                            <td>挂接图谱</td>
                            <td>处理结果</td>
                          </tr>
                          <tr v-for="(item,index) in data.history" :key="index">
                            <td>{{index}}</td>
                            <td>{{item.data}}</td>
                            <td>{{item.models}}</td>
                            <td>{{item.graphs}}</td>
                            <td>成功</td>
                          </tr>
                        </tbody>
                      </table>
                    </template>
                    <template v-else>
                      <p>您暂时还未对该数据进行操作哦～</p>
                    </template>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</div>
</template>
<script>
import { format, getPath } from "../../../util";
export default {
  name: "DataContent",
  data() {
    return {
      data: {}
    };
  },
  mounted() {
    this.fetch();
  },
  methods: {
    fetch: function() {
      const dataId = getPath(window.location.href);
      this.$http.get(`${this.$store.state.basicUrl}/data/${dataId}`).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            const { result, data } = body;
            if (result === "success") {
              this.data = data;
              console.log(this.data);
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
    }
  }
};
</script>
<style scoped>
td {
  font-size: 12px;
  white-space: nowrap;
}
.info-box-number {
  font-size: 14px;
}
</style>
