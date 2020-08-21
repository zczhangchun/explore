package structure.decorator;

public class MorriganGirl extends MorriganChanger {
    public MorriganGirl(Morrigan morrigan) {
        super(morrigan);
    }

    @Override
    public void display() {
        setChanger();
        super.display();
    }

    public void setChanger(){
        ((MorriganOrigin)morrigan).setImage("变身成女孩");
    }
}
