<template>
  <div class="login-container">
    <h2>Login</h2>
    <button @click="loginWithGoogle">Login with Google</button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const clientId =
  "324005622976-0cudr3bemp8mki6n0uid8u19hkg4ts10.apps.googleusercontent.com"

// 🔐 Store with expiry (30 mins)
function setWithExpiry(key, value, ttl) {
  const now = new Date()

  const item = {
    value,
    expiry: now.getTime() + ttl
  }

  localStorage.setItem(key, JSON.stringify(item))
}

// 📦 Load Google script safely
function loadGoogleScript() {
  return new Promise((resolve, reject) => {
    if (window.google) return resolve()

    const script = document.createElement("script")
    script.src = "https://accounts.google.com/gsi/client"
    script.async = true
    script.defer = true
    script.onload = resolve
    script.onerror = reject

    document.head.appendChild(script)
  })
}

// 🚀 Google login
async function loginWithGoogle() {
  try {
    await loadGoogleScript()

    const client = window.google.accounts.oauth2.initCodeClient({
      client_id: clientId,
      scope: "openid email profile",
      ux_mode: "popup",
      callback: ({ code }) => {
        if (!code) {
          console.error("No auth code received")
          return
        }

        sendCodeToBackend(code)
      }
    })

    client.requestCode()
  } catch (err) {
    console.error("Google SDK failed to load:", err)
  }
}

// 🔗 Backend call
async function sendCodeToBackend(code) {
  try {
    const res = await fetch(
      `https://ai-resume-scanner-backend-latest.onrender.com/api/sign-in?code=${code}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json"
        }
      }
    )

    const text = await res.text()

    console.log("Backend response:", text)

    // ⏱️ store for 30 minutes
    setWithExpiry("userData", text, 30 * 60 * 1000)

    // 🔄 Vue router redirect
    router.push('/')

  } catch (err) {
    console.error("Login failed:", err)
  }
}
</script>