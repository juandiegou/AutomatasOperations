from control.analizador import Analizador
from logica.automata import Automata
from logica.estado import Estado
from logica.transicion import Transicion
import random
import itertools
import string


class Main():
    def __init__(self):

        a=Analizador(['A','B','C','d','e','f','g','h'])
        #print(a.verificar('(a.b)+.a|a*.b.b?'))
        a.analizar('A*B|(A+C) ')






if __name__ == '__main__':
   m=Main()

