package br.com.agsouza.loja.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@AllArgsConstructor
public class ItemDTO {
	private Long id;
	private int qtde;	
}
