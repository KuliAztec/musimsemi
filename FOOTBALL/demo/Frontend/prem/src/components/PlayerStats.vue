<template>
    <div class="player-stats-container">
        <h1>{{ team }} Players</h1>
        <button @click="getPlayersByTeam">Refresh</button>

        <div v-if="loading">Loading...</div>
        <div v-if="error" class="error">{{ error }}</div>

        <table v-if="players.length > 0" class="stats-table">
            <thead>
                <tr>
                    <th @click="sort('name')">
                        Name
                        <span class="sort-indicator">{{ getSortIndicator('name') }}</span>
                    </th>
                    <th @click="sort('position')">
                        Position
                        <span class="sort-indicator">{{ getSortIndicator('position') }}</span>
                    </th>
                    <th @click="sort('nation')">
                        Nation
                        <span class="sort-indicator">{{ getSortIndicator('nation') }}</span>
                    </th>
                    <th @click="sort('age')">
                        Age
                        <span class="sort-indicator">{{ getSortIndicator('age') }}</span>
                    </th>
                    <th @click="sort('match_Played')">
                        Matches
                        <span class="sort-indicator">{{ getSortIndicator('match_Played') }}</span>
                    </th>
                    <th @click="sort('starts')">
                        Starts
                        <span class="sort-indicator">{{ getSortIndicator('starts') }}</span>
                    </th>
                    <th @click="sort('minutes')">
                        Minutes
                        <span class="sort-indicator">{{ getSortIndicator('minutes') }}</span>
                    </th>
                    <th @click="sort('goals')">
                        Goals
                        <span class="sort-indicator">{{ getSortIndicator('goals') }}</span>
                    </th>
                    <th @click="sort('assist')">
                        Assists
                        <span class="sort-indicator">{{ getSortIndicator('assist') }}</span>
                    </th>
                    <th @click="sort('penalties')">
                        Penalties
                        <span class="sort-indicator">{{ getSortIndicator('penalties') }}</span>
                    </th>
                    <th @click="sort('cardYellow')">
                        Yellow Cards
                        <span class="sort-indicator">{{ getSortIndicator('cardYellow') }}</span>
                    </th>
                    <th @click="sort('cardRed')">
                        Red Cards
                        <span class="sort-indicator">{{ getSortIndicator('cardRed') }}</span>
                    </th>
                    <th @click="sort('xG')">
                        xG
                        <span class="sort-indicator">{{ getSortIndicator('xG') }}</span>
                    </th>
                    <th @click="sort('xAG')">
                        xAG
                        <span class="sort-indicator">{{ getSortIndicator('xAG') }}</span>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="player in sortedPlayers" :key="player.name">
                    <td>{{ player.name }}</td>
                    <td>{{ player.position }}</td>
                    <td>{{ player.nation }}</td>
                    <td>{{ player.age }}</td>
                    <td>{{ player.match_Played }}</td>
                    <td>{{ player.starts }}</td>
                    <td>{{ player.minutes }}</td>
                    <td>{{ player.goals }}</td>
                    <td>{{ player.assist }}</td>
                    <td>{{ player.penalties }}</td>
                    <td>{{ player.cardYellow }}</td>
                    <td>{{ player.cardRed }}</td>
                    <td>{{ player.xG }}</td>
                    <td>{{ player.xAG }}</td>
                </tr>
            </tbody>
        </table>

        <div v-else-if="!loading && !error">No players found for {{ team }}.</div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'

const route = useRoute()
const team = route.params.team
const players = ref([])
const loading = ref(false)
const error = ref(null)

const api = axios.create({
    baseURL: 'http://localhost:8080/api'
})

const normalizeTeamName = (teamName) => {
    // Special cases mapping
    const specialCases = {
        'Brighton': 'Brighton-and-Hove-Albion',
        'Brighton & Hove Albion': 'Brighton-and-Hove-Albion',
        'Wolverhampton' : 'Wolverhampton-Wanderers'
    }

    // Check for special cases first
    if (specialCases[teamName]) {
        return specialCases[teamName]
    }

    // Default behavior: replace spaces with hyphens
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

const sort = (key) => {
    if (currentSort.value.key === key) {
        currentSort.value.isAsc = !currentSort.value.isAsc
    } else {
        currentSort.value.key = key
        currentSort.value.isAsc = true
    }
}

const getSortIndicator = (key) => {
    if (currentSort.value.key !== key) return ''
    return currentSort.value.isAsc ? '↑' : '↓'
}

onMounted(() => {
    getPlayersByTeam()
})
</script>

<style scoped>
 /* Use the same styles as PlayerByPosition.vue */
.test-result {
  margin-top: 10px;
  padding: 10px;
  background-color: #2c2c2c;
  border-radius: 4px;
  color: #fff;
}

.stats-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  color: #fff;
}

.stats-table th, .stats-table td {
  border: 1px solid #444;
  padding: 8px;
  text-align: left;
}

.stats-table th {
  background-color: #2c2c2c;
  font-weight: bold;
}

.stats-table tr:nth-child(even) {
  background-color: #2c2c2c;
}

.stats-table tr:nth-child(odd) {
  background-color: #262626;
}

.stats-table tr:hover {
  background-color: #363636;
}

h2 {
  color: #fff;
}

.sort-indicator {
    margin-left: 5px;
}

.stats-table th {
    cursor: pointer;
    user-select: none;
}

.stats-table th:hover {
    background-color: #363636;
}
</style>