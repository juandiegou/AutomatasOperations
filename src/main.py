from control.analizador import Analizador
import random
import itertools
import string
from logica.estadoI import EstadoI
from logica.estadoF import EstadoF
from logica.estado import Estado
from logica.transicion import Transicion
from logica.afd import AFD
from control.graphic import Graphic

class Main():
    def __init__(self):
        
        a=Analizador(['A','B','C','d','e','f','g','h'])
        #print(a.verificar('(a.b)+.a|a*.b.b?'))
        #a.analizar('(a+bc*)(cc)?a+')
        a.eliminaespacios('( a.b )+.a | a*.b.b? )')
        """
        estados=[]
        estados.append(EstadoI("x"))
        estados.append(EstadoF("y"))
        estados.append(Estado("w"))
        transiciones=[]
        transiciones.append(Transicion("x","y",1))
        transiciones.append(Transicion("x","w",0))
        transiciones.append(Transicion("y","w",0))
        transiciones.append(Transicion("y","x",1))
        transiciones.append(Transicion("w","x",0))
        transiciones.append(Transicion("w","y",1))
        a = AFD(estados,transiciones)
        g= Graphic(a.automata)
        g.graph_all(a.getInicial(),a.getFinal())
        #print(a.getInicial(),a.getFinal())
        """







if __name__ == '__main__':
   m=Main()

