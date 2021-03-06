package almoxarifado.sistema.material.repositorio;
import java.util.ArrayList;
import almoxarifado.sistema.material.beans.Material;
/**
 * Classe que trabalha com materiais organizados em 
 * um array. Implementa interface IRepositorioMaterais.
 * @author cristovao
 *
 */
public class RepositorioMateriaisArray implements IRepositorioMateriais{
	
	private static RepositorioMateriaisArray instance;
	private ArrayList<Material> materiais;

	
	private RepositorioMateriaisArray()
	{
		this.materiais = new ArrayList<Material>();
	}
	
	public static RepositorioMateriaisArray getInstance()
	{
		if (instance == null)
			instance = new RepositorioMateriaisArray();
		return instance;
	}
	
	/**
	 * Devolve a qtde de materais inseridos no repositorio.
	 * @return
	 */
	public int getQtde()
	{
		return this.materiais.size();
	}
	
	/**
	 * Devolve um Material da i-esima posicao do array.
	 * @param i
	 * @return
	 */
	public Material getMaterial(int i)
	{
		return this.materiais.get(i);
	}
	
	@Override
	public void inserirMaterial(Material m)
	{
		this.materiais.add(m);
	}
	
	@Override
	public Material buscarMaterial(Material m)
	{
		Material mat = null;
		for (Material b : this.materiais)
			if (b.getCodigo ().equals (m.getCodigo() ) )
				mat = b;
		return mat;
	}
	
	@Override
	public ArrayList<Material> buscarMateriais(Material m)
	{
		ArrayList<Material> busca = new ArrayList<Material>();
		if (m.getCodigo() != null){
			for(Material mat: this.materiais){
				if (mat.getCodigo().equals(m.getCodigo() ) ){
					busca.add(mat); break;
				}
			}	
		} 
		if (m.getNomeBasico() != null){
			for(Material mat: this.materiais){
				if(mat.getNomeBasico().contains(m.getNomeBasico() ) ){
					busca.add(mat);
				}
			}
		}
		if (m.getEsp() != null && m.getEsp().getNomeModificador() != null){
			for(Material mat: this.materiais){
				if(m.getEsp().getNomeModificador().
						contains(m.getEsp().getNomeModificador())){
					busca.add(mat);
				}
			}
		}
		return busca;
	}
	
	@Override
	public void removerMaterial(Material m)
	{
		this.materiais.remove(this.buscarMaterial(m));
	}
	
	@Override
	public void alterarMaterial(Material m)
	{
		if (this.indiceDeMaterial(m) < materiais.size())
			materiais.set(this.indiceDeMaterial(m),m);
	}
	
	@Override
	public void listar()
	{
		for(Material m: this.materiais)
			System.out.println(m.toString());
	}
	
	@Override
	public ArrayList<Material> listarParaArrayList(){
		return this.materiais;
	}
	
	/**
	 * Devolve o �ndice do material m no campo materiais
	 * com base no c�digo do mesmo material.
	 * Caso o material n�o seja localizado devolve
	 * o tamanho do campo materiais.
	 * @param m Material
	 * @return int
	 */
	//TODO: inserir algoritmo de busca no lugar do for aqui.
	private int indiceDeMaterial(Material m) {
		int i;
		for ( i = 0; i < this.materiais.size(); i++){
			if (this.materiais.get(i).getCodigo().equals(m.getCodigo())){
				break;
			}
		}
		return i;
	}
	
}

