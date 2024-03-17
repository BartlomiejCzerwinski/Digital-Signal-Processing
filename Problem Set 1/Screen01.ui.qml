

/*
This is a UI file (.ui.qml) that is intended to be edited in Qt Design Studio only.
It is supposed to be strictly declarative and only uses a subset of QML. If you edit
this file manually, you might introduce QML code that is not supported by Qt Design Studio.
Check out https://doc.qt.io/qtcreator/creator-quick-ui-forms.html for details on .ui.qml files.
*/
import QtQuick 6.6
import QtQuick.Controls 6.6


Rectangle {
    width: Constants.width
    height: Constants.height
    color: "#ffffff"



    Image {
        id: image
        x: 816
        y: 165
        width: 986
        height: 405
        source: "qrc:/qtquickplugin/images/template_image.png"
        fillMode: Image.PreserveAspectFit
    }

    Rectangle {
        id: rectangle
        x: 63
        y: 93
        width: 600
        height: 834
        color: "#e5e5e5"

        ComboBox {
            id: comboBox
            x: 21
            y: 28
            width: 250
            height: 40
            visible: true
            font.pointSize: 18
            editable: false
            textRole: ""
            displayText: "Funkcja"
            currentIndex: -1
            model: ListModel {
                ListElement { text: "Szum o rozkładzie jednostajnym" }
                ListElement { text: "Szum gaussowski" }
                ListElement { text: "Sygnał sinusoidalny" }
                ListElement { text: "Sygnał sinusoidalny wyprostowany jednopołówkowo" }
                ListElement { text: "Sygnał sinusoidalny wyprostowany dwupołówkowo" }
                ListElement { text: "Sygnał prostokątny" }
                ListElement { text: "Sygnał prostokątny symetryczny" }
                ListElement { text: "Sygnał trójkątny" }
                ListElement { text: "Skok jednostkowy" }
                ListElement { text: "Impuls jednostkowy" }
                ListElement { text: "Szum impulsowy" }
            }
        }

        Rectangle {
            id: rectangle1
            x: 20
            y: 134
            width: 319
            height: 393
            color: "#dfe9a6"
        }

        Text {
            id: text1
            x: 21
            y: 106
            width: 71
            height: 22
            text: qsTr("Parametry")
            font.pointSize: 14
        }

        Text {
            id: text4
            x: 34
            y: 166
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle2
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"

                TextInput {
                    id: textInput
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    overwriteMode: false
                    cursorVisible: false
                    maximumLength: 10
                    z: 0
                }
            }
        }

        Text {
            id: text5
            x: 34
            y: 286
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle5
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"
                TextInput {
                    id: textInput3
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    z: 0
                    overwriteMode: false
                    maximumLength: 10
                    cursorVisible: false
                }
            }
        }

        Text {
            id: text6
            x: 34
            y: 326
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle6
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"
                TextInput {
                    id: textInput4
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    z: 0
                    overwriteMode: false
                    maximumLength: 10
                    cursorVisible: false
                }
            }
        }

        Text {
            id: text8
            x: 34
            y: 206
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle3
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"
                TextInput {
                    id: textInput1
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    z: 0
                    overwriteMode: false
                    maximumLength: 10
                    cursorVisible: false
                }
            }
        }

        Text {
            id: text9
            x: 34
            y: 246
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle4
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"
                TextInput {
                    id: textInput2
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    z: 0
                    overwriteMode: false
                    maximumLength: 10
                    cursorVisible: false
                }
            }
        }

        Text {
            id: text10
            x: 34
            y: 366
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle7
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"
                TextInput {
                    id: textInput5
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    z: 0
                    overwriteMode: false
                    maximumLength: 10
                    cursorVisible: false
                }
            }
        }

        Text {
            id: text11
            x: 34
            y: 406
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle8
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"
                TextInput {
                    id: textInput6
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    z: 0
                    overwriteMode: false
                    maximumLength: 10
                    cursorVisible: false
                }
            }
        }

        Text {
            id: text12
            x: 34
            y: 466
            text: qsTr("Amplituda:")
            font.pixelSize: 18

            Rectangle {
                id: rectangle9
                x: 155
                y: 0
                width: 100
                height: 24
                color: "#ffffff"
                TextInput {
                    id: textInput7
                    x: 0
                    y: 2
                    width: 92
                    height: 22
                    color: "#000000"
                    text: qsTr("Text Input")
                    font.pixelSize: 18
                    horizontalAlignment: Text.AlignRight
                    z: 0
                    overwriteMode: false
                    maximumLength: 10
                    cursorVisible: false
                }
            }
        }

        Rectangle {
            id: rectangle10
            x: 380
            y: 134
            width: 200
            height: 200
            color: "#92b9a5"

            Button {
                id: button1
                x: 23
                y: 124
                text: qsTr("Button")
            }
        }
        Button {
            id: button
            x: 150
            y: 718
            width: 300
            height: 80
            text: qsTr("Rysuj Wykres")
            font.pointSize: 24
        }
    }

    Item {
        id: __materialLibrary__
    }

    Text {
        id: text7
        x: 13
        y: 32
        text: qsTr("Amplituda:")
        font.pixelSize: 18
    }

    Text {
        id: text2
        x: 442
        y: 200
        width: 71
        height: 22
        text: qsTr("Operacje")
        font.pointSize: 14
    }
}
