package tauru.springframework.WebApp.utilitare;

public class OroErrors {

    private String error;

    private Boolean hasError;

    public OroErrors() {

    }

    public OroErrors(String error) {

        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }
}
