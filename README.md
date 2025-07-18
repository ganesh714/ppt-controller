# 📱 PPT Controller

A mobile + Python-based tool to control PowerPoint presentations from your phone — even using your **volume buttons** — over a local network.

---

## 🎯 Key Features

- 🖱️ Control PowerPoint slides remotely from Android
- 🔊 **Use mobile's volume buttons to change slides**
  - Volume Down → Next Slide
  - Volume Up → Previous Slide
- 📡 Works over Wi-Fi or mobile hotspot
- 🔐 Windows firewall instructions included

---

## 📁 Project Structure

```
ppt-controller/
├── android/    # Native Android app (Java or Kotlin)
├── server/     # Python server code (uses pyautogui to control PPT)
├── README.md
└── .gitignore
```

---

## 🧰 Requirements

- Python 3.x
- Windows OS
- Python packages:
  - `pyautogui`
  - `pygetwindow`

```bash
pip install -r server/requirements.txt
```

---

## 🚀 Setup Instructions

### 1. Clone the Repository

```
git clone <repo-url>
cd ppt-controller
```

### 2. Install Python Dependencies

```bash
cd server
pip install -r requirements.txt
```

---

## 🌐 Network Setup

### Option 1: Same Wi-Fi
- Connect mobile and PC to the same Wi-Fi.
- On PC, open Command Prompt and allow ICMP:

```bash
netsh advfirewall firewall add rule name="ICMP Allow" protocol=icmpv4 dir=in action=allow
```

- To remove this rule later:

```bash
netsh advfirewall firewall delete rule name="ICMP Allow"
```

### Option 2: Mobile Hotspot
- Turn on mobile hotspot.
- Connect your PC to it.

---

## 🖥️ Run the Python Server

```bash
cd server
python ppt_controller.py
```

- Note your PC's IPv4 address using `ipconfig` — enter this IP into the Android app.

---

## 📱 Use the Android App
- Open the app.
- Enter the IP address.
- Use the volume buttons to control slides!

---

## 🛑 Stop the Server
- Press Ctrl + C in the command prompt.

---

## Notes
- Make sure PowerPoint is the active window on your PC for the controls to work.
- The server listens on port 8080 by default. Ensure this port is not blocked by your firewall.
- The ICMP firewall rule is only needed if your devices are on different networks and you have connectivity issues. 