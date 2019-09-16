package tauru.springframework.WebApp.utilitare;

public class OroErrors extends Throwable{

    private String error;

    public OroErrors(){

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

}
