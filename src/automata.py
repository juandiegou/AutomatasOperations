
import itertools

class Automata(object):

    _estados=[]
    _transiciones=[]
    isanAFND=None
    isanAFD=None

    def __init__(self,estados,transiciones):
        self._estados=estados
        self._transiciones=transiciones
        self.automata=self.genereAutomata(self._estados,self._transiciones)
        self._isanAFD=self.isAFD()
        self._isanAFND=self.isAFND()



    def getISAFND(self):
        return self._isanAFND

    def getISAFD(self):
        return self._isanAFD

    
    def genereAutomata(self,estados,transiciones):
        automata={}
        for e in estados:
            #print(e.getNombre())
            automata[e.getNombre()]=[]

        #print(automata)
        

        #

        for t in transiciones:
           # print(t.getEstadoI())
            automata[t.getEstadoI()].append((t.getEstadoI(),t.getEstadoF(),t.getValor() ))

        #print(automata)
        return automata

    def getEstados(self):
        return self._estados

    def getTransiciones(self):
        return self._transiciones

    def getAutomata(self):
        return self._estados,self._transiciones


    def isAFND(self):
        #print(self.automata)
        for e in self.automata.keys():
            if self.validaTransiciones(self.automata[e]):
                return True
       

        return False
        

    def validaTransiciones(self,lista):
        listado=sorted(itertools.combinations(lista,2))
        l=[]
        for x in listado:
            for y in x:
                l.append(y)
            if self.compararTuplas(l[0],l[1]):
                return True
            l=[]
        return False


    def compararTuplas(self,tupla1,tupla2):      
        return tupla1[:2]==tupla2[:2]


    def isAFD(self):
        
        for e in self.automata.keys():
            if not (self.validaTransiciones(self.automata[e])):
                return True
        return False

    def afndAafd(self):
        pass

    def minimizar(self):
        pass

    def recorrer(self):
        pass



