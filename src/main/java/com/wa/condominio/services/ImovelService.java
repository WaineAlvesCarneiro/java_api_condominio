package com.wa.condominio.services;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wa.condominio.dto.ImovelDTO;
import com.wa.condominio.model.Imovel;
import com.wa.condominio.repositories.ImovelRepository;
import com.wa.condominio.services.exceptions.DatabaseException;
import com.wa.condominio.services.shared.PagedResult;
import com.wa.condominio.services.shared.Result;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    @Autowired
    private MoradorService moradorService;

    @Transactional(readOnly = true)
    public Result<List<ImovelDTO>> getAll() {
        List<Imovel> imoveis = imovelRepository.findAll();
        List<ImovelDTO> dtos = imoveis
                .stream()
                .map(ImovelDTO::new)
                .collect(Collectors.toList());

        return Result.success(dtos);
    }

    @Transactional(readOnly = true)
    public Result<PagedResult<ImovelDTO>> getAllPaged(int page, int linesPerPage, String orderBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
        Pageable pageRequest = PageRequest.of(page, linesPerPage, sort);

        Page<Imovel> pageEntities = imovelRepository.findAll(pageRequest);

        List<ImovelDTO> dtos = pageEntities
                .stream()
                .map(ImovelDTO::new)
                .collect(Collectors.toList());

        PagedResult<ImovelDTO> pagedResult = new PagedResult<>();
        pagedResult.setItems(dtos);
        pagedResult.setTotalCount(pageEntities.getTotalElements());
        pagedResult.setPageIndex(page);
        pagedResult.setLinesPerPage(linesPerPage);

        return Result.success(pagedResult);
    }

    @Transactional(readOnly = true)
    public Result<ImovelDTO> getById(Long id) {
        Optional<Imovel> imovelOpt = imovelRepository.findById(id);
        if (imovelOpt.isEmpty()) {
            throw new DatabaseException("Imóvel não encontrado.");
        }

        ImovelDTO dto = new ImovelDTO(imovelOpt.get());

        return Result.success(dto);
    }

    @Transactional
    public Result<ImovelDTO> add(ImovelDTO dto) {
        Imovel imovel = new Imovel();
        imovel.setBloco(dto.getBloco());
        imovel.setApartamento(dto.getApartamento());
        imovel.setBoxGaragem(dto.getBoxGaragem());

        imovel = imovelRepository.save(imovel);

        dto.setId(imovel.getId());

        return Result.success(dto, "Imóvel criado com sucesso.");
    }

    @Transactional
    public Result<Void> update(Long id, ImovelDTO dto) {
        Optional<Imovel> imovelOpt = imovelRepository.findById(id);
        if (imovelOpt.isEmpty()) {
            throw new DatabaseException("Imóvel não encontrado.");
        }

        Imovel imovel = imovelOpt.get();
        imovel.setBloco(dto.getBloco());
        imovel.setApartamento(dto.getApartamento());
        imovel.setBoxGaragem(dto.getBoxGaragem());

        imovelRepository.save(imovel);

        return Result.success("Imóvel atualizado com sucesso.");
    }

    @Transactional
    public void delete(Long id) {
        boolean possuiMorador = moradorService.existsByMoradorId(id);
        if (possuiMorador) {
            throw new DatabaseException("Não é possível excluir o imóvel, pois existem moradores vinculados.");
        }

        Optional<Imovel> imovelOpt = imovelRepository.findById(id);
        if (imovelOpt.isEmpty()) {
            throw new DatabaseException("Imóvel não encontrado.");
        }

        try {
            imovelRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao deletar imóvel: possível vínculo com outros registros.");
        }
    }
}
