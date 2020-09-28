<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <company></company>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import Company from "@/components/main/pool/Company";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    Company
  }
};
</script>

<style scoped>

</style>

