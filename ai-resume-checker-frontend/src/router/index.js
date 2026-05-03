import { createRouter, createWebHistory } from 'vue-router'

// صفحات
import ChatSpace from '@/components/ChatSpace.vue'
import Login from '@/components/Login.vue'

// 🔐 helper: get data with expiry
function getWithExpiry(key) {
  const itemStr = localStorage.getItem(key)

  if (!itemStr) return null

  try {
    const item = JSON.parse(itemStr)
    const now = new Date().getTime()

    if (now > item.expiry) {
      localStorage.removeItem(key)
      return null
    }

    return item.value
  } catch (e) {
    // fallback if plain string was stored
    return itemStr
  }
}

const routes = [
  {
    path: '/',
    name: 'ChatSpace',
    component: ChatSpace,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 🚦 Navigation Guard
router.beforeEach((to, from, next) => {
  const userData = getWithExpiry("userData")

  // 🔒 protect private routes
  if (to.meta.requiresAuth && !userData) {
    return next('/login')
  }

  // 🔁 prevent logged-in users from visiting login
  if (to.path === '/login' && userData) {
    return next('/')
  }

  next()
})

export default router