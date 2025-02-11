<template>
    <div class="player-stats-container">
        <h1>Players by Position</h1>
        <div class="position-selector">
            <select v-model="selectedPosition">
                <option value="FW">Forwards</option>
                <option value="MF">Midfielders</option>
                <option value="DF">Defenders</option>
                <option value="GK">Goalkeepers</option>
            </select>
        </div>
        
        <button @click="handleRefresh">Refresh</button>
        <button @click="testApi">Test API Connection</button>

        <div v-if="loading">Loading...</div>
        <div v-if="error">{{ error }}</div>

        <table v-if="players.length > 0" class="stats-table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Position</th>
                    <th>Team</th>
                    <th>Nation</th>
                    <th>Age</th>
                    <th>Matches</th>
                    <th>Starts</th>
                    <th>Minutes</th>
                    <th>Goals</th>
                    <th>Assists</th>
                    <th>Penalties</th>
                    <th>Yellow Cards</th>
                    <th>Red Cards</th>
                    <th>xG</th>
                    <th>xAG</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="player in players" :key="player.name">
                    <td>{{ player.name }}</td>
                    <td>{{ player.position }}</td>
                    <td>{{ player.team }}</td>
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

        <div v-else-if="!loading && !error && selectedPosition">No players found for this position.</div>
        <div v-if="testResult" class="test-result">{{ testResult }}</div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import axios from 'axios'

const selectedPosition = ref('FW')
const players = ref([])
const loading = ref(false)
const error = ref(null)
const testResult = ref(null)
const isRefreshing = ref(false)

const api = axios.create({
    baseURL: 'http://localhost:8080/api'
})

const handleRefresh = () => {
    window.location.reload()
}

const getPlayersByPosition = async () => {
    try {
        const response = await api.get(`/players/position/${selectedPosition.value}`)
        players.value = response.data
    } catch (err) {
        error.value = 'Error fetching players: ' + err.message
    }
}

const testApi = async () => {
    try {
        const response = await api.get('/players/test')
        testResult.value = response.data
    } catch (err) {
        testResult.value = 'API Test Failed: ' + err.message
    }
}

const positionDisplay = computed(() => {
    switch (selectedPosition.value) {
        case 'MF': return 'Midfield'
        case 'FW': return 'Forward'
        case 'DF': return 'Defender'
        case 'GK': return 'Goalkeeper'
        default: return selectedPosition.value
    }
})

watch(selectedPosition, () => {
    getPlayersByPosition()
})

onMounted(() => {
    getPlayersByPosition()
})
</script>

<style scoped>
.player-stats-container {
    padding: 20px;
    max-width: 100%;
    overflow-x: auto;
}

.stats-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    background-color: #1e1e1e;
    color: #fff;
}

.stats-table th, .stats-table td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #333;
}

.stats-table th {
    background-color: #2c3e50;
    color: #fff;
    font-weight: bold;
}

.stats-table tr:hover {
    background-color: #2c3e50;
}

button {
    margin: 10px;
    padding: 8px 16px;
    background-color: #42b983;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #3aa876;
}

.error {
    color: #ff4444;
    padding: 10px;
    margin: 10px 0;
    background-color: #2c2c2c;
    border-radius: 4px;
}

.test-result {
    margin-top: 20px;
    padding: 10px;
    background-color: #2c2c2c;
    border-radius: 4px;
    color: #42b983;
}

.position-selector {
    margin: 20px 0;
}

.position-selector select {
    padding: 8px 16px;
    font-size: 16px;
    border: 1px solid #42b983;
    border-radius: 4px;
    background-color: #1e1e1e;
    color: #fff;
}

.position-selector select:focus {
    outline: none;
    border-color: #3aa876;
}
</style>