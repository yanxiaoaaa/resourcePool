<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <model-content></model-content>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import ModelContent from "@/components/main/model/ModelContent";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    ModelContent
  }
};
</script>

<style scoped>

</style>

