from analizador import Analizador

class Main():
    def __init__(self):
        a= Analizador("(a|b)*|c")
        a.analizar("(a|b)*|c")




if __name__ == '__main__':
   m=Main()

   