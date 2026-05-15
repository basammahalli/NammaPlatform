# Namma-Platform 🚉

**Namma-Platform** is a high-contrast, offline-first Android application designed to provide railway station and coach guidance for passengers in Karnataka, India. Built with **Kotlin** and **Jetpack Compose**, it features full bilingual support (Kannada & English) and native Text-to-Speech (TTS) announcements.

## 🌟 Key Features

- **Bilingual Interface:** Toggle between Kannada and English instantly across the entire app.
- **Smart Navigation:** Drill down from a list of stations to specific trains and their departure times.
- **Coach Layout Visualization:** View the exact sequence of coaches (e.g., Engine -> GS -> S1 -> S2 -> Guard) for any train.
- **TTS Announcements:** Listen to automated platform announcements in Kannada and English.
- **Role-Based Access:** 
  - **Passenger:** Search for stations and view train details.
  - **Admin:** Management dashboard to add/update train and platform information.
- **Secure Authentication:** User registration and login with strict validation (10-digit phone, @gmail.com email).
- **Accessibility First:** High-contrast color palette (Namma Blue & Namma Yellow) designed for visibility in various lighting conditions.

## 🛠️ Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Navigation:** Compose Navigation
- **Data:** Local JSON repository with in-memory session management.
- **Media:** Android TextToSpeech (TTS) API.
- **Build System:** Gradle (Kotlin DSL).

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17+
- Android SDK 34 (Upside Down Cake) or newer

### Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/NammaPlatform.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync and download dependencies.
4. Run the app on an emulator or a physical device.

## 📁 Project Structure
- `app/src/main/java/com/namma/platform/`: Core source code.
  - `ui/screens/`: Composable screens (Login, SignUp, Dashboard, etc.).
  - `data/`: Data models and local repository.
  - `utils/`: Language management, Session management, and TTS helpers.
- `app/src/main/assets/`: Contains `data.json` with station and train info.

## 🤝 Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License
This project is licensed under the MIT License.
