package dev.gabrielfernandes.comission_api.commission.service;

import dev.gabrielfernandes.comission_api.commission.dto.ComissaoRequestDTO;
import dev.gabrielfernandes.comission_api.commission.dto.ComissaoResponseDTO;
import dev.gabrielfernandes.comission_api.commission.dto.VendaDTO;
import dev.gabrielfernandes.comission_api.commission.entity.Venda;
import dev.gabrielfernandes.comission_api.commission.exception.RegraInvalidaException;
import dev.gabrielfernandes.comission_api.commission.exception.VendaInvalidaException;
import dev.gabrielfernandes.comission_api.commission.repository.VendaRepository;
import dev.gabrielfernandes.comission_api.commission.service.regrascommissaoservice.RegrasComissao;
import dev.gabrielfernandes.comission_api.commission.mapper.VendasMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComissaoService {


    private final VendaRepository repository;
    private final List<RegrasComissao> regras;

    public ComissaoService(VendaRepository repository, List<RegrasComissao> regras) {
        this.repository = repository;
        this.regras = regras;
    }

    private  BigDecimal calcular(BigDecimal valor){
        for (RegrasComissao regra : regras) {
            if (regra.seAplica(valor)) {
                return regra.calcular(valor);
            }
        }
        throw new RegraInvalidaException("Nenhuma regra de comissão aplicável encontrada para o valor: " + valor);

    }


    @Transactional
    public List<ComissaoResponseDTO> calcularComissoes(ComissaoRequestDTO request) {

        if (request.getVendas() == null || request.getVendas().isEmpty()) {
            throw new VendaInvalidaException("Nenhuma venda enviada");
        }

        log.info("Calculando comissões para {} vendas", request.getVendas().size());

        repository.saveAll(
                request.getVendas().stream()
                        .map(v -> {
                            Venda entity = VendasMapper.toEntity(v);
                            entity.setId(null); // evita UPDATE e conflitos
                            return entity;
                        })
                        .toList()
        );

        return request.getVendas().stream()
                .collect(Collectors.groupingBy(VendaDTO::getVendedor))
                .entrySet()
                .stream()
                .map(entry -> {

                    BigDecimal totalVendas = entry.getValue().stream()
                            .map(VendaDTO::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalComissao = entry.getValue().stream()
                            .map(v -> calcular(v.getValor()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new ComissaoResponseDTO(
                            entry.getKey(),
                            totalVendas.setScale(2, RoundingMode.HALF_UP),
                            totalComissao.setScale(2, RoundingMode.HALF_UP),
                            entry.getValue().size()
                    );
                })
                .toList();
    }

    public List<Venda> listarVendasPorVendedor(String vendedor){
        return repository.findByVendedor(vendedor);
    }

    public List<Venda> listarTodos(){
        return repository.findAll();
    }
}
