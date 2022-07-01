package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("/map.txt");
    Canvas canvas = new Canvas(
            40 * Tiles.TILE_WIDTH,
            25 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    GameDatabaseManager dbManager;

    int viewWidth = 40;

    int viewHeight = 25;

    int shiftX = 0;
    int shiftY = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Inventory: "), 0, 2);
        ui.add(inventoryLabel, 1, 2);

        Button button = new Button();
        button.setText("Pick up");
        button.setFocusTraversable(false);
        ui.add(button, 0, 3);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = map.getPlayer().getX();
                int y = map.getPlayer().getY();
                Cell cell = map.getCell(x, y);
                if (cell.getItem() != null) {
                    map.getPlayer().addToInventory(cell.getItem());
                    cell.setItem(null);
                    refresh(shiftX, shiftY);
                }
            }
        });

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        scene.getRoot().setStyle("-fx-font-family: 'sans-serif'");
        primaryStage.setScene(scene);
        refresh(shiftX, shiftY);
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        int currentX = map.getPlayer().getCell().getX();
        int currentY = map.getPlayer().getCell().getY();
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                map.getAggressiveMonster().move();
                map.getRandomMonster().move();
                if (map.getHeight() > viewHeight && (currentX-map.getPlayer().getCell().getX() != 0 ||  currentY-map.getPlayer().getCell().getY() != 0 ) &&
                        map.getHeight()-map.getPlayer().getCell().getY() >= viewHeight-1 && map.getPlayer().getCell().getY() >= 1) {
                    if (shiftY > 0) {
                        shiftY -= 1;
                    }
                }
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                map.getAggressiveMonster().move();
                map.getRandomMonster().move();
                if (map.getHeight() > viewHeight && (currentX-map.getPlayer().getCell().getX() != 0 ||  currentY -map.getPlayer().getCell().getY() != 0 ) &&
                        map.getPlayer().getCell().getY() >= viewHeight-2 && map.getHeight()-map.getPlayer().getCell().getY() >= 3) {
                    if (shiftY < map.getHeight()-viewHeight) {
                        shiftY += 1;
                    }
                }
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                map.getAggressiveMonster().move();
                map.getRandomMonster().move();
                if (map.getWidth() > viewWidth && (currentX-map.getPlayer().getCell().getX() != 0 ||  currentY-map.getPlayer().getCell().getY() != 0 ) &&
                        map.getWidth()-map.getPlayer().getCell().getX() >= viewWidth-1 && map.getPlayer().getCell().getX() >=1) {
                    if (shiftX > 0) {
                        shiftX -= 1;
                    }
                }
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                map.getAggressiveMonster().move();
                map.getRandomMonster().move();
                if (map.getWidth() > viewWidth && (currentX-map.getPlayer().getCell().getX() != 0 ||  currentY-map.getPlayer().getCell().getY() != 0 ) &&
                        map.getWidth()-map.getPlayer().getCell().getX() >= 3 && map.getPlayer().getCell().getX() >= viewWidth-2) {
                    if (shiftX < map.getWidth()-viewWidth) {
                        shiftX += 1;
                    }
                }
                break;
            case Q:
                exit();
        }
        checkGameOver();
        checkNewLevel();
        refresh(shiftX, shiftY);
    }



    private void refresh(int shiftX, int shiftY) {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = shiftX; x < viewWidth+shiftX; x++) {
            for (int y = shiftY; y < viewHeight+shiftY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null || cell.getItem() != null) {
                    if (cell.getItem() != null) {
                        Tiles.drawTile(context, cell.getItem(), x-shiftX, y-shiftY);
                    }
                    if (cell.getActor() != null) {
                        Tiles.drawTile(context, cell.getActor(), x-shiftX, y-shiftY);
                    }
                } else {
                    Tiles.drawTile(context, cell, x-shiftX, y-shiftY);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        inventoryLabel.setText("" + map.getPlayer().toString());
    }


    public void checkGameOver() {
        if (map.getPlayer().getHealth() <= 0) {
            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Dialog");
            dialog.setContentText("Game Over");
            ButtonType type = new ButtonType("Play again", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
            map = MapLoader.loadMap("/map.txt");
            shiftX = 0;
            shiftY = 0;
        }
    }

    public void checkNewLevel() {
        if (map.getPlayer().getCell().getType().equals(CellType.OPEN_DOOR)) {
            List<Item> oldInventory = new ArrayList<>();
            if (!map.getPlayer().getInventory().isEmpty()) {
                List<Item> finalOldInventory = oldInventory;
                map.getPlayer().getInventory().forEach(i -> finalOldInventory.add(i));
            }
            map = MapLoader.loadMap("/map2.txt");
            if (!oldInventory.isEmpty()) {
                Class Sword = com.codecool.dungeoncrawl.logic.items.Sword.class;
                oldInventory = oldInventory.stream().filter(i -> Sword.isInstance(i)).collect(Collectors.toList());
            }
            oldInventory.forEach(i -> map.getPlayer().getInventory().add(i));
            shiftX = 0;
            shiftY = 0;
        }
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }


}
