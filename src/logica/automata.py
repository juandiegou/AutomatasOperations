
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
        #print(self.automata)


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
            automata[str(e.getNombre())]=[]
        #print(automata)

        for t in transiciones:
            automata[str(t.getEstadoI())].append((t.getEstadoI(),t.getEstadoF(),t.getValor() ))

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


    def minimizar(self):
        if self.isAFD():
            pass

    def conjuntos(self):
        dic={}
        finales=self.obtenerFinales()
        estados=self.__restar__(self.__getEstados__(),finales)
        dic[0]=finales
        dic[1]=estados
        return self.obtenerConjuntos(self._transiciones,dic)

    def obtenerConjuntos(self,transiciones,dic):
        valores=self.obtenerValores()
        lista=[]
        temporal1=[]
        lista2=[]
        temporal2=[]
        #print(dic)
        for x in transiciones:
            if x.getTransicion()[0] in dic[1]:
                listica=[]
                for y in valores:
                    if self.obtenerConjunto(x.getTransicion()[0],y) in dic[0]:
                        listica.append(0)
                    else:
                        listica.append(1)
                lista.append(listica)
            else:
                listica2=[]
                for y in valores:
                    if self.obtenerConjunto(x.getTransicion()[0],y) in dic[0]:
                        listica2.append(0)
                    else:
                        listica2.append(1)
                   
                lista2.append(listica2)
        if self.evaluarCambios(lista) or self.evaluarCambios(lista2) :
            if self.evaluarCambios(lista) and self.evaluarCambios(lista2):
                print("HUbo cambios en ambas listas")
            elif self.evaluarCambios(lista):
                print(lista,temporal1)
                print("HUbo cambios en lista")
            else:
                print("HUbo cambios en lista2")
        else:    
            return self.automata


    def evaluarCambios(self,lista):
        
        for x in lista:
            if lista.count(x)<len(lista):
                return True
        return False
                



        


                
    def obtenerConjunto(self,estado,valor):
        transiciones=self.getTransicionesDe(self.getEstadoX(estado))
        for x in transiciones:
            if x.getTransicion()[0]==estado and x.getTransicion()[2]==valor:
                return x.getTransicion()[1]



    
    def obtenerValores(self):
        valores=[]
        for t in self._transiciones:
            if t.getTransicion()[2] not in valores:
                valores.append(t.getTransicion()[2])
        return valores
            

    def obtenerFinales(self):
        finales=[]
        for e in self._estados:
            if e.isFinal():
                finales.append(e.getNombre())
        return finales

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



    def getInicial(self):
        """[metodo que permite saber cual es el nodo inicial]

        Returns:
            [Estado]: [retorna el nombre del  estado inicial]
        """
        for x in self._estados :
            if x.isInicial():
                return x.getNombre()

    def getFinal(self):
        """[metodo que permite saber el nodo final]

        Returns:
            [Estado]: [retorna el estado final]
        """
        for y in self._estados:
            if y.isFinal():
                return y.getNombre()



    def getEstadoInicial(self):
        """[metodo que permite saber cual es el nodo inicial]

        Returns:
            [Estado]: [retorna el estado inicial]
        """
        for x in self._estados :
            if x.isInicial():
                return x
        print("no se encuentra estado inincial")

    def getEstadoFinal(self):
        """[metodo que permite saber cual es el estado final]

        Returns:
            [Estado]: [retorna el estado final]
        """
        for x in self._estados :
            if x.isFinal():
                return x

    def setEstadoI(self,estado):
        """[permite cambiar el asiganr un nuevo estado Inicial]

        Args:
            estado ([Estado]): [el estado que sera un nuevo estado Inicial]
        """
        self.getEstadoInicial().setIsInicial(False)
        estado.setIsInicial(True)
        self.automata=self.genereAutomata(self._estados,self._transiciones)

    def setEstadoF(self,estado):
        """[permite cambiar o asiganr un nuevo estado Final]

        Args:
            estado ([Estado]): [el estado que sera un nuevo estado Final]
        """
        self.getEstadoFinal().setIsFinal(False)
        estado.setIsInicial(True)
        self.automata=self.genereAutomata(self._estados,self._transiciones)



    def agregarEstado(self,estado):
        """[permite agregar un nuevo estado a la lista de estados]

        Args:
            estado ([Estado]): [estado que se agregara]
        """
        self._estados.append(estado)
        self.automata=self.genereAutomata(self._estados,self._transiciones)

    def agregarTransicion(self,transicion):
        """[permite agregar una transicion]

        Args:
            transicion ([Transicion]): [la transicion que se agregara]
        """
        self._transiciones.append(transicion)
        self.automata=self.genereAutomata(self._estados,self._transiciones)
    
    def getTransicionesDe(self,estado):
        """[permite tener las transiciones de un estado]

        Args:
            estado ([Estado]): [el estado que se queire tener la transiciones]
        """
        lista=[]
        for x in self._transiciones:
            if x.getTransicion()[0]==estado.getNombre() or estado.getNombre()==x.getTransicion()[1]:
                lista.append(x)
        return lista

    def eliminarEstado(self,estado):
        """[permite eliminar un estado de un automata]

        Args:
            estado ([Estado]): [el estado que se quiere eliminar]
        """
        self._estados.remove(estado)
        self.automata=self.genereAutomata(self._estados,self._transiciones)

    def getEstadoX(self,x):
        """[permite obtener un estado X(cualquiera que este en el automata)]

        Args:
            x (string): [el nombre del estado buscado]

        Returns:
            [type]: [description]
        """
        for e in self._estados:
            if e.getNombre()==x:
                return e

    def __restar__(self,listaUno,listaDos):
        """[permite restar los valores de dos listas]

        Args:
            listaUno ([list]): [la lista principal de la que se va a restar]
            listaDos ([list]): [la  lista secundaria que contiene los valores a restar]

        Returns:
            [list]: [la lista sin los valores restados]
        """
        for x in listaDos:
            if x in listaUno:
                listaUno.remove(x)
        return listaUno

    def __getEstados__(self):
        """[permite obtener todos los nombres de los estados]

        Returns:
            [list]: [la lista con los nombres de todos los estados]
        """
        lista=[]
        for x in self.getEstados():
            lista.append(x.getNombre())
        return lista
