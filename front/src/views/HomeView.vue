<script setup lang="ts">
import axios from "axios";
import {useDialog} from "element-plus";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const posts: any = ref([]);

axios.get("/api/posts?page=1&size=3").then((res) => {
  res.data.forEach((result: any) => {
    posts.value.push(result);
  });
});
</script>

<template>
  <div>
    <ul>
      <li v-for="post in posts" :key="post.id">
        <div>
          <router-link :to="{name: 'read', params: {postId: post.id}}" replace>{{ post.title }}</router-link>
        </div>
        <div>{{ post.content }}</div>
      </li>
    </ul>
  </div>
</template>
