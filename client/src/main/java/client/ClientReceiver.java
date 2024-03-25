package client;

import common.abstractions.AbstractReceiver;
import common.abstractions.IInputManager;
import common.abstractions.IOutputManager;
import common.model.entities.Movie;

public class ClientReceiver extends AbstractReceiver {

    public ClientReceiver(IInputManager inp, IOutputManager out) {
        super(inp, out);
    }

    @Override
    public void add(Object[] args) {
    }

    @Override
    public void clear(Object[] args) {
        super.clear(args);
    }

    @Override
    public void show(Object[] args) {
        super.show(args);
    }

    @Override
    public void exit(Object[] args) {
        super.exit(args);
    }

    @Override
    public void info(Object[] args) {
        super.info(args);
    }

    @Override
    public void executeScript(Object[] args) {
        super.executeScript(args);
    }

    @Override
    public void filterByGoldenPalmCount(Object[] args) {
        super.filterByGoldenPalmCount(args);
    }

    @Override
    public void help(Object[] args) {
        super.help(args);
    }

    @Override
    public void history(Object[] args) {
        super.history(args);
    }

    @Override
    public void minByCoordinates(Object[] args) {
        super.minByCoordinates(args);
    }

    @Override
    public void removeAllByGoldenPalmCount(Object[] args) {
        super.removeAllByGoldenPalmCount(args);
    }

    @Override
    public void removeById(Object[] args) {
        super.removeById(args);
    }

    @Override
    public void removeFirst(Object[] args) {
        super.removeFirst(args);
    }

    @Override
    public void removeLower(Object[] args) {
        super.removeLower(args);
    }

    @Override
    public void update(Object[] args) {
        super.update(args);
    }
}
