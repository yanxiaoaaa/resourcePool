<template>
<div class="content-wrapper" style="min-height:960px;background-color:#A6FFFF">
 <div v-for="(item,index) in items" :key="index" class="search-result-single" style="background-color:#F0F0F0;max-height:180px;margin-top:3px">
    <div class="left-item ">
      <div class="lazy-img -alias -bg5">
        <div class="logo-text -l4 -w88 -bg2">
          <span class="text">知识产权</span>
        </div>
      </div>
      <div class="claim_icon_ok">
        <img class="img" src="https://static.tianyancha.com/web-require-js/themes/18blue/images/claim-company-pkg-search-0.png">
      </div>
    </div>
    <div class="content">
      <div class="header">
        <a class="name " tyc-event-click tyc-event-ch="CompanySearch.Company" href="https://www.tianyancha.com/company/2944069413" target='_blank'>{{item.name}}</a>
        <div class="tag-common -normal-bg">{{item.state}}</div>
      </div>
      <div class="info row text-ellipsis">
        <div class="title -wider -narrow text-ellipsis" style="color:#999">人员规模：
          <span title="张闽生" style="color:#999">{{item.staffSize}}</span>
        </div>
        <div class="title -narrow text-ellipsis" style="color:#999">注册资本：
          <span title="200000万人民币" style="color:#999">{{item.registerCapital}}</span>
        </div>
        <div class="title -narrow text-ellipsis" style="color:#999">成立日期：
          <span title="2016-09-18 00:00:00.0" style="color:#999">{{item.establishmentDate}}</span>
        </div>
      </div>
      <div class="contact row ">
        <div class="col">
          <span class="label" style="color:#999">电话：</span>
          <span style="color:#999">{{item.telephone}}</span>
        </div>
        <div class="col">
          <span class="label" style="color:#999">邮箱：</span>
          <span style="color:#999">{{item.email}}</span>
        </div>
      </div>
      <div class="match row text-ellipsis">
        <i class="tic tic-match"></i>
        <span class="label" style="color:#999">经营范围：</span>
        <span style="color:#999">{{item.businessScope}}</span>
      </div>
    </div>
  </div>
</div>

</template>
<script>
import { format, getPath } from "../../../util/index";


export default {
  name: "Intelproperty",
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
    this.getItems();
  },
  methods: {
    getItems: function(){
      this.$http.post(`http://localhost:8000/ResourcePool/intelproperty`).then(
        response => {
          const { status, ok, body } = response;
          if (status === 200 && ok) {
            this.items=body;
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
@charset "UTF-8";
@font-face{font-family:tic;src:url(https://cdn.tianyancha.com/web-require-js/public/fonts/tic-82bb436d57.eot);src:url(https://cdn.tianyancha.com/web-require-js/public/fonts/tic-82bb436d57.eot#iefix) format("embedded-opentype"),url(https://cdn.tianyancha.com/web-require-js/public/fonts/tic-4eaae59f34.woff) format("woff"),url(https://cdn.tianyancha.com/web-require-js/public/fonts/tic-104e9322b7.ttf) format("truetype"),url(https://cdn.tianyancha.com/web-require-js/public/fonts/tic-688c81f149.svg#tic) format("svg")}
.tic{
  font-family:tic!important;
  font-size:16px;
  font-style:normal;
  -webkit-font-smoothing:antialiased;
  -moz-osx-font-smoothing:grayscale;
  color:#ff7d18
}
.tic-match:before{
  content:"\e72e"
}
[hidden]{display:none}*{-webkit-box-sizing:border-box;box-sizing:border-box}
.text-ellipsis{
  -webkit-box-sizing:border-box;
  box-sizing:border-box;
  overflow:hidden;
  word-break:keep-all;
  white-space:nowrap;
  text-overflow:ellipsis
}
.-w88{
  width:88px;
  height:88px;
  border-radius:6px
}
.logo-text{
  display:inline-block;
  vertical-align:middle;
  width:100px;
  height:100px;
  border-radius:6px;
  text-align:center;
  overflow:hidden;
  /* background:-webkit-gradient(linear,left top,left bottom,from(#dfdadc),to(#f5f5f5)); */
  background:linear-gradient(#dfdadc,#f5f5f5);
  color:#666;line-height:100px;
  white-space:normal;font-size:44px
}
.logo-text .img{
  width:100%
}
.logo-text.-w88{
  width:88px;
  height:88px;
  border-radius:6px
}
.logo-text.-w88.-l4{
  padding:13px 14px 13px 18px;
  line-height:31px;
  font-size:24px;
  letter-spacing:4px
}
.logo-text.-bg2{
  background:#d1ae9d
}
.logo-text.-bg1,.logo-text.-bg2,.logo-text.-bg3,.logo-text.-bg4,.logo-text.-bg5,.logo-text.-bg6{
  color:#fff
}
.form-upload .frame:after{
  content:"更换图片";
  position:absolute;
  top:0;
  left:0;
  right:0;
  bottom:0;
  display:none;
  padding:10px 16px;
  background:rgba(0,0,0,.5);
  color:#fff;
  cursor:pointer
}
.tag-common{
  display:inline-block;
  vertical-align:top;
  padding:0 8px;
  border:1px solid transparent;
  line-height:1.5714285714;
  height:20px;
  border-radius:2px;
  font-size:12px;
  text-align:center;
  white-space:nowrap
}
.tag-common.-normal-bg{
  color:#10a050;
  border-color:#10a050
}
.search-result-single{
  position:relative;
  padding:24px;
  font-size:0;
  min-height:126px;
  border-bottom:1px solid #f3f3f3
}
.search-result-single .left-item{
  position:relative;
  display:inline-block;
  vertical-align:top;
  width:88px;
  margin-right:25px;
  margin-top:20px
}
.search-result-single .left-item .claim_icon_ok{
  width:118px;
  height:16px
}
.search-result-single .left-item .claim_icon_ok .img{
  position:absolute;
  top:74px;
  left:-15px;
  display:inline-block;
  width:118px
}
.search-result-single .content{
  display:inline-block;
  vertical-align:middle;
  width:720px;
  font-size:14px;
  color:#666
}
.search-result-single .header{
  margin-bottom:10px;
  line-height:24px
}
.search-result-single .header .name{
  display:inline-block;
  vertical-align:middle;
  max-width:560px;
  color:#333;
  font-size:18px;
  line-height:24px;
  font-weight:700;
  overflow:hidden;
  text-overflow:ellipsis;
  white-space:nowrap;
  -webkit-transition:color .3s ease;
  transition:color .3s ease
}
.search-result-single .header .tag-common{
  vertical-align:middle;
  margin-left:16px
}
.search-result-single .row{
  line-height:22px
}
.search-result-single .row+.row{
  margin-top:5px
}
.search-result-single .info .title{

  padding-right:24px;
  display:inline-block;
  vertical-align:top;
  font-size:14px;
  max-width:226px
}
.search-result-single .info .title.-narrow{
  max-width:200px
}
.search-result-single .contact::after,.search-result-single .contact::before{
  display:table;content:" "
}
.search-result-single .contact::after{
  clear:both
}
.search-result-single .contact .col{
  float:left
}
.search-result-single .contact .col .link-click{
  margin-left:8px
}
.search-result-single .contact .col+.col{
  margin-left:24px
}
.search-result-single .match{
  width:560px;font-size:14px
}
.search-result-single .match .tic{
  color:#999;margin-right:6px
}
.search-result-single .match .match-cname{
  display:inline-block;vertical-align:middle;max-width:210px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap
}
.search-result-single .site{
  position:absolute;text-align:right;right:24px;top:27px;line-height:20px;font-size:14px
}
.search-result-single .score{
  position:absolute;text-align:right;right:24px;top:59px;line-height:26px;font-size:14px;vertical-align:bottom;color:#0084ff
}
.search-result-single .score .score-num{
  font-size:20px
}
.search-result-single .score .score-fen{
  font-size:12px
}
.search-result-single .score .score-claim-hover{
  border-radius:2px;
  position:absolute;
  left:-247px;
  bottom:-235px;
  z-index:2;display:none;
  width:308px;
  height:221px;
  -webkit-box-shadow:0 1px 4px rgba(96,96,96,.5);
  box-shadow:0 1px 4px rgba(96,96,96,.5);
  text-align:center;
  background-color:#fff;
  color:#333
}
.search-result-single .score .score-claim-hover .angle{
  position:absolute;
  top:-20px;
  right:38px;
  width:20px;
  height:20px;
  overflow:hidden
}
.search-result-single .score .score-claim-hover .angle:before{
  position:absolute;
  bottom:-8px;
  right:3px;
  display:block;
  width:15px;
  height:15px;
  -webkit-transform:rotate(45deg);
  transform:rotate(45deg);
  -webkit-box-shadow:0 1px 4px rgba(96,96,96,.5);
  box-shadow:0 1px 4px rgba(96,96,96,.5);
  background:#fff;
  content:" "
}
.search-result-single .score .score-claim-hover .score-claim-title{
  margin-bottom:9px;
  font-size:18px;
  font-weight:700
}
.search-result-single .score .score-claim-hover .score-claim-content{
  width:100%;
  height:100%;
  text-align:left;
  background:url(https://cdn.tianyancha.com/web-require-js/public/images/score-claim-bg-3.png) 0 0 no-repeat;
  background-size:100% 100%
}
.search-result-single .score .score-claim-hover .score-claim-content .score-claim-text{
  font-family:Arial;
  position:relative;
  display:inline-block;
  margin-top:60px;
  margin-left:75px;
  font-size:40px;
  font-weight:700;
  line-height:52px;
  color:#0084ff
}
.search-result-single .score .score-claim-hover .score-claim-content .score-claim-text .score-claim-text-add{
  color:#ffb40d;
  font-style:normal
}
.search-result-single .score .score-claim-hover .score-claim-click{
  position:absolute;top:156px;
  left:10px;width:288px
}
.search-result-single .score .score-claim-hover .score-claim-click .link-click{
  color:#ffb40d
}
.search-result-single .score:hover .score-claim-hover{
  display:block
}
.search-result-single .score.-score-isClaimed-nohover{
  color:#ffaf25
}
.search-result-single .-score-isClaimed{
  cursor:pointer;color:#ffaf25
}
.search-result-single .-score-isClaimed .score-num{
  color:#ffaf25
}
.search-result-single .-claim{
  color:#ffaf25
}
.search-result-single .contact-sel{
  font-size:14px;color:#ff7d18
}
.search-result-single.-hasown{
  padding-bottom:14px;border-bottom:none
}
.search-result-single.contact-warp{
  padding-left:46px
}
</style>
