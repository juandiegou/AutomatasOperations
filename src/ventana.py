from ventana_ui import *

class MainWindow(QtWidgets.QMainWindow, Ui_gui):
    def __init__(self, *args, **kwargs):
        QtWidgets.QMainWindow.__init__(self, *args, **kwargs)
        self.setupUi(self)
        self.actionAbrir.triggered.connect(self.abrir)
        self.actionAFND_AFD.triggered.connect(self.transformar)
        self.actionMinimizar.triggered.connect(self.minimizar)
        self.actionEvaluar_expresion.triggered.connect(self.eval_expresion)
        

    def abrir(self):
        print("abrirs")


    def transformar(self):
        print("trasnformar")

    def minimizar(self):
        print("minimizar")

    def eval_expresion(self):
        print("evaluacion")

  

if __name__ == "__main__":
    app = QtWidgets.QApplication([])
    window = MainWindow()
    window.show()
    app.exec_()