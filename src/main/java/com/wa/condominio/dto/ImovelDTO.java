package com.wa.condominio.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import com.wa.condominio.model.Imovel;
import com.wa.condominio.validation.ValidImovel;
import jakarta.validation.constraints.Size;

@ValidImovel
public class ImovelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Bloco é obrigatório.")
    @Size(min = 1, max = 10, message = "O campo Bloco precisa ter entre 1 e 10 caracteres.")
    private String bloco;

    @NotBlank(message = "Apartamento é obrigatório.")
    @Size(min = 1, max = 10, message = "O campo Apartamento precisa ter entre 1 e 10 caracteres.")
    private String apartamento;

    //@JsonProperty("BoxGaragem")
    @NotBlank(message = "Box Garagem é obrigatório.")
    @Size(min = 1, max = 10, message = "O campo Box Garagem precisa ter entre 1 e 10 caracteres.")
    private String boxGaragem;

    public ImovelDTO() { }
    public ImovelDTO(Long id, String bloco, String apartamento, String boxGaragem) {
        this.id = id;
        this.bloco = bloco;
        this.apartamento = apartamento;
        this.boxGaragem = boxGaragem;
    }
    public ImovelDTO(Imovel imovel) {
        this.id = imovel.getId();
        this.bloco = imovel.getBloco();
        this.apartamento = imovel.getApartamento();
        this.boxGaragem = imovel.getBoxGaragem();
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBloco() { return bloco; }
    public void setBloco(String bloco) { this.bloco = bloco; }

    public String getApartamento() { return apartamento; }
    public void setApartamento(String apartamento) { this.apartamento = apartamento; }

    public String getBoxGaragem() { return boxGaragem; }
    public void setBoxGaragem(String boxGaragem) { this.boxGaragem = boxGaragem; }
}
