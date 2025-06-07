package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.usuario.UsuarioAtivacaoDTO;
import com.tccfer.application.controller.dto.usuario.UsuarioCadastroDTO;
import com.tccfer.application.controller.dto.usuario.UsuarioMinDTO;
import com.tccfer.application.controller.dto.usuario.UsuarioSistemaDTO;
import com.tccfer.application.model.entity.pessoa.UsuarioSistema;
import com.tccfer.application.model.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService ;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody UsuarioCadastroDTO dto){
        try {
            usuarioService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSistemaDTO> buscaPorId(@PathVariable long id){
        UsuarioSistemaDTO usuarioSistemaDTO = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuarioSistemaDTO);
    }

    @GetMapping("/listar")
    public List<UsuarioMinDTO> listarUsuarios(
            @RequestParam(value = "tipoUsuario", required = false) String tipoUsuario,
            @RequestParam(value = "id",           required = false) Long   id,
            @RequestParam(value = "cpf",          required = false) String cpf,
            @RequestParam(value = "cnpj",         required = false) String cnpj,
            @RequestParam(value = "nome",         required = false) String nome
    ) {
        // 1) Se vier filtro de tipoUsuario
        if (tipoUsuario != null && !tipoUsuario.isBlank()) {
            if (id != null) {
                return usuarioService.listarPorTipoEId(tipoUsuario, id);
            } else if (cpf != null && !cpf.isBlank()) {
                return usuarioService.listarPorTipoECpf(tipoUsuario, cpf);
            } else if (cnpj != null && !cnpj.isBlank()) {
                return usuarioService.listarPorTipoECnpj(tipoUsuario, cnpj);
            } else if (nome != null && !nome.isBlank()) {
                return usuarioService.listarPorTipoENome(tipoUsuario, nome);
            } else {
                // só tipo
                return usuarioService.listarPorTipoUsuario(tipoUsuario);
            }
        }

        // 2) Se não vier tipoUsuario, usa os outros filtros isolados
        if (id != null) {
            return usuarioService.listarPorId(id);
        } else if (cpf != null && !cpf.isBlank()) {
            return usuarioService.listarPorCpf(cpf);
        } else if (cnpj != null && !cnpj.isBlank()) {
            return usuarioService.listarPorCnpj(cnpj);
        } else if (nome != null && !nome.isBlank()) {
            return usuarioService.listarPorNome(nome);
        } else {
            // sem filtro nenhum
            return usuarioService.listarUsuarios();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSistema> atualizarUsuario(@PathVariable long id, @RequestBody UsuarioCadastroDTO dto){
        usuarioService.atualizar(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/reativar")
    public ResponseEntity<?> reativar(@PathVariable Long id) {
        String token = usuarioService.reativar(id);
        if (token != null) {
            // devolve o token para o front ou p/ envio por e-mail
            Map<String,String> body = Collections.singletonMap("token", token);
            return ResponseEntity.ok(body);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @PostMapping("/ativar")
    public ResponseEntity<Void> ativarUsuario(@RequestBody @Valid UsuarioAtivacaoDTO dto) {
        usuarioService.ativarUsuario(dto.getToken(), dto.getNovaSenha());
        return ResponseEntity.noContent().build();
    }
}
