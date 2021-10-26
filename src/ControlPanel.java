/*
 * Created on 10.Nis.2007
 */

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * @author Fatih Keles
 */

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener
{
	JButton yenile, yazdir, kopyala, cik, ayarlar;
	JTextArea aciklamalar;
	JCheckBox _sum,_mult,_sub;
	JComboBox boyut, copylist;
	private String[] boyutlar = {"4", "5", "6", "7", "8"};
	Cicekler cicekler;
	JPanel buttonPanel;
	private int n, f;
	boolean busy = false;
	private Thread thread = null;
	private String[] kopyalamaSecenekleri = {"Yan Yana", "Alt Alta", "Soru", "Cevap"};
	private boolean[] operator = {false,false,false};
	public ControlPanel(Cicekler cicekler)
	{
		this.cicekler = cicekler;
		_mult = new JCheckBox("\u00c7arpma",true);
		_mult.addActionListener(this);
		_sub = new JCheckBox("\u00c7\u0131karma",true);
		_sub.addActionListener(this);
		_sum = new JCheckBox("Toplama",true);
		_sum.addActionListener(this);
		yenile = new JButton("Yeni Soru");
		yenile.addActionListener(this);
		
		yazdir = new JButton("Yazd\u0131r");
		yazdir.addActionListener(this);
		
		kopyala = new JButton("Kopyala");
		kopyala.addActionListener(this);
		
		ayarlar = new JButton("Ayarlar");
		ayarlar.addActionListener(this);
		
		cik = new JButton("\u00c7\u0131k");
		cik.addActionListener(this);
		
		boyut = new JComboBox(boyutlar);
		boyut.addActionListener(this);
		boyut.setSelectedIndex(3);
		copylist = new JComboBox(kopyalamaSecenekleri);
		
		aciklamalar = new JTextArea();
		setLayout(new BorderLayout());
		aciklamalar.setVisible(true);
		aciklamalar.setEditable(true);
		aciklamalar.setBackground(java.awt.Color.WHITE);
		aciklamalar.setLineWrap(true);
		aciklamalar.setWrapStyleWord(true);
		aciklamalar.setFont(new Font("Verdana",Font.PLAIN,12));
		aciklamalar.setPreferredSize(new java.awt.Dimension(200,60));
		aciklamalar.setText("1\"den 5\"e kadar olan say\u0131lar\u0131 tabloya \u00f6yle yerle\u015ftiriniz ki,Her s\u0131rada ve kolonda her say\u0131 tam olarak 1 kere kullan\u0131lm\u0131\u015f olsun.Koyu \u00e7izgilerle belirtilen bloklardaki say\u0131lara sol \u00fcst k\u00f6\u015fede verilen aritmetik i\u015flem uyguland\u0131\u011f\u0131nda sonu\u00e7 sol \u00fcst k\u00f6\u015fede verilen say\u0131 olsun.");
		add(aciklamalar,BorderLayout.CENTER);
		buttonPanel = new JPanel();
		buttonPanel.add(new JLabel("Boyut:"));
		buttonPanel.add(boyut);
		buttonPanel.add(yenile);
		buttonPanel.add(ayarlar);
		buttonPanel.add(copylist);
		buttonPanel.add(kopyala);
		buttonPanel.add(yazdir);
		buttonPanel.add(cik);
		buttonPanel.add(_mult);
		buttonPanel.add(_sub);
		buttonPanel.add(_sum);
		add(buttonPanel,BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		if(source==boyut)
		{
			int i = boyut.getSelectedIndex();
			int count = i + 4;
			if(aciklamalar != null) {aciklamalar.setText("1\'den "+count+"\'e kadar olan say\u0131lar\u0131 tabloya \u00f6yle yerle\u015ftiriniz ki,Her s\u0131rada ve kolonda her say\u0131 tam olarak 1 kere kullan\u0131lm\u0131\u015f olsun.Koyu \u00e7izgilerle belirtilen bloklardaki say\u0131lara sol \u00fcst k\u00f6\u015fede verilen aritmetik i\u015flem uyguland\u0131\u011f\u0131nda sonu\u00e7 sol \u00fcst k\u00f6\u015fede verilen say\u0131 olsun.");}

		}
		else if(source==yenile)
		{
			if(busy)
			{
				busy = false;
				thread.stop();
				yenile.setText("Yeni Soru");
				cicekler.soruPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				return;
			}
			
			n = Integer.parseInt(boyutlar[boyut.getSelectedIndex()]);

			thread = new Thread(){
				public void run()
				{
					busy = true;
					yenile.setText("\u0130ptal");
					cicekler.soruPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					cicekler.soruPanel.soru = new Board(n,operator);
					yenile.setText("Yeni Soru");
					cicekler.soruPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					busy = false;
					cicekler.soruPanel.repaint();
				}
			};
			
			thread.start();
		}
		else if(source==cik)
		{
			System.exit(0);
		}
		else if(source==ayarlar)
		{
			new Ayarlar(cicekler);
		}
		else if(source==yazdir)
		{
			PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob(cicekler,"", new java.util.Properties());
			if(pj==null) return;
			Graphics g = pj.getGraphics();
			g.setFont(new Font("Times New Roman",1,18));
			int x = 0, y = 40;
			g.drawString("\u00e7i\u00e7ekler", x+30, y);
			y += 25;
			g.setFont(new Font("Times New Roman",0,12));

			int width = pj.getPageDimension().width - 60;
			
			java.util.StringTokenizer st = new java.util.StringTokenizer(aciklamalar.getText());
			java.awt.FontMetrics fm = g.getFontMetrics();
			while (st.hasMoreTokens())
			{
				String temp = st.nextToken();
				if ((x+fm.stringWidth(temp)+30)>(30+width))
				{
					x = 0;
					y += 15;
				}
				g.drawString(temp,x+30,y);
				x += (fm.stringWidth(temp+" "));
			}
			
			cicekler.soruPanel.printWidth = pj.getPageDimension().width - 50;
			cicekler.soruPanel.printHeight = pj.getPageDimension().height - 50;
			cicekler.soruPanel.printX = 25;
			cicekler.soruPanel.printY = y + 30;
			cicekler.soruPanel.print = true;
			cicekler.soruPanel.paintComponent(g);
			cicekler.soruPanel.print = false;
			cicekler.soruPanel.printX = 0;
			cicekler.soruPanel.printY = 0;
			cicekler.soruPanel.printWidth = 0;
			cicekler.soruPanel.printHeight = 0;
			pj.end();
		}
		else if(source==kopyala)
		{
			kopyala();
		}
		if(_mult.isSelected()) {
			this.operator[0] = true;
		}
		else {
			this.operator[0] = false;
		}
		if(_sub.isSelected()) {
			this.operator[1] = true;
		}
		else {
			this.operator[1] = false;
		}
		if(_sum.isSelected()) {
			this.operator[2] = true;
		}
		else {
			this.operator[2] = false;
		}
	}
	
	public void kopyala()
	{
		int copymode = copylist.getSelectedIndex();
		int cellSize = cicekler.soruPanel.hucreBoyu;
		int w = cicekler.soruPanel.getWidth(), h = cicekler.soruPanel.getHeight();
		BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		Graphics2D graph = image.createGraphics();
		image = image.getSubimage(0, 0, w-cellSize, h-cellSize);
		graph.translate(-cellSize+1, -cellSize+1);
		cicekler.soruPanel.paintComponent(graph);
		if (copymode==1) {
			BufferedImage newimage = new BufferedImage(image.getHeight(),image.getWidth()-cellSize,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = newimage.createGraphics();
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, newimage.getWidth(), newimage.getHeight());
			graphics.translate(-cellSize+1, -cellSize+1);
			cicekler.soruPanel.paintComponent(graphics);
			//graphics.translate(cellSize-1, cellSize-1);
			graphics.translate(-1*cellSize-image.getWidth()+cicekler.soruPanel.hucreBoyu*(cicekler.soruPanel.soru.size+1)+6, -1*cellSize + h+cicekler.soruPanel.hucreBoyu);
			
			cicekler.soruPanel.paintComponent(graphics);
			image = newimage;
		}
		else if(copymode==2) image = image.getSubimage(0, 0, cicekler.soruPanel.hucreBoyu*(cicekler.soruPanel.soru.size)+4, cicekler.soruPanel.hucreBoyu*(cicekler.soruPanel.soru.size)+4);
		else if(copymode==3) image = image.getSubimage(image.getWidth()-cellSize*cicekler.soruPanel.soru.size-5, 0, cicekler.soruPanel.hucreBoyu*(cicekler.soruPanel.soru.size)+2, cicekler.soruPanel.hucreBoyu*(cicekler.soruPanel.soru.size)+2);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
				new ImageSelection(image), null);
	}

}
