package util;

import java.util.Objects;

public class MyTimer implements Comparable<MyTimer>{
	private int hora;
	private int minuto;
	private double segundo;

	public MyTimer(int hora, int minuto, double segundo) {
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
	}

	public MyTimer(int hora, int minuto) {
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = 0;
	}

	public MyTimer(int minuto) {
		this.hora = 0;
		this.minuto = minuto;
		this.segundo = 0;
	}

	public MyTimer(double segundos) {
		this.hora = 0;
		this.minuto = 0;
		this.segundo = segundos;
	}
	public int getHora() {
		return hora;
	}
	public static MyTimer fromMinuteToTimer(int tempo) {
		int horas = tempo/60;
		int min = tempo - horas*60;
		
		return new MyTimer(horas, min);
	}

	public int getMinuto() {
		return minuto;
	}

	public double getSegundo() {
		return segundo;
	}

	public void incrementaHora(int hora) {
		this.hora += hora;
	}

	public void incrementaMinuto(int minuto) {
		this.minuto += minuto;
		if (this.minuto >= 60) {
			incrementaHora(1);
			this.minuto = 0;
		}
	}

	public void incrementaSegundo(double segundo) {
		this.segundo += segundo;
		if (this.segundo >= 60) {
			incrementaMinuto(1);
			this.segundo = 0;
		}
	}

	public void decrementaSegundo() {
		if (this.segundo != 0)
			this.segundo = segundo--;
		else if (this.segundo == 0 && this.minuto > 0) {
			this.segundo = 59;
			this.minuto = minuto--;
		}
	}

	public double toSegundos(int hora, int minuto, double segundo) {
		int total = (int) (hora * 60 * 60 + minuto * 60 + segundo);
		return total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hora, minuto, segundo);
	}

	@Override
	public int compareTo(MyTimer o) {
		if(o == null) return -1;
		if(o.getHora() > this.getHora())
			return -1;
		else if(o.getHora() < this.getHora())
			return 1;
		else
			if(o.getMinuto() > this.getMinuto())
				return -1;
			else if(o.getMinuto() < this.getMinuto())
				return 1;
			else
				if(o.getSegundo() > this.getSegundo())
					return -1;
				else if(o.getSegundo() < this.getSegundo())
					return 1;
		return 0;
	}
	
	
}