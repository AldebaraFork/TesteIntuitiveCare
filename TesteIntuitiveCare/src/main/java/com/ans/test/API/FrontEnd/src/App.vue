 <template>
  <div id="app">
    <div class="container">
      <h1>Sistema de Busca de Operadoras ANS</h1>
      <OperadoraSearch />
    </div>
  </div>
</template>

<script>
import OperadoraSearch from './components/OperadoraSearch.vue'

export default {
  name: 'App',
  components: {
    OperadoraSearch
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}

.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
}
</style>