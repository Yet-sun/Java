/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myhotel.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * 专门做界面效果的类
 *
 * @author 以诗为名
 */
public class UiUtil {

    private UiUtil() {
    }

    //修改图标
    public static void setFrameImage(JFrame jf) {
        //获取工具类对象
        //public static Toolkit getDefaultToolkit():获取默认工具包
        Toolkit tk = Toolkit.getDefaultToolkit();

        //根据路径获取图片
        Image i = tk.getImage("src\\com\\myhotel\\pictures\\24-keys.png");

        //给窗体设置图片
        jf.setIconImage(i);
    }

    //设置窗体居中
    public static void setFrameCenter(JFrame jf) {
        /*
        思路：
        A：获取屏幕宽高
        B：获取窗体宽高
        C:（屏幕宽-窗体宽）/2，（屏幕高-窗体高）/2  作为新坐标
         */
        //获取工具对象
        Toolkit tk = Toolkit.getDefaultToolkit();

        //获取屏幕的宽高
        Dimension d = tk.getScreenSize();
        double sreenWidth = d.getWidth();
        double sreenHeight =d.getHeight();
        
        //获取窗体的宽高
        int frameWidth=jf.getWidth();
        int frameHeight=jf.getHeight();
        
        //获取新的宽高
        int width =(int)(sreenWidth-frameWidth)/2;
        int height=(int)(sreenHeight-frameHeight)/2;
        
        //设置窗体坐标
        jf.setLocation(width, height);
    }
}
