<template>
  <div class="player-stats-grid-container">
    <h1>{{ team }} Players (Square Grid View)</h1>

    <button @click="navigateToTable">View All Stats</button>
    <button @click="getPlayersByTeam">Refresh</button>
    <div v-if="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>

    
    <div v-if="players.length > 0">
      <div v-for="group in orderedGroups" :key="group.position" class="position-group">
        <h2>{{ group.position }}</h2>
        <div class="grid-container">
          <div 
            v-for="player in group.players" 
            :key="player.name" 
            class="grid-item"
            @click="selectPlayer(player)"
          >
            {{ player.name }}
          </div>
        </div>
      </div>
    </div>

    <!-- Modal for player details -->
    <div v-if="selectedPlayer" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <h2>{{ selectedPlayer.name }}</h2>
        <p><strong>Position:</strong> {{ selectedPlayer.position }}</p>
        <p><strong>Nation:</strong> {{ selectedPlayer.nation }}</p>
        <p><strong>Age:</strong> {{ selectedPlayer.age }}</p>
        <p><strong>Matches:</strong> {{ selectedPlayer.match_Played }}</p>
        <p><strong>Starts:</strong> {{ selectedPlayer.starts }}</p>
        <p><strong>Minutes:</strong> {{ selectedPlayer.minutes }}</p>
        <p><strong>Goals:</strong> {{ selectedPlayer.goals }}</p>
        <p><strong>Assists:</strong> {{ selectedPlayer.assist }}</p>
        <p><strong>Penalties:</strong> {{ selectedPlayer.penalties }}</p>
        <p><strong>Yellow Cards:</strong> {{ selectedPlayer.cardYellow }}</p>
        <p><strong>Red Cards:</strong> {{ selectedPlayer.cardRed }}</p>
        <p><strong>xG:</strong> {{ selectedPlayer.xG }}</p>
        <p><strong>xAG:</strong> {{ selectedPlayer.xAG }}</p>
        <button @click="closeModal">Close</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const team = route.params.team
const players = ref([])
const loading = ref(false)
const error = ref(null)
const selectedPlayer = ref(null)

const api = axios.create({
    baseURL: 'http://localhost:8080/api'
})

const normalizeTeamName = (teamName) => {
    const specialCases = {
        'Brighton': 'Brighton-and-Hove-Albion',
        'Brighton & Hove Albion': 'Brighton-and-Hove-Albion',
        'Wolverhampton': 'Wolverhampton-Wanderers'
    }
    if (specialCases[teamName]) {
        return specialCases[teamName]
    }
    return teamName.replace(/\s+/g, '-')
}

const getPlayersByTeam = async () => {
    loading.value = true
    error.value = null
    try {
        const formattedTeam = normalizeTeamName(team)
        const encodedTeam = encodeURIComponent(formattedTeam)
        const response = await api.get(`/players/team/${encodedTeam}`)
        players.value = response.data || []
    } catch (err) {
        console.error('API Error:', err)
        error.value = `Error fetching players for ${team}. Please check the team name format.`
        players.value = []
    } finally {
        loading.value = false
    }
}

const currentSort = ref({
    key: 'name',
    isAsc: true
})

const sortedPlayers = computed(() => {
    const sorted = [...players.value]
    return sorted.sort((a, b) => {
        const key = currentSort.value.key
        const aVal = a[key]
        const bVal = b[key]
        
        if (key === 'position') {
            const posOrder = { 'GK': 0, 'DF': 1, 'MF': 2, 'FW': 3 }
            const aOrder = posOrder[aVal] ?? 999
            const bOrder = posOrder[bVal] ?? 999
            return currentSort.value.isAsc ? aOrder - bOrder : bOrder - aOrder
        }
        
        if (typeof aVal === 'number' && typeof bVal === 'number') {
            return currentSort.value.isAsc ? aVal - bVal : bVal - aVal
        }
        
        const aStr = String(aVal).toLowerCase()
        const bStr = String(bVal).toLowerCase()
        return currentSort.value.isAsc 
            ? aStr.localeCompare(bStr)
            : bStr.localeCompare(aStr)
    })
})

// Group sorted players by position
const groupedPlayers = computed(() => {
  return sortedPlayers.value.reduce((groups, player) => {
    // Ensure only expected positions are grouped
    const pos = player.position;
    if (['GK', 'DF', 'MF', 'FW'].includes(pos)) {
      groups[pos] = groups[pos] || [];
      groups[pos].push(player);
    }
    return groups;
  }, {});
})

// Enforce fixed group order using an array
const orderedGroups = computed(() => {
  const order = ['GK', 'DF', 'MF', 'FW'];
  return order.map(pos => ({
    position: pos,
    players: groupedPlayers.value[pos] || []
  }));
})

const sort = (key) => {
    if (currentSort.value.key === key) {
        currentSort.value.isAsc = !currentSort.value.isAsc
    } else {
        currentSort.value.key = key
        currentSort.value.isAsc = true
    }
}

const selectPlayer = (player) => {
    selectedPlayer.value = player
}

const closeModal = () => {
    selectedPlayer.value = null
}

// Add a method to navigate to the table view
const navigateToTable = () => {
  router.push(`/team/${team}`)
}

onMounted(() => {
    getPlayersByTeam()
})
</script>

<style scoped>
.player-stats-grid-container {
    padding: 20px;
    color: #fff;
}
button {
    margin-bottom: 20px;
}
/* Updated grid with square items */
.grid-container {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
    gap: 20px;
}

.grid-item {
    background-color: #262626;
    border: 1px solid #444;
    border-radius: 4px;
    width: 150px;
    height: 150px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    user-select: none;
}

/* Updated Modal styles for vertical layout without slider */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0,0,0,0.7);
    display: flex;
    align-items: center;
    justify-content: center;
}

.modal-content {
    background-color: #2c2c2c;
    padding: 20px;
    border-radius: 4px;
    max-width: 800px; /* increased modal width */
    width: 90%;
    display: flex;
    flex-wrap: wrap;         /* allow wrapping */
    gap: 20px;
    max-height: none;        /* remove fixed height */
    overflow: visible;
}

/* Make h2 and button full-width */
.modal-content h2 {
    flex: 1 0 100%;
}
.modal-content button {
    flex: 1 0 100%;
    margin-top: 10px;
}

/* Split paragraphs into two columns */
.modal-content p {
    flex: 0 0 calc(50% - 10px);
    margin: 0;
}

.error {
    color: red;
}
.position-group {
    margin-bottom: 40px;
}

.position-group h2 {
    margin-bottom: 10px;
}
</style>
