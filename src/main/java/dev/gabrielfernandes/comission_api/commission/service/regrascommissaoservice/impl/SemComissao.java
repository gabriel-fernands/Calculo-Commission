package dev.gabrielfernandes.comission_api.commission.service.regrascommissaoservice.impl;


import dev.gabrielfernandes.comission_api.commission.service.regrascommissaoservice.RegrasComissao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SemComissao implements RegrasComissao {

    @Override
    public boolean seAplica(BigDecimal valor) {
        return valor.compareTo(BigDecimal.valueOf(100)) < 0;
    }

    @Override
    public BigDecimal calcular(BigDecimal valor) {
        return BigDecimal.ZERO;
    }
}