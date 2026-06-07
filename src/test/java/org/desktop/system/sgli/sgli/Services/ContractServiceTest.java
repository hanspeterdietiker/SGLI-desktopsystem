package org.desktop.system.sgli.sgli.Services;


import org.desktop.system.sgli.sgli.Dto.ContractDto;
import org.desktop.system.sgli.sgli.Entity.ContractModel;
import org.desktop.system.sgli.sgli.Entity.Enum.ContractTypeEnum;
import org.desktop.system.sgli.sgli.Repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {


    @Mock
    ContractRepository contractRepository;

    @InjectMocks
    ContractService contractService;

    @Test
    void shouldSaveContractSuccessfully() {
        when(contractRepository.existsByNameLocatario("Maria Souza")).thenReturn(false);
        when(contractRepository.existsByCpfLocatario("123.456.789-00")).thenReturn(false);
        when(contractRepository.save(any(ContractModel.class))).thenAnswer(i -> i.getArgument(0));

        ContractDto input = new ContractDto(
                "Joao Silva", "Maria Souza",
                "123.456.789-00", "987.654.321-00",
                "1500.00", "200.00", "300.00",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31),
                ContractTypeEnum.FIADOR);

        var contractCreated = contractService.save(input);

        assertNotNull(contractCreated);
        assertEquals("Maria Souza", contractCreated.getNameLocatario());
        assertEquals("123.456.789-00", contractCreated.getCpfLocatario());
        assertEquals(new BigDecimal("1500.00"), contractCreated.getValorAlug());

        verify(contractRepository).existsByNameLocatario("Maria Souza");
        verify(contractRepository).existsByCpfLocatario("123.456.789-00");
        verify(contractRepository).save(any(ContractModel.class));
    }
}






