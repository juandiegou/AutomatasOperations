from logica.estadoI import EstadoI
from logica.estadoF import EstadoF
from logica.transicion import Transicion
from logica.afnd import AFND
from logica.automata import Automata
from logica.afd import AFD
from logica.estado import Estado

class Analizador():
    
   
    def __init__(self,alfabeto):
        """Constructor de un Analizador de expresiones(pequeño y no tan poderoso)
            para analizar una expresion solo se requiere de un alfabeto
        """
        self.__constantes=['(',')','|','+','*','?','@','.']
        self.__operadores=['|','+','*','?','.']
        self.__precedencia={'+':3,'?':3,'*':3,'.':2,'|':1}
        self._expresion=None
        self._estados=[]
        self._transiciones=[]
        self._alfabeto=alfabeto
        self._alfabeto.extend(self.__constantes)
    
    

    def setExpresion(self,expresion):
        """setter de expresion 

        Args:
            expresion ([string]): [expresion que tiene el analizador ]
        """
        self._expresion=expresion


    def getExpresion(self):
        """[getter de la expresion para saber cual es]

        Returns:
            [string]: [la expresion del analizador]
        """
        return self._expresion

    def verificar(self,expresion):
        """[verifica si la expresion es valida en el alfabeto definido]

        Args:
            expresion ([string]): [la expresion que se desea evaluar ]

        Returns:
            [boolean]: [True si es válida False si no lo es]
        """
        
        for x in self._estados:
            if x not in self._alfabeto:
                print(x ,"No esta")
                return False
        return True

    def analizar(self,expresion):
        if self.verificar(expresion):
           postfijo=self.obtenerPosfijo(expresion)



    def obtenerPosfijo(self,expresion):
        """[metodo que convierte la expresion en una lista de postfijo es util para convetir un ER a AFND]

        Args:
            expresion ([type]): [la expresion que se quiere trasnformar a PostFijo]

        Returns:
            [type]: [una lista con los valores del PostFijo]
        """
        salida=[] #esta será una lista que tendra el recorrido en postfijo
        temporal=[] #esta lista se comportara como pila para el funcionamiento
        self._estados=self.eliminaespacios(expresion)
        for x in self._estados:
            if x not in self.__constantes:
                salida.append(x)
            if x in self.__operadores:
                if  temporal:
                    if self.__Evaluaprecedencia(x)>self.__Evaluaprecedencia(temporal[len(temporal)-1]):
                        temporal.append(x)
                    else:   
                        salida.append(temporal.pop(len(temporal)-1))
                        temporal.append(x)
                else:
                    temporal.append(x)    
            if x==')':
                if temporal:
                    
                    temporal.reverse()
                    
                    salida.extend(temporal)
                    temporal=[]         
        if temporal:
            salida.extend(temporal)
        return salida



    def DosEstados(self,lista,y):
        """[metodo que crea un estado Inicial y un estado Final y los devuelve en una lista]

        Args:
            lista ([list]): [la lista en que se devuelven los estados creados]
            y ([int]): [indice que dice el numero de estado]

        Returns:
           lista[list] y[int]: [retorna la lista con los estados y el nuevo valor del indice]
        """
        lista.append(EstadoI('q'+str(y)))
        lista.append(EstadoF('q'+str(y+1)))
        return lista
        
    def UnaTransicion(self,estados,transicion,valor):
        """[metodo que une dos estados con una transicion]

        Args:
            estados ([list]): [una lista con los estados que se quieren unir]
            transicion (list, optional): [una lista donde ira la transicion]. Defaults to [].

        Returns:
            [list]: [una lista donde se ingresara la transicion]
        """
        ei=estados[0].getNombre()
        ef=estados[len(estados)-1].getNombre()
        transicion.append(Transicion(str(ei),str(ef),str(valor)))
        return transicion   

    def PostFijoToAFND(self,lista):
        """[permite pasar una lista de expresiones a un AFND]

        Args:
            lista ([list]): [la lista con la expresion en PostFijo]
        """
        temporal=[]
        y=0
        for x in lista:
            if x not in self.__operadores:
                temporal.append(AFND(self.DosEstados([],y),self.UnaTransicion((self.DosEstados([],y)),[],x)))
                y+=2
            else:
               
                salida=(self.thomsonp(x,temporal))
                temporal.clear()
                temporal.append(salida)
        return temporal

    def thomsonp(self,x,temporal):

        if x=='|':
            automata=self.operacionO(temporal)
            #print(automata,"linea 163")

        elif x=='+':
            automata=self.operacionMas(temporal)
        elif x=='*':
            automata=self.operacionPor(temporal)
        elif x=='?':
            automata=self.operacionInterrogacion(temporal)
        else:
            automata=self.operacionConcatenacion(temporal)
            
        return automata

    def __Evaluaprecedencia(self,x):
        """[evlua un caracter para saber el valor de su precedencia (esta es predefinida)]

        Args:
            x ([caracter(char)]): [el caracter que se desea evaluar]

        Returns:
            [int]: [el valor predefinido de su precedencia]
        """
        return self.__precedencia[x]


    def eliminaespacios(self,expresion):
        """[permite eliminar espacio de una expresion]

        Args:
            expresion ([string]): [una expresion regular]

        Returns:
            [string]: [una expresion regular sin espacios]
        """
        salida=''
        for x in expresion:
            if x != ' ':
                salida+=x
        return salida


    def operacionO(self,lista):
        """[operacion (|) o sobre dos automatas]

        Args:
            lista ([list]): [una lista con dos automatas]

        Returns:
            [un automata final]: [el automata despues de aplicarle la operacion o]
        """
        automataUno=lista.pop(0)
        automataDos=lista.pop(0)
        valor=len(automataUno.getEstados())+len(automataDos.getEstados())
        estadoI=EstadoI('q'+str(valor))
        estadoF=EstadoF('q'+str(valor+1))
        estadoUno=automataUno.getEstadoInicial()
        estadoDos=automataUno.getEstadoFinal()
        estadoTres=automataDos.getEstadoInicial()
        estadoCuatro=automataDos.getEstadoFinal()
        estados=[estadoI,estadoF]
        automata=AFND(estados,[])
        for e in automataUno.getEstados():
            automata.agregarEstado(e)
        
        for x in automataDos.getEstados():
            automata.agregarEstado(x)
        transicionUnO=Transicion(estadoI.getNombre(),estadoUno.getNombre(),'@')
        transicionDos=Transicion(estadoI.getNombre(),estadoTres.getNombre(),'@')
        transicionTres=Transicion(estadoCuatro.getNombre(),estadoF.getNombre(),'@')
        transicionCuatro=Transicion(estadoDos.getNombre(),estadoF.getNombre(),'@')
        transiciones=[transicionUnO,transicionDos,transicionTres,transicionCuatro]
        
        for t in transiciones:
            automata.agregarTransicion(t)

        for w in automataUno.getTransiciones():
            automata.agregarTransicion(w)

        for y in automataDos.getTransiciones():
            automata.agregarTransicion(y)
        
        return automata
       


    def operacionMas(self,lista):
        automata=lista.pop(0)
        if lista:
            print("falta algo por hacer")
        else:
            valor=len(automata.getEstados())
            estadoI=EstadoI('q'+str(valor))
            estadoF=EstadoF('q'+str(valor+1))
            estadoUno=automata.getEstadoInicial()
            estadoDos=automata.getEstadoFinal()
            estados=[estadoI,estadoF]
            automataF=AFND(estados,[])
            for e in automata.getEstados():
                automataF.agregarEstado(e)
            transicionUno=Transicion(estadoI.getNombre(),estadoUno.getNombre(),'@')
            transicionDos=Transicion(estadoDos.getNombre(),estadoUno.getNombre(),'@')
            transicionTres=Transicion(estadoDos.getNombre(),estadoF.getNombre(),'@')
            transiciones=[transicionUno,transicionDos,transicionTres]
            for x in transiciones:
                automataF.agregarTransicion(x)

            for y in automata.getTransiciones():
                automataF.agregarTransicion(y)

            return automataF   

            


    def operacionPor(self,lista):
        automata=lista.pop(0)
        if lista:
            print("falta algo por hacer")
        else:
            valor=len(automata.getEstados())
            estadoI=EstadoI('q'+str(valor))
            estadoF=EstadoF('q'+str(valor+1))
            estadoUno=automata.getEstadoInicial()
            estadoDos=automata.getEstadoFinal()
            estados=[estadoI,estadoF]
            automataF=AFND(estados,[])
            for e in automata.getEstados():
                automataF.agregarEstado(e)
            transicionUno=Transicion(estadoI.getNombre(),estadoUno.getNombre(),'@')
            transicionDos=Transicion(estadoDos.getNombre(),estadoUno.getNombre(),'@')
            transicionTres=Transicion(estadoDos.getNombre(),estadoF.getNombre(),'@')
            transicionCuatro=Transicion(estadoI.getNombre(),estadoF.getNombre(),'@')
            transiciones=[transicionUno,transicionDos,transicionTres,transicionCuatro]
            for x in transiciones:
                automataF.agregarTransicion(x)

            for y in automata.getTransiciones():
                automataF.agregarTransicion(y)

            return automataF   


    def operacionInterrogacion(self,lista):
        automata=lista.pop(0)
        if lista:
            print("falta algo por hacer")
        else:
            valor=len(automata.getEstados())
            estadoI=EstadoI('q'+str(valor))
            estadoF=EstadoF('q'+str(valor+1))
            estadoUno=automata.getEstadoInicial()
            estadoDos=automata.getEstadoFinal()
            estados=[estadoI,estadoF]
            automataF=AFND(estados,[])
            for e in automata.getEstados():
                automataF.agregarEstado(e)
            transicionUno=Transicion(estadoI.getNombre(),estadoUno.getNombre(),'@')
            transicionTres=Transicion(estadoDos.getNombre(),estadoF.getNombre(),'@')
            transicionDos=Transicion(estadoI.getNombre(),estadoF.getNombre(),'@')
            transiciones=[transicionUno,transicionDos,transicionTres]
            for x in transiciones:
                automataF.agregarTransicion(x)

            for y in automata.getTransiciones():
                automataF.agregarTransicion(y)

            return automataF   

    def operacionConcatenacion(self,lista):
        """[operacion que concatena dos automatas]

        Args:
            lista ([list]): [una lista con dos automatas]

        Returns:
            [Automata]: [el automata final con la concatenacion]
        """
        automataUno=lista.pop(0)
        automataDos=lista.pop(0)
        estadoi=automataUno.getEstadoFinal()
        for x in automataDos.getEstados():
            automataUno.agregarEstado(x)
        estadox=automataUno.getEstadoX(automataDos.getEstadoFinal().getNombre())
        automataUno.setEstadoF(estadox)
        estadof=automataDos.getEstadoInicial().getNombre()
        automataUno.agregarTransicion(Transicion(estadoi.getNombre(),estadof,'@'))
        for y in automataDos.getTransiciones():
            automataUno.agregarTransicion(y)

        return automataUno

    def obtenerEstados(self,transiciones,estados):
        """[permite obtener los estados que tienen transiciones que entran]

        Args:
            transiciones ([list]): [la lista de transiciones]
            estados ([list]): [la lista de estados]

        Returns:
            [list]: [las transiciones que tienen llegada a los estados]
        """
        salida=[]
        for x in transiciones:
            if x.getTransicion()[1] in estados:
               salida.append(x.getTransicion())
        return salida

    def verificarCadena(self,string, inicial, aceptacion, transiciones):
        """ Proceso para vrificar si una cadena es aceptada por un afd
        :param inicial: estado inicial
        :param aceptacion: estado aceptador
        :param transiciones: transiciones del automata
        :return:
        """
        diccionario = {}

        for x in transiciones:
            diccionario[x.getEstadoI(), str(x.getValor())] = x.getEstadoF()

        return self.procesoCadena(string, inicial, aceptacion, diccionario)


    def procesoCadena(self,string, inicial, aceptacion, transiciones):  # creamos la funcion
        """permite verificar si una cadena o palabra es aceptada por un automata
        :param inicial: nodo inicial
        :param aceptacion: nodo de aceptacion
        :param transiciones: transiciones del automata
        :return: true si la cadena o palabra es aceptada, false si no es aceptada
        """
        if string == "":  # cuando no encuentra nada
            return inicial in aceptacion  # retorna true
        else:
            caracter = string[0]  # comienza con el primer caracter

            if (inicial, caracter) in transiciones:  # compara (estado, caracter) se encuentra en transicion
                destino = transiciones[(inicial, caracter)]  # si es asi destino tendra el nuevo valor estado
                print(inicial, destino)
                remaining_string = string[1:]  # comenzara a recorrer los demas caracteres
                return self.procesoCadena(remaining_string, destino, aceptacion, transiciones)  # funcion recursiva
            else:
                return False  # si no es asi retorna falso