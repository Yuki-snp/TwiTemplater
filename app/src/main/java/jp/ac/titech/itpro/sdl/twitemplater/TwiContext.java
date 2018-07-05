package jp.ac.titech.itpro.sdl.twitemplater;

public class TwiContext {
    long id;
    String Answer;
    String Edit;
    boolean isConnect;
    boolean Selected;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        this.Answer = answer;
    }

    public String getEdit() {
        return Edit;
    }

    public void setEdit(String Edit) {
        this.Edit = Edit;
    }

    public boolean getisConnect() {
        return isConnect;
    }

    public void setisConnect(boolean isConnect) {
        this.isConnect = isConnect;
    }

    public boolean getSelected() {
        return Selected;
    }

    public void setSelected(boolean Selected) {
        this.Selected = Selected;
    }
}
