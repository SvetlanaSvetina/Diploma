package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import ru.netology.database.CreditRequestEntity;
import ru.netology.database.OrderEntity;
import ru.netology.database.PaymentEntity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import org.apache.commons.dbutils.ResultSetHandler;

import lombok.SneakyThrows;

public class SQLHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");


    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static void dropDataBase() {
        var runner = new QueryRunner();
        var payment = "DELETE FROM payment_entity";
        var creditRequest = "DELETE FROM credit_request_entity";
        var order = "DELETE FROM order_entity";


        var connection = getConnection();
        runner.update(connection, order);
        runner.update(connection, payment);
        runner.update(connection, creditRequest);
    }

    @SneakyThrows
    public static List<PaymentEntity> getPaymentInfo() {
        var statusQuery = "SELECT * FROM payment_entity ORDER BY created DESC;";
        var runner = new QueryRunner();
        var connection = getConnection();
        ResultSetHandler<List<PaymentEntity>> resultHandler= new BeanListHandler<>(PaymentEntity.class);
        return runner.query(connection,statusQuery,resultHandler);
    }

    @SneakyThrows
    public static List<CreditRequestEntity> getCreditRequestInfo() {
        var statusQuery = "SELECT * FROM credit_request_entity ORDER BY created DESC;";
        var runner = new QueryRunner();
        var connection = getConnection();
        ResultSetHandler<List<CreditRequestEntity>> resultHandler= new BeanListHandler<>(CreditRequestEntity.class);
        return runner.query(connection,statusQuery,resultHandler);

    }

    @SneakyThrows
    public static List<OrderEntity> getOrderInfo() {
        var idQueryForCardPay = "SELECT * FROM order_entity ORDER BY created DESC;";
        var runner = new QueryRunner();
        var connection = getConnection();
        ResultSetHandler<List<OrderEntity>>resultHandler = new BeanListHandler<>(OrderEntity.class);
        return runner.query(connection, idQueryForCardPay, resultHandler);
    }
}

