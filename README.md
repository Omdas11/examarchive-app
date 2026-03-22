# ExamArchive — Android App

Native Android companion to [examarchive-v3](https://github.com/Omdas11/examarchive-v3).  
Built with **Kotlin** and **Jetpack Compose**, themed with **Material Design 3**.

## Features

| Tab | Description |
|-----|-------------|
| 📚 **Library** | Browse and filter exam papers by programme, category (DSC/DSM/SEC/IDC), and type (Theory/Practical). Live search by title, code, or department. |
| ✨ **Generate** | AI-powered study notes generator. Enter any topic, pick from quick suggestions, view and copy generated notes. |
| 🕒 **History** | Recently viewed exam papers. |
| ⚙️ **Settings** | Theme preference (System / Light / Dark) and course personalisation (DSC, DSM, SEC, IDC, AEC, VAC). |

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.0 |
| UI | Jetpack Compose + Material Design 3 |
| Navigation | Navigation Compose |
| Build | Gradle 8.7 (KTS) + Android Gradle Plugin 8.4 |
| CI | GitHub Actions |

## Download the APK

A debug APK is built automatically by CI on every push to `main` and on every pull request.

👉 **[Read the full download & install guide →](DOWNLOAD_APK.md)**

## Build Locally

```bash
git clone https://github.com/Omdas11/examarchive-app.git
cd examarchive-app
./gradlew assembleDebug
# APK → app/build/outputs/apk/debug/app-debug.apk
```

Requirements: JDK 17, Android SDK (compileSdk 34).
