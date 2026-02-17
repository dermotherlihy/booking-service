# Booking-Service

## Overview

This service is a backend microservice responsible for Bookings in ACD
e.g., managing bookings, processing booking events, exposing booking APIs>.

It is designed to be:
- **Reliable** – resilient to failure and production-ready
- **Observable** – fully instrumented for metrics, tracing, and debugging
- **Scalable** – built to handle increasing load with minimal operational overhead
- **Cloud-native** – deployed and managed using infrastructure as code

The service integrates with <external systems, internal services, queues, databases, etc.> and exposes <REST/gRPC/events> for consumption by other services.

---

## Tech Stack

### Runtime & Framework
- **Java 17**
- **Spring Boot**

### Infrastructure
- **AWS** (compute, storage, networking)
- **Terraform** (infrastructure as code)

### Observability
- **Honeycomb** (distributed tracing & event analysis)
- **Grafana** (metrics visualization & dashboards)
- **Metabase** (analytics & reporting)

---

## Architecture (Optional – Expand Later)

High-level components:
- API Layer
- Service Layer
- Persistence Layer
- External Integrations

Deployment model:
- Containerized application
- Deployed via CI/CD pipeline
- Infrastructure provisioned via Terraform

---

## Learning Goals

This service is being built with the intention of deepening practical expertise in:

- Designing clean, maintainable Spring Boot services
- Implementing production-grade observability (tracing, metrics, dashboards)
- Managing AWS infrastructure using Terraform
- Operating and debugging distributed systems effectively

The goal is not just to ship functionality, but to build strong engineering discipline around reliability, scalability, and operational excellence.

---

## Getting Started

### Prerequisites
- Java 17
- Docker
- AWS credentials configured
- Terraform (if provisioning infra locally)

### Run Locally

```bash
./mvnw spring-boot:ru