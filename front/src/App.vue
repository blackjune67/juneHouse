<script setup lang="ts">
import Header from "@/components/Header.vue";
import Content from "@/components/Content.vue";
</script>

<template>
  <div class="common-layout">
    <el-container>
      <Header/>
      <Content/>
    </el-container>
  </div>
</template>

<style>
body {
  margin: 0;
  width: 100%;
  font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Pretendard Variable", Pretendard, Roboto, "Noto Sans KR", "Segoe UI", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}
</style>
