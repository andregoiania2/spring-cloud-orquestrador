package br.com.agsouza.loja.controller.form;

import java.util.ArrayList;
import java.util.List;

import br.com.agsouza.loja.controller.dto.ItemDTO;
import br.com.agsouza.loja.model.Compra;
import br.com.agsouza.loja.model.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraForm {
	private List<ItemDTO> itens;
	private EnderecoForm endereco;
	
	public Compra convert() {
		Compra compra = new Compra();
		if (this.endereco != null) {
			compra.setRua(this.endereco.getRua());
			compra.setEstado(this.endereco.getEstado());
			compra.setNumero(endereco.getNumero());
		}
		List<Item> listItens = new ArrayList<>(itens.size());
		for (ItemDTO itemDTO : itens) {
			Item item = new Item();
			item.setProdutId(itemDTO.getId());
			item.setQtde(itemDTO.getQtde());
			item.setCompra(compra);
			listItens.add(item);
		}
		compra.setItens(listItens);
		
		return compra;
	}
}
