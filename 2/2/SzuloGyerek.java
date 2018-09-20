

class Broom{
  String Producer;
  String Made;
  String Usage;
  String Speed;

  public Broom(){
    Producer = "Nimbus Racing Broom Company";
    Made = "In or before August 1991";
    Usage = "Quidditch playing";
    Speed = "Slower than the Nimbus 2001";
  }
}

class BroomOwners extends Broom{
  String Owners;

  BroomOwners(){
    Owners = "Harry Potter";
  }
}

public class SzuloGyerek {

  public static void main(String[] args) {

    SzuloGyerek szg = new SzuloGyerek();
    Broom sepru = new Broom();

    System.out.println(sepru.Producer);
    System.out.println(sepru.Made);
    System.out.println(sepru.Usage);
    System.out.println(sepru.Speed);
    //System.out.println(sepru.Owners);

  }
}
