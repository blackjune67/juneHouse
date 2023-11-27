<script setup lang="ts">
import {onMounted} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import {ref} from "vue";

const router = useRouter();
const email = ref('');
const password = ref('');
const loginOn = () => {
  axios.post('/api/auth/login',
      {email: email.value, password: password.value}
  ).then(() => {
    router.replace({
      name: 'home'
    })
  })
};
</script>

<template>
  <div>
    로그인 화면 TEST
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
      <el-button type="primary" @click="loginOn()">
        로그인
      </el-button>
    </div>
  </div>
</template>

<style scoped>

</style>