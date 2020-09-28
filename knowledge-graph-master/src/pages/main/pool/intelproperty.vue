<template>
  <div class="wrapper" style="height: auto; min-height: 100%;">
    <common-header></common-header>
    <common-sidebar></common-sidebar>
    <intelproperty></intelproperty>
  </div>
</template>
<script>
import CommonHeader from "@/components/common/CommonHeader";
import CommonSidebar from "@/components/common/CommonSidebar";
import Intelproperty from "@/components/main/pool/Intelproperty";
export default {
  components: {
    CommonHeader,
    CommonSidebar,
    Intelproperty
  }
};
</script>

<style scoped>

</style>

