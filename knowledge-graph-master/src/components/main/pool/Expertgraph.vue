<template>
<div class="content-wrapper">
  <section class="content">
    <div class="row"> 
      <div class="col-xs-12">
        <div class="box" style="min-height:350px">
          <div class="box-header">
            <div class="box-body">
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
      result: [],
      
      nameToType:{}
    };
  },
  mounted: function(){
    var myChart = echarts.init(document.getElementById('ec3'));
    this.nameToType["王斌"]="expert";
    this.draw_init("王斌","expert",this,myChart);
  },
  methods: {
    draw_init: function(newName,type,pointer,myChart){
        
        var data = {
            name:newName,
            type:type
        };

        this.$http.post(`http://localhost:8000/ResourcePool/write`, data).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
          }
        },
        response => {
          console.log("error");
        }
      )

    
    var recieve_link=[];
    var recieve_data=[];
    //   var url=`http://localhost:5100`;
    var url="/api";
      this.$http.get(url).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            pointer.nameToType={}
            for(var i=0; i<body["node0"].length;i++){
                
                var infor=body["node0"][i];
                pointer.nameToType[infor['name']]=infor['type'];
                var node={
                    name: infor['name'],
                    des: infor['type'],
                    symbolSize: 70,
                    category: 0,
                }
                recieve_data.push(node);
            }
            for(var i=0; i<body["node1"].length;i++){
                var infor=body["node1"][i];
                pointer.nameToType[infor['name']]=infor['type'];
                var node={
                    name: infor['name'],
                    des: infor['type'],
                    symbolSize: 50,
                    category: 1,
                }
                recieve_data.push(node);
            }
            for(var key in body["relation"]){
                var infor=body["relation"][key];
                var node={
                    source: infor["source"],
                    target: infor["target"],
                    name: infor["relation"],
                    des: infor["relation"]
                }
                recieve_link.push(node);
            }
        
                myChart.setOption({
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '销量',
                        data: recieve_data,
                        links: recieve_link
                    }],
                });

            // for(var i=0;i<recieve_link.length;i++){
            //     for(var key in recieve_link[i]){
            //         alert(key+" "+recieve_link[i][key]);
            //     }
            // }
          }
        },
        response => {
          console.log("error");
        }
      );  



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
            name:'hhh',
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
            data: recieve_data,
            // [{
            //     name: '王斌',
            //     des: 'expert',
            //     symbolSize: 70,
            //     category: 0,
            // }, {
            //     name: "北京化工大学",
            //     des: 'expert',
            //     symbolSize: 50,
            //     category: 1,
            // }, {
            //     name: '复旦大学',
            //     des: 'expert',
            //     symbolSize: 50,
            //     category: 1,
            // }, {
            //     name: '中国科学院沈阳自动化研究所',
            //     des: 'expert',
            //     symbolSize: 50,
            //     category: 1,
            // }, {
            //     name: '中国科学院自然科学史研究所',
            //     des: 'expert',
            //     symbolSize: 50,
            //     category: 1,
            // }],
            links: recieve_link,
            // [{
            //     source: '王斌',
            //     target: "北京化工大学",
            //     name: '工作',
            //     des: '工作'
            // }, {
            //     source: '王斌',
            //     target: '复旦大学',
            //     name: '工作',
            //     des: '工作'
            // }, {
            //     source: '王斌',
            //     target: '中国科学院沈阳自动化研究所',
            //     name: '工作',
            //     des: '工作'
            // }, {
            //     source: '王斌',
            //     target: '中国科学院自然科学史研究所',
            //     name: '工作',
            //     des: '工作'
            // }],
            categories: categories,
        }]
    };
    myChart.setOption(option2);
    myChart.on('click',function(params){
        pointer.draw_init(params.name,pointer.nameToType[params.name],pointer,myChart);
    });
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
