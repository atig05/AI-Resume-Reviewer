<template>
  <div class="chat-container">
    <div class="chat-window" ref="chatWindow">
      <div 
        v-for="(msg, index) in messages" 
        :key="index" 
        class="message"
        :class="{ 'user': msg.sender === 'user', 'bot': msg.sender === 'bot' }"
      >
        <div class="message-text">
          <!-- If message is file -->
          <template v-if="msg.file">
            📄 <strong>{{ msg.file.name }}</strong>
              <!-- <p>{{ msg.text }}</p> -->
          </template>

          <!-- Regular text -->
          <p v-else>
            {{ msg.text }}
          </p>
        </div>
      </div>
    </div>

    <form class="input-area" @submit.prevent="sendMessage">
      <label class="upload-label">
        <input type="file" @change="handleFileUpload" />
        📄
      </label>

      <input
        v-model="newMessage"
        type="text"
        placeholder="Type a message..."
      />

      <button type="submit">Send</button>
    </form>
  </div>
</template>

<script setup>
import { ref, watch, nextTick } from 'vue'
import { postData } from '@/utils/ApiUtils'

const messages = ref([
  { sender: 'bot', text: 'Hi there! This is resumeAI. Please upload the resume, AI will give a short review' }
])

const newMessage = ref('')
const chatWindow = ref(null)

async function sendMessage() {
  const userData = localStorage.getItem('userData');
  const parsed = JSON.parse(userData);
  const name = parsed.value;
  if (Date.now() > parsed.expiry) {
      localStorage.removeItem('userData')
      console.log('Session expired')
      redirectToLogin()
    }
  if (!newMessage.value.trim()) return

  const text = newMessage.value
  newMessage.value = ''

  // Add user message
  messages.value.push({ sender: 'user', text })

  // Prepare prompt for API
  const payload = {
    QUESTION: messages.value.map(m => m.text)
  }

  const response = await postData(payload);

  setTimeout(() => {
    messages.value.push({ sender: 'bot', text: response })
  }, 600)
}

async function handleFileUpload(event) {
  const file = event.target.files[0]
  if (!file) return
  const base64 = await fileToBase64(file)
  // Show file message in chat
  messages.value.push({
    sender: 'user',
    file: file,
    text: base64
  })

  // Send file to backend
  // const formData = new FormData()
  // formData.append('file', file)
  const payload = {
    QUESTION: messages.value.map(m => m.text)
  }
  const response = await postData(payload)

  messages.value.push({
    sender: 'bot',
    text: response
  })
}

// Auto scroll
watch(messages, async () => {
  await nextTick()
  chatWindow.value.scrollTop = chatWindow.value.scrollHeight
})

function fileToBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}

</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  max-width: 500px;
  margin: auto;
  background-color: #007bff;
  border: 1px solid #ddd;
  font-family: sans-serif;
}

.chat-window {
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
  background: rgb(230, 219, 219);
}

.message {
  margin-bottom: 1rem;
  display: flex;
}

.message.user {
  justify-content: flex-end;
}

.message.bot .message-text {
  background: #e8e8e8;
  color: #333;
}

.message.user .message-text {
  background: #007bff;
  color: white;
}

.message-text {
  padding: 0.6rem 1rem;
  border-radius: 12px;
  max-width: 70%;
  word-wrap: break-word;
}

.input-area {
  display: flex;
  align-items: center;
  border-top: 1px solid #ccc;
  padding: 0.5rem;
  background: #fafafa;
}

/* Upload button inside input area */
.upload-label {
  cursor: pointer;
  padding: 6px 10px;
  background: #4f46e5;
  color: white;
  border-radius: 6px;
  margin-right: 8px;
}

.upload-label input {
  display: none;
}

.input-area input {
  flex: 1;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
}

.input-area button {
  margin-left: 0.5rem;
  padding: 0.5rem 1rem;
  border: none;
  background: #007bff;
  color: white;
  border-radius: 6px;
  cursor: pointer;
}

.input-area button:hover {
  background: #0d4683;
}
</style>
