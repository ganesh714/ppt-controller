# PPT Controller

A tool to control PowerPoint presentations on your PC from your mobile device over a network.

## Requirements

- Python 3.x
- The following Python packages (install with `pip install -r server/requirements.txt`):
  - pyautogui
  - pygetwindow
- Windows OS (for firewall and network instructions)

## Setup Instructions

### 1. Clone the Repository

```
git clone <repo-url>
cd ppt-controller
```

### 2. Install Python Dependencies

```
cd server
pip install -r requirements.txt
```

## Usage Instructions

### Network Setup

#### If your mobile and PC are connected to different Wi-Fi networks:
1. **First, connect your mobile and PC to the same network (Wi-Fi).**
2. **On your PC:**
   - Open Command Prompt as Administrator.
   - Run the following command to allow ICMP (ping) through the firewall:
     ```
     netsh advfirewall firewall add rule name="ICMP Allow" protocol=icmpv4 dir=in action=allow
     ```
   - After your presentation is complete, remove the rule with:
     ```
     netsh advfirewall firewall delete rule name="ICMP Allow"
     ```

#### If you want to use your mobile's hotspot:
1. **On your mobile:**
   - Turn on the hotspot.
2. **On your laptop/PC:**
   - Connect to the mobile hotspot.

### Running the Server

1. **On your laptop/PC:**
   - Open Command Prompt.
   - Run:
     ```
     ipconfig
     ```
   - Note the IP address listed beside `IPv4 Address`.
   - Start the server:
     ```
     python ppt_controller.py
     ```

2. **On your mobile device:**
   - Open the PPT Controller app.
   - Enter the IP address you noted from the previous step.

## Stopping the Server
- To stop the server, press `Ctrl+C` in the command prompt.

## Notes
- Make sure PowerPoint is the active window on your PC for the controls to work.
- The server listens on port 8080 by default. Ensure this port is not blocked by your firewall.
- The ICMP firewall rule is only needed if your devices are on different networks and you have connectivity issues. 