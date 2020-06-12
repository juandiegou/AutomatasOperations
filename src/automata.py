
class Automata(object):

    __estados=[]
    __transiciones=[]

    def __init__(self,estados,transiciones):
        self.__estados=estados
        self.__transiciones=transiciones


    def getEstados(self):
        return self.__estados

    def getTransiciones(self):
        return self.__transiciones

    def getAutomata(self):
        return self.__estados,self.__transiciones

