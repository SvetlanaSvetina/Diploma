package ru.netology.data;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.database.CreditRequestEntity;
import ru.netology.database.OrderEntity;
import ru.netology.database.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.SneakyThrows;



public class SQLHelper {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");


    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(url, user, password);
    }


    public static void dropDataBase() {
        var runner = new QueryRunner();
        var order = "DELETE FROM order_entity";
        var payment = "DELETE FROM payment_entity";
        var creditRequest = "DELETE FROM credit_request_entity";

        try (var connection = getConnection()) {
            runner.update(connection, order);
            runner.update(connection, payment);
            runner.update(connection, creditRequest);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static String getCardStatusForPayWithCard() {
        String statusQuery = "SELECT * FROM payment_entity";
        var runner = new QueryRunner();
        try (Connection connection = getConnection()) {
            var cardStatus = runner.query(connection, statusQuery, new BeanHandler<>(PaymentEntity.class));
            return cardStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getCardStatusForPayWithCredit() {
        String statusQuery = "SELECT * FROM credit_request_entity";
        var runner = new QueryRunner();
        try (Connection connection = getConnection()) {
            var cardStatus = runner.query(connection, statusQuery, new BeanHandler<>(CreditRequestEntity.class));
            return cardStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getPaymentIdForPayWithCard() {
        var idQueryForCardPay = "SELECT * FROM order_entity";
        var runner = new QueryRunner();
        try (var connection = getConnection()) {
            var paymentId = runner.query(connection, idQueryForCardPay, new BeanHandler<>(OrderEntity.class));
            return paymentId.getPayment_id();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPaymentIdForPayWithCredit() {
        var idQueryForCreditRequest = "SELECT * FROM order_entity";
        var runner = new QueryRunner();
        try (var connection = getConnection()) {
            var paymentId = runner.query(connection, idQueryForCreditRequest, new BeanHandler<>(OrderEntity.class));
            return paymentId.getPayment_id();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTransactionId() {
        var runner = new QueryRunner();
        String idTransactionQuery = "SELECT * FROM payment_entity";
        try (Connection connection = getConnection()) {
            var transactionId = runner.query(connection, idTransactionQuery, new BeanHandler<>(PaymentEntity.class));
            return transactionId.getTransaction_id();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getAmountPurchase() {
        var runner = new QueryRunner();
        String amountQuery = "SELECT * FROM payment_entity";
        try (Connection connection = getConnection()) {
            var transactionId = runner.query(connection, amountQuery, new BeanHandler<>(PaymentEntity.class));
            return transactionId.getAmount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getBankId() {
        String bankIdQuery = "SELECT * FROM credit_request_entity";
        var runner = new QueryRunner();
        try (Connection connection = getConnection()) {
            var bankId = runner.query(connection, bankIdQuery, new BeanHandler<>(CreditRequestEntity.class));
            return bankId.getBank_id();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}