/*    */ package kuroprojects;
/*    */ 
/*    */ import java.nio.charset.Charset;
         import java.security.SecureRandom;
         import java.security.spec.KeySpec;
/*    */ import java.util.Random;
/*    */ import javax.crypto.Cipher;
         import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.IvParameterSpec;
         import javax.crypto.spec.PBEKeySpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ public class Encryptor
/*    */ {
    
            private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    /* 19 */static String key = "";
            static String initVector = "";

/*    */   public static byte[] encrypt(String key, String initVector, byte[] value,int size)
           {
    /*    */    try 
                {
    /* 26 */       IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
                   // SecretKeySpec k = new SecretKeySpec(key.getBytes(), 0, size, "AES");
    /*    */       SecureRandom random = new SecureRandom();
                    byte[] salt = new byte[16];
                    random.nextBytes(salt);
    /* 29 */        KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536,size); // AES-256
                    SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                    byte[] ka = f.generateSecret(spec).getEncoded();
                    SecretKeySpec keySpec = new SecretKeySpec(ka, "AES");

                   Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    /* 30 */       cipher.init(1,keySpec, iv);
    /*    */       
    /* 32 */       byte[] encrypted = cipher.doFinal(value);
/* 36 */           
                   return encrypted;
/*    */         }
/* 38 */        catch (Exception ex) 
                {
/*    */       
/* 40 */            ex.printStackTrace();
                    return null;
/*    */        } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static byte[] decrypt(String key, String initVector, byte[] encrypted,int size) 
           {
/*    */        try 
                {
        /* 49 */       IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        /*    */       SecretKeySpec k = new SecretKeySpec(key.getBytes(), 0, size , "AES");
        /*    */       SecureRandom random = new SecureRandom();
                       byte[] salt = new byte[16];
                       random.nextBytes(salt);
        /* 29 */       KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536,size); // AES-256
                       SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                       byte[] ka = f.generateSecret(spec).getEncoded();
                       SecretKeySpec keySpec = new SecretKeySpec(ka, "AES");

        /* 29 */
                       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        /* 30 */       cipher.init(2,keySpec, iv);
        /*    */       
        /* 55 */       byte[] original = cipher.doFinal(encrypted);
        /*    */       
        /* 57 */       return original;
/*    */        }
/* 59 */        catch (Exception ex) 
                {
/*    */       
/* 61 */            ex.printStackTrace();
/* 63 */            return null;
/*    */        } 
/*    */   }
/*    */   
/*    */   public static String generateRandomString(int pocet)
            {
    /* 68 */     byte[] array = new byte[pocet];
    /* 69 */     (new Random()).nextBytes(array);
    /* 70 */     String generatedString = new String(array, Charset.forName("UTF-8"));
    /*    */     
    /* 72 */     return generatedString;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String randomAlphaNumeric(int count)
            {
    /* 77 */     StringBuilder builder = new StringBuilder();
    /* 78 */     while (count-- != 0)
                 {
    /*    */       
    /* 80 */        int character = (int)(Math.random() * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length());
    /* 81 */        builder.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(character));
    /*    */     } 
    /* 83 */        return builder.toString();
    /*    */}
}


/* Location:              C:\Users\kurom\Desktop\DDDc.jar!\dragndropdemo\Encryptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */