
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class Camera {

    public static VideoCapture getDefault(){

        return new VideoCapture(0);
    }

    public static void saveImage(Mat matrix){

        Date date=new Date() ;
        String lDate=new SimpleDateFormat("dd_MM_YYYY@HH_mm_ss").format(date);

        File gp=new File(".\\IMAGES") ;
        boolean t=gp.mkdirs();
        System.out.println("Folder created : "+t);

        String file="IMAGES\\IMG "+lDate+".jpg";
        imwrite(file,matrix);

    }

    public static void saveImage(Mat matrix,String FolderPath){
        Date date=new Date() ;
        String lDate=new SimpleDateFormat("dd_MM_YYYY@HH_mm_ss").format(date);
        String file=FolderPath+"\\IMG"+lDate+".jpg";
        imwrite(file,matrix);
    }

    public static void saveImage(Mat matrix,String FolderName,String FileType){
        Date date=new Date() ;
        String lDate=new SimpleDateFormat("dd_MM_YYYY@HH_mm_ss").format(date);
        String file=FolderName+"\\IMG"+lDate+"."+FileType;
        imwrite(file,matrix);
    }

    public static WritableImage getImage(VideoCapture cam){

        WritableImage writableImage;
        Mat matrix =new Mat() ;

        cam.read(matrix);
        BufferedImage bufImage=new BufferedImage(
                matrix.width(),matrix.height(),BufferedImage.TYPE_3BYTE_BGR
        ) ;

        WritableRaster raster =bufImage.getRaster();
        DataBufferByte dataBuffer=(DataBufferByte) raster.getDataBuffer();
        byte[] data=dataBuffer.getData();

        matrix.get(0,0,data);

        writableImage= SwingFXUtils.toFXImage(bufImage,null);

        return writableImage;
    }

}
