/*    */ package kuroprojects;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Paths;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FileManipulator
/*    */ {
    /*    */   public static char[] readFileRaw(String file) throws FileNotFoundException, IOException 
               {
        /* 36 */     File f = new File(file);
        /* 37 */     InputStreamReader inputreader = new InputStreamReader(new FileInputStream(file), "windows-1252");
        /* 38 */     BufferedReader bufferedReader = new BufferedReader(inputreader);
        /* 39 */     char[] bytes = new char[(int)f.length()];
        /* 40 */     bufferedReader.read(bytes, 0, (int)f.length());
        /* 41 */     bytes = Arrays.copyOfRange(bytes, 4, bytes.length);
        /* 42 */     return bytes;
    /*    */   }
    /*    */ 
/*    */ 
/*    */   
    /*    */   public static byte[] readByteByByte(String filePath) throws IOException 
               {
        /* 48 */     System.setProperty("file.encoding", "windows-1252");
        /* 49 */     byte[] contentbyte = Files.readAllBytes(Paths.get(filePath, new String[0]));
        /* 50 */     contentbyte = Arrays.copyOfRange(contentbyte, 4, contentbyte.length);
        /* 51 */     return contentbyte;
    /*    */   }
/*    */ }