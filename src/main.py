from control.analizador import Analizador
from logica.automata import Automata
from logica.estado import Estado
from logica.transicion import Transicion
import random
import itertools
import string


class Main():
    def __init__(self):

        a=Analizador(['a','b','c','d','e','f','g','h'])
        print(a.verificar('(a+)|(b+)?(c+)?w'))





if __name__ == '__main__':
   m=Main()

