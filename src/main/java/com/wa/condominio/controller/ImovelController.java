package com.wa.condominio.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

import com.wa.condominio.dto.ImovelDTO;
import com.wa.condominio.services.ImovelService;
import com.wa.condominio.services.shared.PagedResult;
import com.wa.condominio.services.shared.Result;

@RestController
@RequestMapping("imovel")
@Validated
public class ImovelController {

    @Autowired
    private ImovelService imovelService;

    @GetMapping
    @Operation(summary = "Lista todos os imóveis.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<List<ImovelDTO>>> getAll() {
        return ResponseEntity.ok(imovelService.getAll());
    }

    @GetMapping("/paginado")
    @Operation(summary = "Lista todos os imóveis com paginação.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<PagedResult<ImovelDTO>>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int linesPerPage,
            @RequestParam(defaultValue = "bloco") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        return ResponseEntity.ok(imovelService.getAllPaged(page, linesPerPage, orderBy, direction));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um imóvel por ID.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<ImovelDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(imovelService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Adiciona um imóvel.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<ImovelDTO>> add(@RequestBody @Valid ImovelDTO dto) {
        Result<ImovelDTO> resultado = imovelService.add(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resultado.getDados().getId())
                .toUri();

        return ResponseEntity.created(uri).body(resultado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera um imóvel por ID.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<Void>> update(@PathVariable Long id, @RequestBody @Valid ImovelDTO dto) {
        return ResponseEntity.ok(imovelService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um imóvel por ID.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<?> excluirImovel(@PathVariable Long id) {
        imovelService.delete(id);
        return ResponseEntity.ok(Map.of("mensagem", "Imóvel deletado com sucesso!"));
    }
}