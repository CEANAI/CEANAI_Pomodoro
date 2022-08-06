package com.company;

import javazoom.jl.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Vector;

public class Main extends JFrame implements Runnable{
    private static final long serialVersionUID = 1L;
    private JLabel date;
    private JLabel time;
    private JLabel timeup;
    private JList jl;
    private LocalTime lt = LocalTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter d1 = DateTimeFormatter.ofPattern("mm");
    private String mt = d1.format(lt);
    private int m = Integer.parseInt(mt);
    private String nmt;
    private int nm;
    File fa = new File("src/work.mp3");
    File fb = new File("src/rest.mp3");
    ImageIcon imageIcon = new ImageIcon("src/icon.png");
    public Main() {
        //初始化图形界面
        this.setVisible(true);
        this.setTitle("CEANAI番茄钟");
        this.setSize(400, 300);
        this.setLocation(400, 300);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(imageIcon.getImage());
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        //文本提示
        JLabel tip = new JLabel();
        tip.setBounds(56,120,160,24);
        tip.setFont(new Font("微软雅黑",Font.PLAIN,20));
        tip.setText("启动时开始计时");
        panel.add(tip);
        //时间
        time = new JLabel();
        time.setBounds(60, 54, 160, 59);
        time.setFont(new Font("Arial", Font.PLAIN, 50));
        panel.add(time);
        //日期
        date = new JLabel();
        date.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        date.setBounds(47, 10, 190, 22);
        panel.add(date);
        //时间到提示
        timeup = new JLabel();
        timeup.setBounds(31,210,190,24);
        timeup.setFont(new Font("微软雅黑",Font.PLAIN,20));
        panel.add(timeup);
        //list
        Vector content = new Vector();
        jl = new JList(content);

        for (int i = 0;i < 10;i++) {
            LocalTime rt = lt.plusMinutes(25 + 30 * i);
            content.add(dateTimeFormatter.format(rt));
            LocalTime st = lt.plusMinutes(30 + 30 * i);
            content.add(dateTimeFormatter.format(st));
        }

        JScrollPane js = new JScrollPane(jl);
        js.setBounds(240,10,140,250);
        panel.add(js);
        //按钮
        JButton quit = new JButton("点击该按钮停止并退出");
        quit.setBounds(31,160,190,36);
        panel.add(quit);
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
    //一个线程更新时
        public void run() {
            while (true) {
                try {
                    nmt = new SimpleDateFormat("mm").format(new Date());
                    nm = Integer.parseInt(nmt);
                    date.setText(new SimpleDateFormat("yyyy 年 MM 月 dd 日  EEEE").format(new Date()));
                    time.setText(new SimpleDateFormat("HH:mm").format(new Date()));
                    if (nm == m || nm == m - 30 || nm == m + 30) {
                        timeup.setText("休息结束，工作开始");
                        Player pa = new Player(new FileInputStream(fa));
                        pa.play();
                    } else if (nm == m + 25 || nm == m + 55 || nm == m - 5 || nm == m - 35) {
                        timeup.setText("工作结束，休息开始");
                        Player pb = new Player(new FileInputStream(fb));
                        pb.play();
                    }else {
                        timeup.setText(" ");
                    }
                    Thread.sleep(60000);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    public static void main(String[] args) {
        new  Thread(new Main()).start();
    }
}
