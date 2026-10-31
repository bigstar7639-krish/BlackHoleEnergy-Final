
const express = require("express");
const cors = require("cors");
require("dotenv").config();

const app = express();

app.use(cors());
app.use(express.json({ limit: "10mb" }));

const PORT = process.env.PORT || 3000;
const API_KEY = process.env.GEMINI_API_KEY;
const MODEL = "gemini-3.8-flash";

const SYSTEM_PROMPT = `
You are Black Hole Energy OMNI AI.
You are a smart, friendly and helpful AI assistant.
Reply naturally, clearly and directly.
Keep simple answers short.
Use detailed explanations only when needed.
`;

app.get("/", (req, res) => {
  res.json({
    app: "Black Hole Energy OMNI AI",
    status: "Backend online ⚡",
    provider: "Google Gemini",
    model: MODEL
  });
});

app.get("/api/health", (req, res) => {
  res.json({
    status: "ok",
    ai: "Google Gemini"
  });
});

app.post("/api/chat", async (req, res) => {
  try {
    if (!API_KEY) {
      return res.status(500).json({
        error: "Gemini API key is missing in Render."
      });
    }

    const { messages } = req.body;

    if (!Array.isArray(messages) || messages.length === 0) {
      return res.status(400).json({
        error: "Please send a message."
      });
    }

    const contents = messages
      .filter(message => message.content)
      .map(message => ({
        role: message.role === "assistant" ? "model" : "user",
        parts: [{
          text: String(message.content)
        }]
      }));

    const response = await fetch(
      `https://generativelanguage.googleapis.com/v1beta/models/${MODEL}:generateContent`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "x-goog-api-key": API_KEY
        },
        body: JSON.stringify({
          systemInstruction: {
            parts: [{ text: SYSTEM_PROMPT }]
          },
          contents,
          generationConfig: {
            maxOutputTokens: 1024,
            temperature: 0.7
          }
        }),
        signal: AbortSignal.timeout(55000)
      }
    );

    const data = await response.json();

    if (!response.ok) {
      console.error("Gemini API error:", data);

      return res.status(response.status).json({
        error: data.error?.message || "Gemini request failed."
      });
    }

    const reply = data.candidates?.[0]?.content?.parts
      ?.map(part => part.text || "")
      .join("")
      .trim();

    if (!reply) {
      return res.status(502).json({
        error: "Gemini returned an empty response."
      });
    }

    return res.json({ reply });

  } catch (error) {
    console.error("OMNI AI error:", error);

    return res.status(500).json({
      error: error.name === "TimeoutError"
        ? "Gemini response timed out. Please try again."
        : "Server error. Please try again."
    });
  }
});

app.listen(PORT, () => {
  console.log(
    `Black Hole Energy OMNI AI running on port ${PORT}`
  );
});
