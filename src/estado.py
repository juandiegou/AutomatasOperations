

class Estado(object):
    _nombre=None
    _final=False
    _inicial=False


    def __init__(self,nombre):
        self._nombre=nombre


    def getNombre(self):
        return self._nombre


    def isFinal(self):
        return self._inicial

    def isInicial(self):
        return self._inicial