# TaskFlow

A mini full-stack task-management REST API built with Spring Boot, PostgreSQL, and RabbitMQ. 

## Git Workflow

This project follows a lightweight feature-branch workflow to simulate an Agile environment:

* **`main`**: The deployment-ready integration branch. All code here must build cleanly and pass tests.
* **`feature/<short-description>`**: Used for all active development (e.g., `feature/jwt-login`, `feature/rabbitmq-notification`).

### Pull Request Process
1. Create a feature branch off `main`.
2. Write atomic, present-tense commit messages (e.g., `feat(auth): add JWT login endpoint`).
3. Open a Pull Request against `main`.
4. Merge and delete the feature branch.