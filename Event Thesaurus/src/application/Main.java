package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;

import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;

public class Main extends Application {

	static Stage MainWindow;
	static Scene Main,AddEvent,SearchEvent,ShowAll,UpdateEvent,DeleteEvent;
	int HashValue;
	static String EveFile = "Event.txt";
	static String TempFile = "TempEvent.txt";
	int confirmation = 0;
	String Temp;
	String Line;
	String T1,T2,T3;
	ArrayList<FsMiniProjectController>StreamLoader = new ArrayList<FsMiniProjectController>();
	private final static TableView<FsMiniProjectController> tableView = new TableView<>();
	private final static ObservableList<FsMiniProjectController> dataList = FXCollections.observableArrayList();
	static TableColumn columnF1=new TableColumn("Event Name");
	static TableColumn columnF2=new TableColumn("Event Type");
	static TableColumn columnF3=new TableColumn("Event Date(fr)");
	static TableColumn columnF4=new TableColumn("Event Time(fr)");
	static TableColumn columnF5=new TableColumn("Event Date(to)");
	static TableColumn columnF6=new TableColumn("Event Time(to)");
	static TableColumn columnF7=new TableColumn("Event Venue");
	static ArrayList<String>HashList=new ArrayList<String>();
	static ArrayList<String>DateList=new ArrayList<String>();
	static ArrayList<String>LocationList=new ArrayList<String>();
	static Label Header=new Label();
	static Label EmptySpace=new Label();
	String FileF;
	static Label msgs=new Label();
	static VBox vBox = new VBox();
	static GridPane BPSHA=new GridPane();
	static Label SuccessMsg=new Label("Successfull");
	static Button RetButton=new Button("Cancel");
	public static void initTable()
	{
		DateList.clear();
		LocationList.clear();
		HashList.clear();

		BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(EveFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("#");

                DateList.add(fields[3].toUpperCase());//+"-"+fields[5].toUpperCase());
                LocationList.add(fields[6].toUpperCase());
                HashList.add(fields[7].toUpperCase());

            }

        } catch (Exception ex) {
            System.out.println("An Error Occured "+ex);
        }

//System.out.println(LocationList);
//System.out.println(HashList);
	}

	public static void Reset()
	{
		msgs.setVisible(false);
		initTable();
	}
	public static void main(String[] args) {

		initTable();

		columnF1.setCellValueFactory(new PropertyValueFactory<>("f1"));
	    columnF2.setCellValueFactory(new PropertyValueFactory<>("f2"));
	    columnF3.setCellValueFactory(new PropertyValueFactory<>("f3"));
	    columnF4.setCellValueFactory(new PropertyValueFactory<>("f5"));
	    columnF5.setCellValueFactory(new PropertyValueFactory<>("f4"));
		columnF6.setCellValueFactory(new PropertyValueFactory<>("f6"));
		columnF7.setCellValueFactory(new PropertyValueFactory<>("f7"));

	    columnF1.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF2.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF3.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF4.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF5.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF6.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
	    columnF7.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));


	    tableView.setPrefSize(900, 450);
		tableView.getColumns().addAll(columnF1, columnF2, columnF3, columnF4, columnF5, columnF6,columnF7);
		msgs.setFont(Font.font("Verdana",FontWeight.BOLD,15));
		msgs.setTextFill(Color.RED);

		Header.setFont(Font.font("Verdana",15));
		Header.setTextFill(Color.BLUE);
		Header.setText("		");
		EmptySpace.setText("		");

	     Button ReTButton=new Button("Return");
	     ReTButton.setTextFill(Color.GREEN);
	     ReTButton.setOnAction(e->{
	    	 	Reset();
	        	MainWindow.setScene(Main);
	        });
	     Button DelButton=new Button("Delete");
	     DelButton.setTextFill(Color.RED);
	     DelButton.setOnAction(e->{
	    	 deleteEvent();
	        });
//			Button ModifyE = new Button("Modify");
//			ModifyE.setTextFill(Color.BLUE);
//			ModifyE.setOnAction(e->{
//				//MainWindow.setScene(UpdateEvent);
//			TableViewer();
//			});


	     ShowAll=new Scene(BPSHA, 800,550 );
	     HBox hbv=new HBox();
	     hbv.setSpacing(30);
	     hbv.getChildren().add(EmptySpace);
	     //hbv.getChildren().add(ModifyE);
	     hbv.getChildren().add(DelButton);
	     hbv.getChildren().add(ReTButton);

	     hbv.getChildren().add(msgs);
	     msgs.setVisible(false);

	     vBox.setSpacing(10);
	     vBox.getChildren().add(Header);
	     vBox.getChildren().add(tableView);
	     vBox.getChildren().add(hbv);


	     BPSHA.getChildren().add(vBox);
		launch(args);

	}

	public int AddtoFile(String Line)
	{
		try
		{
			Writer writer = new BufferedWriter(new OutputStreamWriter(
			        new FileOutputStream(EveFile, true), "UTF-8"));
			HashValue=Line.hashCode();
			writer.write(Line+"#"+HashValue+"\r\n");
			writer.close();
			initTable();
			return 1;
		}
		catch(Exception E)
		{
			return 0;
		}



	}
	@SuppressWarnings("resource")
	public String SearchFile(String Item)
	{

		 tableView.getItems().clear();

		 String[] Items = Item.split(" ");
		 String[] LineItems;
		 boolean Flag;

		try {
			BufferedReader bb=new BufferedReader(new FileReader(EveFile));
			Temp="";
			 tableView.getItems().clear();
			while((Line=bb.readLine())!=null)
			{
				Flag=false;
				T1=Item;
				T1=T1.toUpperCase();
				T2=Line;
				T2=T2.toUpperCase();
				LineItems=Line.split("#");

				for (String TX : LineItems)
				{
					TX=TX.toUpperCase();
					for(String FT:Items)
					{
						FT=FT.toUpperCase();
						if(TX.startsWith(FT))
				{

					String[] fields = Line.split("#");


					FsMiniProjectController record = new FsMiniProjectController(fields[0], fields[1], fields[2],fields[3],fields[4],fields[5],fields[6]);
	                dataList.add(record);

	                Flag=true;

				}
						if(Flag==true)
						{
							break;
						}
				}
					if(Flag==true)
					break;
					}
			}

			Header.setText(" Search Results ");
			tableView.setItems(dataList);
			if(Temp.length()>0)
				return Temp;
			bb.close();
		}

		catch(Exception e)
		{
			System.out.println("Error in File Access -exception Occured \n"+e);
		}

		return "The Item : "+Item+" not Found in the Event List";
	}

	// Show All Events
	public void ShowAllEvents()
	{
		try (Stream<String> stream = Files.lines(Paths.get(EveFile)))
		{
	        stream.forEach(System.out::println);
		}
		catch( Exception E)
		{
			System.out.println("Error : Cannot Open File");

		}
	}

	public static void TableViewer() {
		tableView.getItems().clear();
		tableView.setItems(dataList);
		ScFileReader();
		Header.setText("			");
		MainWindow.setScene(ShowAll);
	}


	@SuppressWarnings("unused")
	private static void ScFileReader() {
	    String FieldDelimiter = "#";
	    dataList.removeAll();
	    BufferedReader br;
	    try {
	        br = new BufferedReader(new FileReader(EveFile));
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] fields = line.split("#");
	            FsMiniProjectController record = new FsMiniProjectController(fields[0], fields[1], fields[2],fields[3],fields[4],
	            		fields[5],fields[6]);
	            dataList.add(record);
	            //record.erase();
	        }
	    } catch (FileNotFoundException ex) {
	    } catch (IOException ex) {
	    }catch(ArrayIndexOutOfBoundsException e){

	    }
	}

	@SuppressWarnings("unused")
	public static int DuplicateChecker(String NE)
	{
		initTable();
		String[] NewEvent=NE.split("#");
		String HS=Integer.toString(NE.hashCode());


		if(HashList.contains(HS))
		{
			SuccessMsg.setText("This Event Already Exists....!");
			SuccessMsg.setVisible(true);
			return 1;
		}
		else
		return 0;
	}
/*	public static void removeLine(String lineContent) throws IOException
	{
	    File file = new File("Event.txt");
	    List<String> out = Files.lines(file.toPath())
	                        .filter(line -> !line.contains(lineContent))
	                        .collect(Collectors.toList());
	    Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}*/
	public static void deleteEvent()
	{
		FsMiniProjectController DelItem = tableView.getSelectionModel().getSelectedItem();
		if(DelItem==null)
		{
			msgs.setText("Select a Event First");
			msgs.setVisible(true);
			return;
		}
		String DelT=DelItem.getF1()+"#"+DelItem.getF2()+"#"+DelItem.getF3()+"#"+DelItem.getF4()+"#"+DelItem.getF5()+"#"+DelItem.getF6()+"#"+DelItem.getF7();
		//System.out.println("Selected Event is : "+DelT);
		File Original = new File(EveFile);
		try{
			 @SuppressWarnings("unused")
			File file = new File("Event.txt");
			    List<String> out = Files.lines(Original.toPath())
			                        .filter(line -> !line.contains(DelT))
			                        .collect(Collectors.toList());
			    Files.write(Original.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		}
		catch(Exception e)
		{
			System.out.println("\n\nAn Error Occured : "+e);
		}
		TableViewer();

	}


	//User Interface
	@Override
	public void start(Stage mainstage) throws Exception {

		MainWindow = mainstage;
		MainWindow.setResizable(false);
		GridPane PB = new GridPane();
		HBox HB = new HBox();
		VBox VB = new VBox();
		HB.setPadding(new Insets(50, 50, 50, 50));
		HB.setSpacing(10);
		HB.setPrefSize(900,300);

		VB.setPadding(new Insets(15, 32, 55, 12));
		VB.setPrefSize(900, 400);
		VB.setSpacing(30);
		initTable();
		//FIRST SCREEN
		Label WelcomeTitle=new Label("EVENT THESAURUS ");
		WelcomeTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD,FontPosture.REGULAR, 30));
		WelcomeTitle.setTextFill(Color.RED);
//main screen add button
		Button AddC=new Button("Add A New Event");
		AddC.setPadding(new Insets(10,10,10,10));
		AddC.setPrefSize(300,30);
		AddC.setOnAction(e->{MainWindow.setScene(AddEvent);
		SuccessMsg.setVisible(false);
		});
//main screen search button
		Button SearchC=new Button("Search for a Event");
		SearchC.setPadding(new Insets(10,10,10,10));
		SearchC.setPrefSize(300,30);
		SearchC.setOnAction(e->{MainWindow.setScene(SearchEvent);
		});

//main screen view all button
		Button ViewAC=new Button("View All Events");
		ViewAC.setPadding(new Insets(10,10,10,10));
		ViewAC.setPrefSize(300,30);
		ViewAC.setOnAction(e->{MainWindow.setScene(ShowAll);
		TableViewer();
		ShowAllEvents();
		});

//main screen update button

		HB.getChildren().addAll(WelcomeTitle);
		HB.setPadding(new Insets(50,70,20,250));
		VB.getChildren().addAll(AddC,SearchC,ViewAC);
		VB.setPadding(new Insets(20,20,20,250));
		PB.add(HB,0,1);
		PB.add(VB,0,2);
		Main=new Scene(PB,800,550);
		//ICON for the Application
		mainstage.getIcons().add(new Image("icon.png"));

	MainWindow.setScene(Main);
	MainWindow.setTitle("File Structure Mini Project");
	MainWindow.show();
/*-------------------------------------------------------------------------------------------------------------------*/
	//create event screen
	GridPane BP1=new GridPane();
	HBox HB1=new HBox();
	HBox HB11=new HBox();
	HBox HB12=new HBox();
	HBox HB121=new HBox();
	HBox HB111=new HBox();
	HBox HB112=new HBox();
	HBox HB113=new HBox();

	 HB1.setPadding(new Insets(25, 25, 15, 12));
	 HB1.setSpacing(20);

	 HB11.setPadding(new Insets(55, 85, 85, 100));
	 HB11.setSpacing(20);
	 HB12.setPadding(new Insets(25, 25, 15, 12));
	 HB12.setSpacing(20);

	 HB121.setPadding(new Insets(25, 25, 15, 102));
	 HB121.setSpacing(20);
	 HB111.setPadding(new Insets(25, 25, 15, 12));
	 HB111.setSpacing(20);

	 HB112.setPadding(new Insets(25, 25, 15, 12));
	 HB112.setSpacing(25);

	 HB113.setPadding(new Insets(25, 25, 15, 12));
	 HB113.setSpacing(25);

	BP1.setPadding(new Insets(10,10,10,10));

	SuccessMsg.setFont(Font.font("Verdana",FontWeight.BOLD,15));
	SuccessMsg.setTextFill(Color.GREEN);
	SuccessMsg.setVisible(false);

	Label WelcomeTitle1=new Label("Add a New Event to File");
	WelcomeTitle1.setFont(Font.font("Times New Roman", FontWeight.BOLD,FontPosture.REGULAR, 20));
	WelcomeTitle1.setTextFill(Color.RED);

	Label Heading1=new Label("Please Fill all the Fields :");
	Heading1.setFont(Font.font("Verdana",FontWeight.BOLD,15));

	Label EName=new Label("Event Name  :");
	TextField ENameF=new TextField();
	ENameF.setPrefWidth(200);
	ENameF.setPromptText("Event Name");
	ENameF.setOnMouseClicked(e->SuccessMsg.setVisible(false));


	//type
	Label EType=new Label("  Event Type :");
	ComboBox<String> ETypeF = new ComboBox<String>();
	ETypeF.getItems().addAll("TECHNICAL","CULTURAL","CONFERENCE");
	//ETypeF.setPromptText("EventType");
	ETypeF.setEditable(false);
	ETypeF.setPrefWidth(200);

	DatePicker EDateF = new DatePicker();
	Label EDateFr=new Label("Event Date(Fr):");
	EDateF.setPrefWidth(200);
	EDateF.setOnMouseClicked(e->SuccessMsg.setVisible(false));

	Label EDateTo=new Label("Event Date(To):");
	DatePicker EDateT = new DatePicker();
	EDateT.setPrefWidth(200);
	EDateT.setOnMouseClicked(e->SuccessMsg.setVisible(false));

	Label ETimeFr=new Label("Time (Fr) :");
	Label ETimeTo=new Label("Time (To) :");

	TextField ETimeS=new TextField();
	TextField ETimeE=new TextField();
	ETimeE.setPrefWidth(200);
	ETimeE.setPromptText("HH:MM AM/PM");
	ETimeE.setOnMouseClicked(e->SuccessMsg.setVisible(false));
	ETimeS.setPrefWidth(200);
	ETimeS.setPromptText("HH:MM AM/PM");
	ETimeS.setOnMouseClicked(e->SuccessMsg.setVisible(false));

	Label ELoc=new Label("Event Location :");
	TextField ELocF=new TextField();
	ELocF.setPrefWidth(200);
	ELocF.setPromptText("Event Location");
	ELocF.setOnMouseClicked(e->SuccessMsg.setVisible(false));

	/*---------------------------------------------------*/


	Button Save = new Button("Add a New Event");
	Save.setTextFill(Color.BLUEVIOLET);

	Save.setOnAction(e->{

		if((ENameF.getLength()>0)&&(ETypeF.getWidth()>0)&&(EDateF.getWidth()>0)&&(EDateT.getWidth()>0)&&(ETimeS.getLength()>0)&&(ETimeE.getLength()>0)&&(ELocF.getLength()>0))
		{
		Temp=ENameF.getText();
		if(Temp.length()>=2)
		{Temp=ETypeF.getPromptText();
		if(Temp!=null)
		{
		Temp=ENameF.getText()+"#"+ETypeF.getValue()+"#"+EDateF.getValue()+"#"+EDateT.getValue()+"#"+ETimeS.getText()+"#"+ETimeE.getText()+"#"+ELocF.getText();
		if(DuplicateChecker(Temp)==0)
		{
		confirmation=AddtoFile(Temp);
		if(confirmation==1)
		{
			SuccessMsg.setText("Successfull");
			SuccessMsg.setTextFill(Color.GREEN);
			SuccessMsg.setVisible(true);
			ENameF.setText("");
			ETypeF.setPromptText("");
			EDateF.setPromptText("");
			EDateT.setPromptText("");
			ETimeS.setText("");
			ETimeE.setText("");
			ELocF.setText("");
		}
		else
			System.out.println("ERROR - Adding to the File..!");
		}
	}else
	{
		SuccessMsg.setText("Select Event Type");
		SuccessMsg.setTextFill(Color.RED);
		SuccessMsg.setVisible(true);
	}
		}
		else
		{
			SuccessMsg.setText("Please enter event name");
			SuccessMsg.setTextFill(Color.RED);
			SuccessMsg.setVisible(true);
		}


		}
	else
	{
		SuccessMsg.setText("Add All the Details First..!!!");
		SuccessMsg.setTextFill(Color.RED);
		SuccessMsg.setVisible(true);
		}
	});

	Button Cancel=new Button("Cancel");
	Cancel.setTextFill(Color.BLUE);

	Cancel.setOnAction(e->{
		Reset();
		MainWindow.setScene(Main);
	});

	HB1.getChildren().addAll(WelcomeTitle1);
	HB12.getChildren().addAll(Heading1);
	HB111.getChildren().addAll(EName,ENameF,EType,ETypeF);
	HB112.getChildren().addAll(EDateFr,EDateF,ETimeFr,ETimeS);
	HB113.getChildren().addAll(EDateTo,EDateT,ETimeTo,ETimeE);
	HB121.getChildren().addAll(ELoc,ELocF);
	HB11.getChildren().addAll(Save,Cancel,SuccessMsg);
	HB11.setSpacing(100);

	BP1.add(HB1,0,1);
	BP1.add(HB12,0,2);
	BP1.add(HB111,0,3);
	BP1.add(HB112,0,4);
	BP1.add(HB113,0,5);
	BP1.add(HB121,0,6);
	BP1.add(HB11,0,7);

	AddEvent=new Scene(BP1,800,550);
/*--------------------------------------------------------------------------------*/
	//screen to search

	GridPane BP2=new GridPane();
	HBox HB2=new HBox();
	HBox HB22=new HBox();
	HBox HB21=new HBox();
	HBox HB23= new HBox();
	 HB2.setPadding(new Insets(25, 25, 15, 12));
	 HB2.setSpacing(10);
	 HBox HB24 =new HBox();
	 HB22.setPadding(new Insets(25, 25, 15, 12));
	 HB22.setSpacing(10);
	 HB21.setPadding(new Insets(25, 25, 15, 12));
	 HB21.setSpacing(10);
	 HB23.setPadding(new Insets(55, 45, 25, 280));
	 HB23.setSpacing(80);
	 HB24.setPadding(new Insets(35, 45, 25, 100));
	 HB24.setSpacing(80);

		SuccessMsg.setFont(Font.font("Verdana",FontWeight.BOLD,15));
		SuccessMsg.setTextFill(Color.GREEN);
		SuccessMsg.setVisible(false);

		Label WelcomeTitle2=new Label("Search for a Event in File");
		WelcomeTitle2.setFont(Font.font("Times New Roman", FontWeight.BOLD,FontPosture.REGULAR, 20));
		WelcomeTitle2.setTextFill(Color.CADETBLUE);

		Label Heading2=new Label("Enter the event name to search :");
		Heading2.setFont(Font.font("Verdana",FontWeight.BOLD,15));

		Label ESearch=new Label("Event Name :");
		TextField ESearchF=new TextField();
		ESearchF.setPrefWidth(200);
		ESearchF.setPromptText("Enter Proper Event Name");
		ESearchF.setOnMouseClicked(e->SuccessMsg.setVisible(false));


		DatePicker ESearchFD = new DatePicker();
		Label ESearchD=new Label(" or  Event Date :");
		ESearchFD.setPrefWidth(200);
		ESearchFD.setOnMouseClicked(e->SuccessMsg.setVisible(false));

		Label ESearchL=new Label("Event Location :");
		TextField ESearchL1=new TextField();
		ESearchL1.setPrefWidth(200);
		ESearchL1.setPromptText("Location");
		ESearchL1.setOnMouseClicked(e->SuccessMsg.setVisible(false));

		Button Search = new Button("Search");
		Search.setTextFill(Color.BLUEVIOLET);
		Search.setOnAction(e->{
			if(ESearchF.getText().length()>=3)
			{
				tableView.getItems().clear();
				SearchFile(ESearchF.getText());
				MainWindow.setScene(ShowAll);
			}
			else if(ESearchL1.getText().length()>=3)
			{
				tableView.getItems().clear();
				SearchFile(ESearchL1.getText());
				MainWindow.setScene(ShowAll);
			}

			else if(ESearchFD.getValue() != null)
			{
				String date=ESearchFD.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				tableView.getItems().clear();
				SearchFile(date);
				MainWindow.setScene(ShowAll);

			}
			else if(ESearchF.getText().length()>=1)
			{
				ESearchF.setPromptText("Too Short to Search...!!");
				msgs.setText("Too Short to Search...!");
				msgs.setVisible(true);
			}
			else
			{
				ESearchF.setPromptText("Enter Something Here First...!");
				msgs.setText("Enter Something to Search...!");
				msgs.setVisible(true);
			}
		});
		Button Cancel1=new Button("Cancel");
		Cancel.setTextFill(Color.GREEN);



		Cancel1.setOnAction(e->{
			Reset();
			MainWindow.setScene(Main);
		});

		HB2.getChildren().addAll(WelcomeTitle2);
		HB22.getChildren().addAll(Heading2);
		HB21.getChildren().addAll(ESearch,ESearchF,ESearchD,ESearchFD);
		HB24.getChildren().addAll(ESearchL,ESearchL1);
		HB23.getChildren().addAll(Search,Cancel1);
		BP2.add(HB21, 0, 3);
		BP2.add(HB22, 0, 2);
		BP2.add(HB2, 0, 1);
		BP2.add(HB24, 0, 4);
		BP2.add(HB23, 0, 6);
		SearchEvent=new Scene(BP2,800,550);
	}

}
