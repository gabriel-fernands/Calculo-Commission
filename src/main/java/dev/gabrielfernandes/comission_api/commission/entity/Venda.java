package dev.gabrielfernandes.comission_api.commission.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Entity
@Table(name = "vendas")
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vendedor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    public Venda(String vendedor, BigDecimal valor) {
        this.vendedor = vendedor;
        this.valor = valor;
    }
}

