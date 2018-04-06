package core;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Trie extends VBox{
	//ui elements
	Pane linePane = new Pane();
	double baseX = 0;
	double baseY = 0;
	
	//the actual datastructure
	TrieNode root = new TrieNode();
	
	//other things
	boolean animating = false;
	
	public Trie(){
		getChildren().add(Main.wizard);
		super.getChildren().add(root);
		super.getChildren().add(linePane);
	}
	
	public void insert(String s){
		TrieNode tempNode = root;
		s = s.toLowerCase();
		
		if(tempNode == null){
			System.out.println("CRITICAL SYSTEMS FAILURE: the root of the trie is null. You broke it.");
			System.exit(-1);
		}
		
		for(int i = 0; i < s.length(); i++){
			if(tempNode.children[s.charAt(i)-'a'] == null)
				tempNode.addChild(s.charAt(i));
			tempNode = tempNode.children[s.charAt(i)-'a'];
		}
		
		tempNode.increment();
	}
	
	public boolean insertAnimation(String sInput){
		final String s = sInput.toLowerCase();
		
		if(!animating){
			animating = true;
			
			new AnimationTimer(){
				int activationCount = -1;
				long lastActivation = -Constants.INSERT_ANIMATION_GLOW_PAUSE_TIME - 10;
				TrieNode wizardNode = root;
				ArrayList<TrieNode> wizardTrail = new ArrayList<TrieNode>();
				//Main.wizard = new ImageView(new Image("img/Wizard.png"));
				
				@Override
				public void handle(long now) {
					if(wizardNode == null){
						System.out.println("CRITICAL SYSTEMS FAILURE: the root of the trie is null. You broke it.");
						System.exit(-1);
					}
					
					if(now/1000000 - lastActivation >=  Constants.INSERT_ANIMATION_GLOW_PAUSE_TIME){
						lastActivation = now/1000000;
							
						if(activationCount == -1){
							getChildren().remove(Main.wizard);
							wizardNode.nodeImagePane.getChildren().add(Main.wizard);
						} else if(activationCount < s.length()){
							if(s.charAt(activationCount) >= 'a' && s.charAt(activationCount) <= 'z'){
								boolean createdNewNode = false;
								wizardNode.nodeImagePane.getChildren().remove(Main.wizard);
								
								if(wizardNode.children[s.charAt(activationCount)-'a'] == null){
									wizardNode.addChild(s.charAt(activationCount));
									createdNewNode = true;
								}
								wizardNode = wizardNode.children[s.charAt(activationCount)-'a'];
								
								if(createdNewNode){
									Main.wizard.setImage(new Image("img/Wizard.gif"));
									wizardNode.nodeImage.setEffect(new Bloom());
									wizardNode.nodeImagePane.getChildren().add(Main.wizard);
								} else {
									Main.wizard.setImage(new Image("img/Wizard.png"));
									wizardNode.nodeImage.setEffect(new Glow());
									wizardNode.nodeImagePane.getChildren().add(Main.wizard);
								}
								wizardTrail.add(wizardNode);
							}
						} else if (activationCount == s.length()) {
							wizardNode.increment();
							for(TrieNode n : wizardTrail)
								n.nodeImage.setEffect(new Bloom());
							wizardNode.nodeImagePane.getChildren().remove(Main.wizard);
							getChildren().add(0, Main.wizard);
							Main.wizard.setImage(new Image("img/Wizard.png"));
						} else if(activationCount == s.length() + 1){
							for(TrieNode n : wizardTrail)
								n.nodeImage.setEffect(null);
						} else if(activationCount == s.length() + 2){
							animating = false;
							this.stop();
						}
						
						activationCount++;
					}
				}
			}.start();
		}
		
		return !animating;
	}
}
