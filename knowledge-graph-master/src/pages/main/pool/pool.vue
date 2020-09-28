<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <pool></pool>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import Pool from "@/components/main/pool/Pool";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    Pool
  }
};
</script>

<style scoped>

</style>

