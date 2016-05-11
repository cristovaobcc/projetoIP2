package almoxarifado.usuario.beans;

import almoxarifado.solicitacao.beans.SolicitacaoUsuarioCliente;

public class UsuarioGestor extends UsuarioAbstract{
	
	private String lotacao;
	
	// Todo UsuarioGestor deve ser lotado no almoxarifado
	public UsuarioGestor (String id, String nome, String senha)
	{
		super (id, nome, senha);
		this.setLotacao("Almoxarifado");
	}
	
	public void setLotacao(String lotacao){
		if (lotacao == null || lotacao.equals(""))
			imprimeMensagemDeErro("N�o pode passar 'lotacao' nula ou vazia!");
		else
			this.lotacao = lotacao;	
	}
	
	public String getLotacao()
	{
		return this.lotacao;
	}
	
	public String toString()
	{
		return "[Id =" + super.getId() + "] [nome =" + super.getNome() +
				"] [lota��o =" + this.getLotacao() + "]";
	}
	
	public void AlterarSolicitacao(SolicitacaoUsuarioCliente solicitacao)
	{
		//TODO: implementar 
	}
	
	
}
