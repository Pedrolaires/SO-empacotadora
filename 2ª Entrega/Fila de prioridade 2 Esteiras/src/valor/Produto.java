package valor;

public class Produto {

	private double volume;
	private boolean empacotado;
	private int indice;

	public Produto(double volume) {
		this.volume = volume;
		this.empacotado = false;
	}

	public Produto() {
		this.volume = 250.0;
	}
	
	public void setIndice(int i ) {
		this.indice = i;
	}
	
	public int getIndice() {
		return this.indice;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean estaEmpacotado() {
		return empacotado;
	}

	public boolean setEmpacotado(boolean empacotado) {
		return this.empacotado = empacotado;
	}

	public boolean getEmpacotado() {
		return this.empacotado;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("volume: " + this.volume + " status: " + (this.empacotado ? "Empacotado" : "Não empacotado"));
		return sb.toString();
	}
}
