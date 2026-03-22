# 📲 How to Download & Preview the ExamArchive APK

The CI pipeline automatically builds a debug APK on every push to `main` and on every pull request targeting `main`. No local Android setup required — just follow the steps below.

---

## Step 1 — Trigger a Build

The APK is built automatically whenever:

| Event | Branch |
|-------|--------|
| Push | `main` |
| Pull Request | targeting `main` |

If you've just opened or updated a PR (or pushed to `main`), the build starts within seconds. No manual action needed.

---

## Step 2 — Open the Actions Tab

1. Go to the repository on GitHub: **`https://github.com/Omdas11/examarchive-app`**
2. Click the **Actions** tab at the top of the page.

   ![Actions tab](https://docs.github.com/assets/cb-15465/mw-1440/images/help/repository/actions-tab.webp)

---

## Step 3 — Find Your Workflow Run

1. In the left sidebar, select **Android CI**.
2. Click the most recent run that matches your branch or PR (look for a ✅ green checkmark — it means the build succeeded).

   > ⚠️ If you see a ❌ red cross, the build failed. Click the run to read the logs and fix any errors before trying again.

---

## Step 4 — Download the APK Artifact

1. Scroll to the bottom of the workflow run page.
2. Under the **Artifacts** section, you will see **`examarchive-debug`**.
3. Click it — the browser will download a ZIP file called `examarchive-debug.zip`.

   ```
   Artifacts
   ┌──────────────────────────────────┐
   │  📦 examarchive-debug   (↓ ZIP) │
   └──────────────────────────────────┘
   ```

4. Extract the ZIP — it contains the file **`app-debug.apk`**.

> Artifacts are kept for **14 days**. Download before they expire.

---

## Step 5 — Install on an Android Device

### Option A — Sideload via USB (recommended)

1. On your Android phone, go to **Settings → About Phone** and tap **Build Number** 7 times to enable Developer Options.
2. Go to **Settings → Developer Options** and enable **USB Debugging**.
3. Connect your phone to your computer via USB.
4. Open a terminal and run:

   ```bash
   adb install path/to/app-debug.apk
   ```

5. The app **ExamArchive** will appear in your app drawer.

### Option B — Transfer via file manager

1. On your Android phone, go to **Settings → Security** (or **Privacy**) and enable **Install from Unknown Sources** (or "Install Unknown Apps") for your file manager / browser.
2. Copy `app-debug.apk` to your phone (via USB cable, Google Drive, email attachment, etc.).
3. Open the file on your phone and tap **Install**.

### Option C — Wireless install with ADB over Wi-Fi (Android 11+)

1. Enable **Wireless Debugging** in Developer Options on your phone.
2. Pair your computer using the pairing code shown on-screen.
3. Run:

   ```bash
   adb connect <device-ip>:<port>
   adb install path/to/app-debug.apk
   ```

---

## Step 6 — Launch the App

Open the **ExamArchive** app from your launcher. You will see the main screen with the four bottom navigation tabs:

| Tab | What it does |
|-----|-------------|
| 📚 **Library** | Browse & filter exam papers by programme, category, and type |
| ✨ **Generate** | AI-powered study notes generator |
| 🕒 **History** | Recently viewed papers |
| ⚙️ **Settings** | Theme preference and course personalisation |

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| Build failed (❌ in Actions) | Click the run → expand the failing step → read the error log → open an issue |
| `INSTALL_FAILED_UPDATE_INCOMPATIBLE` | Uninstall the old version first: `adb uninstall com.example.examarchive` |
| `adb: command not found` | Install [Android Platform Tools](https://developer.android.com/tools/releases/platform-tools) |
| Artifact expired (> 14 days) | Push an empty commit to re-trigger the build: `git commit --allow-empty -m "rebuild" && git push` |
| App crashes on launch | Check `adb logcat` for the stack trace and open an issue with the output |

---

## Build Locally (Optional)

If you have Android Studio or the Android SDK installed:

```bash
# Clone the repo
git clone https://github.com/Omdas11/examarchive-app.git
cd examarchive-app

# Build the debug APK
./gradlew assembleDebug

# The APK is at:
# app/build/outputs/apk/debug/app-debug.apk
```

Requirements: **JDK 17**, **Android SDK** with `compileSdk = 34`.
