package structure.decorator;

public class MorriganSuccubus extends MorriganChanger {

    public MorriganSuccubus(Morrigan morrigan) {
        super(morrigan);
    }

    @Override
    public void display() {
        setChanger();
        super.display();
    }

    public void setChanger(){
        ((MorriganOrigin)morrigan).setImage("变身成女妖");
    }
}
