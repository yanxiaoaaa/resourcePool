<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <graph-content></graph-content>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import GraphContent from "@/components/main/graph/GraphContent";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    GraphContent
  }
};
</script>

<style scoped>

</style>

