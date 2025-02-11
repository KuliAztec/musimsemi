import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import Lobby from '../components/Lobby.vue';
import PlayerStats from '../components/PlayerStats.vue';
import PlayerByPosition from '../components/PlayerByPosition.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Lobby',
    component: Lobby
  },
  {
    path: '/team/:team',
    name: 'PlayerStats',
    component: PlayerStats
  },
  {
    path: '/position',
    name: 'PlayerByPosition',
    component: PlayerByPosition
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;