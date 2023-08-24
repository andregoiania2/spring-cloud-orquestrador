package br.com.agsouza.loja.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.agsouza.loja.controller.dto.ItemDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long pedidoId;
	private Long voucher;
	private LocalDate dataParaEntrega;
	@Enumerated(EnumType.STRING)
	private CompraState state;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "compra", fetch = FetchType.EAGER)
	private List<Item> itens;
	//entrega
	private String rua;
	private int numero;
	private String estado;
	
	public Compra() {
		this.state = CompraState.RECEBIDO;
	}
	
	public List<ItemDTO> getItensDTO() {
		List<ItemDTO> itensDTO = new ArrayList<>(itens.size());
		for (Item item : itens) {
			itensDTO.add(new ItemDTO(item.getProdutId(), item.getQtde()));			
		}
		return itensDTO;
	}
}
