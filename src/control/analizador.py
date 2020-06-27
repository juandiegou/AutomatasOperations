
class Analizador():
    
   
    def __init__(self,alfabeto):
        self.__constantes=['(',')','|','+','*','?','@','.']
        self.__operadores=['|','+','*','?','.']
        self.__precedencia={'+':3,'?':3,'*':3,'.':2,'|':1}
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
            salida=[]
            temporal=[]
            self._estados=expresion
            for x in self._estados:
                if x not in self.__constantes:
                    salida.append(x)
                if x in self.__operadores:
                    if  temporal:
                        if self.__Evaluaprecedencia(x)>self.__Evaluaprecedencia(temporal[len(temporal)-1]):
                            temporal.append(x)
                        else:   
                            salida.append(temporal.pop(len(temporal)-1))
                            temporal.append(x)
                    else:
                        temporal.append(x)    
                if x==')':
                    if temporal:
                        temporal.reverse()
                        salida.extend(temporal)
                        temporal=[]      
            if temporal:
                salida.extend(temporal)
            print(salida,temporal)
                    


    def __Evaluaprecedencia(self,x):
        return self.__precedencia[x]
