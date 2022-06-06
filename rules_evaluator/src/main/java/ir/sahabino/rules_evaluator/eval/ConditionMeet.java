package ir.sahabino.rules_evaluator.eval;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.List;

public class ConditionMeet implements Runnable {
    List<Rule> rules;
    Connection connection;
    Statement stmt;
    ResultSet rs;

    public ConditionMeet(List<Rule> rules) {
        this.rules = rules;
    }

    public void saveInDatabase(String rule_name, String market) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rules");
//        Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
        stmt = connection.createStatement();
        String sql = String.format("INSERT INTO rule (ruleName, market) VALUES ('%s', '%s')", rule_name, market);
        System.out.println(sql);
        stmt.executeUpdate(sql);
    }


    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            for (Rule rule : rules) {
                if (rule.meetCondition()) {
                    System.out.println(rule);
                    saveInDatabase(rule.getRuleName(), rule.getMarket());
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
