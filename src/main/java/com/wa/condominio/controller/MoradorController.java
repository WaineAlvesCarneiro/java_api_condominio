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

import com.wa.condominio.dto.MoradorDTO;
import com.wa.condominio.services.MoradorService;
import com.wa.condominio.services.shared.PagedResult;
import com.wa.condominio.services.shared.Result;

@Validated
@RestController
@RequestMapping("morador")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    @GetMapping
    @Operation(summary = "Lista todos os moradores.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<List<MoradorDTO>>> getAll() {
        return ResponseEntity.ok(moradorService.getAll());
    }

    @GetMapping("/paginado")
    @Operation(summary = "Lista todos os moradores com paginação.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<PagedResult<MoradorDTO>>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int linesPerPage,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        return ResponseEntity.ok(moradorService.getAllPaged(page, linesPerPage, orderBy, direction));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um morador por ID.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<MoradorDTO>> findById(@PathVariable long id) {
        return ResponseEntity.ok(moradorService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Adiciona um morador.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<MoradorDTO> add(@Valid @RequestBody MoradorDTO dto) {
        Result<MoradorDTO> resultado = moradorService.add(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resultado.getDados().getId())
                .toUri();

        return ResponseEntity.created(uri).body(resultado.getDados());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Altera um morador por ID.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<Result<Void>> update(@PathVariable long id, @Valid @RequestBody MoradorDTO dto) {
        return ResponseEntity.ok(moradorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um morador por ID.", security = @SecurityRequirement(name = "bearer-jwt"))
    public ResponseEntity<?> delete(@PathVariable long id) {
        moradorService.delete(id);
        return ResponseEntity.ok(Map.of("mensagem", "Morador deletado com sucesso!"));
    }
}