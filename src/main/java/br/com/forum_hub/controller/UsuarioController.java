package br.com.forum_hub.controller;

import br.com.forum_hub.domain.usuario.DadosCadastroUsuario;
import br.com.forum_hub.domain.usuario.DadosListagemUsuario;
import br.com.forum_hub.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UsuarioController {

  private final UsuarioService service;

  @Autowired
  public UsuarioController(UsuarioService service) {this.service = service;}

  @PostMapping("/registrar")
  public ResponseEntity<DadosListagemUsuario> cadastrar(
      @RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
    var usuario = service.cadastrar(dados);
    var uri = uriBuilder.path("/{nomeUsuario}").buildAndExpand(usuario.getNomeUsuario()).toUri();
    return ResponseEntity.created(uri).body(new DadosListagemUsuario(usuario));
  }

  @GetMapping("/verificar-conta")
  public ResponseEntity<String> verificarEmail(@RequestParam String codigo) {
    service.verificarEmail(codigo);
    return ResponseEntity.ok("Conta verificada com sucesso!");
  }

}
