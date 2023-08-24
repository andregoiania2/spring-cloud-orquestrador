package br.com.agsouza.loja.controller.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.agsouza.loja.controller.form.EnderecoForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraDTO {
	@JsonIgnore
	private Long compraId;
	private List<ItemDTO> itens;
	private EnderecoForm endereco;	
}
