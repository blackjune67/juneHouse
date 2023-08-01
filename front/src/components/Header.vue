<template class="header">
  <el-header>
    <el-menu mode="horizontal" router>
      <el-menu-item index="/" >
        HOME
<!--        <RouterLink to="/">HOME</RouterLink>-->
      </el-menu-item>
      <el-menu-item index="/write">
        글작성
<!--        <RouterLink to="/write">글작성</RouterLink>-->
      </el-menu-item>
    </el-menu>
  </el-header>
</template>

<script setup lang="ts">
</script>

<style scoped>
body {
  margin: 0;
  height: 60px;
}
</style>