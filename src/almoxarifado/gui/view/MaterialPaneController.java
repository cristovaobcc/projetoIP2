package almoxarifado.gui.view;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

import almoxarifado.gui.MainApp;
import almoxarifado.sistema.fachada.FachadaUsuarioGestor;
import almoxarifado.sistema.material.beans.Material;
import javafx.scene.control.Button;


public class MaterialPaneController {

	private MainApp mainApp;

	private FachadaUsuarioGestor fachada = FachadaUsuarioGestor.getInstance();

	@FXML
	private TableView<Material> materialTable;
	@FXML
	private TableColumn<Material, String> codigoColumn;
	@FXML
	private TableColumn<Material, String> nomeBasicoColumn;

	@FXML
	private Label codigoLabel;
	@FXML
	private Label nomeBasicoLabel;
	@FXML
	private Label quantidadeLabel;
	@FXML
	private Label dataDeCadastroLabel;
	@FXML
	private Label idCadastradorLabel;
	@FXML 
	private Button AlterarMaterial;

	/**
	 * � chamado pela aplica��o principal para dar uma refer�ncia de volta a si mesmo.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp m) {
		this.mainApp = m;
	}

	/**
	 * Inicializa a classe controller. Este m�todo � chamado automaticamente
	 *  ap�s o arquivo fxml ter sido carregado.
	 */
	@FXML
	private void initialize() {
		// Inicializa a tabela de material com duas colunas.
		codigoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
		nomeBasicoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeBasico()));

		// limpa as informacoes na tela.
		mostrarDetalhesMaterial(null);

		// Listen for selection changes and show the person details when changed.
		materialTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> mostrarDetalhesMaterial(newValue));
	}

	private void mostrarDetalhesMaterial(Material material) {
		if (material != null) {

			// preenche os labels com os dados do material
			codigoLabel.setText(material.getCodigo());
			nomeBasicoLabel.setText(material.getNomeBasico());
			quantidadeLabel.setText(Integer.toString(material.getQtde() ) );
			dataDeCadastroLabel.setText(material.getDataDeAquisicao().toString());
			idCadastradorLabel.setText(material.getIdCadastrador());

		} else {

			// material nulo
			codigoLabel.setText("");
			nomeBasicoColumn.setText("");
			quantidadeLabel.setText("");
			dataDeCadastroLabel.setText("");
			idCadastradorLabel.setText("");
		}
	}

	public void setDados(ObservableList<Material> dadosMaterial) {
		materialTable.setItems(dadosMaterial);
	}	

	public void atualizarTabela() {
		final List<Material> items = materialTable.getItems();
		if( items == null || items.size() == 0) return;

		final Material item = materialTable.getItems().get(0);
		items.remove(0);
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				items.add(0, item);
			}
		});
	}

	@FXML 
	public void removerMaterial() {
		try {
			Material materialSelecionado = this.materialTable.getSelectionModel().getSelectedItem();
			
			if (materialSelecionado != null){
				// chama a fachada para remover o material
				this.fachada.removerMaterial(materialSelecionado);
				
				// remove from table
				this.materialTable.getItems().remove(materialTable.getSelectionModel().getSelectedIndex());
			} else {
				// nothing selected.
				Alert alert = new Alert (AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Sem sele��o");
				alert.setHeaderText("Nenhum material selecionado!");
				alert.setContentText("Por favor, selecione um material na tabela.");
				
				alert.showAndWait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML 
	public void editarMaterial() {
		Material materialSelecionado = this.materialTable.getSelectionModel().getSelectedItem();
		int indiceSelecionado = this.materialTable.getSelectionModel().getSelectedIndex();
		
		if (materialSelecionado != null) {
			boolean alterarClicado = this.mainApp.abrirMaterialEditDialog(materialSelecionado);
			if (alterarClicado){
				// chama fachada
				this.fachada.alterarMaterial(materialSelecionado);
				
				this.mostrarDetalhesMaterial(materialSelecionado);
				
				this.atualizarTabela();
			}
		} else {
			
			// nada foi selecionado
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(this.mainApp.getPrimaryStage());
			alert.setHeaderText("Nenhum material selecionado!");
			alert.setContentText("Por favor, selecione um material da tabela");
			
			alert.showAndWait();
		}
	}
	
	
}
