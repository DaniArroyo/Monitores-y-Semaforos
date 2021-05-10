package alumno_B;

class Puerta {
	public static boolean CerrojoA;
	public static boolean CerrojoB;
}

class LlaveA extends Thread {

	// La funcion que se ejecutara en el hilo
	public void run() {
		// Bucle inifinito que causa el problema
		while (!Puerta.CerrojoB)
			;
		Puerta.CerrojoA = true;
		System.out.println("LlaveA terminando");
	}
}

class LlaveB extends Thread {

	// La funcion que se ejecutara en el hilo
	public void run() {
		// Bucle inifinito que causa el problema
		while (!Puerta.CerrojoA)
			;
		Puerta.CerrojoB = true;
		System.out.println("LlaveB terminando");
	}
}

public class Correr {
	public static void main(String[] args) throws InterruptedException {
		Puerta.CerrojoA = false;
		Puerta.CerrojoB = false;
		// En estas dos lineas creamos los dos hilos y los iniciamos.
		Thread a = new LlaveA();
		Thread b = new LlaveB(); 
		a.start();
		b.start();
		/*
		 * Hasta este syso todo bien, pero cuando hagamos el join de cualquiiera de los
		 * dos hilos, Como tienen los dos un bucle infinito no van a continuar los
		 * otros, Asi que es un problema de INTERBLOQUEO dado que los hilos tendran que
		 * estrar siempre esperando, por culpa de los bucles infinitos, y los demas
		 * hilos no podran continuar.
		 */
		System.out.println("Comienzo del hilo principal");
		a.join();
		b.join();
		System.out.println("Fin del hilo principal");
	}
}