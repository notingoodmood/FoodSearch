package Entity;

public class Data{
    String Name,EnglishName,Class,Col,Protein,Fat,Carbon,DanGuChun,VA,VB1,VB2,VB6,VB12,VC,VD,VE,Na,K,Ca;
    public Data(String Name, String English_Name, String Class, String Col, String Protein, String Fat, String Carbon, String DanGuChun,
                String VA, String VB1, String VB2, String VB6, String VB12, String VC, String VD, String VE, String Na, String K, String Ca){
        this.Name=Name;
        this.EnglishName=English_Name;
        this.Class=Class;
        this.Col=Col;
        this.Protein=Protein;
        this.Fat=Fat;
        this.Carbon=Carbon;
        this.DanGuChun=DanGuChun;
        this.VA=VA;
        this.VB1=VB1;
        this.VB2=VB2;
        this.VB6=VB6;
        this.VB12=VB12;
        this.VC=VC;
        this.VD=VD;
        this.VE=VE;
        this.Na=Na;
        this.K=K;
        this.Ca=Ca;
    }
    public String returnInsertSQL(){
        return "insert into foods_detail(Name,EnglishName,Class,Col,Protein,Fat,Carbon,DanGuChun,VA,VB1,VB2,VB6,VB12," +
                "VC,VD,VE,Na,K,Ca) values(\"" +this.Name+
                "\",\""+this.EnglishName+
                "\",\"" +this.Class+
                "\",\"" +this.Col+
                "\",\"" +this.Protein+
                "\",\"" +this.Fat+
                "\",\"" +this.Carbon+
                "\",\"" +this.DanGuChun+
                "\",\""+this.VA+
                "\",\"" +this.VB1+
                "\",\"" +this.VB2+
                "\",\"" +this.VB6+
                "\",\"" +this.VB12+
                "\",\"" +this.VC+
                "\",\"" +this.VD+
                "\",\"" +this.VE+
                "\",\"" +this.Na+
                "\",\"" +this.K+
                "\",\"" +this.Ca+
                "\");";
    }
}
