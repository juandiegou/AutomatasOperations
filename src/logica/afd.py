from logica.automata import Automata


#un afd es el que solo tiene una transicion a cada estado 
class AFD(Automata):
    
    def __init__(self, estados, transiciones):
        super().__init__(estados, transiciones)
        

    