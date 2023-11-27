<script setup lang="ts">
import axios from "axios";
import {useRouter} from "vue-router";
import {ref} from "vue";

const name = ref("");
const email = ref("");
const password = ref("");

const router = useRouter();

const signup = () => {
  axios.post("/api/auth/signup",
      {name: name.value, email: email.value, password: password.value}
  )
      .then(() => {
        router.replace({
          name: "home",
        })
      });
}
</script>
<template>
  회원가입 화면
  <div>
    <el-input
        v-model="name"
        placeholder="이름"
    />
  </div>

  <div>
    <el-input
        v-model="email"
        placeholder="이메일"
    />
  </div>

  <div>
    <el-input
        v-model="password"
        placeholder="비밀번호"
    />
  </div>

  <div>
    <el-button type="primary" @click="signup()">회원가입</el-button>
  </div>

</template>
<style scoped>

</style>