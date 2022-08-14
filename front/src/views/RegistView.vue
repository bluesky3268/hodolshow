<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const title = ref("");
const content = ref("");

const router = useRouter();

const register = function(){
  // axios.post("")
  axios.post("/hyunbennylog-api/posts", {
    title: title.value,
    content: content.value
  })
      // 글 작성 성공 후 홈으로 이동
  .then(() => {
    router.replace({name: "home"}); // 글 작성 후 홈으로 갔을 때 뒤로가기 하면 다시 글 등록화면으로 가지는 것을 막기 위해서 push가 아니라 replace를 주로 사용함
  })
}

</script>

<template>
  <h1>등록화면</h1>
  <div>
    <el-input v-model="title" type="text" placeholder="제목을 입력해주세요."/>
  </div>

  <div class="mt-2">
    <el-input v-model="content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2">
    <div class="d-flex justify-content-end">
      <el-button type="primary" @click="register()">등록</el-button>
    </div>
  </div>
</template>

<style>

</style>