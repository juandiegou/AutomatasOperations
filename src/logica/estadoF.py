from logica.estado import Estado

class EstadoF(Estado):

    def __init__(self,nombre):
        super().__init__(nombre)
        self.setIsFinal(True)


    