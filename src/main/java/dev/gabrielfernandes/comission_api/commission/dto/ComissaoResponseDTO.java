package dev.gabrielfernandes.comission_api.commission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComissaoResponseDTO {

    private String vendedor;
    private BigDecimal totalVendas;
    private BigDecimal totalComissao;
    private Integer quantidadeVendas;
}