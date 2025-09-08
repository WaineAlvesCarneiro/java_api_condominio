package com.wa.condominio.services;

import java.util.List;
import java.util.stream.Collectors;

import com.wa.condominio.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wa.condominio.dto.MoradorDTO;
import com.wa.condominio.model.Imovel;
import com.wa.condominio.model.Morador;
import com.wa.condominio.repositories.ImovelRepository;
import com.wa.condominio.repositories.MoradorRepository;
import com.wa.condominio.services.exceptions.DatabaseException;
import com.wa.condominio.services.shared.PagedResult;
import com.wa.condominio.services.shared.Result;
import com.wa.condominio.utils.DateTimeUtils;

@Service
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private EmailService emailService;

    @Transactional(readOnly = true)
    public Result<List<MoradorDTO>> getAll() {
        List<MoradorDTO> dtos = moradorRepository.findAll()
                .stream()
                .map(m -> new MoradorDTO(m, m.getImovel()))
                .collect(Collectors.toList());

        return Result.success(dtos);
    }

    @Transactional(readOnly = true)
    public Result<PagedResult<MoradorDTO>> getAllPaged(int page, int linesPerPage, String orderBy, String direction) {
        Sort sort = "DESC".equalsIgnoreCase(direction) ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, sort);

        Page<Morador> pageEntities = moradorRepository.findAll(pageRequest);
        List<MoradorDTO> dtos = pageEntities.getContent()
                .stream()
                .map(m -> new MoradorDTO(m, m.getImovel()))
                .collect(Collectors.toList());

        PagedResult<MoradorDTO> pagedResult = new PagedResult<>();
        pagedResult.setItems(dtos);
        pagedResult.setTotalCount(pageEntities.getTotalElements());
        pagedResult.setPageIndex(page);
        pagedResult.setLinesPerPage(linesPerPage);

        return Result.success(pagedResult);
    }

    @Transactional(readOnly = true)
    public Result<MoradorDTO> getById(long id) {
        Morador morador = moradorRepository.findById(id)
                .orElseThrow(() -> new DatabaseException("Morador não encontrado."));
        return Result.success(new MoradorDTO(morador, morador.getImovel()));
    }

    @Transactional
    public Result<MoradorDTO> add(MoradorDTO dto) {
        Imovel imovel = imovelRepository.findById(dto.getImovelId())
                .orElseThrow(() -> new DatabaseException("Imóvel informado não existe."));

        Morador morador = new Morador();
        copyDtoToEntity(dto, morador, imovel);
        morador = moradorRepository.save(morador);

        emailService.enviarEmail(
                morador.getEmail(),
                "Bem-vindo ao Condomínio - JAVA!",
                "Olá, **" + morador.getNome() + "**! Seu cadastro foi realizado com sucesso."
        );

        return Result.success(new MoradorDTO(morador, morador.getImovel()), "Morador criado com sucesso.");
    }

    @Transactional
    public Result<Void> update(long id, MoradorDTO dto) {
        Morador morador = moradorRepository.findById(id)
                .orElseThrow(() -> new DatabaseException("Morador não encontrado."));
        Imovel imovel = imovelRepository.findById(dto.getImovelId())
                .orElseThrow(() -> new DatabaseException("Imóvel informado não existe."));

        copyDtoToEntity(dto, morador, imovel);
        moradorRepository.save(morador);

        emailService.enviarEmail(
                morador.getEmail(),
                "Atualização de Cadastro no Condomínio - JAVA",
                "Olá, **" + morador.getNome() + "**! Seu cadastro foi atualizado com sucesso."
        );

        return Result.success("Morador atualizado com sucesso.");
    }

    @Transactional
    public void delete(long id) {
        if (!moradorRepository.existsById(id)) {
            throw new DatabaseException("Morador não encontrado.");
        }

        try {
            moradorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não foi possível excluir o morador. Pode estar vinculado a outros registros.");
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByMoradorId(long id) {
        return moradorRepository.existsById(id);
    }

    private void copyDtoToEntity(MoradorDTO dto, Morador morador, Imovel imovel) {
        morador.setNome(dto.getNome());
        morador.setCelular(dto.getCelular());
        morador.setEmail(dto.getEmail());
        morador.setIsProprietario(dto.getIsProprietario());

        // Converte de LocalDate (DTO) para LocalDateTime em UTC (Entidade)
        morador.setDataEntrada(DateTimeUtils.toUTC(dto.getDataEntrada()));
        morador.setDataSaida(DateTimeUtils.toUTC(dto.getDataSaida()));
        morador.setDataInclusao(DateTimeUtils.toUTC(dto.getDataInclusao()));
        morador.setDataAlteracao(DateTimeUtils.toUTC(dto.getDataAlteracao()));

        morador.setImovel(imovel);
    }
}