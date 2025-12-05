package dev.gabrielfernandes.comission_api.commission.mapper;

import dev.gabrielfernandes.comission_api.commission.dto.VendaDTO;
import dev.gabrielfernandes.comission_api.commission.entity.Venda;
public class VendasMapper {

    public static Venda toEntity(VendaDTO dto) {
        return Venda.builder()
                .valor(dto.getValor())
                .vendedor(dto.getVendedor())
                .build();
    }

    public static VendaDTO toDto(Venda entity) {
        return VendaDTO.builder()
                .idVenda(entity.getId())
                .valor(entity.getValor())
                .vendedor(entity.getVendedor())
                .build();
    }
}
