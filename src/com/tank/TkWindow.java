package com.tank;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;


//��Ϸ������
public class TkWindow extends JFrame{
	
	Tkpanel jp;             //�������
	
	public TkWindow(){		
		
		jp=new Tkpanel();   //�������
				
		this.add(jp);       //������
		
		this.addKeyListener(jp);  //Ϊ������ע������¼����󶨼�����Ϊ���
		
		Thread th=new Thread(jp); //�������ˢ���߳�
		th.start();
		
		//��������
		this.setTitle("̹�˴�սv2.0");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(500, 100);
		this.setSize(400,400);
		this.setVisible(true);
		this.setResizable(false);
		
	}

	public static void main(String[] args) {

		TkWindow Tk=new TkWindow();  //����������

	}

}

//��Ϸ���
class Tkpanel extends JPanel implements KeyListener,Runnable{
	
	MyTank mk=null;

	YourTank yk=null;
	int yksum=6;                                 //�����о�����
	Vector<YourTank> yl;

	Shot shot=null;
	
	Image im1=null;
	Image im2=null;
	Image im3=null;
	Vector<Boom> bsm=null;
		
	//��ʼ��
	Tkpanel(){
	
		//�ҷ�̹��
		mk=new MyTank(100,300);
		mk.setSpeed(5);                     
		mk.setFireAble(3);
		
		//�з�̹��
		yl=new Vector<YourTank>();
		for(int i=0;i<yksum;i++){
			YourTank yk=new YourTank((i+1)*50,10);
			yl.add(yk);
		}
		
        //��ըЧ��
		bsm=new Vector<Boom>();     
		try {
			im1=ImageIO.read(new File("bomb_1.gif"));
			im2=ImageIO.read(new File("bomb_2.gif"));
			im3=ImageIO.read(new File("bomb_3.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//������
	public void paint(Graphics p){		
		super.paint(p);
		p.fillRect(0, 0, 400, 400);//����

		//�����ҷ�̹��
		if (mk.boom==false){
			if(mk.boom==false){
				this.drawShot(mk, p);
		        this.drawTank(mk.getX(),mk.getY(),mk.getType(), mk.getFx(), p);		
			}else{
				mk=null;
			}
			
		}
		
		//���Ƶз�̹��
		for(int i=0;i<yl.size();i++){
			if (yl.get(i).boom==false){
				this.drawShot(yl.get(i), p);
				this.drawTank(yl.get(i).getX(),yl.get(i).getY(),yl.get(i).getType(), yl.get(i).getFx(), p);
			}else{
				yl.remove(i);
			}
		}
		     	
		//���Ʊ�ըЧ��
		for(int k=0;k<bsm.size();k++){
			
			Boom bm=bsm.get(k);					

				if (bm.life>6){
				p.drawImage(im1, bm.x, bm.y,30,30,this);
			    }else if(bm.life>3){
				p.drawImage(im2, bm.x, bm.y,30,30,this);
			    }else{
				p.drawImage(im3, bm.x, bm.y,30,30,this);
			    }
				
			bm.start();
		    if(bm.life==0){
			   bsm.remove(bm);
		    }		
			
		}

	}
	
	//�ӵ����ƺ���
	public void drawShot(Tank tk, Graphics p){
		
		for(int i=0;i<tk.dj.size();i++){						
			
			shot=tk.dj.get(i);
			
			if(shot!=null&&shot.boom==false){
				p.setColor(Color.WHITE);
				p.fill3DRect(shot.x, shot.y, 3, 3,false);	
			}else{
					tk.dj.remove(shot);					
			}
			
		}			
		
	}		
	

	
	//̹�˻��ƺ���
	public void drawTank(int x,int y,int type,int fx,Graphics p){
		//��ɫ����
		switch (type){
		case 0:
			p.setColor(Color.orange); 
			break;
		case 1:
			p.setColor(Color.CYAN);  
			break;
		}
		//�������
		switch (fx){
		case 0:
			p.fill3DRect(x, y, 5, 30,false);
			p.fill3DRect(x+15, y, 5, 30,false);
			p.fill3DRect(x+5, y+5, 10, 20,false);
			p.fillOval(x+4, y+10, 10, 10);
			p.drawLine(x+9, y+15, x+9, y-4);
			break;
		case 1:
			p.fill3DRect(x, y, 5, 30,false);
			p.fill3DRect(x+15, y, 5, 30,false);
			p.fill3DRect(x+5, y+5, 10, 20,false);
			p.fillOval(x+4, y+10, 10, 10);
			p.drawLine(x+9, y+35, x+9, y+20);
			break;
		case 2:
			p.fill3DRect(x,y,30,5,false);
			p.fill3DRect(x,y+15,30,5,false);
			p.fill3DRect(x+5, y+5, 20, 10,false);
			p.fillOval(x+10, y+4, 10, 10);
			p.drawLine(x+15, y+9, x-4, y+9);
			break;
		case 3:
			p.fill3DRect(x,y,30,5,false);
			p.fill3DRect(x,y+15,30,5,false);
			p.fill3DRect(x+5, y+5, 20, 10,false);
			p.fillOval(x+10, y+4, 10, 10);
			p.drawLine(x+35, y+9, x+20, y+9);
			break;
		}
		
	}

	//�����¼�������������������Ϊ�¼�Դ��������
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			this.mk.setY(this.mk.getY()-this.mk.getSpeed());
			mk.setFx(0);
			break;
		case KeyEvent.VK_S :
			this.mk.setY(this.mk.getY()+this.mk.getSpeed());
			mk.setFx(1);
			break;
		case KeyEvent.VK_A:
			this.mk.setX(this.mk.getX()-this.mk.getSpeed());
			mk.setFx(2);
			break;	
		case KeyEvent.VK_D :
			this.mk.setX(this.mk.getX()+this.mk.getSpeed());
			mk.setFx(3);
			break;
		}				
		if(e.getKeyCode()==KeyEvent.VK_J){
			if(mk.dj.size()<mk.getFireAble())   
			mk.Fire();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	//����ӵ��Ƿ����̹��
	public void touch(Tank tk, Shot shot){
		switch (tk.getFx()){
		case 0:
		case 1:
			if(shot.x>tk.getX()&&shot.x<(tk.getX()+20)&&shot.y>tk.getY()&&shot.y<(tk.getY()+30)){
				if(shot.type==tk.getType()){
					tk.boom=false;
				}else{
					tk.boom=true;
			    	shot.boom=true;
			    	Boom bm=new Boom(tk.getX(),tk.getY());   //��ըЧ��
			    	bsm.add(bm);
				}				
			}
		case 2:
		case 3:
			if(shot.x>tk.getX()&&shot.x<(tk.getX()+30)&&shot.y>tk.getY()&&shot.y<(tk.getY()+20)){
				if(shot.type==tk.getType()){
					tk.boom=false;
				}else{
					tk.boom=true;
			    	shot.boom=true;
			    	Boom bm=new Boom(tk.getX(),tk.getY());   //��ըЧ��
			    	bsm.add(bm);
				}	
			}		
		}
	}
	
	//�����߳�ˢ�����
	public void run() {

		while(true){	
			
			try {
				Thread.sleep(20);//���ˢ����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shot!=null){
				for(int i=0;i<yl.size();i++){
				this.touch(yl.get(i), shot);
				}
				this.touch(mk,shot);
			}
			
			for(int i=0;i<yl.size();i++)
			if(mk.x>yl.get(i).x&&mk.x<(yl.get(i).x+30)&&mk.y>yl.get(i).y&&mk.y<(yl.get(i).y+20)){
				mk.boom=true;
				yl.get(i).boom=true;
		    	Boom bm1=new Boom(mk.getX(),mk.getY());
		    	Boom bm2=new Boom(yl.get(i).getX(),yl.get(i).getY()); //��ըЧ��
		    	bsm.add(bm1);
		    	bsm.add(bm2);
			}
			
		this.repaint();		

		}
		
	}

}

