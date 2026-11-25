package Controller;

import View.MenuView;
import View.PetView;
import View.UsuarioView;

public class MenuController {

    private final MenuView view;

    public MenuController(MenuView view) {
        this.view = view;

        view.getBtnUsuarios().addActionListener(e -> {
            UsuarioView usuarioView = new UsuarioView();
            new UsuarioController(usuarioView);
            usuarioView.setVisible(true);
        });

        view.getBtnPets().addActionListener(e -> {
            PetView petView = new PetView();
            new PetController(petView);
            petView.setVisible(true);
        });
    }
}
