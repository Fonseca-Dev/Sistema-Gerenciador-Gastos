import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5050,
    host: true,
    strictPort: true,
    hmr: {
      overlay: true,
    },
    // Força reload completo para todos os arquivos
    watch: {
      usePolling: true,
    }
  },
  preview: {
    port: 5050,
    host: true,
    strictPort: true
  }
})
