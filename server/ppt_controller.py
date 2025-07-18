import socket
import threading
import pyautogui
import pygetwindow as gw

HOST = '0.0.0.0'
PORT = 8080

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen(50)

def is_powerpoint_active():
    active_window = gw.getActiveWindow()
    return active_window and "PowerPoint" in active_window.title

def handle_client(client_socket, address):
    print(f"Connected to {address}")
    try:
        while True:
            command = client_socket.recv(1024).decode()
            if not command:
                break
            print(f"Received command: {command}")
            if command == "EXIT":
                print("Exit command received. Terminating the server...")
                server_socket.close()
                exit(0)
            if is_powerpoint_active():
                if command == "NEXT":
                    pyautogui.press('right')
                elif command == "PREVIOUS":
                    pyautogui.press('left')
                elif command == "START":
                    pyautogui.hotkey('f5')
                elif command == "STOP":
                    pyautogui.hotkey('esc')
                else:
                    print("Unknown command received.")
            else:
                print("PowerPoint is not the active window.")
    except Exception as e:
        print(f"Error: {e}")
    finally:
        client_socket.close()
        print(f"Client {address} disconnected.")

print(f"Server listening on port {PORT}...")

try:
    while True:
        client_socket, address = server_socket.accept()
        client_thread = threading.Thread(target=handle_client, args=(client_socket, address))
        client_thread.start()

except KeyboardInterrupt:
    print("\nServer terminated by user.")
    server_socket.close()
