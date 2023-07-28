<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";

const title = ref("");
const content = ref("");

const write = () => {

  axios.post("http://localhost:8080/posts", {title: title.value, content: content.value})
  // axios.get("http://localhost:8080/docs/index.html")

  /*try {
    await axios.post("http://localhost:8080/docs/index.html")
  } catch (error) {
    console.log(error)
  }*/
  // console.log(title.value, content.value)
}

</script>

<template>
  <div>
    <el-input
        v-model="title"
        placeholder="제목을 입력해주세요"
    />
  </div>

  <div class="mt-2">
    <el-input
        v-model="content"
        type="textarea"
        rows="15"
    />
  </div>

  <div class="mt-2">
    <el-button
        type="primary"
        @click="write()"
    >
      글작성완료
    </el-button>
  </div>
</template>

<style>

</style>