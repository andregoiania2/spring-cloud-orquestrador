package br.com.agsouza.loja.controller.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnderecoForm {
	private String rua;
	private int numero;
	private String estado;
}
