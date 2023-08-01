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
    params: {postId: props.postId}
  });
};
</script>

<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>

      <div class="sub d-flex">
        <div class="category">부제목</div>
        <div class="regDate">2023-08-01</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ post.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: hsla(160, 100%, 37%, 1);
  margin: 0;
}

.content {
  font-size: 0.95rem;
  margin-top: 8px;
  color: #323131;
  white-space: break-spaces;
  line-height: 1.0;
}

.sub {
  //margin-top: 4px;
  font-size: 0.78rem;
  color: #6b6b6b;
  .regDate {
    margin-left: 0.4rem;
    color: #6b6b6b;
  }
}
</style>