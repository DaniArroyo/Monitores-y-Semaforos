package alumno_A;

/*
 Daniel Arroyo 
 Problema de Condicion de Carrera 
 
 Este codigo consiste en una variable de la clase puerta que indicaria el estado de esta, y se encontraria compartida con
 2 hilos los cuales se encargarian de cerrarla y abrirla sucesivamente si estuvieran bien sincronizados.
 Como no es el caso, existen muchas veces en las que los propios hilos intentan abrir una puerta cuando ya esta abierta como
 en el caso del hilo abrir, o cerrarla cuando esta cerrada en el caso de cerrar, como resultado se entra por el else 
 del if sumando al contador(errores) que nunca es menos de 2000 debido a los bucles for y a que siempre 
 existen desincronizaciones en este codigo.
*/

class Porton {
	public static boolean abierta;
	public static int contador;
}

class Abrir extends Thread {
	public void run() {
		for (int i = 0; i < 1000; i++) {
			if (!Porton.abierta) {
				Porton.abierta = true;
			} else {
				i--;
				Porton.contador++;
			}
		}
		System.out.println("Abrir terminando");
	}
}

class Cerrar extends Thread {
	public void run() {
		for (int i = 0; i < 1000; i++) {
			if (Porton.abierta) {
				Porton.abierta = false;
			} else {
				i--;
				Porton.contador++;
			}
		}
		System.out.println("Cerrar terminando");
	}
}

public class Correr {
	public static void main(String[] args) throws InterruptedException {
		Porton.abierta = true;
		Thread a = new Abrir();
		Thread c = new Cerrar();
		a.start();
		c.start();
		a.join();
		c.join();
		// en el contador se cuenta la cantidad de veces que entro por el else de ambos hilos
		System.out.println("El resultado final es: " + Porton.contador);
	}
}