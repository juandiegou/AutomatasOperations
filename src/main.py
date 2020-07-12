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
        #estados.append(Estado("f"))

        transiciones=[]
        transiciones.append(Transicion("a","b",'a'))
        transiciones.append(Transicion("a","c",'b'))
        transiciones.append(Transicion("b","b",'a'))
        transiciones.append(Transicion("b","d",'b'))
        transiciones.append(Transicion("c","c",'b'))
        transiciones.append(Transicion("c","b",'a'))
        transiciones.append(Transicion("d","b",'a'))
        transiciones.append(Transicion("d","e",'b'))
        transiciones.append(Transicion("e","b",'a'))

        
        a = AFD(estados,transiciones)

        #print(a.obtenerValores())
#        print(a.automata)
        #b=self.modificarDiccionario(a.automata)
        #print(b)
        #g= Graphic(b)
        #g.graph_all(a.getInicial(),a.getFinal())
        #print(a.conjuntos())
        a.conjuntos()
        
        
        
""" def modificarDiccionario(self,diccionario):
        for k in diccionario.keys():
            l=diccionario[k]
            m=[]
            t=[]
            for a in l:
                m.append(a[1])
                t.append(a[2])
            for x in m:
                if self.contar(x,m)>1:
                    v=self.aString(t)
                    tupla=(k,m[0],(v))
                    print(tupla)
                    diccionario[k]=[tupla]
        return diccionario
    def contar(self,x,l):
        return l.count(x)

    def aString(self,lista):
        salida=''
        for x in lista:
            if lista.index(x)<len(lista)-1:
                salida+=str(x)+','
            else:
                salida+=str(x)



        return salida
        
        
                    if lista.count(x)<len(lista):
                    return True
        return False
        
        """
        

if __name__ == '__main__':
   m=Main()

