package dev.gabrielfernandes.comission_api.commission.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseComission {


    private int status;
    private String error;
    private String messagem;
    private String path;
    private LocalDateTime timestamp;

}
