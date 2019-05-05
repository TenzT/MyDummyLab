package enumMy;

/**
 * 自己编写类来模拟枚举类
 * 枚举类即指类的对象是有限且固定的
 */
public class Season {
    // 属性是固定的，说明
    private final String SEASON_NAME;
    private final String SEASON_DESC;

    // 在类的外部不能创建
    private Season(String SEASON_NAME, String SEASON_DESC) {
        this.SEASON_NAME = SEASON_NAME;
        this.SEASON_DESC = SEASON_DESC;
    }

    // 在类的内部创建4个对象，且这4个不能被修改
    public final static Season SPRING = new Season("春天", "春风又绿江南岸");
    public final static Season SUMMER = new Season("夏天", "映日荷花别样红");
    public final static Season AUTUMN = new Season("秋天", "秋水共长天一色");
    public final static Season WINTER = new Season("冬天", "寒窗西岭千秋雪");

    public String getSEASON_NAME() {
        return SEASON_NAME;
    }

    public String getSEASON_DESC() {
        return SEASON_DESC;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{" +
                "SEASON_NAME='" + SEASON_NAME + '\'' +
                ", SEASON_DESC='" + SEASON_DESC + '\'' +
                '}';
    }
}
