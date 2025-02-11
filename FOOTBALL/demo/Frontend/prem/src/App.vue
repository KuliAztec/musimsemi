<script setup lang="ts">
import { onMounted } from 'vue';
import { RouterLink, RouterView } from 'vue-router';

onMounted(() => {
  console.log('Vue app mounted successfully on port 5173');
});
</script>

<template>
  <div class="app-container">
    <nav>
      <router-link to="/">Lobby</router-link> |
      <router-link to="/position">Players by Position</router-link>
    </nav>
    <router-view />
  </div>
</template>

<style scoped>
.app-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

nav {
  padding: 20px;
  text-align: center;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  text-decoration: none;
  margin: 0 10px;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
