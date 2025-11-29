package br.com.pix_service.ewallet.application.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PixTransferResponse implements Serializable {

    @Schema(description = "Identificador único da transação Pix", example = "E2E202511278B3F2A7E")
    private String endToEndId;

    @Schema(description = "Status da transação Pix", example = "PENDING")
    private String status;
}
