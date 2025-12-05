package dev.gabrielfernandes.comission_api.commission.service.regrascommissaoservice;

import java.math.BigDecimal;

public interface RegrasComissao {
    boolean seAplica(BigDecimal valor);
    BigDecimal calcular(BigDecimal valor);
}
