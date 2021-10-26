/*
 * Created on 07.May.2007
 */

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Fatih Keles
 */

@SuppressWarnings("serial")
public class SoruPanel extends JPanel
{
	String duzen = "", sorubasligi="", cevapbasligi="";
	boolean kayitli = true;
	Color tablo = Color.DARK_GRAY,
	tabloIc = Color.WHITE,
	cerceve = Color.BLACK,
	disipucu = Color.WHITE,
	disarka = Color.WHITE,
	cevap = Color.BLACK,
	arkaPlan = Color.WHITE,
	icipucu = Color.BLACK,
	hints = Color.BLACK;
	int cicekYuzde = 80;
	int hucreBoyu = 0; //rakamlarin geldigi hucrelerin en ve boyu
	Font font = null;
	boolean print = false; //yazdiriliyor mu
	int printWidth = 0, printHeight = 0; //kagit boyutlari
	int printX = 0, printY = 0; //kagitin sol ve ust bosluklari
	Cicekler cicekler;
	
	public Board soru;
	public SoruPanel(Cicekler cicekler)
	{
		this.cicekler = cicekler;
		setBackground(arkaPlan);
		try {
			BufferedReader br = new BufferedReader(new FileReader("vs.dat"));
			duzen = br.readLine();
			duzenAl(duzen);
			br.close();
		} catch(IOException e) {}
	}
	
	public void paintComponent(Graphics g)
	{
		if(cicekler.controlPanel.busy) return;
		try {
			if(!print)
				super.paintComponent(g);
			g.setFont(getFont());
			int parentWidth = getParent().getWidth();
			int parentHeight = getParent().getHeight()-105;
			if(print) {
				parentWidth = printWidth;
				parentHeight = printHeight;
			} else {
				printX = 0;
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
			}
			
			if(sorubasligi.length()>0||cevapbasligi.length()>0)
				hucreBoyu = (int)(Math.min((parentHeight-40)/(soru.size+1.0),
					(parentWidth-40)/(soru.size*2.0+3)));
			else
				hucreBoyu = (int)(Math.min((parentHeight-40)/(soru.size+1.0),
					(parentWidth-40)/(soru.size*2.0+3)));
			
			int width = (int)(Math.min((parentHeight-20),
					(parentWidth-20)*(soru.size+1)/(soru.size*2+3)));
			int height = width;
			hucreBoyu = width/(soru.size+1);
			int x = (parentWidth - 2*width - hucreBoyu)/2;
			int y = (parentHeight - height)/2;
			if(!print)
			{
				if(sorubasligi.length()>0||cevapbasligi.length()>0)
				{
					printY=20;
					setBounds(x,y,width*2+hucreBoyu+2,height+22);
				}
				else
				{
					printY=0;
					setBounds(x,y,width*2+hucreBoyu+2,height+2);
				}
			}
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial",Font.BOLD,15));
			g.drawString(sorubasligi, 0, 15);
			g.drawString(cevapbasligi, width+hucreBoyu, 15);
			g.setFont(font);
			
			g.setColor(tabloIc);
			g.fillRect(printX+hucreBoyu,printY+hucreBoyu,hucreBoyu*soru.size,hucreBoyu*soru.size);
			g.fillRect(width+printX+2*hucreBoyu,printY+hucreBoyu,hucreBoyu*soru.size,hucreBoyu*soru.size);
			
			g.setColor(disarka);
			g.fillRect(printX,printY+hucreBoyu,hucreBoyu,hucreBoyu*soru.size);
			g.fillRect(printX+hucreBoyu,printY,hucreBoyu*soru.size,hucreBoyu);
			g.fillRect(width+printX+hucreBoyu,printY+hucreBoyu,hucreBoyu,hucreBoyu*soru.size);
			g.fillRect(width+printX+2*hucreBoyu,printY,hucreBoyu*soru.size,hucreBoyu);
			
			//g.setFont(new Font(font.getFontName(),Font.PLAIN,font.getSize()));
			g.setFont(new Font(font.getFontName(),Font.PLAIN,hucreBoyu - 15));
			for(int r = 0; r < soru.size; r++)
			{
				for(int c = 0; c < soru.size; c++)
				{
					g.setColor(icipucu);
					g.drawString(""+soru.getGameBoard().get(r).get(c),
							printX+(int)((c+1)*hucreBoyu)+width+hucreBoyu
							+(hucreBoyu-g.getFontMetrics().charWidth((""+soru.getGameBoard().get(r).get(c)).charAt(0)))/2,
							printY+(int)((r+2)*hucreBoyu)-(hucreBoyu-(getFontMetrics(g.getFont()).getAscent()
									-getFontMetrics(g.getFont()).getDescent()))/2);
					g.setColor(tablo);
					g.drawRect(printX+(c+1)*hucreBoyu,printY+(r+1)*hucreBoyu,hucreBoyu,hucreBoyu);
					g.drawRect(printX+width+(c+2)*hucreBoyu,printY+(r+1)*hucreBoyu,hucreBoyu,hucreBoyu);
				}
				
			}
			Graphics2D gr = (Graphics2D) g;
			gr.setStroke(new BasicStroke(3));
			int k = soru.size;
			for (int i = 0; i < k-1; i++) {
				for (int j = 0; j < k; j++) {
					if(soru.blok.get(j).get(i) != soru.blok.get(j).get(i+1)){	
						gr.drawLine(printX+(i+2)*hucreBoyu,
								printY+(j+1)*hucreBoyu,
								printX+(i+2)*hucreBoyu,
								printY+(j+2)*hucreBoyu);
						gr.drawLine(printX+(i+3)*hucreBoyu+width,
								printY+(j+1)*hucreBoyu,
								printX+(i+3)*hucreBoyu+width,
								printY+(j+2)*hucreBoyu);
					}		
				}
			}
			for (int m = 0; m < k-1; m++) {
				for (int n = 0; n < k; n++) {
					if(soru.blok.get(m).get(n) != soru.blok.get(m+1).get(n)) {
						gr.drawLine(printX+(n+1)*hucreBoyu,
								printY+(m+2)*hucreBoyu,
								printX+(n+2)*hucreBoyu,
								printY+(m+2)*hucreBoyu);
						gr.drawLine(printX+(n+2)*hucreBoyu+width,
								printY+(m+2)*hucreBoyu,
								printX+(n+3)*hucreBoyu+width,
								printY+(m+2)*hucreBoyu);
					}
				}
			}
			g.setColor(this.hints);
			g.setFont(new Font(font.getName(),Font.BOLD,hucreBoyu/4));
			String temp = new String();
			for (int i = 0; i < this.soru.position.size();i++) {
				if(this.soru.position.get(i).c == '*') {
					temp = this.soru.position.get(i).result.toString()+'x';
				}
				else {temp = this.soru.position.get(i).result.toString()+this.soru.position.get(i).c;}
				g.drawString(temp,printX+(this.soru.position.get(i).Y()+1)*hucreBoyu+3,printY+(this.soru.position.get(i).X()+1)*hucreBoyu+hucreBoyu/4);
				g.drawString(temp,printX+(this.soru.position.get(i).Y()+2)*hucreBoyu+3+width,printY+(this.soru.position.get(i).X()+1)*hucreBoyu+hucreBoyu/4);

			}
			g.setColor(cerceve);
			g.drawRect(printX+hucreBoyu,printY+hucreBoyu,hucreBoyu*soru.size,hucreBoyu*soru.size);
			g.drawRect(printX+hucreBoyu-1,printY+hucreBoyu-1,hucreBoyu*soru.size+2,hucreBoyu*soru.size+2);
			g.drawRect(printX+width+2*hucreBoyu,printY+hucreBoyu,hucreBoyu*soru.size,hucreBoyu*soru.size);
			g.drawRect(printX+width+2*hucreBoyu-1,printY+hucreBoyu-1,hucreBoyu*soru.size+2,hucreBoyu*soru.size+2);
			
		}catch(NullPointerException NPE){}		
	}

	
	void duzenAl(String dosya) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("ayarlar/"+dosya));
			arkaPlan = new Color(Integer.parseInt(br.readLine()));
			setBackground(arkaPlan);
			cicekler.getContentPane().setBackground(arkaPlan);
			cerceve = new Color(Integer.parseInt(br.readLine()));
			tabloIc = new Color(Integer.parseInt(br.readLine()));
			tablo = new Color(Integer.parseInt(br.readLine()));
			cevap = new Color(Integer.parseInt(br.readLine()));
			disipucu = new Color(Integer.parseInt(br.readLine()));
			disarka = new Color(Integer.parseInt(br.readLine()));
			icipucu = new Color(Integer.parseInt(br.readLine()));
			font = new Font(br.readLine(),Integer.parseInt(br.readLine()),Integer.parseInt(br.readLine()));
			sorubasligi = br.readLine();
			cevapbasligi = br.readLine();
			cicekler.setBounds(Integer.parseInt(br.readLine()),
					Integer.parseInt(br.readLine()),
					Integer.parseInt(br.readLine()),
					Integer.parseInt(br.readLine()));
			int tsize = Integer.parseInt(br.readLine());
			int tlights = Integer.parseInt(br.readLine());
			cicekler.controlPanel.boyut.setSelectedIndex(tsize-5);
			br.close();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(cicekler,"Renk d\u00fczeni al\u0131namad\u0131","Hata",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			kayitli = false;
		}
		setBackground(arkaPlan);
		repaint();
	}
}