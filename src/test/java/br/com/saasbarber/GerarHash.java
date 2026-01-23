package br.com.saasbarber;

import org.mindrot.jbcrypt.BCrypt;

public class GerarHash {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
        
    }

    
}
