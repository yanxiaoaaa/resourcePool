<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <task-content></task-content>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import TaskContent from "@/components/main/task/taskContent";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    TaskContent
  }
};
</script>

<style scoped>
</style>

