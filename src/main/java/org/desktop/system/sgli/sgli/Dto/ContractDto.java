package org.desktop.system.sgli.sgli.Dto;

import org.desktop.system.sgli.sgli.Entity.Enum.ContractTypeEnum;

import java.time.LocalDate;

public record ContractDto(
        String nameLocador,
        String nameLocatario,
        String cpfLocatario,
        String cpfLocador,
        String valorAlugStr,
        String valorIptuStr,
        String valorCondStr,
        LocalDate dateInit,
        LocalDate dateEnd,
        ContractTypeEnum contractType
) {}
