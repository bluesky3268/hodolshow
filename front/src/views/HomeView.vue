<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

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
        <!-- a태그를 사용하면 애플리케이션 전체를 다시 불러옴 -> 리소스 낭비가 심하기 때문에 router-link를 이용해서 화면만 변경-->
        <router-link :to="{name:'read', params:{postId : post.id}}">{{post.title}}</router-link>
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