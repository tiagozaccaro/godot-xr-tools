package com.google.android.vending.licensing;

import com.google.android.vending.licensing.util.Base64;
import com.google.android.vending.licensing.util.Base64DecoderException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class AESObfuscator implements Obfuscator {
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] IV = {16, 74, 71, -80, 32, 101, -47, 72, 117, -14, 0, -29, 70, 65, -12, 74};
    private static final String KEYGEN_ALGORITHM = "PBEWITHSHAAND256BITAES-CBC-BC";
    private static final String UTF8 = "UTF-8";
    private static final String header = "com.google.android.vending.licensing.AESObfuscator-1|";
    private Cipher mDecryptor;
    private Cipher mEncryptor;

    public AESObfuscator(byte[] salt, String applicationId, String deviceId) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(KEYGEN_ALGORITHM);
            KeySpec keySpec = new PBEKeySpec((applicationId + deviceId).toCharArray(), salt, 1024, 256);
            SecretKey tmp = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            this.mEncryptor = cipher;
            byte[] bArr = IV;
            cipher.init(1, secret, new IvParameterSpec(bArr));
            Cipher cipher2 = Cipher.getInstance(CIPHER_ALGORITHM);
            this.mDecryptor = cipher2;
            cipher2.init(2, secret, new IvParameterSpec(bArr));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Invalid environment", e);
        }
    }

    @Override // com.google.android.vending.licensing.Obfuscator
    public String obfuscate(String original, String key) {
        if (original == null) {
            return null;
        }
        try {
            return Base64.encode(this.mEncryptor.doFinal((header + key + original).getBytes(UTF8)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Invalid environment", e);
        } catch (GeneralSecurityException e2) {
            throw new RuntimeException("Invalid environment", e2);
        }
    }

    @Override // com.google.android.vending.licensing.Obfuscator
    public String unobfuscate(String obfuscated, String key) throws ValidationException {
        if (obfuscated == null) {
            return null;
        }
        try {
            String result = new String(this.mDecryptor.doFinal(Base64.decode(obfuscated)), UTF8);
            int headerIndex = result.indexOf(header + key);
            if (headerIndex != 0) {
                throw new ValidationException("Header not found (invalid data or key):" + obfuscated);
            }
            return result.substring(header.length() + key.length(), result.length());
        } catch (Base64DecoderException e) {
            throw new ValidationException(e.getMessage() + ":" + obfuscated);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Invalid environment", e2);
        } catch (BadPaddingException e3) {
            throw new ValidationException(e3.getMessage() + ":" + obfuscated);
        } catch (IllegalBlockSizeException e4) {
            throw new ValidationException(e4.getMessage() + ":" + obfuscated);
        }
    }
}