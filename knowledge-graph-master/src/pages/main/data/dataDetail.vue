<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <data-content></data-content>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import DataContent from "@/components/main/data/DataContent";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    DataContent
  }
};
</script>

<style scoped>

</style>

