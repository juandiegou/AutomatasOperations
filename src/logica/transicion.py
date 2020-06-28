


class Transicion:
    
    def __init__(self, EstadoI, EstadoF,valor):
        self._EstadoI=EstadoI
        self._EstadoF=EstadoF
        self._valor=valor

    def getEstadoI(self):
        return self._EstadoI


    def getEstadoF(self):
        return self._EstadoF


    def getValor(self):
        return self._valor

    def getTransicion(self):
        return(self._EstadoI,self._EstadoF,self._valor)
       