package pocketPal.common.model.dto;

public class PalDexDTO {
    private int palNo;
    private String palName;
    private String palType;
    private String palGrade;
    private String discoveryTime;
    private String catchCount;

    public PalDexDTO() {
    }

    public PalDexDTO(int palNo, String palName, String palType, String palGrade, String discoveryTime, String catchCount) {
        this.palNo = palNo;
        this.palName = palName;
        this.palType = palType;
        this.palGrade = palGrade;
        this.discoveryTime = discoveryTime;
        this.catchCount = catchCount;
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

    public String getDiscoveryTime() {
        return discoveryTime;
    }

    public void setDiscoveryTime(String discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public String getCatchCount() {
        return catchCount;
    }

    public void setCatchCount(String catchCount) {
        this.catchCount = catchCount;
    }

    @Override
    public String toString() {
        return "도감 번호 : No." + palNo +
                "\n이름 : " + palName +
                "\n속성 : " + palType +
                "\n등급 : " + palGrade +
                "\n잡은 시간 : " + discoveryTime +
                "\n잡은 횟수 : " + catchCount;

    }
}
