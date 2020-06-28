from logica.estado import Estado


class EstadoI(Estado):

    def __init__(self,nombre):
        super().__init__(nombre)
        self._inicial=True

    

