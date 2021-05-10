package alumno_B;

import java.util.concurrent.Semaphore;

class Puerta_Alternativa {
	public static Semaphore miSemaforo = new Semaphore(0);
}

class LlaveA_Alternativa extends Thread {
	public void run() {

		Puerta_Alternativa.miSemaforo.release();
		System.out.println("LlaveA terminando");
	}
}

class LlaveB_Alternativa extends Thread {
	public void run() {

		try {
			Puerta_Alternativa.miSemaforo.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("LlaveB terminando");
	}
}

public class Correr_alternativo {
	public static void main(String[] args) throws InterruptedException {
		// Creamos los dos hilos y los iniciamos
		Thread a = new LlaveA_Alternativa();
		Thread b = new LlaveB_Alternativa();
		a.start();
		b.start();
		System.out.println("Comienzo del hilo principal");
		a.join();
		b.join();
		System.out.println("Fin del hilo principal");
	}
}