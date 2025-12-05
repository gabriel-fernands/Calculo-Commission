package dev.gabrielfernandes.comission_api.commission.controller;


import dev.gabrielfernandes.comission_api.commission.dto.ComissaoRequestDTO;
import dev.gabrielfernandes.comission_api.commission.dto.ComissaoResponseDTO;
import dev.gabrielfernandes.comission_api.commission.entity.Venda;
import dev.gabrielfernandes.comission_api.commission.service.ComissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comissoes")
@RequiredArgsConstructor
@Tag(name = "Comissões", description = "Endpoints para cálculo de comissões de vendedores")
public class ComissaoController {

    private final ComissaoService comissaoService;

    @PostMapping("/calcular")
    @Operation(summary = "Calcular comissões",
            description = "Calcula as comissões de vendedores baseado nas vendas fornecidas")
    public ResponseEntity<List<ComissaoResponseDTO>> calcularComissoes(
            @RequestBody @Valid ComissaoRequestDTO requestDTO) {
        return ResponseEntity.ok(comissaoService.calcularComissoes(requestDTO));
    }

    @GetMapping("/vendas")
    @Operation(summary = "Listar todas as vendas",
            description = "Retorna todas as vendas registradas no sistema")
    public ResponseEntity<List<Venda>> listarTodasVendas() {
        List<Venda> vendas = comissaoService.listarTodos();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/vendas/{vendedor}")
    @Operation(summary = "Listar vendas por vendedor",
            description = "Retorna todas as vendas de um vendedor específico")
    public ResponseEntity<List<Venda>> listarVendasPorVendedor(
            @PathVariable String vendedor) {
        List<Venda> vendas = comissaoService.listarVendasPorVendedor(vendedor);
        return ResponseEntity.ok(vendas);
    }
}
