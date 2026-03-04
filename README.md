# Voiceover-Android

Voiceover-Android is an Android utility that monitors music playback across different apps on a device and identifies the currently playing track. The application runs in the background and observes active media sessions using Android’s system media APIs.

It works with music players that implement Android's `MediaSession` framework, allowing the app to detect song metadata such as title and artist regardless of the music platform being used.

---

## Overview

Voiceover-Android listens for active media sessions on the device and captures playback metadata whenever a track changes. The application runs as a background service to ensure the observer remains active while music is playing.

The app does not depend on any specific streaming service. It works with a wide range of music players that expose playback information through Android's media session system.

---

## Features

* Detects currently playing music across multiple apps
* Captures track metadata such as **song title** and **artist**
* Runs in the background using a foreground service
* Uses Android MediaSession APIs for system-level playback monitoring

---

## How It Works

Voiceover-Android relies on Android’s media session architecture to observe music playback.

Key components used in the project:

* **NotificationListenerService** – Allows the app to access active media sessions.
* **MediaSessionManager** – Retrieves currently active media sessions on the device.
* **MediaController** – Observes metadata and playback state updates.
* **Foreground Service** – Keeps the monitoring process active while running in the background.

Whenever a music app updates its playback metadata (for example when a new song starts), Voiceover-Android detects the change and records the current track information.

---

## Requirements

* Android **8.0 (API 26)** or higher
* Notification access enabled for the app

---

## Permissions

The application requires the following permission:

**Notification Access**

This permission allows the app to observe active media sessions so it can determine what music is currently playing on the device.

---

## Installation

Clone the repository and open it in **Android Studio**.

```bash
git clone https://github.com/yourusername/Voiceover-Android.git
```

Build and run the project on a physical Android device.

---

## Usage

1. Install and open the application.
2. Enable **Notification Access** when prompted.
3. Start the background monitoring service.
4. Play music from any supported music app.
5. Voiceover-Android will detect the currently playing track.

---

## Tech Stack

* **Kotlin**
* Android MediaSession APIs
* Android Foreground Services
* Android NotificationListenerService

---

## License

This project is provided for educational and experimental purposes.
