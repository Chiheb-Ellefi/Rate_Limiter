# 🔒 Spring Boot Rate Limiter

A customizable and efficient **Rate Limiter** built with **Spring Boot**, **Spring AOP**, and **Spring Data Redis (JPA)**. It supports two advanced rate limiting algorithms — **Sliding Window Log** and **Token Bucket** — and ensures **atomicity** with **Lua scripting in Redis**.

## ✨ Features

- 🚦 Supports **Sliding Window Log** and **Token Bucket** algorithms
- 🌀 Intercepts incoming requests using **Spring AOP**
- 🧠 Rate limiting is applied **per user**
- ⚡ Uses **Redis + Lua scripts** for fast, atomic operations
- 🛡️ Prevents abuse and ensures fairness in APIs

## 🧩 Tech Stack

- **Java + Spring Boot**
- **Spring AOP** – to intercept controller/service methods
- **Spring Data Redis** – to persist rate-limiting data
- **Redis Lua Scripts** – to ensure atomic updates
- **JPA (optional)** – if you're also storing user/rate info in a DB

## 🛠️ Algorithms Implemented

### 1. Sliding Window Log
Records timestamps of requests in Redis and checks if the user has exceeded the limit within a given window.

### 2. Token Bucket
Allows a certain number of tokens (requests) per user. Tokens are refilled over time, and requests are only allowed if tokens are available.

## ⚙️ How It Works

1. **AOP Interception**: Each request is intercepted using a Spring AOP advice.
2. **Identify User**: The user is identified (via headers, tokens, etc.).
3. **Apply Algorithm**: Depending on configuration or user type, the appropriate rate limiting algorithm is applied.
4. **Atomic Lua Scripts**: Redis Lua scripts ensure atomic execution when checking limits or updating counters.
5. **Proceed or Block**: If the user is within limits, the request proceeds. Otherwise, a rate limit error is returned.


## 🚀 Getting Started

### Prerequisites

- Java 17+
- Redis server running locally or remotely
- Maven or Gradle

### Run the App

```bash
# Build and run the app
./mvnw spring-boot:run

