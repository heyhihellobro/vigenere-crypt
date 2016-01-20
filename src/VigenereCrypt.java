import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Vladimir Rodin.
 * Built on: Thinkpad Workstation W540
 * Date: 13.01.2016
 * Twitter: @heyhihellobro
 */

public class VigenereCrypt extends JFrame {

    /* Global Settings */
    public static String WINDOW_TITLE = "Владимир Родин - Vigenere Crypt";
    public static int WINDOW_WIDTH = 1800;
    public static int WINDOW_HEIGHT = 1100;

    final JTextField textIn = new JTextField();
    final JTextField textOut = new JTextField();
    final JTextField textKey = new JTextField();

    JButton generateButton = new JButton();

    Color textColor = new Color(110, 137, 255);
    Color textColorFront = new Color(0, 0, 0);


    public static String tempTextVar = null;
    public static String tempKeyVar = null;


    private int smesh = (int) 'a'; //смещение алфавита относительно таблицы юникодов


    public String encryptFirstMethod(String text, String keyWord) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int num = ((text.charAt(i) + keyWord.charAt(i % keyWord.length()) - 2 * smesh) % 26);
            //в num лежит номер буквы в алфавите
            char c = (char) (num + smesh);//получаем нужный символ
            ans.append(c);
        }
        return ans.toString();
    }

    public String decryptFirstMethod(String shifr, String keyWord) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < shifr.length(); i++) {
            int num = ((shifr.charAt(i) - keyWord.charAt(i % keyWord.length()) + 26) % 26);
            //обратные преобразования с номером буквы в алфавите
            char c = (char) (num + smesh);
            ans.append(c);
        }
        return ans.toString();
    }

    //генератор таблицы Виженера
    private static char[][] genTable() {
        char[][] table = new char[26][26];
        for (int i = 0; i < 26; i++) {
            int off = i;
            for (int j = 0; j < 26; j++) {
                if (off == 26) {
                    off = 0;
                }
                table[i][j] = (char) (97 + off);
                off++;
            }
        }
        return table;
    }
    private static String encrypt(char[] key, char[] text) {
        char[] nText = new char[text.length];
        int k;
        int t;
        char[][] table = genTable();

        for (int i = 0; i < text.length; i++) {
            k = (int) key[i] - 97;
            t = (int) text[i] - 97;
            nText[i] = table[k][t];

        }
        return new String(nText);
    }
    private static String decrypt(char[] key, char[] text) {
        char[] nText = new char[text.length];
        int k;
        int t;
        char[][] table = genTable();

        for (int i = 0; i < text.length; i++) {
            k = (int) key[i] - 97;
            t = (int) text[i] - 97;
            if (k > t) {
                nText[i] = table[26 + (t - k)][0];
            } else {
                nText[i] = table[t - k][0];
            }


        }
        return new String(nText);
    }

    /* Default Constructor */
    VigenereCrypt() {
        initUserInterface();
    }

    /* Initialising Graphic User Interface */
    private void initUserInterface() {

        /* Initialising new window */
        setTitle(WINDOW_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        generateMenuBar();
        generateWindowContent();
    }


    private void generateWindowContent() {

        JPanel panel = new JPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.PAGE_START);

        final JRadioButton encryptRadioButton = new JRadioButton("Encrypt");
        encryptRadioButton.setMnemonic(KeyEvent.VK_E);
        encryptRadioButton.setSelected(true);

        final JRadioButton decryptRadioButton = new JRadioButton("Decrypt");
        decryptRadioButton.setMnemonic(KeyEvent.VK_D);

        final ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(encryptRadioButton);
        buttonGroup.add(decryptRadioButton);


        panel.add(encryptRadioButton, BorderLayout.NORTH);
        panel.add(decryptRadioButton, BorderLayout.NORTH);


//        final JCheckBox boxes[] = new JCheckBox[options.length];
//
//        for (int i = 0; i < boxes.length; i++) {
//            boxes[i] = new JCheckBox(options[i]);
//            panel.add(boxes[i]);
//        }
//        boxes[0].setSelected(true);

        textIn.setFont(new Font("Calibri", Font.BOLD, 72));
        textIn.setHorizontalAlignment(SwingConstants.CENTER);
        textIn.setText("Введите фразу");
        textIn.setEditable(true);
        textIn.setBackground(textColor);
        textIn.setForeground(textColorFront);
        add(textIn, BorderLayout.CENTER);

        textKey.setFont(new Font("Calibri", Font.BOLD, 72));
        textKey.setHorizontalAlignment(SwingConstants.CENTER);
        textKey.setText("Введите ключ сюда");
        textKey.setEditable(true);
        textKey.setBackground(textColor);
        textKey.setForeground(textColorFront);
        add(textKey, BorderLayout.EAST);


        textOut.setFont(new Font("Calibri", Font.BOLD, 72));
        textOut.setHorizontalAlignment(SwingConstants.CENTER);
        textOut.setText("Out");
        textOut.setEditable(true);
        textOut.setBackground(textColor);
        textOut.setForeground(textColorFront);
        add(textOut, BorderLayout.SOUTH);


        generateButton.setFont(new Font("Calibri", Font.BOLD, 72));
        generateButton.setText("Generate");
        generateButton.setForeground( new Color(110, 137, 255));
        generateButton.setBackground(new Color(255,255,255));
        generateButton.setMargin(new Insets(2, 0, 2, 0));
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (encryptRadioButton.isSelected()) {

                    tempTextVar = textIn.getText();
                    int textInLen = tempTextVar.length();

                    tempKeyVar = textKey.getText();
                    int textKeyLen = tempKeyVar.length();

                    textOut.setText(encryptFirstMethod(tempTextVar, tempKeyVar));

//                    char[] textContent = new char[textInLen];
//                    char[] keyContent = new char[textKeyLen];
//
//                    if (textInLen == textKeyLen) {
//                        for (int i = 0; i < textInLen; i++) {
//                            textContent[i] = tempTextVar.charAt(i);
//                            System.out.print(textContent[i]);
//                        }
//
//                        System.out.println();
//
//                        for (int i = 0; i < textKeyLen; i++) {
//                            keyContent[i] = tempKeyVar.charAt(i);
//                            System.out.print(keyContent[i]);
//                        }
//
//                        System.out.println();
//
//                        //textOut.setText(encrypt(keyContent, textContent));
//                        System.out.println(encrypt(keyContent, textContent));
//
//                        // System.out.println(encryptt(tempTextVar, tempKeyVar));
//
//                    } else {
//                      //  JOptionPane.showMessageDialog(VigenereCrypt.this, "Длина ключа должна быть равна длине текста");
//                    }
                } else if (decryptRadioButton.isSelected()) {

                    tempTextVar = textIn.getText();
                    int textInLen = tempTextVar.length();

                    tempKeyVar = textKey.getText();
                    int textKeyLen = tempKeyVar.length();

                    textOut.setText(decryptFirstMethod(tempTextVar, tempKeyVar));

                }


            }
        });
        add(generateButton, BorderLayout.WEST);


    }


    private void generateMenuBar() {

        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        //JMenu tools = new JMenu("Tools");
        JMenu help = new JMenu("About");


        /* Exit Item */
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_E);
        exitItem.setToolTipText("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        help.setMnemonic(KeyEvent.VK_F1);
        JMenuItem versionItem = new JMenuItem("Info about program");
        versionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initVersionWindow();
            }
        });
        help.add(versionItem);


        JMenuItem copyItem = new JMenuItem("Copy output text");
        copyItem.setToolTipText("Copy output text");
        copyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToCopy = textOut.getText();
                StringSelection selection = new StringSelection(textToCopy);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });

        file.add(exitItem);
        edit.add(copyItem);
        menubar.add(file);
        menubar.add(edit);
        //menubar.add(tools);
        menubar.add(help);
        setJMenuBar(menubar);

    }


    private void initVersionWindow() {
        JFrame frame = new JFrame("About Vigenere Crypt");
        frame.setLayout(new GridLayout(1, 1, 5, 5));
        JLabel label = new JLabel("<html><span style='font-size: 16px; text-align: center;'>Version: 1.0 Beta <br> " +
                "Author: Vladimir Rodin <br>" +
                "Twitter: @heyhihellbro <br>" +
                "Website: <a href='http://rodin.xyz/'>http://rodin.xyz/</a> </span></html>");
        label.setHorizontalAlignment(JLabel.CENTER);


        TitledBorder titled = new TitledBorder("About Password Generator");
        label.setBorder(titled);


        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(label);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                VigenereCrypt crypt = new VigenereCrypt();
                crypt.setVisible(true);
            }
        });
    }

}
