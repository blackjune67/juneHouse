<script setup lang="ts">
import {onMounted} from "vue";
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
});

const post = ref({
  id: 0,
  title: "",
  content: "",
});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((res) => {
    post.value = res.data;
  });
});

const router = useRouter();

const moveToEdit = () => {
  router.push({
    name: "edit",
    params: { postId: props.postId }
  });
};
</script>

<template>
  <div>
    <h2>{{ post.title }}</h2>
    <div>{{ post.content }}</div>
  </div>
  <div>
    <el-button type="warning" @click="moveToEdit()">수정</el-button>
  </div>
</template>