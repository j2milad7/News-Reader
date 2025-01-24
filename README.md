# News Reader App

## Overview

This is a News Reader App built using the MVVM (Model-View-ViewModel) and Clean Architecture
principles. The app fetches news articles from a remote source and displays them in a paginated
list. Users can search for news articles and sort the results by relevancy, popularity, or newest.
Additionally, detailed views of individual articles are provided. The app includes unit tests to
ensure code reliability and correctness.

## Architecture

### MVVM (Model-View-ViewModel)

MVVM is an architectural pattern that separates the development of the graphical user interface from
the development of the business logic or back-end logic. The MVVM pattern is made up of the
following components:

- **Model**: Represents the data and the business logic of the application.
- **View**: Represents the UI components and is responsible for displaying the data from the
  ViewModel.
- **ViewModel**: Acts as a link between the Model and the View. It retrieves data from the Model and
  formats it for display in the View.

### Clean Architecture

Clean Architecture emphasizes separation of concerns, making the code more modular, testable, and
maintainable. It divides the project into different layers:

- **Presentation Layer**: Contains the UI components and the ViewModels.
- **Domain Layer**: Contains the business logic and use cases.
- **Data Layer**: Handles data management, including network requests and local data storage.

## Features

### News Feed Screen

- Displays a list of news articles using pagination for better performance.
- Users can search for news articles.
- Users can sort articles by relevancy, popularity, or newest.
- Users can reload the list of news articles using voice command.

### Article Details Screen

- Shows detailed information about a selected article.

## Libraries Used

#### Kotlin and Coroutines

- **Kotlin Coroutines Core & Android**:
    - These libraries enable asynchronous programming by providing a structured way to handle
      asynchronous operations, which is crucial for smooth UI performance. Coroutines simplify the
      code by removing the need for complex callback chains or RxJava streams.

- **Kotlin Serialization JSON**:
    - A library for Kotlin serialization, used to parse and serialize data to JSON format. It
      simplifies the conversion between Kotlin objects and JSON, making data handling more
      straightforward.

#### Jetpack Libraries

- **Core KTX**:
    - Enhances the standard Android APIs to provide more concise and idiomatic Kotlin code,
      improving productivity and readability.

- **Lifecycle ViewModel & Compose**:
    - The ViewModel components help manage UI-related data in a lifecycle-conscious way, surviving
      configuration changes. This is critical for ensuring a seamless user experience.

- **Lifecycle Runtime Compose**:
    - Integrates lifecycle-aware components into Jetpack Compose, Android's modern toolkit for
      building native UI.

- **Activity Compose, Paging Compose, Navigation Compose**:
    - These libraries facilitate the use of Jetpack Compose for building UI, handling pagination
      efficiently, and navigating between different parts of the app.

#### Compose Libraries

- **Compose BOM, UI, UI Tooling, UI Tooling Preview, Material3**:
    - Jetpack Compose is a modern toolkit for building native Android UI. It simplifies UI
      development by providing a declarative API, enabling more straightforward and efficient UI
      development. Material3 provides the latest Material Design components, ensuring a modern look
      and feel.

#### Dependency Injection: Hilt

- **Hilt, Hilt Compiler, Hilt Navigation Compose**:
    - Hilt is a dependency injection library for Android, built on top of Dagger. It simplifies
      dependency injection, reduces boilerplate, and integrates seamlessly with Android components.
      Hilt Navigation Compose enables scoped navigation and dependency injection within Jetpack
      Compose components.

#### Networking: Retrofit

- **Retrofit & Retrofit Converter Gson**:
    - Retrofit is a type-safe HTTP client for Android. It simplifies network calls and parsing
      responses into data objects, supporting various converters like Gson for JSON parsing.

#### Common Libraries

- **Gson**:
    - A JSON parser for converting JSON data to Java objects and vice versa.

- **Coil**:
    - An image loading library for Android backed by Kotlin Coroutines, providing a modern and
      efficient way to load images in the app.

#### Testing Libraries

- **JUnit, Mockito-Kotlin, Coroutines Test, Paging Testing**:
    - These libraries are essential for writing and running unit tests. JUnit provides the
      framework, Mockito-Kotlin is used for mocking dependencies, Coroutines Test helps test
      coroutines, and Paging Testing facilitates testing of paginated data sources.

## To Improve

Due to time limitations, I would like to make a variety of improvements and some of which are:

1. **Language Filtering**:
    - Implement the functionality to filter articles by language based on the phone's settings or
      user preferences.

2. **Enhanced UI/UX**:
    - Add UI tests using tools like Espresso to ensure the user interface behaves as expected.
    - Improve the detail view with better formatting..

3. **Advanced Sorting and Filtering**:
    - Implement more advanced filtering options, such as by author.

4. **Offline Mode**:
    - Cache articles locally so users can read them offline.
    - Implement a refresh mechanism to update the cached articles when the app is online again.

5. **Personalization**:
    - Allow users to save favorite articles locally.

6. **Improved Error Handling**:
    - Show user-friendly error messages and retry options.

7. **Voice Commands and Accessibility**:
    - Expand the voice command feature to include more commands (e.g., "Search", "Sort by ...", "
      Filter by ...", "Open article by title").
    - Improve accessibility features, such as screen reader compatibility.

8. **Analytics**:
    - Integrate analytics to track user behavior and app usage, providing insights into how the app
      is used and where improvements can be made.

9. **Social Sharing**:
    - Add the ability for users to share articles on social media platforms directly from the app.

These enhancements could significantly improve the functionality, usability, and overall user
experience of the News Reader App.

## Conclusion

This News Reader App demonstrates a scalable and maintainable approach to Android development using
MVVM and Clean Architecture. By leveraging Android's Paging 3 library, the app ensures efficient
data loading and better performance. The user interface is implemented using Jetpack Compose,
providing a modern and responsive design. Additionally, the app supports voice commands to refresh
the news list, enhancing user interaction.
