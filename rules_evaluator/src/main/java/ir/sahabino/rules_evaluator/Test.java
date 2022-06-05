package ir.sahabino.rules_evaluator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        Date date = new Date(1654321560000L);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println();
        String format1 = format.format(date);
        System.out.println(format1);
        LinkedList linkedList = new LinkedList();
        linkedList.removeFirst();
    }
}
