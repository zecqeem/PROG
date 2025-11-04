# 🔒 Secure Data Processing and Export API

## 📘 Project Overview

This project is a **Spring Boot** web application designed for **secure data extraction, storage, and export**.  
It demonstrates critical security practices, including **robust authentication**, **login attempt control**, and **cryptographic data protection**.

---

## ⚙️ Core Functionality

### 🧩 Data Extraction (Parsing)
Secure parsing of the **Top 25 movies** from the IMDb website using **JSoup**.

### 💾 Storage
Retrieved data is stored securely in a **PostgreSQL relational database**.

### 📤 Export
Users can **download data as Excel files**, including an **AES-encrypted version** requiring a decryption key.

### 🛡️ Security
Full authentication cycle implemented with **Brute Force protection** and **comprehensive action logging**.

---

## 🚀 Technology Stack

### 🖥️ Backend
- **Framework:** Spring Boot 3  
- **Database:** PostgreSQL  
- **ORM:** Spring Data JPA / Hibernate  
- **Parsing:** JSoup (IMDb data extraction)  
- **Excel Handling:** Apache POI  
- **Hashing & Security:** AES encryption + [argon2-jvm](https://github.com/phxql/argon2-jvm) for password hashing  

### 💻 Frontend
- **Framework:** React + Vite (Frontend developed via Figma/LLM for demonstration purposes)  
- **Language:** JavaScript / TypeScript  

### 🧪 Testing
- **UI/E2E Testing:** Selenium WebDriver (via WebDriverManager)  
- **Framework:** JUnit 5  

---

## 🛡️ Security and Brute Force Protection

The application implements multiple layers of protection for secure authentication:

- **Password Generation and Policy:**  
  Automatically generated user passwords with **80+ bits entropy**, stored via **Argon2 hashing**.

- **Brute Force Protection:**  
  Tracks failed login attempts per user. Accounts are **temporarily or permanently locked** upon exceeding attempt limits.

- **Comprehensive Logging:**  
  Logs all **login attempts** (success & failure) and **file download actions** for auditability.

- **Data Encryption:**  
  Exported `.aes` files are protected using a **user-provided key**, ensuring confidentiality even if the file is compromised.

---

## 🧪 End-to-End (E2E) Testing with Selenium

Automated E2E tests using **Selenium WebDriver** ensure the stability of key features.

### ✅ Key Scenarios
- **Successful Authentication:** Login with correct credentials and navigation to the “Secret Page.”  
- **Incorrect Authentication:** Proper error handling for invalid credentials.  
- **Prompt/Encryption Testing:** Verifies correct response when attempting to download encrypted files without a decryption key.

---

## 🧱 Page Object Model (POM) Structure

Tests follow the **Page Object Model** for clarity and maintainability.

**Page Objects Used:**
- `LoginPage.java`
- `SecretPage.java`

---

## 🛠️ Project Setup

### 📋 Prerequisites
- Java Development Kit (JDK) 21+  
- Apache Maven  
- PostgreSQL  
- Node.js and npm (for React frontend)

### 🚧 Launch Steps

#### 1️⃣ Clone the Repository
```bash
git clone https://github.com/zecqeem/PROG.git
```
#### 2️⃣ Configure the Database
Create a PostgreSQL database.
Update your application.properties (or application.yml) with credentials.

#### 3️⃣ Build & Run Backend (Spring Boot)
```bash
mvn clean install
mvn spring-boot:run
```
#### 4️⃣ Run Frontend (React/Vite)
```bash
npm install
npm run dev
```
**The application will be available at:**
👉 http://localhost:3000
