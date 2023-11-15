package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.print.DocFlavor.INPUT_STREAM;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 0);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0,bankAccount.getBalance());
        assertEquals(0,bankAccount.getTransactionsCount());
        assertEquals(mRossi,bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        //Test deposit
        bankAccount.deposit(mRossi.getUserID(),INITIAL_AMOUNT);
        assertEquals(INITIAL_AMOUNT,bankAccount.getBalance());

        int expectedTransaction = 1;
        double expectedFee = StrictBankAccount.MANAGEMENT_FEE + expectedTransaction * StrictBankAccount.TRANSACTION_FEE;
        double expectedValue = INITIAL_AMOUNT - expectedFee;

        //Test getTransactions
        assertEquals(expectedTransaction,bankAccount.getTransactionsCount());

        //Test chargeManagementFees
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(expectedValue, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -INITIAL_AMOUNT);
            fail("Negative amount can be successfully be withdrawn.\nThis should not be happening");
        } catch (Exception e) {
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), INITIAL_AMOUNT);
            fail("Withdrawn more than balance successfully.\nThis should not be happening");
        } catch (Exception e) {
            assertEquals("Insufficient balance", e.getMessage());
        }
    }
}
