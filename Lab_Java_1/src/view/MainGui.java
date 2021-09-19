package view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import info.ClassDetail;
import info.MyLoader;
import java.net.URL;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class MainGui {

	private JFrame frame;
	private JTree tree;
	private JarFile f;
	private MyLoader loader;
	private JList list;
	private String path;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui window = new MainGui();
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
	public MainGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 551, 383);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{210, 220, 0};
		gridBagLayout.rowHeights = new int[]{249, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		
		DefaultListModel dlm = new DefaultListModel();
		list = new JList(dlm);
		
		DefaultMutableTreeNode tn = new DefaultMutableTreeNode();
		DefaultTreeModel tm = new DefaultTreeModel(tn);
		
		tree = new JTree(tm);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				new Thread(() -> {
					synchronized (dlm) {
						dlm.clear();
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
						if(node != null) {
							JarEntry entry = (JarEntry)node.getUserObject();
							if(entry == null || entry.isDirectory()) return;
							List<String> resultList = results(entry);
							if(resultList != null){
								for (String str : resultList) {		
									dlm.addElement(str);	
								}
							}
						}
						else
							System.out.println("error");
					}
				}).start();
			}
		});
		
		tree.setCellRenderer(new JarTreeCellRenderer());
		GridBagConstraints gbc_tree = new GridBagConstraints();
		gbc_tree.insets = new Insets(0, 0, 0, 5);
		gbc_tree.fill = GridBagConstraints.BOTH;
		gbc_tree.gridx = 0;
		gbc_tree.gridy = 0;
		JScrollPane jPane = new JScrollPane(tree);
		frame.getContentPane().add(jPane, gbc_tree);
		
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 0;
		JScrollPane jPane1 = new JScrollPane(list);
		frame.getContentPane().add(jPane1, gbc_list);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Open");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}
					catch (Exception e1) {
						e1.printStackTrace();
					}
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Відновлення з файлу");
				fileChooser.setApproveButtonText("Відкрити");
				fileChooser.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return "Файли типу .jar";
					}
					
					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.toString().endsWith(".jar");
						
					}
				});
				fileChooser.setMultiSelectionEnabled(false);
				 int selection = fileChooser.showOpenDialog(null);//!!!!!
				if (selection!=JFileChooser.APPROVE_OPTION)
					return;
				File selectedFile = fileChooser.getSelectedFile();
				path = selectedFile.getAbsolutePath();
				loader = new MyLoader(path);
				f = null;
				try {
					f = new  JarFile(selectedFile.getAbsolutePath());								
							try {										
								createTree(tn, f);	
				
							} catch (Exception e1) {
							
								e1.printStackTrace();
							}
							tm.reload();
						}
				 catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				try {
					f.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmNewMenuItem);
	}

	protected JTree getTree() {
		return tree;
	}
	
	private static String getFileExtension(JarEntry j) {
        String jarName = j.getName();
        if(jarName.lastIndexOf(".") != -1 && jarName.lastIndexOf(".") != 0)
        return jarName.substring(jarName.lastIndexOf(".")+1);
        else return "";
    }
	
	private void createTree(DefaultMutableTreeNode root, JarFile file) throws Exception {
        root.removeAllChildren();
        List<JarEntry> entries = file.stream().collect(Collectors.toList());

        entries.sort(Comparator
            .comparing((JarEntry entry) -> entry.getName().split("/").length)
            .thenComparing(entry -> {
                String[] pathElements = entry.getName().split("/");
                return pathElements[pathElements.length - 1];
            }));

        for (JarEntry entry : entries) {

            List<String> pathElements = Arrays.asList(entry.getName().split("/"));
            DefaultMutableTreeNode parent = root;
            
            for (int i = 0; i < pathElements.size() - 1 ; i++) {
                String matchingName = String.join("/", pathElements.subList(0, i+1)) + "/";

                DefaultMutableTreeNode current = find(parent, matchingName);
                if(current == null) {
                  current = parent;
                  JarEntry newEntry = new JarEntry(matchingName);
                    DefaultMutableTreeNode newItem = new DefaultMutableTreeNode(newEntry);
                    current.add(newItem);
                    parent = newItem;
                }
                else
                  parent = current;

            }
          
              parent.add(new DefaultMutableTreeNode(entry));
        }
    }
	
	
	private DefaultMutableTreeNode find(DefaultMutableTreeNode root, String s) {
	    Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
	    while (e.hasMoreElements()) {
	        DefaultMutableTreeNode node = e.nextElement();
	        if (node.toString().equalsIgnoreCase(s)) {
	            return node;
	        }
	    }
	    return null;
	}
	
	protected JList getList() {
		return list;
	}
	
	public class JarTreeCellRenderer extends DefaultTreeCellRenderer{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {     
            if (value instanceof DefaultMutableTreeNode) {
                value = ((DefaultMutableTreeNode)value).getUserObject();	
                if (value instanceof JarEntry) {
                    String jarName = ((JarEntry) value).getName();
                    if(jarName.endsWith("/"))
                    	jarName = jarName.substring(0, jarName.length() - 1);
                    if(jarName.contains("/"))
                    	jarName = jarName.substring(jarName.lastIndexOf('/') + 1);
                    value = jarName;
                }
            }
            return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}
	}
	
	public List<String> results(JarEntry entry) {
		String s;
		List<String> list = new ArrayList();
		Class c;
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
			if (entry.getName().contains(".class") && !entry.getName().contains(".classpath")) {			
				s = entry.getName().replaceAll("/", ".");
				s = s.substring(0, s.length()-6);			
				try{
					c = Class.forName(s, false, loader);				
					if (c!=null){
						list.addAll(ClassDetail.getInfo(c));					
					}			
				}catch(Exception e2){
					list.add("Error:"+e2.getMessage());
				}
			}else if(entry.getName().contains("resource")) {
					list.add(entry.getName());
					list.add("Size: " + entry.getSize() + " bytes");
					list.add("Last modified: " + df.format(entry.getLastModifiedTime().toMillis()));
					list.add("Format: " + MainGui.getFileExtension(entry));
					if(entry.getName().endsWith(".properties")) {
						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(new  JarFile(path).getInputStream(entry)));
							String str;
							List<String> l = new ArrayList();
							l.add("Content: ");
							while ((str = in.readLine()) != null)
								l.add(str);
							list.addAll(l);
						} catch (IOException e) {
							e.printStackTrace();
							return null;	
						}	

				    }
			}	
			else {
				list.add(entry.getName());
				list.add("Size: " + entry.getSize() + " bytes");
				list.add("Time: " + df.format(entry.getLastModifiedTime().toMillis()));
			}	
		return list;
	}
	
}
