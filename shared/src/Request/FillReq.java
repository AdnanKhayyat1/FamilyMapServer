package Request;



/**
 *  Fill Request JSON object
 */
public class FillReq {
    public String username;
    public int generation;
    public FillReq(String u, int gens){
        this.username = u;
        this.generation = gens;
    }
}
