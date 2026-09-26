# Poradnik - Safety Guide Android App

A safety checklist application based on official Polish government publications: "Poradnik Bezpieczeństwa" (2025) and "Instrukcje reagowania - zagrożenie atakiem z powietrza" (MWSWiA/RCB). The app helps you prepare for emergencies with checklists, supply management, and emergency numbers — all stored offline on your device.

## Disclaimer

**This application is NOT an official government application.**

This application was developed based on official publications: "Poradnik Bezpieczeństwa" (Government of the Republic of Poland, 2025) and "Instrukcje reagowania - zagrożenie atakiem z powietrza" (MWSWiA/RCB). The information contained in this application is for informational purposes only. In case of danger, always follow the instructions of rescue services and state authorities.

**Sources:**
- [poradnikbezpieczenstwa.gov.pl](https://poradnikbezpieczenstwa.gov.pl)
- [gov.pl/web/rcb](https://www.gov.pl/web/rcb)

## Features

- **Checklist** - 21 categories with over 150 items to track
- **Supply Management** - Track quantities and expiration dates
- **Household Size** - Automatic supply calculation based on household size
- **Emergency Numbers** - Quick access to emergency phone numbers
- **Export/Import** - Backup your data to JSON file with validation
- **Offline** - All data stored locally on your device
- **Dark Theme** - Supports system light/dark mode

## Why Not on Google Play Store?

This application is **not published on Google Play Store** for privacy reasons.

- **No data collection** — The app does not collect, transmit, or share any personal data
- **No analytics or tracking** — No Google Analytics, Firebase, or any third-party tracking
- **No internet permission** — The app works entirely offline; all data stays on your device
- **No account required** — No login, no registration, no cloud sync

The app is designed to be a **private, offline tool**. Publishing on Google Play Store would require integrating Google Play services, which contradicts this privacy-first approach.

## Is It Safe?

- **Open source** — Full source code is available under the GPLv3 License; you can verify what the app does
- **Signed APK** — The release APK is cryptographically signed; you can verify its authenticity
- **No network calls** — The app makes zero network requests; it cannot send data anywhere
- **Local storage only** — All data is stored locally on your device using SQLite (Room) and Android DataStore. Nothing leaves your phone.

## Installation

### For Regular Users (APK)

1. Download the `SafetyGuide-1.0.0-release.apk` file to your phone
2. Go to **Settings → Security** (or **Settings → Apps → Special access**)
3. Enable **Install unknown apps** for your file manager or browser
4. Open the downloaded `.apk` file and tap **Install**

> **Note:** Google Play Protect may show a warning when installing apps from outside the Play Store. This is normal for any app not distributed through Google Play. You can safely tap **"Install anyway"** or **"Install without scanning"** — the app contains no malware or harmful code.

### For Developers (Android Studio)

1. Open the `android/` folder in Android Studio
2. Wait for Gradle sync
3. Click **Run** on your device or emulator

### For Advanced Users (ADB)

#### Connecting via WiFi

1. On your phone, go to **Settings → About phone**
2. Tap **Build number** 7 times to enable Developer Options
3. Go to **Settings → System → Developer Options**
4. Enable **USB debugging**
5. Enable **Wireless debugging**
6. Tap **Wireless debugging** to open its settings
7. Note the **IP address & Port** shown (you'll need this for `adb connect`)
8. On your computer, run: `adb pair <ip>:<port>` (use the IP/port from the wireless debugging settings)
9. Enter the pairing code displayed on your phone
10. Run: `adb connect <ip>:<port>` to connect wirelessly (you may need to re-check the port after pairing)

**Note:** The port changes after each pairing. Always check the current port in Developer Options → Wireless debugging.

#### Install / Uninstall

- **Install debug version:** `adb install app/build/outputs/apk/debug/SafetyGuide-1.0.0-debug.apk`
- **Install release version:** `adb install app/build/outputs/apk/release/SafetyGuide-1.0.0-release.apk`
- **Uninstall:** `adb uninstall dev.pol.safetyguide`

## Technical Information

### Requirements

- Android 8.0 (API 26) or higher
- Android Studio Ladybug (2024.2) or newer
- JDK 17

### Tech Stack

- **Kotlin** 2.1.0 - Programming language
- **Jetpack Compose** (BOM 2025.01.00) - Declarative UI
- **Material Design 3** - Modern design system
- **Room** 2.6.1 - SQLite database
- **Hilt** 2.51.1 - Dependency injection
- **DataStore** 1.1.1 - Preferences storage
- **Navigation Compose** 2.8.5 - Type-safe navigation
- **Gson** 2.11.0 - JSON serialization for backup

### Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with a **Repository pattern** for data access.

```
┌─────────────────────────────────────────────────────────────┐
│                         UI Layer                            │
│  DisclaimerScreen │ HomeScreen │ CategoryScreen │           │
│  EmergencyScreen  │ SettingsScreen                          │
├─────────────────────────────────────────────────────────────┤
│                    ViewModel Layer                          │
│  HomeViewModel │ CategoryViewModel │ SettingsViewModel      │
├─────────────────────────────────────────────────────────────┤
│                    Repository Layer                         │
│  ChecklistRepository │ PreferencesRepository │ BackupRepo   │
├─────────────────────────────────────────────────────────────┤
│                      Data Layer                              │
│  Room Database │ DataStore Preferences │ Gson (JSON I/O)    │
└─────────────────────────────────────────────────────────────┘
```

### Data Flow

Data flows unidirectionally:
- **Down**: Room → Repository → ViewModel → Composable (via `StateFlow`)
- **Up**: User action → ViewModel method → Repository → DAO → Room

### Key Components

| Layer | Component | Purpose |
|-------|-----------|---------|
| **Data** | `AppDatabase` | Room database with 3 entities, 1 DAO, migrations |
| **Data** | `SeedData` | Initial data: 21 categories, 150+ items, 35 supplies |
| **Data** | `SupplyCalculator` | Business logic for supply quantity formulas |
| **Repository** | `ChecklistRepository` | Wraps Room DAO, export/import/reset |
| **Repository** | `PreferencesRepository` | DataStore access (household, days, disclaimer) |
| **Repository** | `BackupRepository` | JSON export/import with validation and sanitization |
| **ViewModel** | `HomeViewModel` | Category grid, progress tracking, household size |
| **ViewModel** | `CategoryViewModel` | Single category detail, item/supply CRUD |
| **ViewModel** | `SettingsViewModel` | Preferences management, backup operations |
| **UI** | 5 Screens | Disclaimer, Home, Category, Emergency, Settings |
| **UI** | 4 Components | CategoryCard, ChecklistItemCard, SupplyItemCard, SquareProgressIndicator |
| **DI** | `AppModule` | Hilt module providing Database, DAO, Repositories |

### Project Structure

```
android/
├── app/src/main/java/dev/pol/safetyguide/
│   ├── PrepperApp.kt              # Hilt Application class
│   ├── MainActivity.kt            # Single Activity with Compose
│   ├── data/
│   │   ├── db/                    # Room database, DAO, seed data
│   │   │   ├── AppDatabase.kt
│   │   │   ├── ChecklistDao.kt
│   │   │   └── SeedData.kt
│   │   ├── model/                 # Data models (Room entities)
│   │   │   ├── Category.kt
│   │   │   ├── ChecklistItem.kt
│   │   │   ├── SupplyItem.kt
│   │   │   ├── EmergencyNumber.kt
│   │   │   └── CategoryWithProgress.kt
│   │   ├── repository/            # Repository layer
│   │   │   ├── ChecklistRepository.kt
│   │   │   ├── PreferencesRepository.kt
│   │   │   └── BackupRepository.kt
│   │   └── util/
│   │       └── SupplyCalculator.kt
│   ├── di/
│   │   └── AppModule.kt           # Hilt dependency injection
│   ├── ui/
│   │   ├── components/            # Reusable UI components
│   │   │   ├── CategoryCard.kt
│   │   │   ├── ChecklistItemCard.kt
│   │   │   ├── SupplyItemCard.kt
│   │   │   └── SquareProgressIndicator.kt
│   │   ├── navigation/
│   │   │   ├── Screen.kt          # Route definitions
│   │   │   └── NavGraph.kt        # Navigation graph
│   │   ├── screens/               # Screen composables
│   │   │   ├── DisclaimerScreen.kt
│   │   │   ├── HomeScreen.kt
│   │   │   ├── CategoryScreen.kt
│   │   │   ├── EmergencyScreen.kt
│   │   │   └── SettingsScreen.kt
│   │   └── theme/
│   │       ├── Color.kt
│   │       ├── Theme.kt
│   │       └── Type.kt
│   └── viewmodel/
│       ├── HomeViewModel.kt
│       ├── CategoryViewModel.kt
│       └── SettingsViewModel.kt
├── app/src/main/res/              # Resources (strings, themes)
├── app/src/test/                  # Unit tests
├── build.gradle.kts               # Root build file
├── settings.gradle.kts            # Module configuration
├── ATTRIBUTION.md                 # Content attribution
├── LICENSE                        # GPLv3 License
└── README.md                      # This file
```

### Building

```bash
# Debug build (no signing required)
./gradlew assembleDebug

# Release build (signed APK)
# First, create a keystore (one-time):
# keytool -genkey -v -keystore ~/release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias safetyguide
#
# Then set environment variables:
# export KEYSTORE_PASSWORD="your-password"
# export KEY_PASSWORD="your-password"

./gradlew assembleRelease

# AAB for Google Play Store (not needed for direct install)
./gradlew bundleRelease

# Run unit tests
./gradlew test

# Clean
./gradlew clean
```

**APK location:** `app/build/outputs/apk/release/SafetyGuide-1.0.0-release.apk`

### Testing

The project includes unit tests:

- **`SupplyCalculatorTest`** - 12 tests covering supply calculation formulas, Polish grammar, and edge cases
- **`ChecklistRepositoryTest`** - 3 tests for repository data access

Test dependencies:
- JUnit 4.13.2
- Kotlinx Coroutines Test 1.9.0
- MockK 1.13.13
- Compose UI Test JUnit4

## License

This project is distributed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.

## Attribution

Based on official Polish government publications:

- **"Poradnik Bezpieczeństwa"** (Safety Guide) — Government of the Republic of Poland, 2025
- **"Instrukcje reagowania - zagrożenie atakiem z powietrza"** (Air Attack Response Instructions) — MWSWiA/RCB

The content from these publications is in the public domain under Article 4 of the Polish Copyright Act.

See [ATTRIBUTION.md](ATTRIBUTION.md) for detailed attribution information.

---

**Note:** This application is Poland-specific. Emergency numbers and safety procedures are based on Polish standards.
