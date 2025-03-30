<template>
  <div class="operadora-search">
    <div class="search-box">
      <input
        type="text"
        v-model="searchTerm"
        @input="onSearch"
        placeholder="Digite o nome da operadora..."
        class="search-input"
      />
      <button @click="onSearch" class="search-button">Buscar</button>
    </div>

    <div v-if="loading" class="loading">Carregando...</div>

    <div v-if="error" class="error">{{ error }}</div>

    <div v-if="results.length > 0" class="results">
      <h2>Resultados da Busca ({{ results.length }})</h2>
      <div class="result-item" v-for="operadora in results" :key="operadora.registroAns">
        <h3>{{ operadora.nomeFantasia }}</h3>
        <p><strong>Razão Social:</strong> {{ operadora.razaoSocial }}</p>
        <p><strong>Registro ANS:</strong> {{ operadora.registroAns }}</p>
        <p><strong>CNPJ:</strong> {{ operadora.cnpj }}</p>
        <p><strong>Modalidade:</strong> {{ operadora.modalidade }}</p>
      </div>
    </div>

    <div v-else-if="searchTerm && !loading" class="no-results">
      Nenhuma operadora encontrada para "{{ searchTerm }}"
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'OperadoraSearch',
  data() {
    return {
      searchTerm: '',
      results: [],
      loading: false,
      error: null,
      debounce: null
    }
  },
  methods: {
    onSearch() {
      clearTimeout(this.debounce)

      if (this.searchTerm.trim().length < 3) {
        this.results = []
        return
      }

      this.debounce = setTimeout(() => {
        this.fetchResults()
      }, 500)
    },
    async fetchResults() {
      this.loading = true
      this.error = null

      try {
        const response = await axios.get('/operadoras/search', {
          params: {
            termo: this.searchTerm
          }
        })
        this.results = response.data
      } catch (err) {
        this.error = 'Erro ao buscar operadoras. Tente novamente.'
        console.error('Search error:', err)
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.search-box {
  display: flex;
  margin-bottom: 20px;
}

.search-input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px 0 0 4px;
}

.search-button {
  padding: 10px 15px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 0 4px 4px 0;
  cursor: pointer;
  font-size: 16px;
}

.search-button:hover {
  background-color: #369f6b;
}

.results {
  margin-top: 20px;
}

.result-item {
  background: #f9f9f9;
  border: 1px solid #eee;
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 10px;
}

.result-item h3 {
  margin-top: 0;
  color: #42b983;
}

.loading, .error, .no-results {
  text-align: center;
  padding: 20px;
  font-size: 18px;
}

.error {
  color: #ff4444;
}
</style>