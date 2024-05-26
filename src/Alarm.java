public class Alarm {
    private String time;
    private String message;
    private static final String SOUND_FILE = "alarm.wav";

    public Alarm(String time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public static String getSoundFile() {
        return SOUND_FILE;
    }
}