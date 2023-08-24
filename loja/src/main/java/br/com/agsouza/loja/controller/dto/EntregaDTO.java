package br.com.agsouza.loja.controller.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaDTO {
	private Long pedidoId;
	private LocalDate dataParaEntrega;
	private String enderecoOrigem;
	private String enderecoDestino;
}
