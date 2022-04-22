package util;

public class MyTimer {
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

  public int getHora() {
    return hora;
  }

  public int getMinuto() {
    return minuto;
  }

  public double getSegundo() {
    return segundo;
  }
}