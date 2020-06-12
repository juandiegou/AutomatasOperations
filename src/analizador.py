
class Analizador():
    
    def __init__(self):
        self.__expresion=None
        __estados=[]
        __transiciones=[]



    def setExpresion(self,expresion):
        self.__expresion=expresion


    def getExpresion(self):
        return self.__expresion

    def analizar(self,expresion):
        self.__estados=expresion
        for x in self.__estados:
            print(x)
        print(self.__estados,type(self.__estados))
