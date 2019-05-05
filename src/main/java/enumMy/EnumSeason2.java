package enumMy;

public enum EnumSeason2{
    // 枚举类的实例需要在枚举类的第一行列出
    // 构造器
    SPRING("春天", "春风又绿江南岸"){
        @Override
        public String toString() {
            return "SPRING" + "{" +
                    "SEASON_NAME='" + getSEASON_NAME() + '\'' +
                    ", SEASON_DESC='" + getSEASON_DESC() + '\'' +
                    '}';
        }
    },
    SUMMER("夏天", "映日荷花别样红"){
        @Override
        public String toString() {
            return "SUMMER" + "{" +
                    "SEASON_NAME='" + getSEASON_NAME() + '\'' +
                    ", SEASON_DESC='" + getSEASON_DESC() + '\'' +
                    '}';
        }
    },
    AUTUMN("秋天", "秋水共长天一色"){
        @Override
        public String toString() {
            return "AUTUMN" + "{" +
                    "SEASON_NAME='" + getSEASON_NAME() + '\'' +
                    ", SEASON_DESC='" + getSEASON_DESC() + '\'' +
                    '}';
        }
    },
    WINTER("冬天", "寒窗西岭千秋雪"){
        @Override
        public String toString() {
            return "WINTER" + "{" +
                    "SEASON_NAME='" + getSEASON_NAME() + '\'' +
                    ", SEASON_DESC='" + getSEASON_DESC() + '\'' +
                    '}';
        }
    };

    // 表示属性不允许被修改
    private final String SEASON_NAME;
    private final String SEASON_DESC;

    private EnumSeason2(String seasonName, String seasonDesc) {
        this.SEASON_NAME = seasonName;
        this.SEASON_DESC = seasonDesc;
    }

    public String getSEASON_NAME() {
        return SEASON_NAME;
    }

    public String getSEASON_DESC() {
        return SEASON_DESC;
    }

    // 在这里体现switch的用法
    @Override
    public String toString() {
        String header = null;
        switch (this) {
            case SPRING:
                header = "SPRING";
                break;
            case SUMMER:
                header = "SUMMER";
                break;
            case AUTUMN:
                header = "AUTUMN";
                break;
            case WINTER:
                header = "WINTER";
                break;
        }
        return header + "{" +
                "SEASON_NAME='" + SEASON_NAME + '\'' +
                ", SEASON_DESC='" + SEASON_DESC + '\'' +
                '}';
    }
}
