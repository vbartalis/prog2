import static java.lang.Thread.sleep;

public class Gagyi
{

public static void main (String[]args) throws InterruptedException
{

//Integer x = new Integer (5);
//Integer t = new Integer (x - 0);

Integer x=128;    //végtelen 128
Integer t=128;    //nem fut le 127
System.out.println (x);
System.out.println (t);
System.out.println(x == t);
while (x <= t && x >= t && t != x){
  System.out.println ("fut"); //végtelen
  sleep(1000);
}
}

}
