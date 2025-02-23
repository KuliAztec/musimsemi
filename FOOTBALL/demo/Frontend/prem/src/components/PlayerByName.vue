<template>
  <div class="search-by-name-container">
    <h1>Search Players by Name</h1>
    <input type="text" placeholder="Enter player name" v-model="keyword" @keyup.enter="searchPlayers" />
    <button @click="searchPlayers">Search</button>

    <div v-if="loading">Loading...</div>
    <div v-if="error" class="error">{{ error }}</div>

    <table v-if="players.length > 0">
      <thead>
        <tr>
          <th>Name</th>
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

const keyword = ref('')
const players = ref([])
const loading = ref(false)
const error = ref(null)

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

const searchPlayers = async () => {
  if (keyword.value.trim() === '') {
    error.value = 'Please enter a name to search.'
    return
  }
  loading.value = true
  error.value = null

  try {
    const encodedKeyword = encodeURIComponent(keyword.value.trim())
    const response = await api.get(`/players/search/${encodedKeyword}`)
    players.value = response.data || []
  } catch (err) {
    error.value = 'Error fetching players.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.search-by-name-container {
  color: #fff;
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
