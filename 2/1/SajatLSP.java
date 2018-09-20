
class Teglalap {

  private int szel;
  private int hossz;

  public int getSzel() {
    return szel;
  }
  public void setSzel(int szel) {
    this.szel = szel;
  }
  public int getHossz() {
    return hossz;
  }
  public void setHossz(int hossz) {
    this.hossz = hossz;
  }
  public int getTerulet() {
    return this.szel*this.hossz;
  }
}

class Negyzet extends Teglalap {
/*
  @Override
  public void setSzel(int szel) {
    super.setSzel(szel);
    super.setHossz(szel);
  }
  @Override
  public void setHossz(int hossz) {
    super.setSzel(hossz);
    super.setHossz(hossz);
  }
*/

}

public class SajatLSP {

  public void szamol(Teglalap r) {
    r.setSzel(3);
    r.setHossz(2);

    System.out.println(" hossz:"+ r.getHossz());
    System.out.println(" szel:"+ r.getSzel());
    System.out.println(" terulet:"+ r.getTerulet());
    }

  public static void main(String[] args) {
    SajatLSP sajat = new SajatLSP();

    System.out.println("Teglalap  ");
    sajat.szamol(new Teglalap());
    System.out.println("Negyzet   ");
    sajat.szamol(new Negyzet());


  }
}
