package com.wa.condominio.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.wa.condominio.model.Imovel;
import com.wa.condominio.model.Morador;
import com.wa.condominio.utils.DateTimeUtils;
import com.wa.condominio.validation.ValidMorador;

@ValidMorador
public class MoradorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Nome do Morador é obrigatório.")
    @Size(min = 3, max = 50, message = "O campo Nome do Morador precisa ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "Celular é obrigatório.")
    @Pattern(regexp = "^[0-9]{11,16}$", message = "O campo Celular precisa ter entre 11 e 16 dígitos, apenas números.")
    private String celular;

    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @Size(max = 50, message = "O campo Email pode ter no máximo 50 caracteres.")
    private String email;

    private Boolean isProprietario;

    @NotNull(message = "Data de entrada é obrigatória.")
    @PastOrPresent(message = "Data de entrada não pode ser futura.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrada;

    @PastOrPresent(message = "Data de saída não pode ser futura.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSaida;

    @PastOrPresent(message = "Data de inclusão não pode ser futura.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInclusao;

    @PastOrPresent(message = "Data de alteração não pode ser futura.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAlteracao;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ImovelDTO imovelDto;

    @NotNull(message = "Imóvel é obrigatório.")
    private Long imovelId;

    public MoradorDTO() {}

    public MoradorDTO(Long id, String nome, String celular, String email, Boolean isProprietario,
                      LocalDate dataEntrada, LocalDate dataSaida,
                      LocalDate dataInclusao, LocalDate dataAlteracao) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.isProprietario = isProprietario;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.dataInclusao = dataInclusao;
        this.dataAlteracao = dataAlteracao;
    }

    public MoradorDTO(Morador morador) {
        this.id = morador.getId();
        this.nome = morador.getNome();
        this.celular = morador.getCelular();
        this.email = morador.getEmail();
        this.isProprietario = morador.getIsProprietario();

        this.dataEntrada = DateTimeUtils.fromUTC(morador.getDataEntrada());
        this.dataSaida = DateTimeUtils.fromUTC(morador.getDataSaida());
        this.dataInclusao = DateTimeUtils.fromUTC(morador.getDataInclusao());
        this.dataAlteracao = DateTimeUtils.fromUTC(morador.getDataAlteracao());
    }

    public MoradorDTO(Morador morador, Imovel imovel) {
        this(morador);
        this.imovelDto = new ImovelDTO(imovel);
        this.imovelId = imovel.getId();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getCelular() { return celular; }

    public void setCelular(String celular) { this.celular = celular; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Boolean getIsProprietario() { return isProprietario; }

    public void setIsProprietario(Boolean isProprietario) { this.isProprietario = isProprietario; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public LocalDate getDataInclusao() { return dataInclusao; }
    public void setDataInclusao(LocalDate dataInclusao) { this.dataInclusao = dataInclusao; }

    public LocalDate getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(LocalDate dataAlteracao) { this.dataAlteracao = dataAlteracao; }

    public ImovelDTO getImovelDto() { return imovelDto; }

    public void setImovelDto(ImovelDTO imovelDto) { this.imovelDto = imovelDto; }

    public Long getImovelId() { return imovelId; }

    public void setImovelId(Long imovelId) { this.imovelId = imovelId; }
}
