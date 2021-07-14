package xyz.esion.manage;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author qiao shengda
 * @since 2021/6/7
 */
public class StringTest {

    @Test
    public void splite(){
        String test = "f3e4c581-37d6-41ad-9d52-4dd4c61bc2da.jpg";
        System.out.println(Arrays.toString(test.split("\\.")));
    }

}
