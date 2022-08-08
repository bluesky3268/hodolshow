import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },

  // vue.config.js 에서도 가능
  server:{
    proxy:{
      "/hyunbennylog-api/posts": {
        target: "http://localhost:8080",
        rewrite: (path) => path.replace(/^\/hyunbennylog-api/, ""),
      }
    },
  }
});
