import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
class dibba extends JFrame implements ActionListener
{
JButton bt[]=new JButton[9];
JButton side=new JButton();
JButton shuru=new JButton("START");
JPanel pn= new JPanel();
Random ran=new Random();
JLabel timelimit1=new JLabel("Time Left:");
JLabel timelimit2=new JLabel("3");
Color view,c[]={Color.red,Color.pink,Color.orange,Color.yellow,Color.green,Color.darkGray,Color.magenta,Color.blue,Color.cyan};
static int t=0,time=3;
private boolean pause=false;
CheckTimeLimit ctl=new CheckTimeLimit();//Has power to game over 

public dibba()
{
super("Badhiya");
setSize(600,400);
setLayout(null);
setLocationRelativeTo(null);
pn.setLayout(new GridLayout(3,3));
pn.setBounds(200,50,300,300);
viewbutton();
makebutton();
colorbutton();
startbutton();
maketime();
//new Thread(ctl).start();//start Timer
add(shuru);
add(pn);
add(side);
setVisible(true);
}

public void maketime()
{
timelimit1.setFont(new Font("Aerial",Font.BOLD,19));
timelimit1.setBounds(50,150,100,50);
timelimit1.setForeground(Color.red);
timelimit2.setFont(new Font("Magneto",Font.BOLD,70));
timelimit2.setBounds(70,200,100,100);
timelimit2.setForeground(Color.red);
add(timelimit2);
add(timelimit1);
}
 			  	
class CheckTimeLimit implements Runnable
{
private final Object Lock=new Object();
public void run()
{
while(time-->=0)
{
synchronized(Lock)
{
if(pause)
{
try{Lock.wait();}
catch(InterruptedException e){}
}
else
repaint();
}
try{Thread.sleep(1000);}
catch(InterruptedException e){}
timelimit2.setText(""+time);
}
remove(pn);
remove(timelimit1);
remove(timelimit2);
remove(side);
remove(shuru);
//removeAll();
setVisible(false);
setVisible(true);
JLabel lab=new JLabel();
lab.setBounds(250,200,200,50);
lab.setForeground(Color.red);
lab.setFont(new Font("Aerial",Font.BOLD,30));
lab.setText("GAME OVER");
getContentPane().setBackground(Color.yellow);
add(lab);
}

public void StartAndStop()
{
synchronized(Lock)
{
pause=!pause;
Lock.notifyAll();
}
}

}

public void makebutton()
{
for(int i=0;i<9;i++)
{
bt[i]=new JButton();
bt[i].addActionListener(this);
pn.add(bt[i]);
}
}

public void viewbutton()
{
view=c[ran.nextInt(9)];
side.setBounds(50,50,100,100);
side.setBackground(view);
}

public void startbutton()
{
shuru.setBounds(50,300,100,40);
shuru.addActionListener(this);
}

public void actionPerformed(ActionEvent evt)
{
JButton konsibuttonhai=(JButton)evt.getSource();
Color check=konsibuttonhai.getBackground();
if(check==view)
{
/*removeAll();
setVisible(false);
new dibba();*/
colorbutton();
viewbutton();
timelimit2.setText("3");
time=3;
}
if(konsibuttonhai==shuru)
{
if(t==0)
{
t=1;
shuru.setText("PAUSE");
new Thread(ctl).start();
}
else
{
ctl.StartAndStop();
if(pause)
shuru.setText("START");
else
shuru.setText("PAUSE");
}
}
}

public void colorbutton()
{
boolean sahi[]=new boolean[9];
int no=8,k;
while(no>=0)
{
k=ran.nextInt(9);
if(!sahi[k])
{
sahi[k]=true;
bt[no--].setBackground(c[k]);
}
}
}

public static void main(String ...a)
{
new dibba();
}
}