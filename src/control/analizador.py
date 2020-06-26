
class Analizador():
    
   
    def __init__(self,alfabeto):
        self.__constantes=['(',')','|','+','*','?','@']
        self._expresion=None
        self._estados=[]
        self._transiciones=[]
        self._alfabeto=alfabeto
        self._alfabeto.extend(self.__constantes)
    
    
    def setExpresion(self,expresion):
        self._expresion=expresion


    def getExpresion(self):
        return self._expresion

    def verificar(self,expresion):
        for x in self._estados:
            if x not in self._alfabeto:
                print(x ,"No esta")
                return False
        return True

    def analizar(self,expresion):
        if self.verificar(expresion):
            self._estados=expresion
