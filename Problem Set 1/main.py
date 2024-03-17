import sys
from PySide6.QtCore import QUrl
from PySide6.QtGui import QGuiApplication
from PySide6.QtQuick import QQuickView

if __name__ == "__main__":
    app = QGuiApplication(sys.argv)

    # Create a QML view
    view = QQuickView()
    view.setSource(QUrl("Screen01.ui.qml"))  # Replace "your_qml_file.ui.qml" with your QML file path

    # Show the QML view
    view.show()

    # Execute the application
    sys.exit(app.exec())