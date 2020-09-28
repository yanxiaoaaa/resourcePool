<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <expertgraph></expertgraph>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import Expertgraph from "@/components/main/pool/Expertgraph";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    Expertgraph
  }
};
</script>

<style scoped>

</style>

