<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  },
});

const post = ref({
  id: 0,
  title : "",
  content : "",
});

onMounted(() => {
 axios.get(`/hyunbennylog-api/posts/${props.postId}`)
  .then((response) => {
    post.value = response.data;
  });
});
</script>
<template>
  <h2>제목</h2>
  <div>{{post.title}}</div>
  <h2>내용</h2>
  <div>{{post.content}}</div>
</template>


<style scoped>

</style>