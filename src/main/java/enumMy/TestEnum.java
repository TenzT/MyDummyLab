package enumMy;


import org.junit.Test;

public class TestEnum {
    @Test
    public void testEnum() {
        System.out.println(EnumSeason.SPRING);

        // 1. 遍历枚举类
        for (EnumSeason season : EnumSeason.values()) {
            System.out.println(season);
        }
        // 1. 遍历枚举类
        for (EnumSeason2 season : EnumSeason2.values()) {
            System.out.println(season);
        }

        // 2. 把一个字符串转为对应的枚举类对象
        String input = "SPRING";
        EnumSeason s = Enum.valueOf(EnumSeason.class, input);
        System.out.println(s);
    }

}
