package br.com.agsouza.loja.controller.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VoucherDTO {
	private Long numero;
	private LocalDate previsaoParaEntrega;
}
