package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class TetrisFrame extends JFrame implements Observer {

    public enum Screen {
        MENU, GAME, PAUSE, HELP, SETTINGS, END;
    }

    private static TetrisFrame instance;
    protected JPanel panelShower;
    protected CardLayout panelSelection;

    private TetrisFrame(){
        panelShower = new JPanel();
        panelSelection = new CardLayout();
        panelShower.setLayout(panelSelection);
    }

    public static TetrisFrame getInstance(){
        if(instance == null) instance = new TetrisFrame();
        return instance;
    }

    public void switchScreen(Screen screen) {
        panelSelection.show(panelShower, screen.name());
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (arg) {
            case Game.GameEvent event -> {

                switch (event){
                    case ROW_COMPLETED -> {
                        //clearRow();
                    }

                    case END -> {
                        switchScreen(Screen.END);
                    }
                }

            }

            default -> {}

        }

    }
}
