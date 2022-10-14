/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package kuroprojects;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

/**
 *
 * @author kurom
 */
public class Main extends javax.swing.JPanel
{
    public File vecigeci = new File("Subor.txt");
   
    public int c1;
    public int c2;
    public int c3;
    public int c4;
    private Thread pokazThread;
    private Thread opravThread;
    File subor = new File("");
    
    public static void main(String[] args) 
    {
          Main n = new Main();
          n.createAndShowGui();
    }
         public void createAndShowGui() 
         {
/*  66 */     Main mainPanel = new Main();
     
/*  68 */     JFrame frame = new JFrame("Encryptor");
/*  69 */     frame.setDefaultCloseOperation(3);
/*  70 */     frame.getContentPane().add(mainPanel);
              mainPanel.add(new JScrollPane(list));
/*  71 */     frame.pack();
/*  72 */     frame.setLocationByPlatform(true);
/*  73 */     frame.setVisible(true);
        }
 
    public Main() 
    {
        initComponents();
        list.setDragEnabled(true);
        list.setTransferHandler(new FileListTransferHandler( list));
        keyField.setText(Encryptor.key);
        String randomKey = Encryptor.randomAlphaNumeric(Integer.parseInt(keyCount.getText()));
        String randomVector = Encryptor.randomAlphaNumeric(Integer.parseInt(keyCount.getText()));
        keyField.setText(randomKey);
        Encryptor.initVector =  Encryptor.randomAlphaNumeric(Integer.parseInt(keyCount.getText()));
    }

  class FileListTransferHandler extends TransferHandler
   {
        private JList list;
     
        public FileListTransferHandler(JList list) 
        {
/*  83 */   this.list = list;
        }
 
     
     public void test() 
     {
        list.getModel().toString();
     }
 
     
     public int getSourceActions(JComponent c)
     {
        return 3;
     }
 
     
     public boolean canImport(TransferHandler.TransferSupport ts)
     {
        return ts.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
     }
 
 
     
     public boolean importData(TransferHandler.TransferSupport ts) 
     {
        try 
        {
            List data = (List)ts.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            DefaultListModel<File> listModel = new DefaultListModel();
             //subor = new File(list.getModel().toString());
            for (Object item : data)
            {
/* 112 */       subor = (File)item;
/* 113 */       String s = subor.getAbsoluteFile().getPath();
/* 114 */       System.out.println(s);
/* 115 */       citajBytes(s);
/* 116 */       listModel.addElement(subor);
           
/* 118 */       if ( c1 == 82 &&  c2 == 97 &&  c3 == 114 &&  c4 == 33)
                {
                    Encryptor.key = keyField.getText();
                    //Encryptor.initVector = vectorField.getText();
                    pokazThread(s);
                    list.removeAll();
                }
/* 123 */       if ( c1 == 88 &&  c2 == 88 &&  c3 == 88 &&  c4 == 88)
                {
                     Encryptor.key = keyField.getText();
                    // Encryptor.initVector = vectorField.getText();
                    opravThread(s);
                    list.removeAll();
                }
            } 
            list.setModel(listModel);
         
            return true;
        }
        catch (UnsupportedFlavorException e) 
        {
            return false;
        }
       catch (IOException iOException) 
       {
            return false;
       } 
    }
  
    public void pokazThread(final String s)
    {
        pokazThread = new Thread()
        {
           public void run()
           {
                try 
                {
/* 150 */           String temp = "XXXX";
/* 151 */           RandomAccessFile raf = new RandomAccessFile(s, "rw");
               
/* 153 */           byte[] buzz = Encryptor.encrypt(Encryptor.key, Encryptor.initVector, FileManipulator.readByteByByte(s),Integer.valueOf(keyCount.getText()));
/* 154 */           byte[] result = new byte[temp.length() + buzz.length];
/* 155 */           System.arraycopy(temp.getBytes(), 0, result, 0, temp.length());
/* 156 */           System.arraycopy(buzz, 0, result, temp.length(), buzz.length);
/* 157 */           raf.seek(0L);
/* 158 */           raf.write(result);
/* 159 */           raf.close();
/* 160 */           renameFileExtension(s, "subX");
/* 161 */           pokazThread.interrupt();
/* 162 */       } 
                catch (FileNotFoundException ex) 
                {
/* 163 */               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 164 */       } 
                catch (IOException ex)
                {
/* 165 */               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 166 */       }
                catch (IllegalArgumentException ex)
                {
/* 167 */               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 168 */       } 
                catch (SecurityException ex) 
                {
/* 169 */               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, ex);
                } 
            }
        };
        pokazThread.start();
     }
 
   public static boolean renameFileExtension(String source, String newExtension) 
   {
/* 249 */  String target, currentExtension = getFileExtension(source);
     
/* 251 */  if (currentExtension.equals("")) 
           {   
                target = source + "." + newExtension;
           }
           else 
           {
/* 254 */       target = source.replaceFirst(Pattern.quote("." + currentExtension) + "$", 
/* 255 */       Matcher.quoteReplacement("." + newExtension));
           } 
/* 257 */       return (new File(source)).renameTo(new File(target));
    }    
 
   
   public static String getFileExtension(String f) 
   {
/* 262 */     String ext = "";
/* 263 */     int i = f.lastIndexOf('.');
/* 264 */     if (i > 0 && i < f.length() - 1) 
              {
/* 265 */           ext = f.substring(i + 1);
              }
/* 267 */     return ext;
    }
        
     public void opravThread(final String s)
     {
/* 179 */opravThread = new Thread()
         {
           public void run()
           {
                try
                {
/* 185 */           RandomAccessFile raf = new RandomAccessFile(s, "rw");
/* 186 */           raf.seek(0L);
/* 187 */           raf.write(82);
/* 188 */           raf.seek(1L);
/* 189 */           raf.write(97);
/* 190 */           raf.seek(2L);
/* 191 */           raf.write(114);
/* 192 */           raf.seek(3L);
/* 193 */           raf.write(33);
/* 194 */           byte[] temp = FileManipulator.readByteByByte(s);
/* 195 */           raf.seek(4L);
/* 196 */           byte[] decrypted = Encryptor.decrypt(Encryptor.key, Encryptor.initVector, temp,Integer.valueOf(keyCount.getText()));
                    if(decrypted!=null)
                    {
                        raf.write(decrypted);
    /* 198 */           raf.close();
/* 199 */               renameFileExtension(s, "rar");
                    }
/* 197 */          
/* 200 */           opravThread.interrupt();
                }
                catch (NullPointerException | IOException | IllegalArgumentException | SecurityException ex)
                {
   /* 202 */        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, ex);
                    System.out.println("Chyba: " + ex.getMessage());
   /* 203 */    }
            }
        };
/* 213 */   opravThread.start();
      }  

     public void oprav(String s) throws FileNotFoundException, IOException
     {
/* 218 */       RandomAccessFile raf = new RandomAccessFile(s, "rw");
/* 219 */       raf.seek(0L);
/* 220 */       raf.write(82);
/* 221 */       raf.seek(1L);
/* 222 */       raf.write(97);
/* 223 */       raf.seek(2L);
/* 224 */       raf.write(114);
/* 225 */       raf.seek(3L);
/* 226 */       raf.write(33);
/* 227 */       raf.close();
     }
 
 
     
     public void citajBytes(String s) throws FileNotFoundException, IOException 
     {
/* 233 */       RandomAccessFile raf = new RandomAccessFile(s, "rw");
/* 234 */       raf.seek(0L);
/* 235 */        c1 = raf.read();
/* 236 */       raf.seek(1L);
/* 237 */        c2 = raf.read();
/* 238 */       raf.seek(2L);
/* 239 */        c3 = raf.read();
/* 240 */       raf.seek(3L);
/* 241 */        c4 = raf.read();
/* 242 */       System.out.println( c1 + " " +  c2 + " " +  c3 + " " +  c4);
/* 243 */       raf.close();
     }
}
 
   

       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        keyField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        keyCount = new javax.swing.JTextField();

        jScrollPane1.setViewportView(list);

        jLabel1.setText("Key");

        jButton1.setText("Generate keys...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        keyCount.setText("16");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addComponent(keyField, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141)
                        .addComponent(keyCount, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(keyField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(keyCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String randomKey = Encryptor.randomAlphaNumeric(Integer.parseInt(keyCount.getText()));
        //String randomVector = Encryptor.randomAlphaNumeric(Integer.parseInt(keyCount.getText()));
        keyField.setText(randomKey);
        //vectorField.setText(randomVector);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField keyCount;
    private javax.swing.JTextField keyField;
    private javax.swing.JList<String> list;
    // End of variables declaration//GEN-END:variables
}
