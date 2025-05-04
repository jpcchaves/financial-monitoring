package com.financialmonitoring.orchestrator.core.saga;

import static com.financialmonitoring.commonlib.enums.EventSource.*;
import static com.financialmonitoring.commonlib.enums.SagaStatus.*;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.*;

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
            {FRAUD_SERVICE, SUCCESS, NOTIFICATION_SUCCESS},

            // FRAUD_SERVICE - FAIL - BALANCE_CHECK_FAIL (PREVIOUS TOPIC FAILURE TO UNDO THE PREVIOUS OPERATION)
            {FRAUD_SERVICE, FAIL, BALANCE_CHECK_FAIL},

            // FRAUD_SERVICE - ROLLBACK_PENDING - FRAUD_CHECK_FAIL (TO ROLL BACK THE FRAUD_CHECK OPERATION)
            {FRAUD_SERVICE, ROLLBACK_PENDING, FRAUD_CHECK_FAIL},

            // NOTIFICATION_SERVICE - SUCCESS - FINISH_SUCCESS THE SAGA
            // When it finishes success, it will send the event to the notify-ending topic
            // mapped in the transaction-service which will be consumed and process the data from the event
            {NOTIFICATION_SERVICE, SUCCESS, FINISH_SUCCESS},

            // NOTIFICATION_SERVICE - FAIL - FRAUD_CHECK_FAIL (PREVIOUS TOPIC FAILURE TO UNDO THE PREVIOUS OPERATION)
            {NOTIFICATION_SERVICE, FAIL, FRAUD_CHECK_FAIL},

            // NOTIFICATION_SERVICE - ROLLBACK_PENDING - NOTIFICATION_FAIL (TO ROLL BACK THE NOTIFICATION OPERATION)
            {NOTIFICATION_SERVICE, ROLLBACK_PENDING, NOTIFICATION_FAIL},

    };

    public static final int EVENT_SOURCE_INDEX = 0;
    public static final int SAGA_STATUS_INDEX = 1;
    public static final int TOPIC_INDEX = 2;
}
