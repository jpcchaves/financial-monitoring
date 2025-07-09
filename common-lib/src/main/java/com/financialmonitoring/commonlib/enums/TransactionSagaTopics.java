package com.financialmonitoring.commonlib.enums;

public enum TransactionSagaTopics {
    START_TRANSACTION_SAGA("start-transaction-saga"),
    BASE_ORCHESTRATOR("orchestrator"),
    FINISH_SUCCESS("finish-success"),
    FINISH_FAIL("finish-fail"),
    BALANCE_CHECK_SUCCESS("balance-check-success"),
    BALANCE_CHECK_FAIL("balance-check-fail"),
    FRAUD_CHECK_SUCCESS("fraud-check-success"),
    FRAUD_CHECK_FAIL("fraud-check-fail"),
    NOTIFICATION_SUCCESS("notification-success"),
    NOTIFICATION_FAIL("notification-fail"),
    NOTIFY_ENDING("notify-ending"),
    TESTE_TEMPORAl("teste-temporal");

    private final String transactionTopic;

    TransactionSagaTopics(String transactionTopic) {
        this.transactionTopic = transactionTopic;
    }

    public String getTransactionTopic() {
        return transactionTopic;
    }
}
