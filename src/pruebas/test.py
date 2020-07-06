import Analizador
import pytest

dic={'q0':[('q0','q1','a')],'q1':[]}
def mainTest(self):
    an=Analizador(['A','B','C','d','e','f','g','h'])
    a=an.PostFijoToAFND(an.obtenerPosfijo('a'))
    assert a.pop().automata ==dic


if __== '__main__':
    mainTest()
