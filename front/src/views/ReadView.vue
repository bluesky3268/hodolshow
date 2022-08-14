<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";

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


const moveToEdit = () => {
  router.push({name : "modify", params:{postId: props.postId}});
}
onMounted(() => {
 axios.get(`/hyunbennylog-api/posts/${props.postId}`)
  .then((response) => {
    post.value = response.data;
  });
});
</script>
<template>
  <el-row>
    <el-col>
      <h2 class="title">{{post.title}}</h2>
    </el-col>
  </el-row>
  <el-row>
    <el-col>
      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2022-08-14 18:00:00</div>
      </div>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="content">{{post.content}}</div>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>


<style scoped lang="scss">
.title{
  margin:0;
  font-size: 1.6rem;
  font-weight: 600;
  color: #3c3c3c;
}

.content{
  font-size: 1rem;
  margin-top: 20px;
  color: #5d5d5d;
  white-space: break-spaces;
  line-height: 1.5;
}

.sub{
  font-size: 0.85rem;
  margin-top: 10px;
  color: #7e7e7e;

  .regDate {
    margin-left: 10px;
    color: #6b6b6b;
  }
}
</style>