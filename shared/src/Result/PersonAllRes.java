package Result;

import Models.PersonModel;

import java.util.List;

/**
 * Persons getter JSON response object
 */
public class PersonAllRes extends ParentRes {
    /**
     * List of all persons associated with user
     */
    private List<PersonModel> data;

    /**
     *
     * @param data List of all persons associated with user
     */
    public PersonAllRes(List<PersonModel> data, boolean isSuccess, String msg) {
        super(isSuccess, msg);

        this.data = data;
    }

    public List<PersonModel>  getData() {
        return data;
    }

    public void setData(List<PersonModel> data) {
        this.data = data;
    }
}
