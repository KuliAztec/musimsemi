import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import Lobby from '../components/Lobby.vue';
import PlayerStats from '../components/PlayerStats.vue';
import PlayerStatsGridlike from '../components/PlayerStatsGridlike.vue';
import PlayerByPosition from '../components/PlayerByPosition.vue';
import PlayerByName from '../components/PlayerByName.vue';
import TopStats from '../components/PlayerByTopStats.vue'; 

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
    path: '/team/:team/grid',
    name: 'PlayerStatsGridlike',
    component: PlayerStatsGridlike
  },
  {
    path: '/position',
    name: 'PlayerByPosition',
    component: PlayerByPosition
  },
  {
    path: '/search',
    name: 'PlayerByName',
    component: PlayerByName
  },
  {
    path: '/topstats',
    name: 'TopStats',
    component: TopStats // fixed route component
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;