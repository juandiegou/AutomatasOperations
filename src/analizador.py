
class Analizador():
    
    def __init__(self,alfabeto):
        self._expresion=None
        self._estados=[]
        self._transiciones=[]
        self._alfabeto=alfabeto
        self._alfabeto.add('(',')','|','+','*','?','@') #el @ significa vacio

    def setExpresion(self,expresion):
        self._expresion=expresion


    def getExpresion(self):
        return self._expresion

    def analizar(self,expresion):
        self._estados=expresion
        for x in self._estados:
            if x not in self._alfabeto:
                return False
        return True
