# Strathfinder
A comprehensive mobile application for campus navigation 
# StrathFinder: Campus Navigation App for Strathmore University

## Overview
StrathFinder is a mobile-based application designed to simplify campus navigation at Strathmore University. The app aims to help new students, staff, and visitors navigate the university's expansive layout effectively, reducing confusion and improving the overall campus experience.

![Image Alt]([(https://github.com/Sydney-Nyanchoga/Strathfinder/blob/e6e342bf5a63d005ce281858ebe716dea35af7da/Screenshot%202025-01-10%20153601.png)])

## Features
- **Interactive Campus Map**: Detailed maps of the campus, including building layouts, pathways, and points of interest
- **Real-time Navigation**: Step-by-step directions to help users reach their destination
- **Building & Room Finder**: Easy location of specific rooms, offices, and facilities
- **Indoor Navigation**: Seamless transition between outdoor and indoor navigation
- **User Authentication**: Secure login via email/password or Google Sign-In
- **Accessibility Features**: Support for users with different navigation needs

## Technologies Used
- **Frontend**: Android (Kotlin)
- **Backend**: Firebase (Authentication, Firestore)
- **Mapping**: MappedIn SDK
- **Authentication**: Firebase Auth, Google Sign-In
- **UI Components**: Material Design

## Requirements
- Android device running Android 6.0 (API level 23) or higher
- Google Play Services installed
- Internet connection for real-time updates
- Location services enabled

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/StrathFinder.git
   ```

2. Open the project in Android Studio

3. Add your Firebase configuration:
    - Create a new project in Firebase Console
    - Add an Android app in the Firebase project settings
    - Download the `google-services.json` file
    - Place it in the app directory

4. Configure MappedIn SDK:
    - Add your MappedIn credentials in MainActivity.kt
    - Update the venue slug with your university's venue ID

5. Build and run the project

## Project Structure
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/strathfinderapp/
│   │   │   ├── activities/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── RegisterActivity.kt
│   │   │   │   ├── LoginActivity.kt
│   │   │   │   └── ...
│   │   │   ├── adapters/
│   │   │   ├── models/
│   │   │   └── utils/
│   │   └── res/
│   │       ├── layout/
│   │       ├── values/
│   │       └── drawable/
│   └── test/
└── build.gradle
```

## Setup Development Environment
1. Install Android Studio
2. Install required SDK packages through SDK Manager
3. Configure an Android Virtual Device or connect a physical device
4. Sync project with Gradle files
5. Build and run the project

## Configuration
1. Firebase Setup:
    - Enable Authentication (Email/Password and Google Sign-In)
    - Set up storage database
    - Configure security rules

2. Google Sign-In Setup:
    - Add SHA-1 fingerprint to Firebase project
    - Configure OAuth consent screen
    - Add required credentials

## Contributing
1. Fork the repository
2. Create a feature branch:
   ```bash
   git checkout -b feature/new-feature
   ```
3. Commit your changes:
   ```bash
   git commit -am 'Add new feature'
   ```
4. Push to the branch:
   ```bash
   git push origin feature/new-feature
   ```
5. Submit a pull request

## Testing
- Run unit tests:
  ```bash
  ./gradlew test
  ```
- Run instrumented tests:
  ```bash
  ./gradlew connectedAndroidTest
  ```

## Project Status
Currently in development. 

## License
This project is submitted in partial fulfillment of the requirements of the Bachelor of Science in Informatics and Computer Science at Strathmore University.

## Acknowledgments
- Strathmore University School of Computing and Engineering Sciences

---
*Note: This README is part of the academic project documentation and may be updated as the project evolves.*
