package org.test.bankapp.model;

import org.test.bankapp.exception.NotEnoughFundsException;

public interface Account extends Report {
    // Получить баланс
    float getBalance();

    // Положить деньги на счет
    void deposit(float x);

    // Снять деньги со счета
    void withdraft(float x) throws NotEnoughFundsException;

    //  выводящий округленное значение баланса счета
    void decimalValue();
}
