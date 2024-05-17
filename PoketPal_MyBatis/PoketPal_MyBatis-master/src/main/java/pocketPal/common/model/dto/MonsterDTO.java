package pocketPal.common.model.dto;

public class MonsterDTO {
    private int palNo;
    private String palName;
    private String palType;
    private String palGrade;

    public MonsterDTO() {
    }

    public MonsterDTO(int palNo, String palName, String palType, String palGrade) {
        this.palNo = palNo;
        this.palName = palName;
        this.palType = palType;
        this.palGrade = palGrade;
    }

    public int getPalNo() {
        return palNo;
    }

    public void setPalNo(int palNo) {
        this.palNo = palNo;
    }

    public String getPalName() {
        return palName;
    }

    public void setPalName(String palName) {
        this.palName = palName;
    }

    public String getPalType() {
        return palType;
    }

    public void setPalType(String palType) {
        this.palType = palType;
    }

    public String getPalGrade() {
        return palGrade;
    }

    public void setPalGrade(String palGrade) {
        this.palGrade = palGrade;
    }

    @Override
    public String toString() {
        return "도감 번호 : No." + palNo +
        "\n이름 : " + palName +
        "\n속성 : " + palType +
        "\n등급 : " + palGrade;
    }
}
