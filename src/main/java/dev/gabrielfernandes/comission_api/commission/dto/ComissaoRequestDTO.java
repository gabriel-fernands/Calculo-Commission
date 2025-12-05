package dev.gabrielfernandes.comission_api.commission.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComissaoRequestDTO {

    @NotEmpty(message = "Lista de vendas não pode estar vazia")
    @Valid
    private List<VendaDTO> vendas;
}
