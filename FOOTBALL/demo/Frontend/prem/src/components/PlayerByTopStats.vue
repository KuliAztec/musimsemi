<template>
  <div class="top-stats-container">
    <h1>Top Player Statistics</h1>
    <div class="controls">
      <input type="number" min="1" v-model.number="count" placeholder="Enter count" />
      <select v-model="sortBy">
        <option disabled value="">Select statistic</option>
        <option value="age">Age</option>
        <option value="match_Played">Matches Played</option>
        <option value="starts">Starts</option>
        <option value="minutes">Minutes</option>
        <option value="goals">Goals</option>
        <option value="assist">Assists</option>
        <option value="penalties">Penalties</option>
        <option value="cardYellow">Yellow Cards</option>
        <option value="cardRed">Red Cards</option>
        <option value="xG">xG</option>
        <option value="xAG">xAG</option>
      </select>
      <button @click="getTopPlayers">Fetch Top Players</button>
    </div>

    <div v-if="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>

    <table v-if="players.length > 0">
      <thead>
        <tr>
          <th>Name</th>
          <th>Team</th>
          <th>Position</th>
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
          <td>{{ player.team }}</td>
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
    <div v-else-if="!loading && !error">No players found.</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const count = ref(5)
const sortBy = ref('')
const players = ref([])
const loading = ref(false)
const error = ref(null)

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

const getTopPlayers = async () => {
  if (!sortBy.value) {
    error.value = 'Please select a statistic to sort by.'
    return
  }

  loading.value = true
  error.value = null
  try {
    const response = await api.get(`/players/top/${count.value}?sortBy=${sortBy.value}`)
    players.value = response.data || []
  } catch (err) {
    error.value = 'Error fetching top players.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.top-stats-container {
  color: #fff;
}

.controls {
  margin-bottom: 20px;
}

.controls input,
.controls select {
  margin-right: 10px;
  padding: 5px;
}

.error {
  color: red;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  color: #fff;
}

th, td {
  border: 1px solid #444;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #2c2c2c;
}

tr:nth-child(even) {
  background-color: #2c2c2c;
}

tr:nth-child(odd) {
  background-color: #262626;
}

tr:hover {
  background-color: #363636;
}
</style>
