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
		frame = new JFrame("��������������");
		frame.setBounds(100, 100, 411, 281);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);// ����ͼ������Ļ�м�λ��
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("��Ҫ������ļ���");
		lblNewLabel.setBounds(69, 28, 149, 15);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(69, 101, 166, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("��������ļ�");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButtonPerformed(e);
			}

			public void btnNewButtonPerformed(ActionEvent e) {
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.showDialog(new JLabel(), "ѡ��");// ��������JFrameChooser��ѡ��ť
				File file = fc.getSelectedFile();
				textField.setText(file.getAbsolutePath());
			}
		});
		btnNewButton.setBounds(69, 53, 166, 23);
		frame.getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("����");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnNewButton_1) {
					cleanData(textField.getText());
					JFrame frame = new JFrame("����ɹ���");
					frame.setBounds(100, 100, 100, 100);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					Button b = new Button("����ɹ�������˳�!");
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

		btnNewButton_2 = new JButton("ȡ��");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setBounds(258, 175, 76, 23);
		frame.getContentPane().add(btnNewButton_2);

		btnNewButton_3 = new JButton("��ʾ");
		btnNewButton_3.addActionListener(this);// ����Ĭ�Ϲر�Frame�ķ�ʽ�������ʾ��ť������ʾ�¼�
		btnNewButton_3.setBounds(258, 100, 73, 23);
		frame.getContentPane().add(btnNewButton_3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton_3) {
			JFrame f = new JFrame();
			f.setSize(700, 700);
			f.setLocationRelativeTo(null);
			f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);// �����ǵ���´򿪵���ʾ���ݵ�Frame�����Frame����
			String extensionName = getExtensionName(textField.getText());
			if ("csv".equals(extensionName)) {
				f.setTitle("��ʾ����");
				area = new TextArea();
				// ��ȡ����
				String data = readTxt(textField.getText());
				area.setText(data);
				f.getContentPane().add(area);
				f.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "��ѡ��csv��ʽ���ļ���");
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
			reader = new BufferedReader(read);// ����API֪������ BufferedReader �ڰ�װ InputStreamReader�ɴﵽ���Ч��
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
			reader.readLine();//�����ȡ��һ�����ݣ�Ҳ���Բ�Ҫ
			writer = new BufferedWriter(new FileWriter(newCsvFile, true));
			String line = null;
			while ((line = reader.readLine()) != null) {
				int intIndex = line.indexOf("\"");//����һ���������ж϶�ȡ��ÿһ���������Ƿ���ڡ�����
				if (intIndex != -1) {//���������ڣ�����Ϊ-1��intIndex == -1ʱ��ֱ�Ӵ�ӡ�������ݼȿ�
					String s1 = line.substring(0, line.indexOf("\"") + 1);//��ǰ˫���ż�ǰ������ݸ��Ƶ�s1
					String s2 = line.substring(line.indexOf("\"") + 1, line.indexOf("\","));//��˫�����е����ݸ��Ƶ�s2
					String s3 = line.substring(line.indexOf("\","), line.length() - 1);//����˫���ż�ǰ������ݸ��Ƶ�s2
					String line_copy = s1 + s2 + s3;//��һ�����ݵĿ���

					s2 = s2.replace(",", ":").trim();//��˫������������ݵġ�,���Ż�Ϊ��:��
					String[] strs1 = s2.split(":");//ͨ����:����ȡ���ݣ�����ȡ�����ݴ����ַ���������
					for (int i = 0; i < strs1.length; i++) {
						strs1[i] = strs1[i].trim();//�����ַ���������ÿһ��Ԫ�ص�ǰ��ո�
						if (set.contains(strs1[i]))
							strs1[i] = null; // ���Ԫ���ظ�����ɾ��
						else
							set.add(strs1[i]);//Ԫ�ز��ظ������set����
					}
					s2 = set.toString();//��set����ת����String���ͣ���Ҫע������ת���������ַ���ǰ�����[]���൱��һ���б�
					s2 = s2.substring(1, s2.length() - 1);//��[]�޳�
					line_copy = s1 + s2 + s3;//���޳����ظ����ݵ��������»����ַ�����
					// System.out.println(line_copy);
					writer.write(line_copy);//д�ļ�
					writer.newLine();
					set.removeAll(set);//���set���Է�ֹ��һ�����ݴ���set��Ӱ����һ������
				} else {
					writer.write(line);//intIndex == -1ʱ��ֱ��д�ļ�
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
					//�ر���
					read.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}// main()
}
