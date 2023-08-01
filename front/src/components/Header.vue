<template class="header">
  <el-header>
    <el-menu mode="horizontal">
      <el-menu-item index="1">
        <RouterLink to="/">HOME</RouterLink>
      </el-menu-item>
      <el-menu-item index="2">
        <RouterLink to="/write">글작성</RouterLink>
      </el-menu-item>
    </el-menu>
  </el-header>
</template>

<script setup lang="ts">
</script>

<style scoped>
body {
  margin: 0;
  height: 100%;
}
</style>