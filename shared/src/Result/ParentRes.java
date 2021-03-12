package Result;
/**
 *  Error Result JSON object
 */
public class ParentRes {
    /**
     *  API URL Success boolean
     */
    private boolean success;
    /**
     * Error or success message, depending on success boolean
     */
    private String message;

    ParentRes(boolean isSuccess, String message) {
        this.success = isSuccess;
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
