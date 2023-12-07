package net.minecraftforge.fml.relauncher;

import java.security.Permission;

public class FMLSecurityManager extends SecurityManager {

    static {
        System.out.println("[MEME]: hello, im a dummy class for bypassing idiotic forge check!");
    }

    public void checkPermission(Permission perm) {
    }
}
