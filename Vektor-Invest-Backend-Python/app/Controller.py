import httpx

response = httpx.get("https://api.groq.com")

print(response.status_code)