package cn.edu.ynu.lab3;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ClearData extends JFrame implements ActionListener {
	JFrame frame;
	JButton btnNewButton;
	JButton btnNewButton_1;
	JButton btnNewButton_2;
	JButton btnNewButton_3;
	JFileChooser fc = new JFileChooser();
	TextArea area;
	JTextField textField;
	InputStreamReader read = null;
	BufferedReader reader = null;
	BufferedWriter writer = null;
	StringBuffer sb = new StringBuffer();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClearData window = new ClearData();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClearData() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("教务数据清理工具");
		frame.setBounds(100, 100, 411, 281);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);// 将视图放在屏幕中间位置
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("需要清理的文件：");
		lblNewLabel.setBounds(69, 28, 149, 15);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(69, 101, 166, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("浏览本地文件");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButtonPerformed(e);
			}

			public void btnNewButtonPerformed(ActionEvent e) {
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.showDialog(new JLabel(), "选择");// 跳出来的JFrameChooser的选择按钮
				File file = fc.getSelectedFile();
				textField.setText(file.getAbsolutePath());
			}
		});
		btnNewButton.setBounds(69, 53, 166, 23);
		frame.getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("清理");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnNewButton_1) {
					cleanData(textField.getText());
					JFrame frame = new JFrame("清理成功！");
					frame.setBounds(100, 100, 100, 100);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					Button b = new Button("清理成功，点击退出!");
					b.setBounds(69, 53, 166, 23);
					frame.getContentPane().add(b);
					b.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.exit(0);
						}
					});
				}
			}
		});
		btnNewButton_1.setBounds(69, 175, 76, 23);
		frame.getContentPane().add(btnNewButton_1);

		btnNewButton_2 = new JButton("取消");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(258, 175, 76, 23);
		frame.getContentPane().add(btnNewButton_2);

		btnNewButton_3 = new JButton("显示");
		btnNewButton_3.addActionListener(this);// 设置默认关闭Frame的方式：点击显示按钮触发显示事件
		btnNewButton_3.setBounds(258, 100, 73, 23);
		frame.getContentPane().add(btnNewButton_3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton_3) {
			JFrame f = new JFrame();
			f.setSize(700, 700);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);// 这里是点击新打开的显示数据的Frame后将这个Frame隐藏
			String extensionName = getExtensionName(textField.getText());
			if ("csv".equals(extensionName)) {
				f.setTitle("显示数据");
				area = new TextArea();
				// 获取数据
				String data = readTxt(textField.getText());
				area.setText(data);
				f.getContentPane().add(area);
				f.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "请选择csv格式的文件！");
			}
		}
	}

	private String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	private String readTxt(String path) {
		if (path == null || "".equals(path)) {
			return "";
		}
		File file = new File(path);
		try {
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
			reader = new BufferedReader(read);// 根据API知道，在 BufferedReader 内包装 InputStreamReader可达到最高效率
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line.trim());
				sb.append("\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public void cleanData(String path) {
		Set<String> set = new HashSet<>();
		File file = new File(path);
		File newCsvFile = new File("F:/newCSV.csv");
		try {
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
			reader = new BufferedReader(read);
			reader.readLine();//这里读取第一行数据，也可以不要
			writer = new BufferedWriter(new FileWriter(newCsvFile, true));
			String line = null;
			while ((line = reader.readLine()) != null) {
				int intIndex = line.indexOf("\"");//设置一个索引，判断读取的每一行数据中是否存在“”号
				if (intIndex != -1) {//“”不存在，索引为-1；intIndex == -1时候直接打印该行数据既可
					String s1 = line.substring(0, line.indexOf("\"") + 1);//将前双引号及前面的数据复制到s1
					String s2 = line.substring(line.indexOf("\"") + 1, line.indexOf("\","));//将双引号中的数据复制到s2
					String s3 = line.substring(line.indexOf("\","), line.length() - 1);//将后双引号及前面的数据复制到s2
					String line_copy = s1 + s2 + s3;//做一个数据的拷贝

					s2 = s2.replace(",", ":").trim();//将双引号里面的数据的“,”号换为“:”
					String[] strs1 = s2.split(":");//通过“:”截取数据，将截取的数据存入字符串数组中
					for (int i = 0; i < strs1.length; i++) {
						strs1[i] = strs1[i].trim();//忽略字符串数组中每一个元素的前后空格
						if (set.contains(strs1[i]))
							strs1[i] = null; // 如果元素重复，则删除
						else
							set.add(strs1[i]);//元素不重复则加入set集合
					}
					s2 = set.toString();//将set集合转换成String类型，需要注意这里转换出来的字符串前后带有[]，相当于一个列表
					s2 = s2.substring(1, s2.length() - 1);//将[]剔除
					line_copy = s1 + s2 + s3;//将剔除了重复数据的数据重新换回字符串中
					// System.out.println(line_copy);
					writer.write(line_copy);//写文件
					writer.newLine();
					set.removeAll(set);//清空set，以防止上一行数据存入set后影响下一行数据
				} else {
					writer.write(line);//intIndex == -1时候直接写文件
					writer.newLine();
					// System.out.println(line);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {
				try {
					//关闭流
					read.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}// main()
}
