<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";

const posts = ref([]);

axios.get("/hyunbennylog-api/posts?page=1&size=5")
.then((response) =>{
  response.data.forEach((post : any) => {
    posts.value.push(post);
  })
})
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div>
        {{post.title}}
      </div>
      <div>
        {{post.content}}
      </div>
    </li>
  </ul>
</template>
<style>
li{
  margin-bottom: 1rem;
}
li:last-child {
  margin-bottom: 0;
}
</style>