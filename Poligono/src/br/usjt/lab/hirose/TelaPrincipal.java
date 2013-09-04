package br.usjt.lab.hirose;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * @author thiago.cardoso
 */

public class TelaPrincipal extends JFrame implements ActionListener {

	/**
	 * Trabalho Professor Hirose - Turma D1 CGAPL - 4ACPN
	 */

	private static final long serialVersionUID = 1L;
	private final static String VAZIO = "";

	Dimension sz = new Dimension();
	JTextField txt_PontoX1, txt_PontoX2, txt_PontoX3, txt_PontoX4, txt_PontoX5, txt_PontoY1, txt_PontoY2, txt_PontoY3, txt_PontoY4, txt_PontoY5,
			txt_PontoPivotX, txt_PontoPivotY, txt_Angulo, txt_Escala, txt_TranslacaoX, txt_TranslacaoY;
	JLabel lbl_Ponto1, lbl_Ponto2, lbl_Ponto3, lbl_Ponto4, lbl_Ponto5, lbl_PontoPivot, lbl_Angulo, lbl_PontoReferencia, lbl_Escala, lbl_Tranlacao;
	JButton bt_Desenhar, bt_Translacao, bt_Rotacao, bt_Escala, bt_Limpar;

	JRadioButton rd_Java, rd_DDA, rd_Declive;

	int radioSelect = 0;

	PtCartesiano ptCartesianoPadrao = new PtCartesiano(10, 10);

	private PtCartesiano p1 = new PtCartesiano(50.0, 50.0);
	private PtCartesiano p2 = new PtCartesiano(00.0, 50.0);
	private PtCartesiano p3 = new PtCartesiano(00.0, 00.0);
	private PtCartesiano p4 = new PtCartesiano(25.0, 25.0);
	private PtCartesiano p5 = new PtCartesiano(50.0, 0.0);

	private final PtCartesiano pRef = new PtCartesiano(0.0, 0.0);

	Container painel;

	final JLabel lbl_PosicaoMouse = new JLabel("Posição: ");

	public TelaPrincipal() {
		Container caixa = getContentPane();
		caixa.setLayout(new BorderLayout());

		constroiPainel();
		iniciaComponentes();
		adicionaComponentesPanel();

		caixa.add(painel, BorderLayout.SOUTH);
		this.getContentPane().add(lbl_PosicaoMouse, BorderLayout.NORTH);

		rd_DDA.addActionListener(this);
		rd_Declive.addActionListener(this);
		rd_Java.addActionListener(this);
		bt_Desenhar.addActionListener(this);
		bt_Translacao.addActionListener(this);
		bt_Rotacao.addActionListener(this);
		bt_Escala.addActionListener(this);
		bt_Limpar.addActionListener(this);
		eventoMouseMotionListener();

		setTitle("Desenhando com Hirose");
		sz.setSize(1000, 600);
		setSize(sz);
		setVisible(true);

	}

	private void eventoMouseMotionListener() {
		this.addMouseMotionListener(new MouseMotionAdapter() {

			private final Point2D p = new Point2D.Double();

			private final DecimalFormat df = new DecimalFormat("00.00");
			{
				this.df.setPositivePrefix("+");
			}

			@Override
			public void mouseMoved(MouseEvent evt) {
				calculaPosicaoPlanoCartesiano(p, evt.getX(), evt.getY());
				lbl_PosicaoMouse.setText("Posição: (" + df.format(p.getX()) + "; " + df.format(p.getY()) + ")");
			}
		});
	}

	private void adicionaComponentesPanel() {

		painel.add(rd_Java);
		painel.add(rd_DDA);
		painel.add(rd_Declive);

		painel.add(bt_Desenhar);
		painel.add(bt_Translacao);
		painel.add(bt_Escala);
		painel.add(bt_Rotacao);
		painel.add(bt_Limpar);

		painel.add(lbl_Tranlacao);
		painel.add(txt_TranslacaoX);
		painel.add(txt_TranslacaoY);

		painel.add(lbl_Ponto1);
		painel.add(txt_PontoX1);
		painel.add(txt_PontoY1);

		painel.add(lbl_Ponto2);
		painel.add(txt_PontoX2);
		painel.add(txt_PontoY2);

		painel.add(lbl_Ponto3);
		painel.add(txt_PontoX3);
		painel.add(txt_PontoY3);

		painel.add(lbl_Escala);
		painel.add(txt_Escala);

		painel.add(lbl_Ponto4);
		painel.add(txt_PontoX4);
		painel.add(txt_PontoY4);

		painel.add(lbl_Ponto5);
		painel.add(txt_PontoX5);
		painel.add(txt_PontoY5);

		painel.add(lbl_PontoReferencia);
		painel.add(txt_PontoPivotX);
		painel.add(txt_PontoPivotY);

		painel.add(lbl_Angulo);
		painel.add(txt_Angulo);

	}

	private void iniciaComponentes() {
		lbl_Ponto1 = new JLabel("Ponto 1 :", 4);
		lbl_Ponto2 = new JLabel("Ponto 2 :", 4);
		lbl_Ponto3 = new JLabel("Ponto 3 :", 4);
		lbl_Ponto4 = new JLabel("Ponto 4 :", 4);
		lbl_Ponto5 = new JLabel("Ponto 5 :", 4);
		lbl_PontoReferencia = new JLabel("Referencia :", 4);
		lbl_Angulo = new JLabel("Angulo :", 4);
		lbl_Escala = new JLabel("Escala : ", 4);
		lbl_Tranlacao = new JLabel("Translação : ", 4);

		rd_Java = new JRadioButton("Java");
		rd_DDA = new JRadioButton("DDA");
		rd_Declive = new JRadioButton("Declive");

		txt_PontoX1 = new JTextField(5);
		txt_PontoX2 = new JTextField(5);
		txt_PontoX3 = new JTextField(5);
		txt_PontoX4 = new JTextField(5);
		txt_PontoX5 = new JTextField(5);
		txt_PontoY1 = new JTextField(5);
		txt_PontoY2 = new JTextField(5);
		txt_PontoY3 = new JTextField(5);
		txt_PontoY4 = new JTextField(5);
		txt_PontoY5 = new JTextField(5);
		txt_Angulo = new JTextField(5);
		txt_PontoPivotX = new JTextField(5);
		txt_PontoPivotY = new JTextField(5);
		txt_Escala = new JTextField(5);
		txt_TranslacaoY = new JTextField(5);
		txt_TranslacaoX = new JTextField(5);

		bt_Desenhar = new JButton("Desenhar");
		bt_Translacao = new JButton("Translacao");
		bt_Escala = new JButton("Escala");
		bt_Rotacao = new JButton("Rotacao");
		bt_Limpar = new JButton("Limpar");
	}

	private void constroiPainel() {
		painel = new Container();

		GridLayout gridLayout = new GridLayout();
		gridLayout.setColumns(3);
		gridLayout.setRows(3);
		gridLayout.setHgap(-2);
		gridLayout.setVgap(-2);
		painel.setLayout(gridLayout);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {

		if (evento.getSource() == bt_Desenhar) {
			eventoDesenhar();
		} else if (evento.getSource() == bt_Translacao) {
			eventoTranslacao();
		} else if (evento.getSource() == bt_Escala) {
			eventoEscala();
		} else if (evento.getSource() == bt_Rotacao) {
			eventoRotacao();
		} else if (evento.getSource() == bt_Limpar) {
			eventoLimpar();
		} else if (evento.getSource() == rd_Java) {
			rd_Declive.setSelected(false);
			rd_DDA.setSelected(false);
			radioSelect = 0;
		} else if (evento.getSource() == rd_DDA) {
			rd_Declive.setSelected(false);
			rd_Java.setSelected(false);
			radioSelect = 1;
		} else if (evento.getSource() == rd_Declive) {
			rd_DDA.setSelected(false);
			rd_Java.setSelected(false);
			radioSelect = 2;
		}
	}

	private void eventoLimpar() {
		limparCampos();
		Graphics g = getGraphics();
		g.clearRect(0, 0, 1024, 768);
		painel.repaint();
	}

	private void eventoRotacao() {
		PtCartesiano piv;
		double angulo;
		if (txt_Angulo.getText().isEmpty())
			txt_Angulo.setText("30");

		else {
			angulo = Double.parseDouble(txt_Angulo.getText());

			if (txt_PontoPivotX.getText().isEmpty() || txt_PontoPivotY.getText().isEmpty()) {

				txt_PontoPivotX.setText("0");
				txt_PontoPivotY.setText("0");

				piv = ptCartesianoPadrao;

			} else {
				piv = new PtCartesiano(Integer.parseInt(txt_PontoPivotX.getText()), Integer.parseInt(txt_PontoPivotY.getText()));
			}

			movePontosParaPlanoCartesianoJava(piv);
			RotacionarFigura r = new RotacionarFigura();

			p1 = r.rotacionar(p1, angulo, piv);
			p2 = r.rotacionar(p2, angulo, piv);
			p3 = r.rotacionar(p3, angulo, piv);
			p4 = r.rotacionar(p4, angulo, piv);
			p5 = r.rotacionar(p5, angulo, piv);

			desenhar();
		}
	}

	private void eventoEscala() {
		double escala = 1.0;
		try {
			escala = Double.parseDouble(txt_Escala.getText());
		} catch (NumberFormatException e) {
			txt_Escala.setText("1");
		} finally {
			escala(escala);
			desenhar();
		}

		painel.repaint();
	}

	private void eventoTranslacao() {
		int refX;
		int refY;
		try {
			refX = Integer.parseInt(txt_TranslacaoX.getText());
			refY = Integer.parseInt(txt_TranslacaoY.getText());
		} catch (NumberFormatException e) {

			txt_TranslacaoX.setText("1");
			txt_TranslacaoY.setText("1");

			refX = 1;
			refY = 1;
		}

		pRef.x = refX;
		pRef.y = refY;

		efetuaTranslacao(refX, refY);

		desenhar();
	}

	private void eventoDesenhar() {
		if (txt_PontoX1.getText().isEmpty() || txt_PontoY1.getText().isEmpty() || txt_PontoX2.getText().isEmpty() || txt_PontoY2.getText().isEmpty()
				|| txt_PontoX3.getText().isEmpty() || txt_PontoY3.getText().isEmpty() || txt_PontoX4.getText().isEmpty()
				|| txt_PontoY4.getText().isEmpty() || txt_PontoX5.getText().isEmpty() || txt_PontoY5.getText().isEmpty()) {

			txt_PontoX1.setText("50");
			txt_PontoY1.setText("50");
			txt_PontoX2.setText("0");
			txt_PontoY2.setText("50");
			txt_PontoX3.setText("0");
			txt_PontoY3.setText("0");
			txt_PontoX4.setText("25");
			txt_PontoY4.setText("25");
			txt_PontoX5.setText("50");
			txt_PontoY5.setText("0");

		} else {

			p1.x = Double.parseDouble(txt_PontoX1.getText());
			p1.y = Double.parseDouble(txt_PontoY1.getText());

			p2.x = Double.parseDouble(txt_PontoX2.getText());
			p2.y = Double.parseDouble(txt_PontoY2.getText());

			p3.x = Double.parseDouble(txt_PontoX3.getText());
			p3.y = Double.parseDouble(txt_PontoY3.getText());

			p4.x = Double.parseDouble(txt_PontoX4.getText());
			p4.y = Double.parseDouble(txt_PontoY4.getText());

			p5.x = Double.parseDouble(txt_PontoX5.getText());
			p5.y = Double.parseDouble(txt_PontoY5.getText());
		}

		movePontosParaPlanoCartesianoJava(ptCartesianoPadrao);
		movePontosParaPlanoCartesianoJava(pRef);

		movimentaPontosParaPlanoCartesianoJava();

		desenhar();

		movePontosParaPlanoCartesiano(ptCartesianoPadrao);
		movePontosParaPlanoCartesiano(pRef);
	}

	private void efetuaTranslacao(int refX, int refY) {

		p1.transladar(refX, refY);
		p2.transladar(refX, refY);
		p3.transladar(refX, refY);
		p4.transladar(refX, refY);
		p5.transladar(refX, refY);

		desenhar();

	}

	private void desenhaCentroTela(Graphics g) {
		int largura = this.getWidth();
		int altura = this.getHeight();
		int metadeAltura = altura / 2;
		int metadeLargura = largura / 2;

		g.setColor(Color.RED);
		g.drawLine(metadeLargura, 0, metadeLargura, altura);
		g.drawLine(0, metadeAltura, largura, metadeAltura);
	}

	public void escala(double escala) {

		movimentaPontosParaPlanoCartesiano();

		p1.escalar(escala, pRef);
		p2.escalar(escala, pRef);
		p3.escalar(escala, pRef);
		p4.escalar(escala, pRef);
		p5.escalar(escala, pRef);

		desenhar();

		movimentaPontosParaPlanoCartesianoJava();
	}

	public void desenhar() {

		Graphics g = getGraphics();
		g.clearRect(0, 0, 1024, 768);

		desenhaCentroTela(g);

		switch (radioSelect) {
			case 1:
				retaDDA(g);
				break;
			case 2:
				retaDeclive(g);
				break;
			default:
				retaJava(g);
				break;
		}

		painel.repaint();
		
		 
	}

	
	
	private void retaDeclive(Graphics g) {
		g.setColor(Color.BLUE);

		p1.retaDeclive(p2, g);
		p2.retaDeclive(p3, g);
		p3.retaDeclive(p4, g);
		p4.retaDeclive(p5, g);
		p1.retaDeclive(p5, g);

	}

	private void retaDDA(Graphics g) {
		g.setColor(Color.BLUE);

		p1.retaDDA(p2, g);
		p2.retaDDA(p3, g);
		p4.retaDDA(p3, g);
		p4.retaDDA(p5, g);
		p1.retaDDA(p5, g);

	}

	private void retaJava(Graphics g) {
		g.setColor(Color.BLUE);

		p1.retaJava(p2, g);
		p2.retaJava(p3, g);
		p3.retaJava(p4, g);
		p4.retaJava(p5, g);
		p5.retaJava(p1, g);
		
	}

	private void limparCampos() {

		txt_PontoX1.setText(VAZIO);
		txt_PontoY1.setText(VAZIO);
		txt_PontoX2.setText(VAZIO);
		txt_PontoY2.setText(VAZIO);
		txt_PontoX3.setText(VAZIO);
		txt_PontoY3.setText(VAZIO);
		txt_PontoX4.setText(VAZIO);
		txt_PontoY4.setText(VAZIO);
		txt_PontoX5.setText(VAZIO);
		txt_PontoY5.setText(VAZIO);
		txt_PontoPivotX.setText(VAZIO);
		txt_PontoPivotY.setText(VAZIO);
		txt_Angulo.setText(VAZIO);
		txt_Escala.setText(VAZIO);
		txt_TranslacaoX.setText(VAZIO);
		txt_TranslacaoY.setText(VAZIO);
	}

	public void calculaPosicaoPlanoCartesiano(Point2D posicaoPlano, double xTela, double yTela) {

		double sx = calculaXPlanoCartesiano(xTela);
		double sy = calculaYPlanoCartesiano(yTela);
		posicaoPlano.setLocation(sx, sy);
	}

	private double calculaYPlanoCartesiano(double yTela) {
		return -(((yTela * 200) / this.getHeight()) - 100) * 2;
	}

	private double calculaXPlanoCartesiano(double xTela) {
		return (((xTela * 200) / this.getWidth()) - 100) * 2;
	}

	private double calculaXPlanoCartesianoJava(double xTela) {
		return (((xTela / 2) + 100) * this.getWidth() / 200);
	}

	private double calculaYPlanoCartesianoJava(double yTela) {

		return ((((-yTela / 2) + 100) * this.getHeight()) / 200);

	}

	private void movimentaPontosParaPlanoCartesiano() {
		movePontosParaPlanoCartesiano(p1);
		movePontosParaPlanoCartesiano(p2);
		movePontosParaPlanoCartesiano(p3);
		movePontosParaPlanoCartesiano(p4);
		movePontosParaPlanoCartesiano(p5);
	}

	private void movimentaPontosParaPlanoCartesianoJava() {
		movePontosParaPlanoCartesianoJava(p1);
		movePontosParaPlanoCartesianoJava(p2);
		movePontosParaPlanoCartesianoJava(p3);
		movePontosParaPlanoCartesianoJava(p4);
		movePontosParaPlanoCartesianoJava(p5);
	}

	private void movePontosParaPlanoCartesiano(PtCartesiano pt) {
		pt.x = calculaXPlanoCartesiano(pt.x);
		pt.y = calculaYPlanoCartesiano(pt.y);
	}

	private void movePontosParaPlanoCartesianoJava(PtCartesiano pt) {
		pt.x = calculaXPlanoCartesianoJava(pt.x);
		pt.y = calculaYPlanoCartesianoJava(pt.y);
	}

}
