<template>
<div class="content-wrapper">
  <section class="content">
    <div class="row"> 
      <div class="col-xs-12">
        <div class="box" style="min-height:350px">
          <div class="box-header">
            <h3 class="box-title">
              <span class="badge bg-green">图表信息</span>
            </h3>
            
            <div class="box-tools pull-right">
              <a class="btn btn-box-tool" href="#/modeldetail">跳至信息查询</a>
            </div>
            
            <div class="box-body">
              <div class="pull-left" style="margin-bottom:10px">
                <label class="pull-left">可选字段</label>
                <select class="form-control" v-model="RecordName">
                  <option v-for="record in records" >{{record}}</option>
                </select>
              </div>
              <div>
                  <button class="btn btn-success pull-right" style="margin:10px" @click="find_group">确认</button>            
              </div>
              <section class="content" style="min-height:450px">
                <div class="row">
                  <div class="col-xs-12">
                    <div class="box">
                      <div id="ec3" style="width:100%;height:100%;margin-top:10px;min-height:450px"></div>
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
      show: false,
      records: [],
      result: []
    };
  },
  mounted: function(){
    this.getcolumns();
    this.draw_init();
  },
  methods: {
    find_group: function(){
      var data = {
        title: this.$store.state.pagename[this.$store.state.page],
        record: this.RecordName
      };
      this.$http.post(`http://localhost:8000/ResourcePool/findByGroup`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {

            var records=body.records;
            var values=body.values;
            var record=data.title;
            var myChart = echarts.init(document.getElementById('ec3'));
            myChart.clear();
            myChart.setOption({
              title: {
                textStyle: {
                  color: '#000'
                },
                text: '信息统计',
                subtext: record+'数据',
                left: 'center'
              },
              tooltip : {
                trigger: 'item',
                formatter: "{a}<br/>{b} : {c} ({d}%)"
              },
              legend: {
                textStyle: {
                  color: '#000'
                },
                bottom: 0,
                left: 'center',
                data: records
              },
              series : [
                {
                  name: this.RecordName,
                  type: 'pie',
                  radius : '70%',
                  center: ['50%', '50%'],
                  selectedMode: 'single',
                  data: values,
                  itemStyle: {
                    emphasis: {
                      shadowBlur: 10,
                      shadowOffsetX: 0,
                      shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                  }
                }
              ]
            });
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
    },
    draw_init: function(){
      var mydata = [
        {name: '北京',value: '8115' },{name: '天津',value: '649' },
        {name: '上海',value: '2349' },{name: '重庆',value: '808' },
        {name: '河北',value: '371' },{name: '河南',value: '3159' },
        {name: '内蒙古',value: '0' },{name: '辽宁',value: '623' },
        {name: '黑龙江',value: '0' },{name: '湖南',value: '872' },
        {name: '安徽',value: '1953' },{name: '山东',value: '1689' },
        {name: '新疆',value: '0' },{name: '江苏',value: '3824' },
        {name: '浙江',value: '2167' },{name: '江西',value: '399' },
        {name: '湖北',value: '1674' },{name: '广西',value: '293' },
        {name: '甘肃',value: '101' },{name: '山西',value: '154' },
        {name: '云南',value: '272' },{name: '陕西',value: '971' },
        {name: '吉林',value: '184' },{name: '福建',value: '968' },
        {name: '贵州',value: '223' },{name: '广东',value: '11564' },
        {name: '青海',value: '0' },{name: '西藏',value: '0' },
        {name: '四川',value: '1851' },{name: '宁夏',value: '0' },
        {name: '海南',value: '0' },{name: '台湾',value: '213' },
        {name: '香港',value: '0' },{name: '澳门',value: '0' },{name: '南海诸岛',value: '0'}
      ];

      var myChart = echarts.init(document.getElementById('ec3'));
        var option = {
          title: {
            textStyle: {
              color: '#000'
            },
            text: '国内专利信息',
            subtext: '',
            x:'center'
          },
          tooltip : {
            trigger: 'item',
            formatter: "{a}<br/>{b} : {c} "
          },
                          
          visualMap: {
            show : true,
            x: 'left',
            y: 'center',
            splitList: [
              {start:400},
              {start: 200, end: 400},
              {start: 100, end: 200},{start: 0, end: 100},
            ],
            textStyle: {
              color: '#000'
            },
            color: ['#5475f5', '#e6ac53', '#9fb5ea','#74e2ca']
          },

          series: [{
            zoom:0.9,
            name: '数据',
            type: 'map',
            mapType: 'china',
            roam: true,
            label: {
              normal: {
                show: true  
              },
            emphasis: {
              show: false
            }
          },
          data:mydata  
        }]
      };
      myChart.setOption(option);
      
      var categories = [];
    for (var i = 0; i < 2; i++) {
        categories[i] = {
            name: '类目' + i
        };
    }
      var option2 = {
        // 图的标题
        title: {
            text: '知识图谱'
        },
        // 提示框的配置
        tooltip: {
            formatter: function (x) {
                return x.data.des;
            }
        },
        // 工具箱
        toolbox: {
            // 显示工具箱
            show: true,
            feature: {
                mark: {
                    show: true
                },
                // 还原
                restore: {
                    show: true
                },
                // 保存为图片
                saveAsImage: {
                    show: true
                }
            }
        },
        legend: [{
            // selectedMode: 'single',
            data: categories.map(function (a) {
                return a.name;
            })
        }],
        series: [{
            type: 'graph', // 类型:关系图
            layout: 'force', //图的布局，类型为力导图
            symbolSize: 40, // 调整节点的大小
            roam: true, // 是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移,可以设置成 'scale' 或者 'move'。设置成 true 为都开启
            edgeSymbol: ['circle', 'arrow'],
            edgeSymbolSize: [2, 10],
            edgeLabel: {
                normal: {
                    textStyle: {
                        fontSize: 20
                    }
                }
            },
            force: {
                repulsion: 2500,
                edgeLength: [10, 50]
            },
            draggable: true,
            lineStyle: {
                normal: {
                    width: 2,
                    color: '#4b565b',
                }
            },
            edgeLabel: {
                normal: {
                    show: true,
                    formatter: function (x) {
                        return x.data.name;
                    }
                }
            },
            label: {
                normal: {
                    show: true,
                    textStyle: {}
                }
            },
 
            // 数据
            data: [{
                name: 'node01',
                des: 'nodedes01',
                symbolSize: 70,
                category: 0,
            }, {
                name: 'node02',
                des: 'nodedes02',
                symbolSize: 50,
                category: 1,
            }, {
                name: 'node03',
                des: 'nodedes3',
                symbolSize: 50,
                category: 1,
            }, {
                name: 'node04',
                des: 'nodedes04',
                symbolSize: 50,
                category: 1,
            }, {
                name: 'node05',
                des: 'nodedes05',
                symbolSize: 50,
                category: 1,
            }],
            links: [{
                source: 'node01',
                target: 'node02',
                name: 'link01',
                des: 'link01des'
            }, {
                source: 'node01',
                target: 'node03',
                name: 'link02',
                des: 'link02des'
            }, {
                source: 'node01',
                target: 'node04',
                name: 'link03',
                des: 'link03des'
            }, {
                source: 'node01',
                target: 'node05',
                name: 'link04',
                des: 'link05des'
            }],
            categories: categories,
        }]
    };
    // myChart.setOption(option2);
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
