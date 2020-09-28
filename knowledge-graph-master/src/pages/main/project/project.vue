<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <project-content></project-content>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import ProjectContent from "@/components/main/project/ProjectContent";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    ProjectContent
  }
};
</script>

<style scoped>

</style>

