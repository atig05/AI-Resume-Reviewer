export async function postData(payload) {

  const url = "https://ai-resume-scanner-backend-latest.onrender.com/ai/chat/payload" //"http://localhost:8001/ai/chat/payload";
  console.log(JSON.stringify(payload));
  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.text();
    console.log(data);
    return data;
  } catch (error) {
    console.error("Error calling API:", error);
  }
}

