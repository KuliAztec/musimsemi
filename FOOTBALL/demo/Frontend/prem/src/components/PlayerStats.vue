<template>
  <div>
    <h2>Players - {{ team }}</h2>
    <button @click="getPlayersByTeam">Refresh</button>
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
    
    <div v-else-if="!loading && !error && team">No players found for this team.</div>
    <div v-if="testResult" class="test-result">{{ testResult }}</div>
  </div>
</template>

<script>
import axios from 'axios';
import { useRoute } from 'vue-router';

const api = axios.create({
  baseURL: 'http://localhost:8080'
});

export default {
  setup() {
    const route = useRoute();
    return { route };
  },
  data() {
    return {
      players: [],
      loading: false,
      error: null,
      testResult: null,
      teamNameMap: {
        'Brighton': 'Brighton-and-Hove-Albion',
      }
    };
  },
  computed: {
    team() {
      return this.route.params.team || '';
    }
  },
  created() {
    if (this.team) {
      this.getPlayersByTeam();
    }
  },
  methods: {
    async getPlayersByTeam() {
      this.loading = true;
      this.error = null;
      try {
        // Get the database team name from the map
        const databaseTeamName = this.teamNameMap[this.team] || this.team;

        // Replace spaces with dashes in the database team name
        const teamNameForApi = databaseTeamName.replace(/ /g, '-');

        const response = await api.get(`/api/players/team/${teamNameForApi}`);
        this.players = response.data;
        if (response.data.length === 0 && this.team) {
          this.error = "No players found for this team.";
        }
      } catch (err) {
        console.error("Error fetching players:", err);
        this.error = "Error fetching players. Please check the console."; // More user-friendly error
        if (err.response && err.response.status === 404) {
          this.error = "Team not found or no players for this team."; // 404 handling
        }

      } finally {
        this.loading = false;
      }
    },
    async testApi() {
      try {
        const response = await api.get('/api/players/test');
        this.testResult = response.data;
        console.log('API Test Response:', response.data);
      } catch (err) {
        this.testResult = 'Error connecting to API';
        console.error('API Test Error:', err);
      }
    }
  }
};
</script>

<style scoped>
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
</style>