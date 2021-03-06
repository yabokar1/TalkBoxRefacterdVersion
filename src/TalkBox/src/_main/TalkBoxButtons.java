package _main;

import java.io.FileNotFoundException;
import javax.sound.sampled.LineUnavailableException;
import TalkBoxSim.TalkBoxSim;
import audio_players.Sound;
import io.TalkBoxLogger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TalkBoxButtons {
	
	private Sound sound;
	private String filename;
	private String newname;
	public AudioSampleList list;
	//public TextArea Area;
	
	public TalkBoxButtons() {
		sound = new Sound();
		
	}
	
	
	public Button addLaunchButton(ButtonPanel button,ProfileList profile) {
		TalkBoxConfig  config = new TalkBoxConfig();
		Button Launch = new Button("Launch Button");
		Launch.setMinSize(Names.LAUNCHBUTTON_WIDTH, Names.LAUNCHBUTTON_HEIGHT);
		Launch.setOnAction(e -> {
			try {
		    config.rename = profile.RenameSet;
			config.row = profile.getRow();
		    config.images = profile.ImageSet;
			config.setNumberofAudioButtons(button);
			config.Profiles = profile.getSets();
			config.AudioName = profile.getAudioFileNames();
			config.path = "/Audio/";
			config.NumOfAudioSets = profile.numofAudioSets;
				Serializer.Save(config, "TalkBoxData/");
				TalkBoxSim Gui = new TalkBoxSim();
				Gui.start(new Stage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		return Launch;
		
	}
	
	public TextField enterProfileTextField(ProfileList profile,ButtonPanel panel) {
		
		TextField enterProfile = new TextField("Enter Profile");
		enterProfile.setOnMouseClicked(e -> enterProfile.clear());
		enterProfile.setOnAction(e -> {
			TalkBoxLogger.logTextFieldEvent(e);
		profile.setProfileTitle(enterProfile.getText());
			panel.getChildren().clear();
			panel.resetColumn();
			panel.resetRow();

		 });
		return enterProfile;
	
	}
	
	
	public TextField Rename(ButtonPanel p) {
		TextField text = new TextField("Enter New Name");
		text.setMaxSize(200, 50);
		text.setOnMouseClicked(e -> text.clear());
		text.setOnAction(e -> {
			TalkBoxLogger.logTextFieldEvent(e);
			this.newname = text.getText();
			p.setnewName(this.newname)
			;});
		
		return text;
	}
	

    public Label headerLabel() {
    	Label talkBoxLabel = new Label(Names.TITLE);
    	talkBoxLabel.setStyle("-fx-font-family: TRON; -fx-font-size: 20;");
    	return talkBoxLabel;
    }
    

    public VBox FileandStart() {
    	VBox v = new VBox();
    	TextField filename = new TextField("Enter Filename");
    	filename.setOnMouseClicked(e -> filename.clear());
    	filename.setOnAction(e -> {
    		TalkBoxLogger.logTextFieldEvent(e);
    		this.filename = filename.getText();
    	});
    	Button Stop = new Button("Stop");
    	Stop.setOnAction(e ->{sound.stop();this.list.refresh();});
    	Stop.setMinSize(Names.STARTBUTTON_WIDTH, Names.STARTBUTTON_HEIGHT);
    	v.getChildren().addAll(Stop,filename);
    	return v;
    }

    
    
    public HBox addRecordArea(AudioSampleList list) throws FileNotFoundException {

    	HBox RecordingArea = new HBox();
    	Button Record = new Button();
    	Record.setOnAction(e ->{
    		TalkBoxLogger.logButtonPressEvent(e);
    		try {
				sound.soundFormat();
				sound.start(this.filename);
			} catch (LineUnavailableException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	Image image = new Image("/Image/recorderImage.png");
    	ImageView imageView = new ImageView();
    	imageView.setImage(image);
    	imageView.setFitHeight(90);
    	imageView.setFitWidth( 90);
    	Record.setMinSize(Names.RECORDBUTTON_HEIGHT, Names.RECORDBUTTON_WIDTH);
    	Record.setGraphic(imageView);
    	RecordingArea.getChildren().addAll(Record,FileandStart());
    	return RecordingArea;
    }
    
	public MenuBar Menu(ProfileList list){
		TopMenu menu = new TopMenu();
		menu.getImport().setOnAction(e ->{
			TalkBoxLogger.logMenuPressEvent(e);
			menu.ImportAudioListener(menu.getImport());
			this.list.refresh();
		});
		
		return menu.getMenu();
	}
	
	

}
