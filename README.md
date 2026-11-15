# ☁️ Enterprise Cloud Storage Implementation (Base Architecture)

[![Java Support](https://img.shields.io/badge/Java-25-blue.svg?style=for-the-badge&logo=java)](#)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-brightgreen.svg?style=for-the-badge&logo=spring)](#)
[![Maven Build](https://img.shields.io/badge/Maven-Build-orange.svg?style=for-the-badge&logo=apachemaven)](#)

> A production-grade Spring Boot REST API engineered for image asset management. Currently operating on **local file system storage**, this repository serves as the baseline for a structural migration to **public cloud object storage**.

---

## 📖 Architectural Context

This project is part of the **Enterprise Cloud Application (ECA)** module in the **Higher Diploma in Software Engineering (HDSE)** program at the **Institute of Software Engineering (IJSE)**. It demonstrates the transition from host-bound file systems to decoupled, stateless cloud storage layers. 

## 🎯 Migration Objective

In its current state, the application persists binary payloads directly to the local host machine (default path: `~/.ijse/eca/storage`). 

**The Engineering Task:** Clone this repository and refactor the storage abstraction layer to integrate a highly available cloud object storage provider (e.g., **AWS S3, Google Cloud Storage, or Azure Blob Storage**) without modifying the exposed REST API contracts.

---

## ⚙️ Technology Stack

* **Runtime Environment:** Java 25
* **Application Framework:** Spring Boot 4.1.2
* **Build Automation:** Apache Maven
* **Boilerplate Reduction:** Lombok
* **Current State:** Local File I/O (Pre-Migration)
* **Target State:** Cloud Object Storage (IaaS)

---

## 🔌 API Interface Specification

| HTTP Method | Endpoint URI | Operation | Payload Type |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/images` | Upload a new image | `multipart/form-data` |
| `GET` | `/api/v1/images` | Fetch stored image metadata | None |
| `GET` | `/api/v1/images/{filename}` | Download specific image binary | Path Variable |
| `DELETE` | `/api/v1/images/{filename}` | Purge specific image record | Path Variable |

---

## 🚀 Initialization & Execution

**1. Clone and resolve dependencies:**
```bash
git clone [https://github.com/ijse-eca-hdse-73-74/cloud-storage-implementation.git](https://github.com/ijse-eca-hdse-73-74/cloud-storage-implementation.git)
cd cloud-storage-implementation
./mvnw clean install
```

**2. Boot the embedded Tomcat server:**
```bash
./mvnw spring-boot:run
```

*System Note: Upon initial execution prior to the cloud migration phase, the application will automatically provision the local storage directory (`~/.ijse/eca/storage`) on the host environment if it does not already exist.*

---

## 🧪 Quality Assurance & Testing

Endpoint validation must be conducted using standard REST clients. Ensure HTTP headers specify `Content-Type: multipart/form-data` during binary transmissions.

Click the button below to view and import the pre-configured API collection directly into your workspace:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/34588088/2sBYHaQHoe)

---

## 🆘 Support & Communication

If you encounter any issues during the migration or experience runtime configuration errors, please initiate a discussion thread via the official cohort Slack workspace.

**Access:** (Use this link to access the workspace later)
🔗 [eca-ijse.slack.com](https://eca-ijse.slack.com)

---
**Lead Architect:** Shamodha Sahan | Enterprise Cloud Architecture
