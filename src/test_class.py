from control.analizador import Analizador
class TestClass:
    dic={'q0':[('q0','q1','a')],'q1':[]}
    def mainTest(self):
        an=Analizador(['A','B','C','d','e','f','g','h'])
        a=an.PostFijoToAFND(an.obtenerPosfijo('a'))
        assert a==['a']

    
    def test_one(self):
        x = "this"
        assert "h" in x