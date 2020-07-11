import time
from control.analizador import Analizador
import random
import itertools
import string
from tkinter import *
from tkinter import messagebox
from logica.estadoI import EstadoI
from logica.estadoF import EstadoF
from logica.estado import Estado
from logica.transicion import Transicion
from logica.afd import AFD
from control.graphic import Graphic

estados = []
transiciones = []


"""----------------------------------------Diseño de interfaz------------------------------------------------------"""

# Se crea la ventana:
ventana = Tk()

# Se le da un tamaño:
ventana.geometry("1350x700")

# Agregando un titulo a la ventana
ventana.title("Proyecto Autómatas")

# En este canvas colocaremos las herramientas que nos permitan manipular el programa
canvas_principal = Canvas(ventana, width=550, height=700, bg="#2E065E")
canvas_principal.place(x=800, y=0)

# En este canvas se va a dibujar el automata que se genere:
canvas_dibujo = Canvas(ventana, width=800, height=700, bg="#2E065E")
canvas_dibujo.place(x=0, y=0)

# Titulo del dibujo
etiqueta = Label(canvas_dibujo, text="Autómata", fg="white", bg="#2E065E", font=("Arial", 15))
etiqueta.place(x=350, y=2)

etiqueta2 = Label(canvas_principal, text="Estados", fg="white", bg="#2E065E", font=("Arial", 13))
etiqueta2.place(x=250, y=2)

estadosIng = StringVar()
txt_estados= Entry(canvas_principal, width = 30, textvariable = estadosIng).place(x = 100, y = 35)

inicialIng = StringVar()
txt_inicialIng= Entry(canvas_principal, width = 10, textvariable = inicialIng).place(x = 200, y = 73)

aceptacionIng = StringVar()
txt_aceptacionIng= Entry(canvas_principal, width = 10, textvariable = aceptacionIng).place(x = 200, y = 103)

etiqueta3 = Label(canvas_principal, text="Transiciones", fg="white", bg="#2E065E", font=("Arial", 13))
etiqueta3.place(x=235, y=140)

transicionIni = StringVar()
txt_transicionIni = Entry(canvas_principal, width = 5, textvariable = transicionIni).place(x = 125, y = 175)

etiqueta4 = Label(canvas_principal, text="--->", fg="white", bg="#2E065E", font=("Arial", 13))
etiqueta4.place(x=160, y=173)

transicionFin = StringVar()
txt_transicionFin = Entry(canvas_principal, width = 5, textvariable = transicionFin).place(x = 195, y = 175)

etiqueta5 = Label(canvas_principal, text="=", fg="white", bg="#2E065E", font=("Arial", 15))
etiqueta5.place(x=230, y=170)

transicionVal = StringVar()
txt_transicionVal = Entry(canvas_principal, width = 5, textvariable = transicionVal).place(x = 250, y = 175)

cadena = StringVar()
txt_cadena = Entry(canvas_principal, width = 30, textvariable = cadena).place(x = 100, y = 540)

lst_cadena = Listbox(canvas_principal, fg = "white", bg = "#2E065E", width = 30, height = 1,
                         highlightbackground = "white")

lst_cadena.place(x = 100, y = 570)



"""----------------------------------------Funciones---------------------------------------------------"""

def ingresarEstados():
    """Ingreso de estados desde el campo de la ventana principal"""

    texto = estadosIng.get()

    if texto == "":
        messagebox.showinfo(message="Campo vacío", title="Error")
    else:
        for x in estadosIng.get().split(','):
            estados.append(Estado(x.strip()))


def ingresarEstadoIni():
    """Ingreso de estado inicial desde el campo de la ventana principal"""

    texto = inicialIng.get()

    if texto == "":
        messagebox.showinfo(message="Campo vacío", title="Error")
    else:
        estados.append(EstadoI(texto))


def ingresarEstadoFin():
    """Ingreso de estado de aceptacion desde el campo de la ventana principal"""

    texto = aceptacionIng.get()

    if texto == "":
        messagebox.showinfo(message="Campo vacío", title="Error")
    else:
        estados.append(EstadoF(texto))


def ingresarTransicion():
    """Ingreso de transiciones desde el campo de la ventana principal"""

    estado1 = transicionIni.get()
    estado2 = transicionFin.get()
    valor = transicionVal.get()

    if estado1 == "" or estado2 == "" or valor == "":
        messagebox.showinfo(message="Campo vacío", title="Error")
    else:
        transiciones.append(Transicion(estado1, estado2, valor))


def graficarAutomata():
    """Graficar autómata en la ventana de dibujo"""

    canvas_dibujo.destroy()
    canvas_dibujo1 = Canvas(ventana, width=800, height=700, bg="#2E065E")
    canvas_dibujo1.place(x=0, y=0)

    #titulo del dibujo
    titulo2 = Label(canvas_dibujo1,text="Automata", font=("Arial",15))
    titulo2.place(x=350,y=2)

    a = AFD(estados, transiciones)
    b=modificarDiccionario(a.automata)
    g = Graphic(b)
    g.graph_all(a.getInicial(), a.getFinal())

    img_automata = PhotoImage(file="D:\\Programming\\Python\\Automata1\\Full automata.png")
    canvas_dibujo1.create_image(40, 60, image=img_automata, anchor=NW)
    canvas_dibujo1.mainloop()

def modificarDiccionario(diccionario):
    for k in diccionario.keys():
        l=diccionario[k]
        m=[]
        t=[]
        for a in l:
            m.append(a[1])
            t.append(a[2])
        for x in m:
            if contar(x,m)>1:
                v=aString(t)
                tupla=(k,m[0],(v))
                print(tupla)
                diccionario[k]=[tupla]
    return diccionario
def contar(x,l):
    return l.count(x)

def aString(lista):
    salida=''
    for x in lista:
        if lista.index(x)<len(lista)-1:
            salida+=str(x)+','
        else:
            salida+=str(x)
    return salida

def afnd():
    pass

def  minimizar():
    pass

def verificarCadena():
    pass

"""---------------------------------------Botonera---------------------------------------------------------------------------"""

btn_ingresarEstados = Button(canvas_principal, width=15, text="Ingresar", font=("Arial", 11), fg="#ffffff",
                          command=ingresarEstados, background="#1E6F4A", state="normal")
btn_ingresarEstados.place(x=300, y=30)

btn_estadoinicial = Button(canvas_principal, width=10, text="Inicial", font=("Arial", 8), fg="#ffffff",
                          command=ingresarEstadoIni, background="#1E6F4A", state="normal")
btn_estadoinicial.place(x=275, y=70)

btn_estadoAceptacion = Button(canvas_principal, width=10, text="Aceptación", font=("Arial", 8), fg="#ffffff",
                          command=ingresarEstadoFin, background="#1E6F4A", state="normal")
btn_estadoAceptacion.place(x=275, y=100)

btn_transicionIngresar = Button(canvas_principal, width=10, text="Ingresar", font=("Arial",11), fg="#ffffff",
                          command=ingresarTransicion, background="#1E6F4A", state="normal")
btn_transicionIngresar.place(x=300, y=170)

btn_graficar = Button(canvas_principal, width=10, text="Graficar", font=("Arial",11), fg="#ffffff",
                          command=graficarAutomata, background="#1E6F4A", state="normal")
btn_graficar.place(x=240, y=220)

btn_afnd = Button(canvas_principal, width=15, text="AFND => AFD", font=("Arial", 11), fg="#ffffff",
                          command=afnd, background="#1E6F4A", state="normal")
btn_afnd.place(x=210, y=430)

btn_minimizar = Button(canvas_principal, width=15, text="Minimizar", font=("Arial", 11),
                                      fg="#ffffff", command=minimizar, background="#1E6F4A", state="normal")
btn_minimizar.place(x=210, y=480)

btn_verficar_cadena = Button(canvas_principal, width=15, text="Evaluar Expresión", font=("Arial", 11),
                                      fg="#ffffff", command=verificarCadena, background="#1E6F4A", state="normal")
btn_verficar_cadena.place(x=300, y=550)

btn_cerrar_ventana_principal = Button(canvas_principal, width=15, text="Salir", font=("Arial", 11),
                                      fg="#ffffff", command=lambda: ventana.destroy(), background="#1E6F4A")
btn_cerrar_ventana_principal.place(x=210, y=640)


"""---------------------------------------------Hilo principal de ejecución----------------------------------------------------------"""

# Hace que la ventana se mantenga persistente en pantalla
ventana.mainloop()