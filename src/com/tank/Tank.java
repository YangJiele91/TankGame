package com.tank;
import java.util.Vector;

class Shot implements Runnable {

	int x;
	int y;      //�ӵ������λ��
	int fx;     //�ӵ�����ķ���
	int sp=20;   //�ӵ�������ٶ�
	boolean boom=false; //�ӵ��Ƿ��Ѿ���ը
	int type=0;
	
	Shot(int x,int y,int fx){
		this.x=x;
		this.y=y;
		this.fx=fx;
		Thread th=new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {

		while(true){		
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //�����߳����ٷ����ӵ�
			switch(fx){
			case 0:
				y-=sp;
//				System.out.println("x="+x+"y="+y);
				break;
			case 1:
				y+=sp;
//				System.out.println("x="+x+"y="+y);
				break;
			case 2:
				x-=sp;
//				System.out.println("x="+x+"y="+y);
				break;
			case 3:
				x+=sp;
//				System.out.println("x="+x+"y="+y);
				break;
			}
			if(x<0||y<0||y>400||x>400){
				boom=true;
				break;
			}
		}

	}
	
}
 
//��ը��
class Boom{
	
	int x;
	int y;
	int life = 9;
	
	public Boom(int x,int y){
		this.x=x;
		this.y=y;		
	}
	
	public void start(){
		if (life>0){
		life--;
	    }
		
	}
		
}
	


//̹����
abstract class Tank {

	int x;  //̹��x����
	int y;  //̹��y����
	int fx; //̹�˷���
	int speed=1; //̹���ƶ��ٶ�
	int FireAble=2;//�ڵ�������
	Shot shot=null;//�ӵ�
	boolean boom=false; //̹�˱�ը

    Vector<Shot> dj=new Vector<Shot>();  //̹�˵���

	abstract public int getType();    //̹������
	
	public Tank(int x, int y){
		this.x=x;
		this.y=y;
		fx=0;
	}
	
	//̹�˿���
	public void Fire(){	
		
		switch(this.fx){
		case 0:
			shot=new Shot(x+8,y-10,fx);
			shot.type=this.getType();
			break;
		case 1:
			shot=new Shot(x+8,y+40,fx);
			shot.type=this.getType();
			break;
		case 2:
			shot=new Shot(x-10,y+8,fx);
			shot.type=this.getType();
			break;
		case 3:
            shot=new Shot(x+40,y+8,fx);
			shot.type=this.getType();
			break;
		}
	    dj.add(shot);   
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setFireAble(int fireAble) {
		FireAble = fireAble;
	}
	
	public int getX() {
		return x;
	}

	//����̹���ƶ���Χ
	public void setX(int x) {
		if(x<0){
			this.x = 0;
		}else if (x>355)
		    this.x = 355;
		else
			this.x = x;
	}

	public int getY() {
		return y;
	}

	//����̹���ƶ���Χ
	public void setY(int y) {
		if(y<0){
			this.y = 0;
		}else if (y>335)
		    this.y = 335;
		else
			this.y = y;
	}

    public int getFx() {
		return fx;
	}

	public void setFx(int fx) {
		this.fx = fx;
	}

	public int getSpeed() {
		return speed;
	}
	
	public int getFireAble() {
		return FireAble;
	}
		
}

//�ҷ�̹��
class MyTank extends Tank {
	
	int type=0;
	
	MyTank(int x, int y){
		super(x, y);
	}
	
	public int getType() {
		return type;
	}
	
}

//�з�̹��
class YourTank extends Tank implements Runnable{
	
	int type=1;
	
	YourTank(int x, int y){
		super(x, y);
		super.setFx(1);	
		Thread th = new Thread(this);
		th.start();
	}

	public int getType() {
		return type;
	}

	@Override
	public void run() {

		while(true){
			
			this.fx=(int)(Math.random()*4);
					
			switch(this.fx){
			case 0 :				
				for(int i=0;i<30;i++){
					this.setY(this.getY()-this.getSpeed());
					try {
						Thread.sleep(80);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}				
				}
				break;
			case 1 :
				for(int i=0;i<30;i++){
					this.setY(this.getY()+this.getSpeed());
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}				
				}
				break;
			case 2 :
				for(int i=0;i<30;i++){
					this.setX(this.getX()-this.getSpeed());
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;	
			case 3 :
				for(int i=0;i<30;i++){
					this.setX(this.getX()+this.getSpeed());
					try {
						Thread.sleep(60); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			
			if((int)(Math.random()*4)==1){
				if(dj.size()<getFireAble())   
				Fire();
				
			}	
			
			this.fx=(int)(Math.random()*4);		
			if(this.boom==true)
			break;	
			
		}
				
	}
	
}




