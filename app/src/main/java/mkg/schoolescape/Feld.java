package mkg.schoolescape;
public class Feld {
    Block enthaltenerBlock;

    public void setzeEnthaltenenBlock(Block pBlock) {     //setzt ein im Parameter überliefertes Objekt der Klasse Block in die Eigenschaft 'enthaltenerblock'
        enthaltenerBlock = pBlock;
    }

    public Block holeObjekt() {
        return enthaltenerBlock;
    }

    public String holeTyp() {                   //holt den Typ des Objektes in der Eigenschaft 'enthaltenerblock'
        return enthaltenerBlock.holeTyp();
    }
}