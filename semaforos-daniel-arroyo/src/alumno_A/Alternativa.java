package alumno_A;

import java.util.concurrent.Semaphore;

class Puerta {
	public static boolean abierta;
	public static int contador;
}

class AbrirE extends Thread {
	Semaphore SemaforoAbrir;

	public AbrirE(Semaphore SemaforoAbrir) {
		this.SemaforoAbrir = SemaforoAbrir;
	}

	public void run() {

		for (int i = 0; i < 1000; i++) {
			try {
				SemaforoAbrir.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!Puerta.abierta) {
				Puerta.abierta = true;
				Puerta.contador--;
			} else {
				i--;
			}
			try {
				SemaforoAbrir.release();
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Abrir terminando");
	}
}

class CerrarE extends Thread {
	Semaphore SemaforoCerrar;

	public CerrarE(Semaphore SemaforoCerrar) {
		this.SemaforoCerrar = SemaforoCerrar;
	}

	public void run() {

		for (int i = 0; i < 1000; i++) {
			try {
				SemaforoCerrar.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Puerta.abierta) {
				Puerta.abierta = false;
				Puerta.contador++;
			} else {
				i--;

			}
			try {
				SemaforoCerrar.release();
				sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Cerrar terminando");
	}
}

public class Alternativa {
	public static void main(String[] args) throws InterruptedException {
		Semaphore semaforo = new Semaphore(1);
		Puerta.abierta = true;
		Thread a = new AbrirE(semaforo);
		Thread c = new CerrarE(semaforo);
		c.start();
		a.start();
		c.join();
		a.join();
		System.out.println("El resultado final es: " + Puerta.contador);
	}
}
