<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <graph-table></graph-table>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import GraphTable from "@/components/main/graph/GraphTable";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    GraphTable
  }
};
</script>

<style scoped>

</style>

