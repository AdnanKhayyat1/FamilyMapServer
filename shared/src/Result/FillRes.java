package Result;
/**
 *  Fill Result JSON object
 */
public class FillRes extends ParentRes {
    public FillRes(String msg, boolean success) {
        super(success, msg);
    }

}
