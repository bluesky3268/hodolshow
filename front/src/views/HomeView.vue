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
      <div class="title">
        <!-- a태그를 사용하면 애플리케이션 전체를 다시 불러옴 -> 리소스 낭비가 심하기 때문에 router-link를 이용해서 화면만 변경-->
        <router-link :to="{name:'read', params:{postId : post.id}}">{{post.title}}</router-link>
      </div>

      <div class="content">
        {{post.content}}
      </div>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2022-08-14</div>
      </div>

    </li>
  </ul>
</template>
<style scoped lang="scss">
ul{
  list-style: none;
  padding: 0;

  li{
    margin-bottom: 2rem;

    .title{
      a{
        font-size: 1.5rem;
        color: #3c3c3c;
        text-decoration: none;
      }

      &:hover{
        text-decoration: underline;
      }
    }

    .content{
      font-size: 0.95rem;
      margin-top: 8px;
      color: #5d5d5d;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub{
      margin-top: 8px;
      font-size: 0.8rem;

      .regDate{
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}


</style>