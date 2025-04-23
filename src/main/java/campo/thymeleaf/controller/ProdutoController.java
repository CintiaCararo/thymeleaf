package campo.thymeleaf.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import campo.thymeleaf.domain.produto.Produto;
import campo.thymeleaf.domain.produto.ProdutoService;
import campo.thymeleaf.domain.produto.TipoProduto;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller // define que essa classe vai receber e tratar requisições HTTP
@RequestMapping("produtos") // define que todas as rotas aqui dentro começam com /produtos
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService; // injeção de dependência via construtor
    }

    // get na rota /produtos
    @GetMapping
    public String carregarPaginaListagem(Model model) {
        var produtos = produtoService.listar();
        model.addAttribute("produtos", produtos);
        return "produtos/listagem-produtos";
    }

    // get na rota /produtos/formulario
    @GetMapping("formulario")
    public String carregarPaginaCadastro(Long id, Model model) {
        if (id != null) {
            model.addAttribute("dados", produtoService.carregarPorId(id));
        } else {
            model.addAttribute("dados", new Produto(null, "", "", 0, null, BigDecimal.ZERO));
        }

        return "produtos/formulario-produto";
    }

    @PostMapping
    public String cadastrar(@ModelAttribute("dados") Produto dados, BindingResult result, Model model) {

        try {
            produtoService.salvar(dados);
            return "redirect:/produtos?sucesso";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("dados", dados);
            return "produtos/formulario-produto";
        }
    }

    @ModelAttribute("tipo")
    public TipoProduto[] tipos() {
        return TipoProduto.values();
    }

}
