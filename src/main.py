from analizador import Analizador
from automata import Automata
from estado import Estado
from transicion import Transicion
import random
import itertools
import string


class Main():
    def __init__(self):


        e=[]
        t=[]
        for i in range(0,5):
            e.append(Estado(i))
            for j in range(0,4):
                t.append(Transicion(i,random.choice(string.ascii_letters),random.randint(1, 3)))  
        #print(e,'\n',t)
        t.append(Transicion(3,'@',random.randint(0, 4)))
        t.append(Transicion(2,'@',random.randint(4, 4)))
        at=Automata(e,t)
        #print(at.getISAFD(),at.getISAFND())
        print(at.clausura_epsilon(4))





if __name__ == '__main__':
   m=Main()

'''
    ap={}
    ap.add("(a|b)*|c")
    a= Analizador(ap)
    a.analizar("(a|b)*|c")
'''