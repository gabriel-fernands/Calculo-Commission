package dev.gabrielfernandes.comission_api.commission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendaDTO {
    @NotNull
    private Long idVenda;
    @NotBlank(message = "Nome do vendedor é obrigatório")
    private String vendedor;

    @NotNull(message = "Valor da venda é obrigatório")
    @Positive(message = "Valor da venda deve ser positivo")
    private BigDecimal valor;
}