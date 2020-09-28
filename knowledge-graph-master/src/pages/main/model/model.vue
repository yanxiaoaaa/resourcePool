<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <model-table></model-table>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import ModelTable from "@/components/main/model/ModelTable";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    ModelTable
  }
};
</script>

<style scoped>

</style>

