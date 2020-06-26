from ventana_ui import *
from PyQt5 import QtWidgets
from PyQt5.QtGui import QPainter, QPixmap
from PyQt5.QtCore import *
import threading
from PyQt5.Qt import QGraphicsScene, QLabel


class MainWindow(QtWidgets.QMainWindow, Ui_gui):
    def __init__(self, *args, **kwargs):
        QtWidgets.QMainWindow.__init__(self, *args, **kwargs)
        self.setupUi(self)
        self.setWindowTitle("Automatas")
        #self.setWindowIcon()
        self.actionAbrir.triggered.connect(self.abrir)
        self.actionAFND_AFD.triggered.connect(self.transformar)
        self.actionMinimizar.triggered.connect(self.minimizar)
        self.actionEvaluar_expresion.triggered.connect(self.eval_expresion)
        self.path='C:\\Users\\juand\\Desktop\\killer\\image.png'
        
        

    def abrir(self):
        print("abrir")


    def transformar(self):
        print("trasnformar")
        #self.paintEvent()





    def paintEvent(self,event):
        image= QPixmap(self.path)
        self.label.setPixmap(image)
        self.label.setSizeIncrement(image.width(), image.height())
        self.resize(image.width(), image.height())
        #painter.drawPixmap(self.rect(),image)
       




        
    def minimizar(self):
        print("minimizar")

    def eval_expresion(self):
        print("evaluacion")

  

if __name__ == "__main__":
    app = QtWidgets.QApplication([])
    window = MainWindow()
    window.show()
    app.exec_()