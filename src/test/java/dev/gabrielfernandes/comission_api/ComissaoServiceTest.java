package dev.gabrielfernandes.comission_api;

import dev.gabrielfernandes.comission_api.commission.dto.ComissaoRequestDTO;
import dev.gabrielfernandes.comission_api.commission.dto.ComissaoResponseDTO;
import dev.gabrielfernandes.comission_api.commission.dto.VendaDTO;
import dev.gabrielfernandes.comission_api.commission.entity.Venda;
import dev.gabrielfernandes.comission_api.commission.exception.RegraInvalidaException;
import dev.gabrielfernandes.comission_api.commission.exception.VendaInvalidaException;
import dev.gabrielfernandes.comission_api.commission.repository.VendaRepository;
import dev.gabrielfernandes.comission_api.commission.service.ComissaoService;
import dev.gabrielfernandes.comission_api.commission.service.regrascommissaoservice.RegrasComissao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@SpringBootTest
class ComissaoServiceTest {

    @Mock
    private VendaRepository repository;

    @Mock
    private RegrasComissao regra1;

    @Mock
    private RegrasComissao regra2;

    @InjectMocks
    private ComissaoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new ComissaoService(repository, List.of(regra1, regra2));
    }

    @Test
    void deveCalcularComissoesCorretamente() {

        // DTO de entrada
        VendaDTO venda1 = new VendaDTO(null, "Ana", new BigDecimal("100"));
        VendaDTO venda2 = new VendaDTO(null, "Ana", new BigDecimal("200"));

        ComissaoRequestDTO request = new ComissaoRequestDTO(List.of(venda1, venda2));

        // Mocks
        when(regra1.seAplica(new BigDecimal("100"))).thenReturn(true);
        when(regra1.calcular(new BigDecimal("100"))).thenReturn(new BigDecimal("10"));

        when(regra1.seAplica(new BigDecimal("200"))).thenReturn(false);
        when(regra2.seAplica(new BigDecimal("200"))).thenReturn(true);
        when(regra2.calcular(new BigDecimal("200"))).thenReturn(new BigDecimal("40"));

        // Act
        List<ComissaoResponseDTO> result = service.calcularComissoes(request);

        // Assert
        assertEquals(1, result.size());

        ComissaoResponseDTO response = result.get(0);
        assertEquals("Ana", response.getVendedor());
        assertEquals(new BigDecimal("300.00"), response.getTotalVendas());
        assertEquals(new BigDecimal("50.00"), response.getTotalComissao()); // 10 + 40
        assertEquals(2, response.getQuantidadeVendas());

        // saveAll deve ser chamado
        verify(repository, times(1)).saveAll(anyList());
    }

    @Test
    void deveLancarErroQuandoListaDeVendasForVazia() {
        ComissaoRequestDTO request = new ComissaoRequestDTO(List.of());

        assertThrows(VendaInvalidaException.class, () -> service.calcularComissoes(request));
    }

    @Test
    void deveLancarErroQuandoNenhumaRegraSeAplica() {
        VendaDTO venda = new VendaDTO(null, "Pedro", new BigDecimal("100"));
        ComissaoRequestDTO request = new ComissaoRequestDTO(List.of(venda));

        // Nenhuma regra válida
        when(regra1.seAplica(any())).thenReturn(false);
        when(regra2.seAplica(any())).thenReturn(false);

        assertThrows(RegraInvalidaException.class, () -> service.calcularComissoes(request));
    }

    @Test
    void deveListarVendasPorVendedor() {
        when(repository.findByVendedor("Ana")).thenReturn(List.of(new Venda("Ana", BigDecimal.TEN)));

        List<Venda> vendas = service.listarVendasPorVendedor("Ana");

        assertEquals(1, vendas.size());
        assertEquals("Ana", vendas.get(0).getVendedor());
    }

    @Test
    void deveListarTodasAsVendas() {
        when(repository.findAll()).thenReturn(List.of(new Venda("Ana", BigDecimal.ONE)));

        List<Venda> vendas = service.listarTodos();

        assertEquals(1, vendas.size());
    }
}
