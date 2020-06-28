
import itertools

class Automata(object):

    _estados=[]
    _transiciones=[]
    isanAFND=None
    isanAFD=None

    def __init__(self,estados,transiciones):
        """[constructor de la clase automata]

        Args:
            estados ([list<Estado>]): [una lista con los estados del automata]
            transiciones ([list<Transicion>]): [una lista con las trasiciones del automata]
        """
        self._estados=estados
        self._transiciones=transiciones
        self.automata=self.genereAutomata(self._estados,self._transiciones)
        self.isanAFD=self.isAFD()
        self.isanAFND=self.isAFND()
        print(self.automata)


    def getISAFND(self):
        """[getter para saber si es AFND]

        Returns:
            [boolean]: [True si es AFND]
        """
        return self.isanAFND

    def getISAFD(self):
        """[getter para saber si es AFD]

        Returns:
            [boolean]: [True si es AFD]
        """
        return self.isanAFD

    
    def genereAutomata(self,estados,transiciones):
        """[metodo que genera el autmata a partir de un diccionario]

        Args:
            estados ([list<Estados>]): [una lista con los estados del automata]
            transiciones ([list<Transicion>]): [una lista con las transiciones del automata]

        Returns:
            [dict]: [el automata como un diccionario]
        """
        automata={}
        for e in estados:
            automata[e.getNombre()]=[]


        for t in transiciones:
            automata[t.getEstadoI()].append((t.getEstadoI(),t.getEstadoF(),t.getValor() ))

        return automata

    def getEstados(self):
        """[metodo que permite obtener la lista con los estados del automata]

        Returns:
            [list]: [una lista con todos los estados del automata]
        """
        return self._estados

    def getTransiciones(self):
        """[metodo que permite obtener todas las transiciones del automata]

        Returns:
            [list]: [una lista con las transiciones del automata]
        """
        return self._transiciones

    def getAutomata(self):
        """[metodo que devuelve los valores del automata(una lista de transiciones , una lista de estados)]

        Returns:
            [lists]: [una lista de estados, y una lista de transiciones]
        """
        return self._estados,self._transiciones


    def isAFND(self):
        """[metodo que calcula si es un AFND]

        Returns:
            [boolean]: [True si es AFND , False si no lo es]
        """
        for e in self.automata.keys():
            if self.validaTransiciones(self.automata[e]):
                return True
       

        return False
        

    def validaTransiciones(self,lista):
        """[metodo que que recorre una lista de transiciones para saber si se repiten o son lambda]

        Args:
            lista ([list]): [una lista de transiciones de un estado]

        Returns:
            [boolean]: [True si la transicion es repetida o lambda, False si ninguna trasicion es ]
        """
        listado=sorted(itertools.combinations(lista,2))
        l=[]
        for x in listado:
            for y in x:
                if y[2]=='@':  #@ representa una transicion lambda
                    return True
                l.append(y)
            if self.compararTuplas(l[0],l[1]):
                return True
            l=[]
        return False


    def compararTuplas(self,tupla1,tupla2): 
        """[metodo que compara si los valores de dos tuplas son iguales]

        Args:
            tupla1 ([tuple]): [una tupla que representa una transicion]
            tupla2 ([tuple]): [una tupla que representa una transicion]

        Returns:
            [boolean]: [True si son iguales]
        """

        return tupla1[:2]==tupla2[:2]


    def isAFD(self):
        """[metodo que permite saber si el automata es AFD]

        Returns:
            [boolean]: [True si es AFD, False si no lo es]
        """        
        for e in self.automata.keys():
            if not (self.validaTransiciones(self.automata[e])):
                return True
        return False

    def afndAafd(self):
        pass

    def minimizar(self):
        pass

    def recorrer(self):
        pass


    def moverA(self,estado,transicion):
        """[metodo de operacion MoverA]

        Args:
            estado ([type]): [description]
            transicion ([type]): [description]

        Returns:
            [type]: [description]
        """
        lista=self.automata.items[estado]
        if transicion in lista[2]:
            return lista[1]
        
        return None

    def clausura_epsilon(self,estado,listaTransiciones=[]):
        """[metod de la operacion de clausura de epsilon]

        Args:
            estado ([type]): [description]
            listaTransiciones (list, optional): [description]. Defaults to [].

        Returns:
            [type]: [description]
        """
        listaTransiciones.append(estado)
        for transicion in self.automata[estado]:
            if transicion[1]=='@':
                self.clausura_epsilon(transicion[2],listaTransiciones)
                
        return listaTransiciones





