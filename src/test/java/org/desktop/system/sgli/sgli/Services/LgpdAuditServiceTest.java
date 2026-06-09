package org.desktop.system.sgli.sgli.Services;

import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LgpdAuditServiceTest {

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private LgpdAuditService auditService;

    private ContractModel contractWithInvalidCpf;
    private ContractModel contractWithValidCpf;

    @BeforeEach
    void setUp() {
        contractWithInvalidCpf = new ContractModel();
        contractWithInvalidCpf.setId(UUID.randomUUID());
        contractWithInvalidCpf.setNameLocatario("João Silva");
        contractWithInvalidCpf.setNameLocador("Maria Costa");
        contractWithInvalidCpf.setCpfLocatario("111.111.111-11");
        contractWithInvalidCpf.setCpfLocador("529.982.247-25");
        contractWithInvalidCpf.setValorAlug(BigDecimal.valueOf(1000));
        contractWithInvalidCpf.setValorIptu(BigDecimal.ZERO);
        contractWithInvalidCpf.setValorCond(BigDecimal.ZERO);
        contractWithInvalidCpf.setDateInit(LocalDate.now().minusYears(6));
        contractWithInvalidCpf.setDateEnd(LocalDate.now().minusYears(5));

        contractWithValidCpf = new ContractModel();
        contractWithValidCpf.setId(UUID.randomUUID());
        contractWithValidCpf.setNameLocatario("Ana Paula");
        contractWithValidCpf.setNameLocador("Carlos Lima");
        contractWithValidCpf.setCpfLocatario("529.982.247-25");
        contractWithValidCpf.setCpfLocador("529.982.247-25");
        contractWithValidCpf.setValorAlug(BigDecimal.valueOf(2000));
        contractWithValidCpf.setValorIptu(BigDecimal.ZERO);
        contractWithValidCpf.setValorCond(BigDecimal.ZERO);
        contractWithValidCpf.setDateInit(LocalDate.now().minusYears(1));
        contractWithValidCpf.setDateEnd(LocalDate.now().plusYears(1));
    }

    @Test
    void detectsInvalidCpfInContracts() {
        when(contractRepository.findAll()).thenReturn(List.of(contractWithInvalidCpf, contractWithValidCpf));

        var report = auditService.runAudit();

        assertTrue(report.invalidCpfCount() > 0);
        assertTrue(report.invalidCpfContracts().contains(contractWithInvalidCpf.getId().toString()));
    }

    @Test
    void detectsExpiredContracts() {
        when(contractRepository.findAll()).thenReturn(List.of(contractWithInvalidCpf, contractWithValidCpf));

        var report = auditService.runAudit();

        assertEquals(1, report.expiredContractCount());
    }

    @Test
    void cleanSystemReturnsNoViolations() {
        when(contractRepository.findAll()).thenReturn(List.of(contractWithValidCpf));

        var report = auditService.runAudit();

        assertEquals(0, report.invalidCpfCount());
    }
}
