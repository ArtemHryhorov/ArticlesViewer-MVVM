# ArticlesViewer-MVVM

## Table of Contents
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Task Checklist](#task-checklist)
- [Contact](#contact)

---

## Architecture
> The project follows the **MVVM (Model-View-ViewModel)** architecture. It is divided into three key layers:
1. **UI Layer**: Handles UI components using Jetpack Compose.
2. **Domain Layer**: Manages business logic with use cases.
3. **Data Layer**: Responsible for data retrieval from local (Room) or remote (Retrofit) sources.

---

## Technologies Used
List the key tools, frameworks, and libraries used in the project.

- Programming Language: Kotlin
- UI Framework: XML-based imperative approach
- Networking: Retrofit with Kotlin Coroutines
- Dependency Injection: Hilt
- Local Storage: Room
- Build Tools: Gradle

---

## Features

- Display a list of articles from a public API.
- View article details in a WebView.
- Offline data storage for recent searches.

---

## Task Checklist

### Completed
- [x] API integration with Retrofit
- [x] UI implementation for article listing
- [x] State management using ViewModel
- [x] WebView

### To-Do
- [ ] Add pagination for articles
- [ ] Error handling
- [ ] Implement dark mode
- [ ] Unit tests for ViewModel
- [ ] Caching data
- [ ] Unit tests for ViewModel

---

## Contact

**Author:** Artem Hryhorov 
**Email:** tema9stark@gmail.com
**LinkedIn:** [Your LinkedIn](https://www.linkedin.com/in/artem-hryhorov-191568194/)
