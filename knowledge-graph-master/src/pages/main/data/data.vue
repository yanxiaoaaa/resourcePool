<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <data-table></data-table>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import DataTable from "@/components/main/data/DataTable";

export default {
  components: {
    CommonHeader,
    CommonSidebar,
    DataTable
  }
};
</script>

<style scoped>

</style>

