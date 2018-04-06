package core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
	static ImageView wizard = new ImageView(new Image("img/Wizard.png"));
	Pane root = new Pane();
    Scene mainScene = new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, Constants.BACKGROUND);
	
    TrieNode rootNode = new TrieNode();
    Trie trie = new Trie();
    
	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.toFront();
        
        primaryStage.setTitle("Trie Wizard Tournament");
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
        //This allows the closing of the primaryStage to end the program
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent t) {
                System.exit(0);
            }
        });
        
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
        	@Override
            public void handle(KeyEvent t) {
        		System.out.println(t.getCode());
        		switch (t.getCode()){
        			case UP:
        				Input.up = true;
        				break;
        			case DOWN:
        				Input.down = true;
        				break;
        			case LEFT:
        				Input.left = true;
        				break;
        			case RIGHT:
        				Input.right = true;
        				break;
        			default:
        				break;
        		}
        	}
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
        	@Override
            public void handle(KeyEvent t) {
        		System.out.println(t.getCode());
        		switch (t.getCode()){
        			case UP:
        				Input.up = false;
        				break;
        			case DOWN:
        				Input.down = false;
        				break;
        			case LEFT:
        				Input.left = false;
        				break;
        			case RIGHT:
        				Input.right = false;
        				break;
        			default:
        				break;
        		}
        	}
        });
        
        
        (new AnimationTimer(){

			@Override
			public void handle(long now) {
					if(Input.up)
        				trie.setLayoutY(trie.getLayoutY()+Constants.SCROLL_SPEED);
        			if(Input.down)
        				trie.setLayoutY(trie.getLayoutY()-Constants.SCROLL_SPEED);
        			if(Input.left)
        				trie.setLayoutX(trie.getLayoutX()+Constants.SCROLL_SPEED);
        			if(Input.right)
        				trie.setLayoutX(trie.getLayoutX()-Constants.SCROLL_SPEED);
			}
        	
        }).start();
        
        root.getChildren().add((Pane)trie);
        Scanner scan1 = new Scanner(new File("corpus.txt"));
        while(scan1.hasNext())
        	trie.insert(scan1.next());
        scan1.close();
        
        Scanner scan2 = new Scanner(new File("corpusAnimate.txt"));
        new AnimationTimer(){
				@Override
				public void handle(long now) {
					if(!trie.animating){
						trie.insertAnimation(scan2.next());
					}
					
					if(!scan2.hasNext()){
						scan2.close();
						
						SnapshotParameters params = new SnapshotParameters();
						params.setFill(Constants.BACKGROUND);
						Main.saveToFile(trie.snapshot(params, null), "finalTrie.png");
						
						this.stop();
					}
				}
				
        }.start();
        
//        
//        Node temp = trie.root.nodeImage;
//        System.out.println(temp.localToScreen(temp.getBoundsInLocal()));
//        temp = trie.root.children[7].nodeImage;
//        System.out.println(temp.localToScreen(temp.getBoundsInLocal()));
//        
//        Node parent = trie.root.nodeImage;
//        Bounds b = parent.getBoundsInLocal();
//        while(parent != root){
//        	b = parent.localToParent(b);
//        	parent = parent.getParent();
//        }
//        System.out.println(b);
//        
//        parent = trie.root.children[7].nodeImage;
//        b = parent.getBoundsInLocal();
//        while(parent != root){
//        	b = parent.localToParent(b);
//        	parent = parent.getParent();
//        }
//        System.out.println(b);
        
//        root.getChildren().add(rootNode);
//        rootNode.addChild('a');
//        rootNode.addChild('b');
//        rootNode.addChild('u');
//        
//        rootNode.children[0].addChild('c');
//        
//        rootNode.children['u'-'a'].addChild('e');
//        rootNode.children['u'-'a'].addChild('f');
//        rootNode.children['u'-'a'].addChild('a');
	}
	
	public static void saveToFile(Image image, String filePath) {
	    File outputFile = new File(filePath);
	    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
	    
	    try {
	    	ImageIO.write(bImage, "png", outputFile);
	    } catch (IOException e) {
	    	throw new RuntimeException(e);
	    }
	}

}
