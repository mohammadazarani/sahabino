package ir.sahabino.rules_evaluator.eval;

import lombok.SneakyThrows;

import java.sql.*;
import java.util.List;

public class ConditionMeet implements Runnable {
    List<RuleChecker> ruleCheckers;
    Connection connection;
    Statement stmt;
    ResultSet rs;

    public ConditionMeet(List<RuleChecker> ruleCheckers) {
        this.ruleCheckers = ruleCheckers;
    }

    public void saveInDatabase(String rule_name, String market, String currentValue, String data) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rules");
//        Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
        stmt = connection.createStatement();
        String sql = String.format("INSERT INTO alert (rule_name, market, current_value, date) VALUES ('%s', '%s', '%s', '%s')", rule_name, market, currentValue, data);
        System.out.println(sql);
        stmt.executeUpdate(sql);
    }


    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            for (RuleChecker ruleChecker : this.ruleCheckers) {
                Alert alert = ruleChecker.meetCondition();
                if (alert != null) {
                    saveInDatabase(
                            alert.getRuleName(),
                            alert.getMarket(),
                            alert.getCurrentValue(),
                            alert.getData()
                    );
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
