

class Estado(object):

    def __init__(self,nombre,final=False,inicial=False):
        self._nombre=nombre
        self._inicial=inicial
        self._final=final

    def setIsInicial(self,x):
        """[permite cambiar el valor de inicial]

        Args:
            x ([boolean]): [True si es inicial]
        """
        self._inicial=x
    
    def setIsFinal(self,x):
        """[permite cambiar el valor de final]

        Args:
            x ([boolean]): [True si es final]
        """
        self._final=x

    def getNombre(self):
        return self._nombre

    def isFinal(self):
        return self._final==True

    def isInicial(self):
        return self._inicial==True