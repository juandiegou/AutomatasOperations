from analizador import Analizador
from automata import Automata
from estado import Estado
from transicion import Transicion
import random
import itertools


class Main():
    def __init__(self):


        e=[]
        t=[]
        for i in range(0,5):
            e.append(Estado(i))
            for j in range(0,4):
                t.append(Transicion(i,random.randint(0,38),random.randint(1, 3)))  
        #print(e,'\n',t)
        at=Automata(e,t)
        print(at.getISAFD(),at.getISAFND())





if __name__ == '__main__':
   m=Main()

'''
    ap={}
    ap.add("(a|b)*|c")
    a= Analizador(ap)
    a.analizar("(a|b)*|c")
'''