<template>
  <el-header class="header">
<!--            <RouterLink to="/">Home</RouterLink>-->
<!--            <RouterLink to="/regist">글 등록</RouterLink>-->

    <el-menu mode="horizontal" router>
      <el-menu-item index="/">HOME</el-menu-item>
      <el-menu-item index="/regist">글 등록</el-menu-item>
    </el-menu>
  </el-header>
</template>
<script setup lang="ts"></script>

<style scoped>
.header{
  padding: 0px;
  height: 60px;
}
</style>