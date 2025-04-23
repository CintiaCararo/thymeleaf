package campo.thymeleaf.domain.produto;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto carregarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void salvar(Produto produto) {
        produtoRepository.save(produto);
    }

}
