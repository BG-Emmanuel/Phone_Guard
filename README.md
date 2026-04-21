# PhoneGuard Pro 🛡️

> **Intelligent multi-sensor phone security for the African market**

PhoneGuard Pro is a fully functional Android security application that detects intrusion attempts, silently captures photo and audio evidence of the intruder, and sends real-time SMS alerts with GPS coordinates to your emergency contacts — entirely without an internet connection.

Built by Level 3 students at **ICT University Cameroon** as part of the Android Application Development course.

---

## Table of contents

- [Overview](#overview)
- [Features](#features)
- [Sensors used](#sensors-used)
- [Screenshots](#screenshots)
- [Tech stack](#tech-stack)
- [Project structure](#project-structure)
- [Getting started](#getting-started)
- [Running the app](#running-the-app)
- [How it works](#how-it-works)
- [Team](#team)
- [Future improvements](#future-improvements)
- [License](#license)

---

## Overview

Most security apps available today were designed for markets with stable internet connections. In Cameroon and across Africa, when a phone is stolen the thief removes the SIM card — and those apps go completely silent.

PhoneGuard Pro was built to solve this specific problem. Every critical feature — alerts, location tracking, evidence capture — works over SMS. No internet required.

It also introduces **behavioral biometrics**: the app silently learns how the owner holds and uses the phone. If a different person picks it up, even with the correct PIN, their behavior deviates from the learned profile and the system flags an anomaly.

---

## Features

### Core security
| Feature | Description |
|---|---|
| **Grab detection** | Detects sudden violent grab or snatch motions using the accelerometer |
| **Intruder photo** | Silently captures a front-camera photo of anyone accessing the device |
| **Audio recording** | Records 15 seconds of surrounding audio on intrusion |
| **SMS alerts** | Sends real-time alerts with GPS location to emergency contacts via SMS |
| **GPS tracking** | Continuously reports device location every 2 minutes after theft is confirmed |

### Advanced protection
| Feature | Description |
|---|---|
| **Behavioral biometrics** | Learns the owner's movement patterns; flags anomalous behavior even with correct PIN |
| **Duress mode** | Enter a special PIN when forced to unlock — phone appears normal but silently sends SOS |
| **SIM swap detection** | Detects SIM card changes and alerts contacts from the new number before locking |
| **Camera cover detection** | Light sensor detects when someone covers the front camera |
| **Pocket exit detection** | Proximity sensor triggers an alert if phone leaves pocket without authentication |
| **Boot persistence** | Service auto-restarts after phone reboot so protection is never lost |

### Evidence & management
| Feature | Description |
|---|---|
| **Evidence locker** | Encrypted local storage of all intruder photos and audio recordings |
| **Alert history** | Full timestamped log of every intrusion event with evidence attached |
| **Emergency contacts** | Manage multiple contacts who receive SMS alerts |
| **Sensitivity control** | Adjust detection sensitivity: Low / Medium / High |

---

## Sensors used

```
┌─────────────────────┬──────────────────────────────────────────────────┐
│ Sensor              │ Security role                                    │
├─────────────────────┼──────────────────────────────────────────────────┤
│ Accelerometer       │ Grab/snatch detection, behavioral profiling      │
│ Gyroscope           │ Rotation pattern learning, anomaly detection     │
│ Front camera        │ Silent intruder photo capture                    │
│ Microphone          │ Surrounding audio recording                      │
│ GPS                 │ Real-time location tracking and SMS reporting    │
│ Light sensor        │ Camera cover detection                           │
│ Proximity sensor    │ Pocket exit detection                            │
└─────────────────────┴──────────────────────────────────────────────────┘
```

> The course requirement was a minimum of 2 sensors. PhoneGuard Pro uses 7.

---

## Screenshots

```
┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│  Dashboard   │  │ Alert History│  │   Settings   │  │  Evidence    │
│              │  │              │  │              │  │   Locker     │
│ Protection   │  │ GRAB_DETECTED│  │ Sensitivity  │  │ INTRUDER_    │
│  ACTIVE  ●  │  │ 12/04 14:32  │  │ ─────●────  │  │ 20240412.jpg │
│              │  │              │  │              │  │              │
│ Total Alerts │  │ WRONG_PIN    │  │ Duress PIN   │  │ INTRUDER_    │
│     3        │  │ 12/04 09:15  │  │ ••••••      │  │ 20240412.3gp │
└──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘
```

---

## Tech stack

```
Language          Kotlin
IDE               Android Studio Hedgehog 2023+
Min SDK           API 26 (Android 8.0)
Target SDK        API 34 (Android 14)

Architecture      Single-activity + multiple activities
Background        Android Foreground Service (START_STICKY)
Database          Room (SQLite)
Camera            CameraX
Location          Google Play Services — FusedLocationProvider
SMS               Android SmsManager API
Sensors           Android SensorManager API
Version control   Git + GitHub
```

---

## Project structure

```
PhoneGuardPro/
├── app/
│   ├── src/main/
│   │   ├── java/com/phoneguard/pro/
│   │   │   │
│   │   │   ├── PhoneGuardApp.kt              # Application class
│   │   │   │
│   │   │   ├── data/                         # Partner 2 — database layer
│   │   │   │   ├── AlertEvent.kt             # Room entity — alert records
│   │   │   │   ├── AppDatabase.kt            # Room database instance
│   │   │   │   ├── Daos.kt                   # Data access objects
│   │   │   │   ├── AlertRepository.kt        # Repository pattern
│   │   │   │   └── PreferencesManager.kt     # SharedPreferences wrapper
│   │   │   │
│   │   │   ├── security/                     # Partner 1 — security engine
│   │   │   │   ├── SensorMonitorService.kt   # Core foreground service
│   │   │   │   ├── BehaviorProfileManager.kt # Behavioral biometrics engine
│   │   │   │   ├── GPSTracker.kt             # Location tracking
│   │   │   │   ├── SMSAlertManager.kt        # SMS alert delivery
│   │   │   │   ├── IntruderCaptureManager.kt # Camera + audio capture
│   │   │   │   └── Receivers.kt              # Boot + SIM change receivers
│   │   │   │
│   │   │   └── ui/                           # Partner 2 — user interface
│   │   │       ├── MainActivity.kt           # Dashboard
│   │   │       ├── AlertHistoryActivity.kt   # Alert log with evidence
│   │   │       ├── SettingsActivity.kt       # App settings
│   │   │       ├── EmergencyContactsActivity.kt
│   │   │       └── EvidenceLockerActivity.kt
│   │   │
│   │   ├── res/
│   │   │   ├── layout/                       # All XML screen layouts
│   │   │   ├── drawable/                     # Icons and shape drawables
│   │   │   └── values/                       # Colors, strings, themes
│   │   │
│   │   └── AndroidManifest.xml
│   │
│   └── build.gradle
│
├── INSTALLATION_AND_RUN_GUIDE.txt
├── README.md
└── build.gradle
```

---

## Getting started

### Requirements

- **Android Studio** Hedgehog 2023.1.1 or newer — [Download](https://developer.android.com/studio)
- **Java JDK 17** — [Download](https://adoptium.net/temurin/releases/)
- **Git** — [Download](https://git-scm.com)
- **Android device** running Android 8.0 (API 26) or higher
- A **data USB cable** to connect your device

> Many features require physical sensors. Test on a real device, not an emulator.

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/PhoneGuardPro.git
cd PhoneGuardPro
```

### Open in Android Studio

1. Open Android Studio
2. Click **File → Open**
3. Select the `PhoneGuardPro` folder
4. Wait for Gradle sync to complete (requires internet, ~5–15 minutes first time)
5. Click **Trust Project** if prompted

---

## Running the app

### On a physical device (recommended)

**Step 1 — Enable Developer Options on your phone**
```
Settings → About Phone → tap Build Number 7 times
```

**Step 2 — Enable USB Debugging**
```
Settings → Developer Options → USB Debugging → ON
```

**Step 3 — Connect and run**
```
1. Plug in your phone via USB
2. Accept "Allow USB Debugging" on the phone
3. Select your device in the Android Studio toolbar
4. Press Shift + F10 or click the green ▶ Run button
```

**Step 4 — Grant permissions on first launch**

When the app opens, grant all requested permissions:
- Camera
- Microphone
- Location (Allow all the time)
- Phone
- Send and receive SMS

### Required permissions

```xml
CAMERA
RECORD_AUDIO
SEND_SMS
READ_PHONE_STATE
ACCESS_FINE_LOCATION
FOREGROUND_SERVICE
RECEIVE_BOOT_COMPLETED
VIBRATE
```

---

## How it works

### Alert levels

```
Level 1 — MEDIUM (suspicious)
  └── Logged silently, no SMS sent
      Example: behavioral anomaly, pocket exit

Level 2 — HIGH (confirmed suspicious access)
  └── SMS alert sent with location
      Example: wrong PIN × 3, camera covered

Level 3 — CRITICAL (confirmed intrusion)
  └── Loud alarm + intruder photo + audio + SMS + continuous GPS tracking
      Example: violent grab detected, wrong PIN × 5
```

### Behavioral biometrics flow

```
Phase 1 — Learning (first 500 sensor samples)
  └── App records owner's average accelerometer patterns
  └── Progress shown in dashboard as "Learning..."

Phase 2 — Active protection
  └── Every interaction is scored against the learned profile
  └── Deviation > 35% from baseline = MEDIUM alert triggered
  └── Dashboard shows "Profile: Ready"
```

### Offline-first SMS alert format

```
PHONEGUARD PRO - CRITICAL ALERT!
Your phone is being accessed by an intruder!
Location: https://maps.google.com/?q=3.848,11.502
Time: Mon Apr 14 2025 15:32:11
Evidence photo captured.
Reply LOCK to lock device.
```

---

## Team

| Role | Responsibilities |
|---|---|
| **Partner 1** — Security Engine | `SensorMonitorService`, `GPSTracker`, `SMSAlertManager`, `IntruderCaptureManager`, `BehaviorProfileManager`, `BootReceiver`, `SIMChangeReceiver` |
| **Partner 2** — UI & Database | `MainActivity`, `AlertHistoryActivity`, `SettingsActivity`, `EmergencyContactsActivity`, `EvidenceLockerActivity`, Room Database, all XML layouts |

**Institution:** ICT University Cameroon
**Course:** Android Application Development
**Level:** 3
**Year:** 2024–2025

---

## Future improvements

- [ ] **ML Kit face recognition** — identify specific individuals, not just unfamiliar faces
- [ ] **Safe zone geofencing** — relax security inside trusted GPS zones (home, campus)
- [ ] **Cloud evidence backup** — auto-upload photos and audio to email/cloud before factory reset
- [ ] **WhatsApp alert integration** — send evidence directly via WhatsApp for contacts without SMS
- [ ] **Remote lock via SMS** — owner sends a coded SMS to lock the device remotely
- [ ] **Wear OS companion** — receive alerts on a smartwatch even if phone is out of reach

---

## Branch structure

```
main                    Production-ready code
├── feature/security-engine    Partner 1 — sensor and alert logic
└── feature/ui-database        Partner 2 — screens and database
```

---

## Contributing

This is a university project. If you find a bug or want to suggest an improvement:

1. Fork the repository
2. Create a new branch: `git checkout -b fix/your-fix-name`
3. Commit your changes: `git commit -m "Fix: description of fix"`
4. Push to your fork: `git push origin fix/your-fix-name`
5. Open a Pull Request

---

## License

```
MIT License

Copyright (c) 2025 PhoneGuard Pro Team — ICT University Cameroon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software to use, copy, modify, merge, and distribute it, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
```

---

<div align="center">

Built with dedication at **ICT University Cameroon** 🇨🇲

*"Securing your device. Protecting your life."*

</div>
