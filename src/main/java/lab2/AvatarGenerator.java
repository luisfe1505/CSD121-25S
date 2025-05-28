package lab2;//define where this package belongs

import javax.imageio.ImageIO;//Provides classes for reading and writing images.
import javax.swing.*;//to import graphical user applications, like JFrame and , ImageIcon
import java.awt.*;//Provide classes for creating users interfaces and painting  graphics and images.(Image,Color, BorderLayout)
import java.io.IOException;// To say to the user that input/output error has occurred
import java.io.InputStream;//Represent input stream of bytes.
import java.net.URI;//Creates a new URI object representing the web address used to request avatar.
import java.net.http.HttpClient;//To send HTTP requests and retrieve their responses.
import java.net.http.HttpRequest;//To initiate a communication with a server.
import java.net.http.HttpResponse;//Returned as a result of sending HttpRequest.

public class AvatarGenerator {//Creates a public class named "AvatarGenerator"

    public static void main(String[] args) { //The starting point of the program

        try { //Here I am creating an exception for avatarGenerator and ShowAvatar
            var avatarStream = AvatarGenerator.getRandomAvatarStream();//I'm declaring the avatarStream (type InputStream), and store the resulting stream in avatarStream so I can later read and display the image
            AvatarGenerator.showAvatar(avatarStream);// Here the showAvatar is called as a helper, giving it the avatarStream that was get from the API. (reads the image data from the stream, places it in a label, and shows the window so can see random avatar.)
        } catch (IOException | InterruptedException e) {//Here is telling that if either of these exceptions is throw in the preceding, give to the user these errors
            e.printStackTrace();//If something goes wrong, the console is going to print where is wrong in the code or in the libraries.
        }

    }

    public static InputStream getRandomAvatarStream() throws IOException, InterruptedException {//getRandomAvatarStream( class method to build and send a HTTP request)
        //InputStream containing the PNG avatar data, IOexception is to read the image fails and the InterruptedException if the HTTP calls is interrupted.
        // Pick a random style
        String[] styles = { "adventurer", "adventurer-neutral", "avataaars", "big-ears", "big-ears-neutral", "big-smile", "bottts", "croodles", "croodles-neutral", "fun-emoji", "icons", "identicon", "initials", "lorelei", "micah", "miniavs", "open-peeps", "personas", "pixel-art", "pixel-art-neutral" };//array storing different avatar style options
        var style = styles[(int)(Math.random() * styles.length)];//Math.random is a class method returning primitive dobule
//styles.length is an instance variable
//the convert to int and array index gives a random index
        //

        // Generate a random seed
        var seed = (int)(Math.random() * 10000);//generates a int random seed

        // Create an HTTP request for a random avatar
        var uri = URI.create("https://api.dicebear.com/9.x/%s/png?seed=%d".formatted(style, seed));// is a class method returning a URI
       //.formatted is an instance method returning a string

        var request = HttpRequest.newBuilder(uri).build(); //newBuilder is a class method returning hhtprequest
//build is an instance method returning HttpRequest

        // Send the request
        try (var client = HttpClient.newHttpClient()) {//HttpClient will be closed automatically
            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());//instance method sends the request and returns the reference
            //class method returns a BodyHandlers
            return response.body();//instance method returns the reference of the response
        }
    }

    public static void showAvatar(InputStream imageStream) {//displays the avatar image and the inputstream contain a image
            JFrame frame = new JFrame("PNG Viewer");//creates a Jframe object (reference)
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//instancemethod "exitonclose" is a static primitive int constant
            frame.setResizable(false);//instance method with boolean argument
            frame.setSize(200, 200);//instance method with two int arguments (width, height)
            frame.getContentPane().setBackground(Color.BLACK);//instance method returns a container and setbackground is an instance method that define the color

            try {
                // Load the PNG image
                Image image = ImageIO.read(imageStream);//class method reads the input and returns an image

                // Create a JLabel to display the image
                JLabel imageLabel = new JLabel(new ImageIcon(image));//wraps the icon in a JLabel
                frame.add(imageLabel, BorderLayout.CENTER);//instance method adds the component
                //BorderLayout is a static layout constant

            } catch (IOException e) {
                e.printStackTrace();
            }//instance method prints any input or output error

            frame.setVisible(true);// instance method; primitive boolean true shows the window on screen
    }
}
