
/*
 * Author: Daniel Arroyo
 * 
 * Para evitar el deadlock he usado 2 bucles while con las 2 condiciones posibles en el codigo, que se encargaran de detener 	
 * un hilo pero nunca los dos provocando un bloqueo, ya que siempre se cumplira una de las dos condiciones, por tanto podran
 * desbloquear al otro.
 * 
 * Ademas uso un sleep en el Consumidor para forzar la entrada siempre por Productor primero
 * 
 * 
 */

class Productor extends Thread {
	private Deposito deposito;

	public Productor(Deposito d) {
		deposito = d;
	}

	public void run() {
		for (int i = 1; i < 20; i++) {
			deposito.guardar();
		}
	}
}

class Consumidor extends Thread {
	private Deposito deposito;

	public Consumidor(Deposito d) {
		deposito = d;
	}

	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 1; i < 20; i++) {
			deposito.sacar();
		}
	}
}

class Deposito {
	private int elementos = 0;

	public synchronized void guardar() {
// En el while de guardar se accedera cuando nuestro deposito este lleno y por tanto no podamos guardar mas, asi que lo bloqueamos
// hasta que se vacie y nos notifique
		while (getElementos() == 1) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
// En este if comprobamos el estado vacio de nuestro deposito y si se cumple guardaremos 1 y notificaremos al metodo sacar si 
// se encuentra esperando 
		if (getElementos() == 0) {
			elementos = getElementos() + 1;
			System.out.println("guarda " + getElementos());
			notifyAll();
		}
	}

	public synchronized void sacar() {
// En el while de sacar se accedera cuando nuestro deposito este vacio y por tanto no podamos sacar mas, asi que lo bloqueamos
// hasta que se guarde alguno y nos notifique para poder sacar		
		while (getElementos() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
// En este if comprobamos el estado lleno de nuestro deposito y si se cumple sacaremos 1 y notificaremos al metodo guardar si 
// se encuentra esperando 
		if (getElementos() == 1) {
			elementos = getElementos() - 1;
			System.out.println("saca " + getElementos());
			notifyAll();
		}
	}
// Getter de la variable del Deposito
	public int getElementos() {
		return elementos;
	}
}

public class Ejercicio2 {
	public static void main(String[] args) throws InterruptedException {
// Creo el objeto compartido Deposito y los dos hilos Productor y Consumidor, y ejecuto su funcionamiento
		Deposito miDeposito = new Deposito();
		Thread s1 = new Productor(miDeposito);
		Thread r1 = new Consumidor(miDeposito);
		s1.start();
		r1.start();
		s1.join();
		r1.join();
		System.out.println("El resultado final es: " + miDeposito.getElementos());
	}
}