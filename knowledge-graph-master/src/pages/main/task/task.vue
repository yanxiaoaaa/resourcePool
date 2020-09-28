<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <task-table></task-table>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import TaskTable from "@/components/main/task/taskTable";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    TaskTable
  }
};
</script>

<style scoped>

</style>

