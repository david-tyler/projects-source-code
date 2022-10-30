import SN

import PySimpleGUI as sg

def menu():
    layout = [
        [sg.Text("Select the following games to play")], [sg.Button("Snake")]
    ]
    window = sg.Window("Snake Game", layout)

    while True:
        event, values = window.read()
        if event == "Snake":
            snek = SN.start()
        if event == sg.WIN_CLOSED:
            break
    if SN.manual() == 'N':
        menu()
    
    window.close()

if __name__ == "__main__":
    menu()
