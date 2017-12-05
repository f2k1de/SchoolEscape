package mkg.schoolescape;

/**
 * Created by freddy on 13.10.17.
 */

public class Feld {
//    String typ;
    Block block;
    public void setzeObjekt(Block pTyp) {
        block = pTyp;
        //f.blockeKoordinaten(this);

    }
    
    public Block holeObjekt() {
        return block;
    }
    
    public String holeTyp() {
     return block.holeTyp();   
    }
}
