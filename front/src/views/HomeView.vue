<script setup lang="ts">
import axios from "axios";
import {useDialog} from "element-plus";
import {ref} from "vue";

const posts:any = ref([]);

axios.get("/api/posts?page=1&size=3").then((res) => {
  res.data.forEach((result: any) => {
    posts.value.push(result);
  });
});
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="posts.id">
      <div>{{post.title}}</div>
      <div>{{post.content}}</div>
    </li>
  </ul>
</template>
