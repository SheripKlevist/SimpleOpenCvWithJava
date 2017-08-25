import com.sherip.camera.Camera;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import  javafx.scene.image.ImageView;
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

public class VideoApp extends Application{

    private ImageView viewPoint=new ImageView();
    private boolean capturing=false;

    public static void main(String args[])
    {
            launch(args);
    }

    public void start(Stage dStage)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        GUI().show();
    }

    private Stage GUI(){
        Stage window=new Stage(StageStyle.DECORATED);
        window.setTitle("JumboCamera");
        HBox mainBox=new HBox() ;
        mainBox.setMinWidth(500);
        mainBox.setMinHeight(450);
        mainBox.setAlignment(Pos.CENTER);

        HBox.setHgrow(viewPoint, Priority.ALWAYS);
        VBox.setVgrow(viewPoint,Priority.ALWAYS);
        viewPoint.setPreserveRatio(true);
        viewPoint.setSmooth(true);

        VBox leftBox=new VBox(5) ;
        leftBox.getChildren().add(viewPoint);
        mainBox.getChildren().add(leftBox);

        FlowPane flowPane=new FlowPane(5,5);
        mainBox.getChildren().add(flowPane);

        VideoCapture cam=Camera.getDefault();
        viewPoint.setImage(Camera.getImage(cam));
        Scene mainScene=new Scene(mainBox, Paint.valueOf("crimson"));
        window.setScene(mainScene);

        window.setOnCloseRequest(event -> {
            capturing=false;
            cam.release();
            System.exit(0);
        });

        Thread p=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!capturing){
                    viewPoint.setImage(Camera.getImage(cam));
                }
            }
        });
        p.setDaemon(true);
        p.setPriority(9);
        p.setName("cameraRunning");
        p.start();


        return window;
    }

}
