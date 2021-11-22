public class ButtonInstance {
    public int posX;
    public int posY;
    public int width;
    public int height;
    public int red;
    public int green;
    public int blue;
    public String title;

    public ButtonInstance(int posX, int posY, int width, int height, String title, int red, int green, int blue){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.title = title;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public ButtonInstance(int posX, int posY, int width, int height, String title){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.title = title;
    }
}
