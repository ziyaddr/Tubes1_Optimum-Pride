package za.co.entelect.challenge.command;

public class BoostCommand implements Command {

    @Override
    public String render() {
        return String.format("USE_BOOST");
    }
}
