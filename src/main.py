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
from logica.automata import Automata
class Main():
    def __init__(self):
    
        """
        a=Analizador(['A','B','C','d','e','f','g','h'])
        #print(a.verificar('(a.b)+.a|a*.b.b?'))
        #a.analizar('(a+bc*)(cc)?a+')
        a.eliminaespacios('( a.b )+.a | a*.b.b? )')
           
        estados=[]
        estadoUno=EstadoI("x")
        estados.append(estadoUno)
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
        
    
        an= Analizador(['A','B','C','d','e','f','g','h'])
        a=an.PostFijoToAFND(an.obtenerPosfijo('a')) #para probar una er solo cambiar en obtenerpostfijo
        #print(a.pop().automata)
        #ax=a.pop()
        #g= Graphic(ax.automata)
        #g.graph_all(ax.getInicial(),ax.getFinal())
        #print(a.getInicial(),a.getFinal())
        """
        estados=[]
        estadoUno=EstadoI("a")
        estados.append(estadoUno)
        estados.append(Estado("b"))
        estados.append(EstadoF("c"))
        estados.append(EstadoF("d"))
        estados.append(EstadoF("e"))
        estados.append(Estado("f"))

        transiciones=[]
        transiciones.append(Transicion("a","b",0))
        transiciones.append(Transicion("b","a",0))
        transiciones.append(Transicion("a","c",1))
        transiciones.append(Transicion("b","d",1))
        transiciones.append(Transicion("c","e",0))
        transiciones.append(Transicion("c","f",1))
        transiciones.append(Transicion("d","e",0))
        transiciones.append(Transicion("d","f",1))
        transiciones.append(Transicion("e","e",0))
        transiciones.append(Transicion("e","f",1))
        transiciones.append(Transicion("f","f",0))
        transiciones.append(Transicion("f","f",1))
        
        a = AFD(estados,transiciones)

        #print(a.obtenerValores())
        ##g= Graphic(a.automata)
        #g.graph_all(a.getInicial(),a.getFinal())
        print(a.conjuntos())
        



if __name__ == '__main__':
   m=Main()

