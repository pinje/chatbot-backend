package sem3chatbot.backend.business.ServiceImpl;


public class Logger {
    private Logger(){}

    protected static void print(String text, Object... args) {System.out.printf((text) + "%n", args);}
}
