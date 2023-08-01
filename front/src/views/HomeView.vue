<script setup lang="ts">
import axios from "axios";
import {useDialog} from "element-plus";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const posts: any = ref([]);

axios.get('/api/posts?page=1&size=5').then((res) => {
  console.log(res.data)
  res.data.forEach((result: any) => {
    posts.value.push(result);
  });
});
</script>

<template>
  <div>
    <ul>
      <li v-for="post in posts" :key="post.id">
        <div class="title">
          <router-link :to="{name: 'read', params: {postId: post.id}}" replace>{{ post.title }}</router-link>
        </div>
        <div class="content">
          {{ post.content }}
        </div>

        <div class="sub d-flex">
          <div class="category">개발</div>
          <div class="regDate">2023-08-01</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 1.8rem;
    padding: 0;

    .title {
      a {
        font-size: 1.1rem;
        //color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
        color: hsla(160, 100%, 37%, 1);
      }
    }

    .content {
      font-size: 0.95rem;
      margin-top: 8px;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 5px;
      font-size: 0.78rem;
      //color: #63696edb;
      .regDate {
        margin-left: 0.4rem;
      }
    }

  }
}
</style>