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
        // Transaction SAGA
            // Source orchestrator, status success, goes to balance_check_success
            {ORCHESTRATOR, SUCCESS, BALANCE_CHECK_SUCCESS},
            // Source orchestrator, status fail, go to finish fail
            {ORCHESTRATOR, FAIL, FINISH_FAIL},
            // Source balance_service, status success, goes to fraud_check_success
            {BALANCE_SERVICE, SUCCESS, FRAUD_CHECK_SUCCESS},
            // Source balance_service, status fail, go to finish_fail (it goes to finish fail because it failed in the first step of the saga)
            {BALANCE_SERVICE, FAIL, FINISH_FAIL},
            // Source balance_service, status rollback, goes to balance_check_fail
            {BALANCE_SERVICE, ROLLBACK_PENDING, BALANCE_CHECK_FAIL},
            // Source fraud_service, status success, goes to finish_success (this topic should be mapped in the transaction service)
            {FRAUD_SERVICE, SUCCESS, FINISH_SUCCESS},
            // Source fraud_service, status fail, goes to previous topic to rollback the balance operation
            {FRAUD_SERVICE, FAIL, BALANCE_CHECK_FAIL},
            // Source fraud_service, status rollback_pending, goes to fraud_check_fail (invalidate the transaction because it didn't pass the fraud check)
            {FRAUD_SERVICE, ROLLBACK_PENDING, FRAUD_CHECK_FAIL}
    };

    public static final int EVENT_SOURCE_INDEX = 0;
    public static final int SAGA_STATUS_INDEX = 1;
    public static final int TOPIC_INDEX = 2;
}
