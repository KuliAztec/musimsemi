<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const teams = ref([
  { name: 'Arsenal', logo: 'https://resources.premierleague.com/premierleague/badges/t3.png' },
  { name: 'Aston Villa', logo: 'https://resources.premierleague.com/premierleague/badges/t7.png' },
  { name: 'Bournemouth', logo: 'https://resources.premierleague.com/premierleague/badges/t91.png' },
  { name: 'Brentford', logo: 'https://resources.premierleague.com/premierleague/badges/t94.png' },
  { name: 'Brighton', logo: 'https://resources.premierleague.com/premierleague/badges/t36.png' },
  { name: 'Chelsea', logo: 'https://resources.premierleague.com/premierleague/badges/t8.png' },
  { name: 'Crystal Palace', logo: 'https://resources.premierleague.com/premierleague/badges/t31.png' },
  { name: 'Everton', logo: 'https://resources.premierleague.com/premierleague/badges/t11.png' },
  { name: 'Fulham', logo: 'https://resources.premierleague.com/premierleague/badges/t54.png' },
  { name: 'Leeds United', logo: 'https://resources.premierleague.com/premierleague/badges/t2.png' },
  { name: 'Leicester City', logo: 'https://resources.premierleague.com/premierleague/badges/t13.png' },
  { name: 'Liverpool', logo: 'https://resources.premierleague.com/premierleague/badges/t14.png' },
  { name: 'Manchester City', logo: 'https://resources.premierleague.com/premierleague/badges/t43.png' },
  { name: 'Manchester United', logo: 'https://resources.premierleague.com/premierleague/badges/t1.png' },
  { name: 'Newcastle United', logo: 'https://resources.premierleague.com/premierleague/badges/t4.png' },
  { name: 'Nottingham Forest', logo: 'https://resources.premierleague.com/premierleague/badges/t17.png' },
  { name: 'Southampton', logo: 'https://resources.premierleague.com/premierleague/badges/t20.png' },
  { name: 'Tottenham Hotspur', logo: 'https://resources.premierleague.com/premierleague/badges/t6.png' },
  { name: 'West Ham United', logo: 'https://resources.premierleague.com/premierleague/badges/t21.png' },
  { name: 'Wolverhampton', logo: 'https://resources.premierleague.com/premierleague/badges/t39.png' }
])

const handleTeamClick = (teamName: string) => {
  router.push(`/player-stats/${teamName}`)
}

const getTeamId = (teamName: string) => {
  switch (teamName) {
    case 'Arsenal': return 1
    case 'Aston Villa': return 2
    case 'Bournemouth': return 127
    case 'Brentford': return 130
    case 'Brighton': return 36
    case 'Chelsea': return 4
    case 'Crystal Palace': return 6
    case 'Everton': return 7
    case 'Fulham': return 9
    case 'Leeds United': return 10
    case 'Leicester City': return 11
    case 'Liverpool': return 12
    case 'Manchester City': return 13
    case 'Manchester United': return 14
    case 'Newcastle United': return 23
    case 'Nottingham Forest': return 17
    case 'Southampton': return 20
    case 'Tottenham Hotspur': return 21
    case 'West Ham United': return 25
    case 'Wolverhampton': return 38
    default: return null
  }
}
</script>

<template>
  <div class="teams-container">
    <h1>Premier League Teams</h1>
    <div class="teams-grid">
      <div v-for="team in teams" :key="team.name" class="team-item" @click="handleTeamClick(team.name)">
        <img :src="team.logo" :alt="team.name" class="team-logo">
        <div class="team-name">{{ team.name }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.teams-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: center;
  color: #fff;
  margin-bottom: 2rem;
}

.teams-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  padding: 20px 0;
}

.team-item {
  padding: 20px;
  text-align: center;
  border: 1px solid #444;
  border-radius: 8px;
  background-color: #1a1a1a;
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: all 0.3s ease;
  cursor: pointer;
}

.team-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  border-color: #646cff;
}

.team-logo {
  width: 100px;
  height: 100px;
  object-fit: contain;
  margin-bottom: 15px;
  transition: transform 0.3s ease;
}

.team-item:hover .team-logo {
  transform: scale(1.1);
}

.team-name {
  font-size: 16px;
  font-weight: 500;
  color: #fff;
}

@media (max-width: 768px) {
  .teams-grid {
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
    gap: 15px;
  }

  .team-logo {
    width: 80px;
    height: 80px;
  }

  .team-name {
    font-size: 14px;
  }
}
</style>
