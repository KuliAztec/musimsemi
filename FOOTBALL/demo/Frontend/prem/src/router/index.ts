import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import Lobby from '../components/Lobby.vue';
import PlayerStats from '../components/PlayerStats.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Lobby',
    component: Lobby
  },
  {
    path: '/player-stats',
    name: 'PlayerStats',
    component: PlayerStats
  },
  {
    path: '/player-stats/:team',
    name: 'PlayerStats',
    component: PlayerStats
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;