package core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TrieNode extends HBox{
	static final BorderStroke borderStrokeLeft = new BorderStroke(Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR,
															  BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
															  CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY
															 );
	static final BorderStroke borderStrokeRight = new BorderStroke(Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR,
															  BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
															  CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY
															 );
	static final BorderStroke borderStrokeFull = new BorderStroke(Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR, Constants.NODE_BORDER_COLOR,
															  BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
															  CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY
															 );
	
	//UI fields
	VBox mainPane = new VBox();
	
	StackPane nodeImagePane = new StackPane();
	Ellipse nodeImage = new Ellipse(0, 0, Constants.NODE_SIZE, Constants.NODE_SIZE);
	HBox childrenPane = new HBox();
	Text countText = new Text("0");
	
	//traditional trie fields
	TrieNode[] children = new TrieNode[26];
	int numChildren = 0;
	private int count = 0;
	
	public TrieNode(){
		super.setAlignment(Pos.TOP_CENTER);
		mainPane.setAlignment(Pos.TOP_CENTER);
		//nodeImagePane.setAlignment(Pos.CENTER);
		
		nodeImage.setFill(Constants.NODE_COLOR);
		nodeImagePane.getChildren().add(nodeImage);
		nodeImagePane.getChildren().add(countText);
		
		super.getChildren().add(createSpacer());
		mainPane.getChildren().add(nodeImagePane);
		mainPane.getChildren().add(createStaticSizeSpacer(5, 10));
		mainPane.getChildren().add(childrenPane);
		super.getChildren().add(mainPane);
		super.getChildren().add(createSpacer());
		
		setBorder(new Border(borderStrokeFull));
	}
	
	public void addChild(char c){
		TrieNode newNode = new TrieNode();
		children[c-'a'] = newNode;
		
		int count = 0;
		for(int i = 0; i < c-'a'; i++)
			if(children[i] != null)
				count++;
		
		newNode.mainPane.getChildren().add(0, new Text(Character.toUpperCase(c)+""));
		
		childrenPane.getChildren().add(count, newNode);
		if(numChildren > 0) {
			childrenPane.getChildren().add(count, createStaticSizeSpacer(20, 5));
			childrenPane.getChildren().add(count+2, createStaticSizeSpacer(20, 5));	
		}
		
		numChildren++;
	}
	
	public int getCount(){
		return count;
	}
	public void increment(){
		count++;
		countText.setText(""+count);
	}
	public void decrement(){
		count--;
		countText.setText(""+count);
	}
	
	/**
	 * Below function from https://stackoverflow.com/questions/40883858/how-to-evenly-distribute-elements-of-a-javafx-vbox
	 * <0>
	 * Written by Nicolas Filotto
	 * @return
	 */
	private Node createSpacer() {
		final Region spacer = new Region();
		// Make it always grow or shrink according to the available space
		VBox.setVgrow(spacer, Priority.ALWAYS);
		return spacer;
	}
	
	/**
	 * this one was me
	 * @param width
	 * @param height
	 * @return
	 */
	private Node createStaticSizeSpacer(int width, int height){
		Rectangle spacer = new Rectangle(0, 0, width, height);
		spacer.setFill(Color.TRANSPARENT);
		return spacer;
	}
}
