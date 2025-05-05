package com.financialmonitoring.orchestrator.core.saga;

import static com.financialmonitoring.commonlib.enums.EventSource.BALANCE_SERVICE;
import static com.financialmonitoring.commonlib.enums.EventSource.FRAUD_SERVICE;
import static com.financialmonitoring.commonlib.enums.EventSource.ORCHESTRATOR;
import static com.financialmonitoring.commonlib.enums.SagaStatus.FAIL;
import static com.financialmonitoring.commonlib.enums.SagaStatus.ROLLBACK_PENDING;
import static com.financialmonitoring.commonlib.enums.SagaStatus.SUCCESS;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.BALANCE_CHECK_FAIL;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.BALANCE_CHECK_SUCCESS;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.FINISH_FAIL;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.FINISH_SUCCESS;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.FRAUD_CHECK_FAIL;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.FRAUD_CHECK_SUCCESS;

public class SagaHandler {

    private SagaHandler() {

    }

    public static final Object[][] SAGA_HANDLER = new Object[][] {

            // SOURCE      STATUS   DESTINATION TOPIC
            // ORCHESTRATOR - SUCCESS - DO_BALANCE_CHECK
            {ORCHESTRATOR, SUCCESS, BALANCE_CHECK_SUCCESS},

            // ORCHESTRATOR - FAIL - FINISH_FAIL
            {ORCHESTRATOR, FAIL, FINISH_FAIL},

            // BALANCE_SERVICE - SUCCESS - DO_FRAUD_CHECK
            {BALANCE_SERVICE, SUCCESS, FRAUD_CHECK_SUCCESS},

            // BALANCE_SERVICE - FAIL - FINISH_FAIL
            {BALANCE_SERVICE, FAIL, FINISH_FAIL},

            // BALANCE_SERVICE - ROLLBACK_PENDING - BALANCE_CHECK_FAIL
            {BALANCE_SERVICE, ROLLBACK_PENDING, BALANCE_CHECK_FAIL},

            // FRAUD_SERVICE - SUCCESS - DO_NOTIFICATION_SEND
            {FRAUD_SERVICE, SUCCESS, FINISH_SUCCESS},

            // FRAUD_SERVICE - FAIL - BALANCE_CHECK_FAIL (PREVIOUS TOPIC FAILURE TO UNDO THE PREVIOUS OPERATION)
            {FRAUD_SERVICE, FAIL, BALANCE_CHECK_FAIL},

            // FRAUD_SERVICE - ROLLBACK_PENDING - FRAUD_CHECK_FAIL (TO ROLL BACK THE FRAUD_CHECK OPERATION)
            {FRAUD_SERVICE, ROLLBACK_PENDING, FRAUD_CHECK_FAIL}
    };

    public static final int EVENT_SOURCE_INDEX = 0;
    public static final int SAGA_STATUS_INDEX = 1;
    public static final int TOPIC_INDEX = 2;
}
