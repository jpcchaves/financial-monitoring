package com.financialmonitoring.orchestrator.core.saga;

import static com.financialmonitoring.commonlib.enums.EventSource.*;
import static com.financialmonitoring.commonlib.enums.SagaStatus.*;
import static com.financialmonitoring.commonlib.enums.TransactionSagaTopics.*;

public class SagaHandler {

    private SagaHandler() {

    }

    public static final Object[][] SAGA_HANDLER = new Object[][] {
            {ORCHESTRATOR, SUCCESS, BALANCE_CHECK_SUCCESS},
            {ORCHESTRATOR, FAIL, FINISH_FAIL},

            {BALANCE_SERVICE, SUCCESS, FRAUD_CHECK_SUCCESS},
            {BALANCE_SERVICE, FAIL, FINISH_FAIL},
            {BALANCE_SERVICE, ROLLBACK_PENDING, BALANCE_CHECK_FAIL},

            {FRAUD_SERVICE, SUCCESS, NOTIFICATION_SUCCESS},
            {FRAUD_SERVICE, FAIL, BALANCE_CHECK_FAIL},
            {FRAUD_SERVICE, ROLLBACK_PENDING, FRAUD_CHECK_FAIL},

            {NOTIFICATION_SERVICE, SUCCESS, FINISH_SUCCESS},
            {NOTIFICATION_SERVICE, FAIL, FRAUD_CHECK_FAIL},
            {NOTIFICATION_SERVICE, ROLLBACK_PENDING, NOTIFICATION_FAIL},

    };

    public static final int EVENT_SOURCE_INDEX = 0;
    public static final int SAGA_STATUS_INDEX = 1;
    public static final int TOPIC_INTEX = 2;
}
