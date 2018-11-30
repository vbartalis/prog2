public class Yoda {

    public static void main(String[] args) {
/*
        String myString = null;
        if (myString.equals("valami")) { System.out.println("valami"); }
*/
//Exception in thread "main" java.lang.NullPointerException at Yoda.main(Yoda.java:6)

        String myString = null;
        if ("valami".equals(myString)) { System.out.println("valami"); }

//így helyes
    }

}
