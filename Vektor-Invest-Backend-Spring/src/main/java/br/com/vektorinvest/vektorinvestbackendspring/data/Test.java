package br.com.vektorinvest.vektorinvestbackendspring.data;

public class Test {
    public static void main(String[] args) {

        Integer a = null;

            //false    false    false    true
        if (2 != 2 && 5 != 5 || 6 != 6 || a != null) {
            System.out.println("jdjd");
            System.out.println(2 != 2);
            System.out.println(5 != 5);
            System.out.println(6 != 6);
            System.out.println(a == null);

        } else {
            System.err.println("dd");
            System.out.println(2 != 2);
            System.out.println(5 != 5);
            System.out.println(a == null);
        }

    }
}
