package gui.abas;

import bean.Produto;
import dao.ProdutoDAO;
import gui.Cadastrar_cliente;
import gui.Cadastrar_produto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Painel_produto extends JPanel {

    private final String username;
    private final String password;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_de_botoes;
    private JButton cadastrar_produtos;
    private JButton realizar_consulta;
    private JButton editar_dados;

    private JPanel painel_de_dados;

    public Painel_produto(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;

        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());
        inicializa_painel_da_tabela();
        inicializa_painel_de_botoes();
        inicializa_painel_de_dados();
        setVisible(true);
    }

    private void inicializa_painel_de_dados() {
        painel_de_dados = new JPanel(new FlowLayout());

        painel_de_dados.setBorder(BorderFactory.createLineBorder(Color.black));
        painel_de_dados.setPreferredSize(new Dimension(600, 1000));

        add(painel_de_dados, BorderLayout.LINE_START);
    }

    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        cadastrar_produtos = new JButton("Cadastrar Novo Produto", new ImageIcon(getClass().getResource("ico_mais.png")));
        realizar_consulta = new JButton("Consultar Produtos", new ImageIcon(getClass().getResource("ico_lupa.png")));
        editar_dados = new JButton("Editar Dados do Produto", new ImageIcon(getClass().getResource("ico_editar.png")));
        painel_de_botoes.add(cadastrar_produtos, BorderLayout.LINE_START);
        painel_de_botoes.add(realizar_consulta, BorderLayout.CENTER);
        painel_de_botoes.add(editar_dados, BorderLayout.LINE_END);

        add(painel_de_botoes, BorderLayout.PAGE_START);

        realizar_consulta.addActionListener((ActionEvent) -> {

            atualizar_tabela();
        });
        cadastrar_produtos.addActionListener((ActionEvent) -> {
            new Cadastrar_produto(this.username, this.password);
        });
    }

    private void inicializa_painel_da_tabela() {
        painel_da_tabela = new JPanel(new GridLayout());
        modelo_tabela = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return ImageIcon.class;
                    default:
                        return Object.class;
                }
            }
        };
        tabela = new JTable(modelo_tabela);
        tabela.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ((ke.getKeyCode() == KeyEvent.VK_UP) || (ke.getKeyCode() == KeyEvent.VK_DOWN)) {
                    atualizar_caixas_de_texto(0);
                }
            }
        });
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                atualizar_caixas_de_texto(1);
            }
        });

        modelo_tabela.addColumn("Foto");
        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome");
        modelo_tabela.addColumn("Preco uni. C");
        modelo_tabela.addColumn("Preco uni. V");
        modelo_tabela.addColumn("Categoria");

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);

        add(painel_da_tabela, BorderLayout.CENTER);
    }

    private void atualizar_caixas_de_texto(int dif) {

    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Produto> dados_produtos = ProdutoDAO.read(username, password);

        for (int i = 0; i < dados_produtos.size(); i++) {
            Produto p = dados_produtos.get(i);
            modelo_tabela.addRow(
                    new Object[]{p.getProduto_foto_para_tabela(),
                        p.getProduto_cod(),
                        p.getProduto_nome(),
                        p.getPreco_uni_compra(),
                        p.getPreco_uni_venda(),
                        p.getCategoria()}
            );
        }
        tabela.setRowHeight(100);

        tabela.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela.getColumnModel().getColumn(0).setMinWidth(100);

        tabela.getColumnModel().getColumn(1).setMaxWidth(70);
        tabela.getColumnModel().getColumn(1).setMinWidth(70);

        tabela.getColumnModel().getColumn(3).setMaxWidth(150);
        tabela.getColumnModel().getColumn(3).setMinWidth(150);

        tabela.getColumnModel().getColumn(4).setMaxWidth(150);
        tabela.getColumnModel().getColumn(4).setMinWidth(150);

        tabela.getColumnModel().getColumn(5).setMaxWidth(180);
        tabela.getColumnModel().getColumn(5).setMinWidth(180);
    }
}
