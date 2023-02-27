package com.clevertec.testapp.supermarket.dao;

import com.clevertec.testapp.supermarket.entity.Cashier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CashierDAOTest {
    private CashierDAO cashierDAO;
    private final Cashier FIRST = new Cashier(1L, "Ivanov", 'm', 200.0, "ivanov@gmail.com");
    private final Cashier SECOND = new Cashier(2L, "Petrova", 'f', 200.0, "petrova@gmail.com");

    @BeforeEach
    void setUp() {
        cashierDAO = CashierDAO.getInstance();
    }

    @Test
    void cashiersEmptyIfNoCashierAdded() {
        List<Cashier> cashiers = cashierDAO.findAll();
        assertTrue(cashiers.isEmpty(), () -> "Cashier list should be empty");
    }

    @Test
    void cashiersSizeIfCashierAdded() {
        cashierDAO.add(FIRST);
        cashierDAO.add(SECOND);
        List<Cashier> cashiers = cashierDAO.findAll();
        assertThat(cashiers).hasSize(2);
    }

    @Test
    void updateSuccessIfCashierExists() {
        cashierDAO.add(FIRST);
        boolean upCashier = cashierDAO.update(FIRST);
        assertThat(upCashier).isTrue();
    }
    @Test
    void updateFailIfCashierDoesNotExist() {
        cashierDAO.add(FIRST);
        boolean upCashier = cashierDAO.update(SECOND);
        assertFalse(upCashier);
    }
    @Test
    void throwExceptionIfCashirDoesNotExist() {
        cashierDAO.add(FIRST);
        Long id=3L;
        assertAll(
                () -> {
                    IllegalArgumentException exception = assertThrows(
                            IllegalArgumentException.class, () -> cashierDAO.delete(id));
                    assertThat(exception.getMessage()).isEqualTo("cashier id = " + id + " not found");
                },
                () -> assertThrows(IllegalArgumentException.class, () -> cashierDAO.findById(id))
        );
    }
}