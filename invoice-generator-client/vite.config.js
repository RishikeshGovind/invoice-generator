import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  // When deployed to https://<username>.github.io/invoice-generator/
  base: '/invoice-generator/',
  plugins: [react()],

  // proxy API calls to your Spring Boot server in dev
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      }
    }
  }
})
