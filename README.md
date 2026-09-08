# 🍽️ DineSplit

DineSplit is a bill-splitting application that makes it easy to divide restaurant bills fairly among multiple people.

Instead of manually calculating who owes what, DineSplit allows you to enter the bill details, assign food items to the people who consumed them, and automatically calculate each person's final share — including taxes, service charges, and other additional charges.

---

## 🚀 Features

* 👥 Add multiple people to a bill
* 🍕 Add food and beverage items
* 👤 Assign items to one or multiple people
* 💰 Automatically calculate individual shares
* 🧾 Support for taxes such as GST
* 💵 Support for service charges
* ⚖️ Handle shared items fairly
* 📊 View the final amount payable by each person
* 🔢 Accurate decimal calculations
* 🌐 Simple and responsive web interface
* 🧩 REST API based backend
* 🔒 Environment-variable based configuration for sensitive credentials

---

## 🏗️ How It Works

Suppose seven people go out for dinner.

* Two people shared the biryani
* One person only had a Coke
* Three people shared another dish
* One person left early
* The restaurant added a service charge
* GST was added to the bill

Instead of dividing the total bill equally, DineSplit calculates the actual amount each person owes based on what they consumed.

### Example

| Person   | Items          | Subtotal | GST/Charges | Final Amount |
| -------- | -------------- | -------: | ----------: | -----------: |
| Person 1 | Biryani + Coke |     ₹350 |         ₹63 |         ₹413 |
| Person 2 | Biryani        |     ₹250 |         ₹45 |         ₹295 |
| Person 3 | Paneer + Roti  |     ₹300 |         ₹54 |         ₹354 |
| Person 4 | Roti           |     ₹100 |         ₹18 |         ₹118 |

The actual calculation depends on the items, quantities, sharing, tax and service-charge configuration.

---

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* REST APIs
* Maven

### Database

* PostgreSQL

### Frontend

* Thymeleaf
* Tailwind CSS
* HTML
* JavaScript

### Development Tools

* IntelliJ IDEA
* Postman
* Git & GitHub

---

## 📂 Project Structure

```text
dinesplit/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── dinesplit/
│   │   │           ├── controller/
│   │   │           ├── service/
│   │   │           ├── repository/
│   │   │           ├── entity/
│   │   │           ├── dto/
│   │   │           ├── exception/
│   │   │           └── DineSplitApplication.java
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── index.html
│   │       │   ├── bill.html
│   │       │   └── result.html
│   │       │
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── .gitignore
└── README.md
```

---

## 🧠 Core Calculation Logic

DineSplit follows a simple calculation flow:

Restaurant Bill
       │
       ▼
   Add People
       │
       ▼
   Add Items
       │
       ▼
Assign Items
to People
       │
       ▼
Calculate Item Shares
       │
       ▼
Apply Service Charge
       │
       ▼
Apply GST / Taxes
       │
       ▼
Calculate Final Amount
for Each Person
```


## ⚙️ Configuration

Create the required environment variables before running the application.

Example:

```text
POSTGRESQL_HOST=localhost
POSTGRESQL_PORT=5432
POSTGRESQL_DB=dinesplit
POSTGRESQL_USER=postgres
POSTGRESQL_PASS=your_password
```

Then configure Spring Boot:

```properties
spring.datasource.url=jdbc:postgresql://${POSTGRESQL_HOST}:${POSTGRESQL_PORT}/${POSTGRESQL_DB}
spring.datasource.username=${POSTGRESQL_USER}
spring.datasource.password=${POSTGRESQL_PASS}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Never commit your database password or other secrets to GitHub.

---

## ▶️ Running the Application

### 1. Clone the repository

```bash
git clone https://github.com/your-username/dinesplit.git
```

### 2. Navigate to the project

```bash
cd dinesplit
```

### 3. Configure PostgreSQL

Create a PostgreSQL database:

```sql
CREATE DATABASE dinesplit;
```

### 4. Configure environment variables

Set your PostgreSQL credentials:

```text
POSTGRESQL_HOST=localhost
POSTGRESQL_PORT=5432
POSTGRESQL_DB=dinesplit
POSTGRESQL_USER=postgres
POSTGRESQL_PASS=your_password
```

### 5. Build the project

```bash
mvn clean install
```

### 6. Run the application

```bash
mvn spring-boot:run
```

Or run:

```text
DineSplitApplication.java
```

directly from IntelliJ IDEA.

### 7. Open the application

```text
http://localhost:8080
```

---

## 🧪 Testing

APIs can be tested using Postman.

Typical flow:

```text
Create Bill
    ↓
Add People
    ↓
Add Items
    ↓
Assign Items
    ↓
Add Taxes / Charges
    ↓
Calculate Split
    ↓
Get Individual Amounts
```


## 💡 Why DineSplit?

Splitting a restaurant bill equally isn't always fair.

If one person orders a ₹500 dish while another only drinks a ₹50 Coke, dividing the final bill equally creates unnecessary arguments.

DineSplit solves this by calculating the bill based on **actual consumption**.

> **Eat what you want. Pay what you owe.**

---

## 🔮 Future Enhancements

* 📷 Scan restaurant bills using OCR
* 🤖 Automatically detect food items from a bill image
* 🧠 AI-based item/person assignment
* 📱 Mobile application
* 🔗 Shareable bill links
* 📲 QR-code based bill joining
* 💳 Payment integration
* 📈 Spending history
* 👨‍👩‍👧‍👦 Group management
* 🌍 Multi-currency support
* 🔐 User authentication
* 📧 Share final split through email/WhatsApp



## 👨‍💻 Created By

**Mayank Mitra (EN23CS301604)**

