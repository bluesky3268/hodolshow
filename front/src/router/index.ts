import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import RegistView from "../views/RegistView.vue";
import ReadView from "../views/ReadView.vue";
import ModifyView from "../views/ModifyView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/regist",
      name: "regist",
      component: RegistView,
    },
    {
      path: "/read/:postId",
      name: "read",
      component: ReadView,
      props: true,
    },
    {
      path: "/modify/:postId",
      name: "modify",
      component: ModifyView,
      props: true,
    },
  ],
});

export default router;